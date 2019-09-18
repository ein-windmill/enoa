package io.enoa.toolkit.is;

import java.util.Collection;
import java.util.Map;

public class Is {

  private static IsJava is() {
    return IsJava.instance();
  }

  public static IsNotJava not() {
    return is().not();
  }

  public static IsAnyJava any() {
    return is().any();
  }

  public static IsAllJava all() {
    return is().all();
  }

  public static boolean string(Object obj) {
    return is().string(obj);
  }

  public static boolean number(String str) {
    return is().number(str);
  }

  public static boolean digit(String str) {
    return is().digit(str);
  }

  public static boolean negative(String str) {
    return is().negative(str);
  }

  public static boolean negative(Number number) {
    return is().negative(number);
  }

  public static boolean zero(String str) {
    return is().zero(str);
  }

  public static boolean zero(Number number) {
    return is().zero(number);
  }

  public static boolean nullx(Object object) {
    return is().nullx(object);
  }

  public static boolean blank(String str) {
    return is().blank(str);
  }

  public static boolean array(Object object) {
    return is().array(object);
  }

  public static boolean empty(Collection collection) {
    return is().empty(collection);
  }

  public static boolean empty(Object[] arrs) {
    return is().empty(arrs);
  }

  public static boolean empty(byte[] arrs) {
    return is().empty(arrs);
  }

  public static boolean empty(int[] arrs) {
    return is().empty(arrs);
  }

  public static boolean empty(long[] arrs) {
    return is().empty(arrs);
  }

  public static boolean empty(short[] arrs) {
    return is().empty(arrs);
  }

  public static boolean empty(String[] arrs) {
    return is().empty(arrs);
  }

  public static boolean empty(Integer[] arrs) {
    return is().empty(arrs);
  }

  public static boolean empty(Double[] arrs) {
    return is().empty(arrs);
  }

  public static boolean empty(Short[] arrs) {
    return is().empty(arrs);
  }

  public static boolean empty(Map map) {
    return is().empty(map);
  }

  public static boolean equal(Object obj0, Object obj1) {
    return is().equal(obj0, obj1);
  }

  public static boolean truthy(String str) {
    return is().truthy(str);
  }

  public static boolean sameType(Object ob0, Object obj1) {
    return is().sameType(ob0, obj1);
  }

  public static boolean include(String large, String small) {
    return is().include(large, small);
  }

  public static boolean space(String text) {
    return is().space(text);
  }

  public static boolean alphanumeric(String text) {
    return is().alphanumeric(text);
  }

  public static boolean email(String text) {
    return is().email(text);
  }

  public static boolean startWith(String text, String with) {
    return is().startWith(text, with);
  }

  public static boolean endWith(String text, String with) {
    return is().endWith(text, with);
  }

  public static boolean even(String num) {
    return is().even(num);
  }

  public static boolean even(Number num) {
    return is().even(num);
  }

  public static boolean odd(String num) {
    return is().odd(num);
  }

  public static boolean odd(Number num) {
    return is().odd(num);
  }

  public static boolean in(Object[] arrs, Object item) {
    return is().in(arrs, item);
  }

  public static boolean in(byte[] arrs, Object item) {
    return is().in(arrs, item);
  }

  public static boolean in(int[] arrs, Object item) {
    return is().in(arrs, item);
  }

  public static boolean in(long[] arrs, Object item) {
    return is().in(arrs, item);
  }

  public static boolean in(short[] arrs, Object item) {
    return is().in(arrs, item);
  }

  public static boolean in(Byte[] arrs, Object item) {
    return is().in(arrs, item);
  }

  public static boolean in(Integer[] arrs, Object item) {
    return is().in(arrs, item);
  }

  public static boolean in(Long[] arrs, Object item) {
    return is().in(arrs, item);
  }

  public static boolean in(Short[] arrs, Object item) {
    return is().in(arrs, item);
  }

  public static boolean in(Collection collection, Object item) {
    return is().in(collection, item);
  }

  public static boolean regex(String text, String regex) {
    return is().regex(text, regex);
  }
}
