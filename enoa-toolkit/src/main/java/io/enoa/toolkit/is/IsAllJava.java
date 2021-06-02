package io.enoa.toolkit.is;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

public class IsAllJava implements EoAIs {

  private EoIs is;
  private boolean not;
  private IsAllJava allnot;

  public IsAllJava(EoIs is) {
    this(is, false);
  }

  private IsAllJava(EoIs is, boolean not) {
    this.is = is;
    this.not = not;
  }

  public IsAllJava not() {
    if (this.allnot == null)
      this.allnot = new IsAllJava(new IsNotJava(this.is), !this.not);
    return this.allnot;
  }

  @Override
  public boolean string(Collection objs) {
    if (objs == null)
      return this.not;
    return objs.stream().allMatch(i -> this.is.string(i));
  }

  @Override
  public boolean number(Collection<String> objs) {
    if (objs == null)
      return this.not;
    return objs.stream().allMatch(i -> this.is.number(i));
  }

  @Override
  public boolean digit(Collection<String> strs) {
    if (strs == null)
      return this.not;
    return strs.stream().allMatch(i -> this.is.digit(i));
  }

  @Override
  public boolean negative(Collection numbers) {
    if (numbers == null)
      return this.not;
    return numbers.stream().allMatch(i -> Is.string(i) ?
      this.is.negative((String) i) :
      this.is.negative((Number) i));
  }

  @Override
  public boolean zero(Collection cols) {
    if (cols == null)
      return this.not;
    return cols.stream().allMatch(i -> Is.string(i) ?
      this.is.zero((String) i) :
      this.is.zero((Number) i));
  }

  @Override
  public boolean nullx(Collection cols) {
    if (cols == null)
      return this.not;
    return cols.stream().allMatch(i -> this.is.nullx(i));
  }

  @Override
  public boolean blank(Collection<String> strs) {
    if (strs == null)
      return this.not;
    return strs.stream().allMatch(i -> this.is.blank(i));
  }

  @Override
  public boolean array(Collection objects) {
    if (objects == null)
      return this.not;
    return objects.stream().allMatch(i -> this.is.array(i));
  }

  @Override
  public boolean empty(Collection collection) {
    return this.is.empty(collection);
  }

  @Override
  public boolean empty(Object[][] arrs) {
    if (arrs == null)
      return this.not;
    return Stream.of(arrs).allMatch(i -> this.is.empty(i));
  }

  @Override
  public boolean empty(byte[][] arrs) {
    if (arrs == null)
      return this.not;
    return Stream.of(arrs).allMatch(i -> this.is.empty(i));
  }

  @Override
  public boolean empty(int[][] arrs) {
    if (arrs == null)
      return this.not;
    return Stream.of(arrs).allMatch(i -> this.is.empty(i));
  }

  @Override
  public boolean empty(long[][] arrs) {
    if (arrs == null)
      return this.not;
    return Stream.of(arrs).allMatch(i -> this.is.empty(i));
  }

  @Override
  public boolean empty(short[][] arrs) {
    if (arrs == null)
      return this.not;
    return Stream.of(arrs).allMatch(i -> this.is.empty(i));
  }

  @Override
  public boolean empty(String[][] arrs) {
    if (arrs == null)
      return this.not;
    return Stream.of(arrs).allMatch(i -> this.is.empty(i));
  }

  @Override
  public boolean empty(Integer[][] arrs) {
    if (arrs == null)
      return this.not;
    return Stream.of(arrs).allMatch(i -> this.is.empty(i));
  }

  @Override
  public boolean empty(Double[][] arrs) {
    if (arrs == null)
      return this.not;
    return Stream.of(arrs).allMatch(i -> this.is.empty(i));
  }

  @Override
  public boolean empty(Short[][] arrs) {
    if (arrs == null)
      return this.not;
    return Stream.of(arrs).allMatch(i -> this.is.empty(i));
  }

  @Override
  public boolean empty(Map map) {
    return this.is.empty(map);
  }

  @Override
  public boolean equal(Object obj0, Object obj1) {
    return this.is.equal(obj0, obj1);
  }

  @Override
  public boolean truthy(Collection<String> strs) {
    if (strs == null)
      return this.not;
    return strs.stream().allMatch(i -> this.is.truthy(i));
  }

  @Override
  public boolean sameType(Object ob0, Object obj1) {
    return this.is.sameType(ob0, obj1);
  }

  @Override
  public boolean include(String large, String small) {
    return this.is.include(large, small);
  }

  @Override
  public boolean space(Collection<String> texts) {
    if (texts == null)
      return this.not;
    return texts.stream().allMatch(i -> this.is.space(i));
  }

  @Override
  public boolean alphanumeric(Collection<String> texts) {
    if (texts == null)
      return this.not;
    return texts.stream().allMatch(i -> this.is.alphanumeric(i));
  }

  @Override
  public boolean email(Collection<String> text) {
    if (text == null)
      return this.not;
    return text.stream().allMatch(i -> this.is.email(i));
  }

  @Override
  public boolean startWith(String text, String with) {
    return this.is.startWith(text, with);
  }

  @Override
  public boolean endWith(String text, String with) {
    return this.is.endWith(text, with);
  }

  @Override
  public boolean even(Collection nums) {
    if (nums == null)
      return this.not;
    return nums.stream().allMatch(i -> Is.string(i) ?
      this.is.even((String) i) :
      this.is.even((Number) i));
  }

  @Override
  public boolean odd(Collection num) {
    if (num == null)
      return this.not;
    return num.stream().allMatch(i -> Is.string(i) ?
      this.is.odd((String) i) :
      this.is.odd((Number) i));
  }

  @Override
  public boolean in(Object[] arrs, Object item) {
    return this.is.in(arrs, item);
  }

  @Override
  public boolean in(byte[] arrs, Object item) {
    return this.is.in(arrs, item);
  }

  @Override
  public boolean in(int[] arrs, Object item) {
    return this.is.in(arrs, item);
  }

  @Override
  public boolean in(long[] arrs, Object item) {
    return this.is.in(arrs, item);
  }

  @Override
  public boolean in(short[] arrs, Object item) {
    return this.is.in(arrs, item);
  }

  @Override
  public boolean in(Byte[] arrs, Object item) {
    return this.is.in(arrs, item);
  }

  @Override
  public boolean in(Integer[] arrs, Object item) {
    return this.is.in(arrs, item);
  }

  @Override
  public boolean in(Long[] arrs, Object item) {
    return this.is.in(arrs, item);
  }

  @Override
  public boolean in(Short[] arrs, Object item) {
    return this.is.in(arrs, item);
  }

  @Override
  public boolean in(Collection collection, Object item) {
    return this.is.in(collection, item);
  }

  @Override
  public boolean regex(String text, String regex) {
    return this.is.regex(text, regex);
  }
}