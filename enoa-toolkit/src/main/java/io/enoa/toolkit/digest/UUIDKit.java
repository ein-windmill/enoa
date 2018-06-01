/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
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
package io.enoa.toolkit.digest;

import java.util.UUID;

public final class UUIDKit {

  public static String next() {
    return next(true);
  }

  public static String next(boolean segment) {
    String uuid = UUID.randomUUID().toString();
    return segment ? uuid : uuid.replace("-", "");
  }

  public static String multiple(int num, boolean segment) {
    if (num == 0)
      return null;
    StringBuilder uuid = new StringBuilder();
    for (; num-- > 0; ) {
      uuid.append(next(segment));
    }
    return uuid.toString();
  }

  public static String multiple(int num) {
    return multiple(num, false);
  }

  public static String multiple(boolean segment) {
    return multiple(2, segment);
  }

  public static String multiple() {
    return multiple(false);
  }

}
