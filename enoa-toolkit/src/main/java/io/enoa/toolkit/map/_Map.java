package io.enoa.toolkit.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

interface _Map extends Map<String, Object> {


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



}
