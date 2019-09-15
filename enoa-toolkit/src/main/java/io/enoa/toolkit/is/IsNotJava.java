package io.enoa.toolkit.is;

import java.util.Collection;
import java.util.Map;

public class IsNotJava implements EoIs {

  private EoIs is;

  public IsNotJava(EoIs is) {
    this.is = is;
  }

  @Override
  public boolean string(Object obj) {
    return !this.is.string(obj);
  }

  @Override
  public boolean number(String str) {
    return !this.is.number(str);
  }

  @Override
  public boolean digit(String str) {
    return !this.is.digit(str);
  }

  @Override
  public boolean negative(String str) {
    return !this.is.negative(str);
  }

  @Override
  public boolean negative(Number number) {
    return !this.is.negative(number);
  }

  @Override
  public boolean zero(String str) {
    return !this.is.zero(str);
  }

  @Override
  public boolean zero(Number number) {
    return !this.is.zero(number);
  }

  @Override
  public boolean nullx(Object object) {
    return !this.is.nullx(object);
  }

  @Override
  public boolean blank(String str) {
    return !this.is.blank(str);
  }

  @Override
  public boolean empty(Collection collection) {
    return !this.is.empty(collection);
  }

  @Override
  public boolean empty(Map map) {
    return !this.is.empty(map);
  }

  @Override
  public boolean empty(Object[] arrs) {
    return !this.is.empty(arrs);
  }

  @Override
  public boolean empty(byte[] arrs) {
    return !this.is.empty(arrs);
  }

  @Override
  public boolean empty(int[] arrs) {
    return !this.is.empty(arrs);
  }

  @Override
  public boolean empty(long[] arrs) {
    return !this.is.empty(arrs);
  }

  @Override
  public boolean empty(short[] arrs) {
    return !this.is.empty(arrs);
  }

  @Override
  public boolean empty(String[] arrs) {
    return !this.is.empty(arrs);
  }

  @Override
  public boolean empty(Integer[] arrs) {
    return !this.is.empty(arrs);
  }

  @Override
  public boolean empty(Double[] arrs) {
    return !this.is.empty(arrs);
  }

  @Override
  public boolean empty(Short[] arrs) {
    return !this.is.empty(arrs);
  }

  @Override
  public boolean equal(Object obj0, Object obj1) {
    return !this.is.equal(obj0, obj1);
  }

  @Override
  public boolean truthy(String str) {
    return !this.is.truthy(str);
  }

  @Override
  public boolean sameType(Object ob0, Object obj1) {
    return !this.is.sameType(ob0, obj1);
  }

  @Override
  public boolean include(String large, String small) {
    return !this.is.include(large, small);
  }

  @Override
  public boolean space(String text) {
    return !this.is.space(text);
  }

  @Override
  public boolean alphanumeric(String text) {
    return !this.is.alphanumeric(text);
  }

  @Override
  public boolean email(String text) {
    return !this.is.email(text);
  }

  @Override
  public boolean startWith(String text, String with) {
    return !this.is.startWith(text, with);
  }

  @Override
  public boolean endWith(String text, String with) {
    return !this.is.endWith(text, with);
  }

  @Override
  public boolean even(String num) {
    return !this.is.even(num);
  }

  @Override
  public boolean even(Number num) {
    return !this.is.even(num);
  }

  @Override
  public boolean odd(String num) {
    return !this.is.odd(num);
  }

  @Override
  public boolean odd(Number num) {
    return !this.is.odd(num);
  }

  @Override
  public boolean in(Object[] arrs, Object item) {
    return !this.is.in(arrs, item);
  }

  @Override
  public boolean in(byte[] arrs, Object item) {
    return !this.is.in(arrs, item);
  }

  @Override
  public boolean in(int[] arrs, Object item) {
    return !this.is.in(arrs, item);
  }

  @Override
  public boolean in(long[] arrs, Object item) {
    return !this.is.in(arrs, item);
  }

  @Override
  public boolean in(short[] arrs, Object item) {
    return !this.is.in(arrs, item);
  }

  @Override
  public boolean in(Byte[] arrs, Object item) {
    return !this.is.in(arrs, item);
  }

  @Override
  public boolean in(Integer[] arrs, Object item) {
    return !this.is.in(arrs, item);
  }

  @Override
  public boolean in(Long[] arrs, Object item) {
    return !this.is.in(arrs, item);
  }

  @Override
  public boolean in(Short[] arrs, Object item) {
    return !this.is.in(arrs, item);
  }

  @Override
  public boolean in(Collection collection, Object item) {
    return !this.is.in(collection, item);
  }

  @Override
  public boolean regex(String text, String regex) {
    return !this.is.regex(text, regex);
  }


}
