/*
 * Copyright (c) 2018, enoa (fewensa@enoa.io)
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
package io.enoa.toolkit.collection;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.thr.EoException;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by ein on 2017/8/13.
 */
public class CollectionKit {


  private static final byte[] EMPTY_BYTES = new byte[0];
  private static final int[] EMPTY_INTS = new int[0];
  private static Map<String, GenericEmptyArray> ARRAY_CACHE = new HashMap<>();

  private static class GenericEmptyArray<T> {
    private T[] array;

    GenericEmptyArray(Class<T> clazz) {
      this.array = (T[]) Array.newInstance(clazz, 0);
    }

    T[] array() {
      return this.array;
    }
  }

  public static <T> T[] emptyArray(Class<T> clazz) {
    String name = clazz.getName();
    try {
      GenericEmptyArray cahr = ARRAY_CACHE.get(name);
      if (cahr == null) {
        cahr = new GenericEmptyArray<>(clazz);
        ARRAY_CACHE.put(name, cahr);
      }
      return (T[]) cahr.array();
    } catch (Exception e) {
      throw new EoException(e);
    }
  }

  private static byte[] emptyBytes() {
    return EMPTY_BYTES;
  }

  private static int[] emptyInts() {
    return EMPTY_INTS;
  }

//  public static <E> Boolean isEmpty(Collection<E> collection) {
//    return collection == null || collection.isEmpty();
//  }
//
//  public static <K, V> Boolean isEmpty(Map<K, V> map) {
//    return map == null || map.isEmpty();
//  }
//
//  public static Boolean isEmpty(Object[] objects) {
//    return objects == null || objects.length == 0;
//  }
//
//  public static Boolean isEmpty(byte[] objects) {
//    return objects == null || objects.length == 0;
//  }
//
//  public static Boolean isEmpty(int[] objects) {
//    return objects == null || objects.length == 0;
//  }
//
//  public static <E> Boolean notEmpty(Collection<E> collection) {
//    return !Is.empty(collection);
//  }
//
//  public static <K, V> Boolean notEmpty(Map<K, V> map) {
//    return !Is.empty(map);
//  }
//
//  public static Boolean notEmpty(Object[] objects) {
//    return !Is.empty(objects);
//  }
//
//  public static Boolean notEmpty(byte[] objects) {
//    return !Is.empty(objects);
//  }
//
//  public static Boolean notEmpty(int[] objects) {
//    return !Is.empty(objects);
//  }

  public static String[] distinct(String[] arrs) {
    return Arrays.stream(arrs).distinct().toArray(String[]::new);
  }

  public static Integer[] distinct(Integer[] arrs) {
    return Arrays.stream(arrs).distinct().toArray(Integer[]::new);
  }

  public static String[] concat(String[] first, String[] sec) {
    String[] res = new String[first.length + sec.length];
    System.arraycopy(first, 0, res, 0, first.length);
    System.arraycopy(sec, 0, res, first.length, sec.length);
    return res;
  }

  public static byte[] merge(byte[] bytes0, byte[] bytes1) {
    if (Is.empty(bytes0))
      return Is.empty(bytes1) ? emptyBytes() : bytes1;
    if (Is.empty(bytes1))
      return Is.empty(bytes0) ? emptyBytes() : bytes0;
    byte[] rets = new byte[bytes0.length + bytes1.length];
    System.arraycopy(bytes0, 0, rets, 0, bytes0.length);
    System.arraycopy(bytes1, 0, rets, bytes0.length, bytes1.length);
//    clear(bytes0, bytes1);
    return rets;
  }

  public static void clear(Collection... collections) {
    for (Collection collection : collections)
      if (collection != null)
        collection.clear();
  }

  public static void clear(Map... maps) {
    for (Map map : maps)
      if (map != null)
        map.clear();
  }

  public static void clear(Object[]... arrays) {
    for (Object[] array : arrays)
      if (array != null)
        Arrays.fill(array, null);
  }

//  public static void clear(byte[]... arrays) {
//    for (byte[] array : arrays)
//      if (array != null)
//        Arrays.fill(array, (byte) 0);
//  }
//
//  public static void clear(int[]... arrays) {
//    for (int[] array : arrays)
//      if (array != null)
//        Arrays.fill(array, 0);
//  }

  public static boolean isArray(Object object) {
    return object != null && object.getClass().isArray();
  }

  public static <T> T[] array(Object object) {
    List<T> rets = new ArrayList<>();

    if (object == null)
      return (T[]) rets.toArray();

    if (!isArray(object))
      return (T[]) rets.toArray();

    int len = Array.getLength(object);
    for (int i = 0; i < len; ++i) {
      rets.add((T) Array.get(object, i));
    }
    return (T[]) rets.toArray();
  }

  public static boolean contains(Object[] arr, Object target) {
    if (arr == null || target == null)
      return false;
    for (Object o : arr) {
      if (o.equals(target))
        return true;
    }
    return false;
  }

  public static boolean contains(Collection colls, Object target) {
    if (colls == null || target == null)
      return false;
    return colls.stream().anyMatch(c -> c.equals(target));
  }

  /**
   * 按照指定数量对 collection 进行切割
   *
   * @param colls collections
   * @param size  切割后每份数量
   * @param <T>   T
   * @return List
   */
  public static <T> List<List<T>> split(Collection<T> colls, int size) {
    if (Is.empty(colls))
      return Collections.emptyList();

    List<List<T>> rets = new ArrayList<>();
    List<T> items = new ArrayList<>();
    for (T coll : colls) {
      items.add(coll);
      if (size == items.size()) {
        rets.add(items);
        items = new ArrayList<>();
      }
    }
    if (0 != items.size())
      rets.add(items);
    return rets;
  }

  /**
   * 等份切割, 依据原始数组按照对分原则进行等份切割
   *
   * @param colls collections
   * @param parts 等份数量
   * @param <T>   T
   * @return List
   */
  public static <T> List<List<T>> parts(Collection<T> colls, int parts) {
    if (Is.empty(colls))
      return Collections.emptyList();
    if (parts == 0)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.collection_parts_num_zero"));
    if (colls.size() <= parts)
      return new ArrayList<List<T>>() {{
        add(new ArrayList<>(colls));
      }};

    List<List<T>> rets = new ArrayList<>();
    List<T> items = new ArrayList<>();
    int pnum = colls.size() / parts;
    for (T coll : colls) {
      items.add(coll);
      if (items.size() == pnum) {
        rets.add(items);
        // 无法被整除的全部轧入到最后一个集合中
        if (rets.size() < parts)
          items = new ArrayList<>();
      }
    }
    return rets;
  }
}
