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
package io.enoa.toolkit.random;

import io.enoa.toolkit.eo.tip.EnoaTipKit;

import java.util.Random;

public class RandomKit {

  private static final Random RANDOM = new Random();

  public static boolean nextBoolean() {
    return RANDOM.nextBoolean();
  }

  private static void valid(boolean cond, String message) {
    if (cond)
      return;
    throw new IllegalArgumentException(message);
  }

  public static byte[] nextBytes(final int count) {
    valid(count >= 0, EnoaTipKit.message("eo.tip.toolkit.count_no_negative"));

    final byte[] result = new byte[count];
    RANDOM.nextBytes(result);
    return result;
  }

  public static int nextInt(final int start, final int end) {
    valid(end >= start,
      EnoaTipKit.message("eo.tip.toolkit.start_gt_end"));
    valid(start >= 0, EnoaTipKit.message("eo.tip.toolkit.both_range_no_negative"));

    if (start == end) {
      return start;
    }

    return start + RANDOM.nextInt(end - start);
  }

  public static int nextInt() {
    return nextInt(0, Integer.MAX_VALUE);
  }

  public static long nextLong(final long start, final long end) {
    valid(end >= start,
      EnoaTipKit.message("eo.tip.toolkit.start_gt_end"));
    valid(start >= 0, EnoaTipKit.message("eo.tip.toolkit.both_range_no_negative"));

    if (start == end) {
      return start;
    }

    return (long) nextDouble(start, end);
  }

  public static long nextLong() {
    return nextLong(0, Long.MAX_VALUE);
  }

  public static double nextDouble(final double start, final double endInclusive) {
    valid(endInclusive >= start,
      EnoaTipKit.message("eo.tip.toolkit.start_gt_end"));
    valid(start >= 0, EnoaTipKit.message("eo.tip.toolkit.both_range_no_negative"));

    if (start == endInclusive) {
      return start;
    }

    return start + ((endInclusive - start) * RANDOM.nextDouble());
  }

  public static double nextDouble() {
    return nextDouble(0, Double.MAX_VALUE);
  }

  public static float nextFloat(final float start, final float endInclusive) {
    valid(endInclusive >= start,
      EnoaTipKit.message("eo.tip.toolkit.start_gt_end"));
    valid(start >= 0, EnoaTipKit.message("eo.tip.toolkit.both_range_no_negative"));

    if (start == endInclusive) {
      return start;
    }

    return start + ((endInclusive - start) * RANDOM.nextFloat());
  }

  public static float nextFloat() {
    return nextFloat(0, Float.MAX_VALUE);
  }

//  public static String rangeText(int ) {
//
//  }
}
