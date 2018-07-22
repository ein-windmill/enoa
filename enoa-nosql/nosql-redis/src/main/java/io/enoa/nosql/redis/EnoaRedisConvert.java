/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.enoa.nosql.redis;

import io.enoa.serialization.Serializer;
import redis.clients.jedis.ScanResult;
import redis.clients.util.JedisByteHashMap;
import redis.clients.util.SafeEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EnoaRedisConvert {

  private static Map<Serializer, EnoaRedisConvert> CACHE = new HashMap<>();

  private Serializer serializer;

  public static EnoaRedisConvert with(Serializer serializer) {
    EnoaRedisConvert convert = CACHE.get(serializer);
    if (convert != null)
      return convert;

    convert = new EnoaRedisConvert();
    convert.serializer = serializer;
    CACHE.put(serializer, convert);
    return convert;
  }

  /**
   * 将 key 转换为 byte 数组
   *
   * @param keys keys
   * @return byte[][]
   */
  public byte[][] toBytesKeys(String... keys) {
    byte[][] result = new byte[keys.length][];
    for (int i = result.length; i-- > 0; )
      result[i] = SafeEncoder.encode(keys[i]);
    return result;
  }

  /**
   * 转换值为 byte 数组
   *
   * @param values 值
   * @param <T>    values type
   * @return byte[][]
   */
  public <T> byte[][] toBytesValues(T... values) {
    byte[][] result = new byte[values.length][];
    for (int i = result.length; i-- > 0; )
      result[i] = this.serializer.serialize(values[i]);
    return result;
  }

  public Map<byte[], Double> toBytesZmap(Map<String, Double> data) {
    try {
      Map<byte[], Double> ret = new HashMap<>();
      data.forEach((k, v) -> ret.put(SafeEncoder.encode(k), v));
      return ret;
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * 转换 redis 返回 list 为目标类型数据
   *
   * @param data redis return data
   * @param <T>  data type
   * @return List
   */
  public <T> List<T> convertList(List<byte[]> data) {
    List<T> rets = data.stream().map(b -> this.serializer.<T>reduction(b)).collect(Collectors.toList());
    data.clear();
    return rets;
  }

  /**
   * 转换 redis 返回 set 为目标类型数据
   *
   * @param data redis return data
   * @param <T>  data type
   * @return Set
   */
  public <T> Set<T> convertSet(Set<byte[]> data) {
    Set<T> rets = data.stream().map(s -> this.serializer.<T>reduction(s)).collect(Collectors.toSet());
    data.clear();
    return rets;
  }

  /**
   * 转换 redis 返回 ScanResult 为目标类型数据
   *
   * @param data redis return data
   * @param <T>  data type
   * @return ScanResult
   */
  public <T> ScanResult<T> convertScan(ScanResult<byte[]> data) {
    List<byte[]> rets0 = data.getResult();
    List<T> rets1 = EnoaRedisConvert.with(serializer).convertList(rets0);
    rets0.clear();
    return new ScanResult<>(data.getCursorAsBytes(), rets1);
  }

  /**
   * 转换 redis 返回 Map.Entry 为目标类型数据
   *
   * @param data redis return data
   * @param <T>  data type
   * @return Map.Entry
   */
  public <T> Map.Entry<String, T> convertEntry(Map.Entry<byte[], byte[]> data) {
    return new Map.Entry<String, T>() {
      @Override
      public String getKey() {
        return SafeEncoder.encode(data.getKey());
      }

      @Override
      public T getValue() {
        return serializer.reduction(data.getValue());
      }

      @Override
      public T setValue(T value) {
        T oldVal = this.getValue();
        data.setValue(serializer.serialize(value));
        return oldVal;
      }
    };
  }

  /**
   * 转换 redis 返回 map 为目标类型数据
   *
   * @param data redis return data
   * @param <T>  data type
   * @return Map
   */
  public <T> Map<String, T> convertMap(Map<byte[], byte[]> data) {
    if (data == null)
      return null;
    try {
      Map<String, T> ret = new HashMap<>();
      data.forEach((k, v) -> ret.put(SafeEncoder.encode(k), this.serializer.reduction(v)));
      data.clear();
      return ret;
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * 序列化 map , 转换为 jedis 识别 map
   *
   * @param map map
   * @return Map
   */
  public Map<byte[], byte[]> serializeMap(Map map) {
    try {
      Map<byte[], byte[]> ret = new JedisByteHashMap();
      map.forEach((k, v) -> ret.put(SafeEncoder.encode(k.toString()), this.serializer.serialize(v)));
      return ret;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
