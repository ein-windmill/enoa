package io.enoa.toolkit.is;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface EoAIs extends EoIs {

  @Override
  default boolean string(Object obj) {
    return this.string(Stream.of(obj).collect(Collectors.toList()));
  }

  default boolean string(Object... objs) {
    return this.string(Stream.of(objs).collect(Collectors.toList()));
  }

  boolean string(Collection objs);

  @Override
  default boolean number(String str) {
    return this.number(Stream.of(str).collect(Collectors.toList()));
  }

  default boolean number(String... str) {
    return this.number(Stream.of(str).collect(Collectors.toList()));
  }

  boolean number(Collection<String> objs);

  @Override
  default boolean digit(String str) {
    return this.digit(Stream.of(str).collect(Collectors.toList()));
  }

  default boolean digit(String... str) {
    return this.digit(Stream.of(str).collect(Collectors.toList()));
  }

  boolean digit(Collection<String> strs);

  @Override
  default boolean negative(String str) {
    return this.negative(Stream.of(str).collect(Collectors.toList()));
  }

  default boolean negative(String... str) {
    return this.negative(Stream.of(str).collect(Collectors.toList()));
  }

  @Override
  default boolean negative(Number number) {
    return this.negative(Stream.of(number).collect(Collectors.toList()));
  }

  default boolean negative(Number... numbers) {
    return this.negative(Stream.of(numbers).collect(Collectors.toList()));
  }

  boolean negative(Collection numbers);

  @Override
  default boolean zero(String str) {
    return this.zero(Stream.of(str).collect(Collectors.toList()));
  }

  default boolean zero(String... strs) {
    return this.zero(Stream.of(strs).collect(Collectors.toList()));
  }

  @Override
  default boolean zero(Number number) {
    return this.zero(Stream.of(number).collect(Collectors.toList()));
  }

  default boolean zero(Number... numbers) {
    return this.zero(Stream.of(numbers).collect(Collectors.toList()));
  }

  boolean zero(Collection cols);

  @Override
  default boolean nullx(Object object) {
    return this.nullx(Stream.of(object).collect(Collectors.toList()));
  }

  default boolean nullx(Object... objects) {
    return this.nullx(Stream.of(objects).collect(Collectors.toList()));
  }

  boolean nullx(Collection cols);

  @Override
  default boolean blank(String str) {
    return this.blank(Stream.of(str).collect(Collectors.toList()));
  }

  default boolean blank(String... strs) {
    return this.blank(Stream.of(strs).collect(Collectors.toList()));
  }

  boolean blank(Collection<String> strs);

  @Override
  default boolean array(Object object) {
    return this.array(Stream.of(object).collect(Collectors.toList()));
  }

  default boolean array(Object... objects) {
    return this.array(Stream.of(objects).collect(Collectors.toList()));
  }

  boolean array(Collection objects);

  @Override
  boolean empty(Collection collection);

  @Override
  boolean empty(Map map);

  @Override
  default boolean empty(Object[] arrs) {
    return this.empty(new Object[][]{arrs});
  }

  boolean empty(Object[][] arrs);

  @Override
  default boolean empty(byte[] arrs) {
    return this.empty(new byte[][]{arrs});
  }

  boolean empty(byte[][] arrs);

  @Override
  default boolean empty(int[] arrs) {
    return this.empty(new int[][]{arrs});
  }

  boolean empty(int[][] arrs);

  @Override
  default boolean empty(long[] arrs) {
    return this.empty(new long[][]{arrs});
  }

  boolean empty(long[][] arrs);

  @Override
  default boolean empty(short[] arrs) {
    return this.empty(new short[][]{arrs});
  }

  boolean empty(short[][] arrs);

  @Override
  default boolean empty(String[] arrs) {
    return this.empty(new String[][]{arrs});
  }

  boolean empty(String[][] arrs);

  @Override
  default boolean empty(Integer[] arrs) {
    return this.empty(new Integer[][]{arrs});
  }

  boolean empty(Integer[][] arrs);

  @Override
  default boolean empty(Double[] arrs) {
    return this.empty(new Double[][]{arrs});
  }

  boolean empty(Double[][] arrs);

  @Override
  default boolean empty(Short[] arrs) {
    return this.empty(new Short[][]{arrs});
  }

  boolean empty(Short[][] arrs);

  @Override
  boolean equal(Object obj0, Object obj1);

  @Override
  default boolean truthy(String str) {
    return this.truthy(new String[]{str});
  }

  default boolean truthy(String... strs) {
    return this.truthy(Stream.of(strs).collect(Collectors.toList()));
  }

  boolean truthy(Collection<String> strs);

  @Override
  boolean sameType(Object ob0, Object obj1);

  @Override
  boolean include(String large, String small);

  @Override
  default boolean space(String text) {
    return this.space(new String[]{text});
  }

  default boolean space(String... texts) {
    return this.space(Stream.of(texts).collect(Collectors.toList()));
  }

  boolean space(Collection<String> texts);

  @Override
  default boolean alphanumeric(String text) {
    return this.alphanumeric(Stream.of(text).collect(Collectors.toList()));
  }

  default boolean alphanumberic(String... texts) {
    return this.alphanumeric(Stream.of(texts).collect(Collectors.toList()));
  }

  boolean alphanumeric(Collection<String> texts);

  @Override
  default boolean email(String text) {
    return this.email(Stream.of(text).collect(Collectors.toList()));
  }

  default boolean email(String... text) {
    return this.email(Stream.of(text).collect(Collectors.toList()));
  }

  boolean email(Collection<String> text);

  @Override
  boolean startWith(String text, String with);

  @Override
  boolean endWith(String text, String with);

  @Override
  default boolean even(Number num) {
    return this.even(Stream.of(num).collect(Collectors.toList()));
  }

  default boolean even(Number... nums) {
    return this.even(Stream.of(nums).collect(Collectors.toList()));
  }

  @Override
  default boolean even(String num) {
    return this.even(Stream.of(num).collect(Collectors.toList()));
  }

  default boolean even(String... nums) {
    return this.even(Stream.of(nums).collect(Collectors.toList()));
  }

  boolean even(Collection nums);

  @Override
  default boolean odd(String num) {
    return this.odd(Stream.of(num).collect(Collectors.toList()));
  }

  default boolean odd(String... nums) {
    return this.odd(Stream.of(nums).collect(Collectors.toList()));
  }

  @Override
  default boolean odd(Number num) {
    return this.odd(Stream.of(num).collect(Collectors.toList()));
  }

  default boolean odd(Number... nums) {
    return this.odd(Stream.of(nums).collect(Collectors.toList()));
  }

  boolean odd(Collection nums);

  @Override
  boolean in(Object[] arrs, Object item);

  @Override
  boolean in(byte[] arrs, Object item);

  @Override
  boolean in(int[] arrs, Object item);

  @Override
  boolean in(long[] arrs, Object item);

  @Override
  boolean in(short[] arrs, Object item);

  @Override
  boolean in(Byte[] arrs, Object item);

  @Override
  boolean in(Integer[] arrs, Object item);

  @Override
  boolean in(Long[] arrs, Object item);

  @Override
  boolean in(Short[] arrs, Object item);

  @Override
  boolean in(Collection collection, Object item);

  @Override
  boolean regex(String text, String regex);
}
