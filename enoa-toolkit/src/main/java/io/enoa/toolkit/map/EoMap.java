package io.enoa.toolkit.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface EoMap<S extends Map> extends Map<String, Object>, FastKv {


  Map<String, Object> map();

  default Object put(String key, Object value) {
    return this.map().put(key, value);
  }

  default Object remove(Object key) {
    return this.map().remove(key);
  }

  default void putAll(Map<? extends String, ?> m) {
    this.map().putAll(m);
  }

  default void clear() {
    this.map().clear();
  }

  default Set<String> keySet() {
    return this.map().keySet();
  }

  default Collection<Object> values() {
    return this.map().values();
  }

  default Set<Entry<String, Object>> entrySet() {
    return this.map().entrySet();
  }

  default int size() {
    return this.map().size();
  }

  default boolean isEmpty() {
    return this.map().isEmpty();
  }

  default boolean containsKey(Object key) {
    return this.map().containsKey(key);
  }

  default boolean containsValue(Object value) {
    return this.map().containsValue(value);
  }

  @Override
  default Object origin(String key) {
    return this.get(key);
  }

  default S set(String key, Object value) {
    this.put(key, value);
    return (S) this;
  }

  default S set(Map<String, ?> map) {
    if (map == null)
      return (S) this;
    this.putAll(map);
    return (S) this;
  }

  default S delete(String key) {
    this.remove(key);
    return (S) this;
  }


  /**
   * 是否存在当前 key
   */
  default boolean exists(String key) {
    return this.containsKey(key);
  }

  /**
   * 值是否为 null
   *
   * @param key 键
   * @return boolean
   */
  default boolean nullValue(String key) {
    return this.get(key) == null;
  }

  /**
   * 值是否不为 null
   *
   * @param key 键
   * @return boolean
   */
  default boolean notNullValue(String key) {
    return !this.nullValue(key);
  }


}
