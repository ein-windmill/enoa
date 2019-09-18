package io.enoa.toolkit.is;

import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.text.TextKit;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class IsJava implements EoIs {

  private IsNotJava not;
  private IsAllJava all;
  private IsAnyJava any;

  private static class Holder {
    private static final IsJava INSTANCE = new IsJava();
  }

  public static IsJava instance() {
    return Holder.INSTANCE;
  }


  IsJava() {
    this.not = new IsNotJava(this);
    this.any = new IsAnyJava(this);
    this.all = new IsAllJava(this);
  }

  public IsNotJava not() {
    return this.not;
  }

  public IsAnyJava any() {
    return this.any;
  }

  public IsAllJava all() {
    return this.all;
  }

  @Override
  public boolean string(Object obj) {
    return obj instanceof String;
  }

  @Override
  public boolean number(String obj) {
    if (this.blank(obj))
      return false;
    boolean hasDot = false;
    for (int i = obj.length(); i-- > 0; ) {
      char at = obj.charAt(i);
      if (at == '-' && i == 0)
        continue;
      if (at == '.') {
        if (hasDot)
          return false;
        hasDot = true;
        continue;
      }
      if (Character.isDigit(at))
        continue;
      return false;
    }
    return true;
  }

  @Override
  public boolean digit(String str) {
    for (int i = str.length(); i-- > 0; ) {
      char at = str.charAt(i);
      if (at == '-' && i == 0)
        continue;
      if (Character.isDigit(at))
        continue;
      return false;
    }
    return true;
  }

  @Override
  public boolean negative(String str) {
    if (!this.number(str)) return false;
    return this.negative(ConvertKit.number(str));
  }

  @Override
  public boolean negative(Number number) {
    return number.floatValue() < 0;
  }

  @Override
  public boolean zero(String str) {
    if (!this.number(str)) return false;
    return this.zero(ConvertKit.number(str));
  }

  @Override
  public boolean zero(Number number) {
    return number.floatValue() == 0F;
  }

  @Override
  public boolean nullx(Object object) {
    return object == null;
  }

  @Override
  public boolean blank(String str) {
    return str == null || str.isEmpty();
  }

  @Override
  public boolean array(Object object) {
    return object != null && object.getClass().isArray();
  }

  @Override
  public boolean empty(Collection collection) {
    return collection == null || collection.isEmpty();
  }

  @Override
  public boolean empty(Map map) {
    return map == null || map.isEmpty();
  }

  @Override
  public boolean empty(Object[] arrs) {
    return arrs == null || arrs.length == 0;
  }

  @Override
  public boolean empty(byte[] arrs) {
    return arrs == null || arrs.length == 0;
  }

  @Override
  public boolean empty(int[] arrs) {
    return arrs == null || arrs.length == 0;
  }

  @Override
  public boolean empty(long[] arrs) {
    return arrs == null || arrs.length == 0;
  }

  @Override
  public boolean empty(short[] arrs) {
    return arrs == null || arrs.length == 0;
  }

  @Override
  public boolean empty(String[] arrs) {
    return arrs == null || arrs.length == 0;
  }

  @Override
  public boolean empty(Integer[] arrs) {
    return arrs == null || arrs.length == 0;
  }

  @Override
  public boolean empty(Double[] arrs) {
    return arrs == null || arrs.length == 0;
  }

  @Override
  public boolean empty(Short[] arrs) {
    return arrs == null || arrs.length == 0;
  }

  @Override
  public boolean equal(Object obj0, Object obj1) {
    return Objects.equals(obj0, obj1);
  }

  @Override
  public boolean truthy(String str) {
    if (this.blank(str))
      return false;
    return !TextKit.nospace(str).isEmpty();
  }

  @Override
  public boolean sameType(Object ob0, Object obj1) {
    Class<?> cz0 = ob0.getClass();
    Class<?> cz1 = obj1.getClass();
    return cz0.isAssignableFrom(cz1) ||
      cz1.isAssignableFrom(cz0);
  }

  @Override
  public boolean include(String large, String small) {
    if (this.blank(large) || this.blank(small))
      return false;
    return large.contains(small);
  }

  @Override
  public boolean space(String text) {
    if (this.nullx(text))
      return false;
    return TextKit.nospace(text, true).isEmpty();
  }

  @Override
  public boolean alphanumeric(String text) {
    if (this.nullx(text))
      return false;
    for (int i = text.length(); i-- > 0; ) {
      char c = text.charAt(i);
      if (
        (c >= 'a' && c <= 'z') ||
          (c >= 'A' && c <= 'Z') ||
          (c >= 48 && c <= 57)
      )
        continue;
      return false;
    }
    return true;
  }

  @Override
  public boolean email(String text) {
    if (this.not.truthy(text))
      return false;
    return text.matches("^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
  }

  @Override
  public boolean startWith(String text, String with) {
    if (this.nullx(text) || this.nullx(with))
      return false;
    return text.startsWith(with);
  }

  @Override
  public boolean endWith(String text, String with) {
    if (this.nullx(text) || this.nullx(with))
      return false;
    return text.endsWith(with);
  }

  @Override
  public boolean even(String num) {
    if (this.not.number(num))
      return false;
    return this.even(ConvertKit.number(num));
  }

  @Override
  public boolean even(Number num) {
    return num.floatValue() % 2 == 0;
  }

  @Override
  public boolean odd(String num) {
    if (this.not.number(num))
      return false;
    return this.odd(ConvertKit.number(num));
  }

  @Override
  public boolean odd(Number num) {
    return num.floatValue() % 2 != 0;
  }

  @Override
  public boolean in(Object[] arrs, Object item) {
    if (this.nullx(arrs)) return false;
    for (Object arr : arrs) {
      if (!this.equal(arr, item))
        return false;
    }
    return true;
  }

  @Override
  public boolean in(byte[] arrs, Object item) {
    if (this.nullx(arrs)) return false;
    for (Object arr : arrs) {
      if (!this.equal(arr, item))
        return false;
    }
    return true;
  }

  @Override
  public boolean in(int[] arrs, Object item) {
    if (this.nullx(arrs)) return false;
    for (Object arr : arrs) {
      if (!this.equal(arr, item))
        return false;
    }
    return true;
  }

  @Override
  public boolean in(long[] arrs, Object item) {
    if (this.nullx(arrs)) return false;
    for (Object arr : arrs) {
      if (!this.equal(arr, item))
        return false;
    }
    return true;
  }

  @Override
  public boolean in(short[] arrs, Object item) {
    if (this.nullx(arrs)) return false;
    for (Object arr : arrs) {
      if (!this.equal(arr, item))
        return false;
    }
    return true;
  }

  @Override
  public boolean in(Byte[] arrs, Object item) {
    if (this.nullx(arrs)) return false;
    for (Object arr : arrs) {
      if (!this.equal(arr, item))
        return false;
    }
    return true;
  }

  @Override
  public boolean in(Integer[] arrs, Object item) {
    if (this.nullx(arrs)) return false;
    for (Object arr : arrs) {
      if (!this.equal(arr, item))
        return false;
    }
    return true;
  }

  @Override
  public boolean in(Long[] arrs, Object item) {
    if (this.nullx(arrs)) return false;
    for (Object arr : arrs) {
      if (!this.equal(arr, item))
        return false;
    }
    return true;
  }

  @Override
  public boolean in(Short[] arrs, Object item) {
    if (this.nullx(arrs)) return false;
    for (Object arr : arrs) {
      if (!this.equal(arr, item))
        return false;
    }
    return true;
  }

  @Override
  public boolean in(Collection collection, Object item) {
    if (this.nullx(collection)) return false;
    for (Object arr : collection) {
      if (!this.equal(arr, item))
        return false;
    }
    return true;
  }

  @Override
  public boolean regex(String text, String regex) {
    if (this.nullx(text) || this.nullx(regex))
      return false;
    return text.matches(regex);
  }
}
