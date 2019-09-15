package io.enoa.toolkit.is;

import java.util.Collection;
import java.util.Map;

interface EoIs {

  boolean string(Object obj);

  boolean number(String str);

  boolean digit(String str);

  boolean negative(String str);

  boolean negative(Number number);

  boolean zero(String str);

  boolean zero(Number number);

  boolean nullx(Object object);

  boolean blank(String str);

  boolean empty(Collection collection);

  boolean empty(Map map);

  boolean empty(Object[] arrs);

  boolean empty(byte[] arrs);

  boolean empty(int[] arrs);

  boolean empty(long[] arrs);

  boolean empty(short[] arrs);

  boolean empty(String[] arrs);

  boolean empty(Integer[] arrs);

  boolean empty(Double[] arrs);

  boolean empty(Short[] arrs);

  boolean equal(Object obj0, Object obj1);

  boolean truthy(String str);

  boolean sameType(Object ob0, Object obj1);

  boolean include(String large, String small);

  boolean space(String text);

  boolean alphanumeric(String text);

  boolean email(String text);

  boolean startWith(String text, String with);

  boolean endWith(String text, String with);

  boolean even(String num);

  boolean even(Number num);

  boolean odd(String num);

  boolean odd(Number num);

  boolean in(Object[] arrs, Object item);

  boolean in(byte[] arrs, Object item);

  boolean in(int[] arrs, Object item);

  boolean in(long[] arrs, Object item);

  boolean in(short[] arrs, Object item);

  boolean in(Byte[] arrs, Object item);

  boolean in(Integer[] arrs, Object item);

  boolean in(Long[] arrs, Object item);

  boolean in(Short[] arrs, Object item);

  boolean in(Collection collection, Object item);

  boolean regex(String text, String regex);

}
