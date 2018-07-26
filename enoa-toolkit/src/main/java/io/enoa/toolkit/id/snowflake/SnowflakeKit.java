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
package io.enoa.toolkit.id.snowflake;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.thr.EoException;
import xyz.downgoon.snowflake.Snowflake;

public final class SnowflakeKit {

  private SnowflakeKit() {

  }

  private static Long datacenterId;
  private static Long workerId;
  private static Snowflake snowflake;

  public static void init(long workerId) {
    init(1L, workerId);
  }

  public static void init(long datacenterId, long workerId) {
    try {
      // 不允许初始化第二次
      if (snowflake != null)
        throw new EoException(EnoaTipKit.message("eo.tip.toolkit.snowflake_init_once"));
      snowflake = new Snowflake(datacenterId, workerId);
    } catch (Exception e) {
      throw new EoException(e);
    }
    SnowflakeKit.datacenterId = datacenterId;
    SnowflakeKit.workerId = workerId;

  }

  public static long next() {
    if (snowflake == null)
      throw new EoException(EnoaTipKit.message("eo.tip.toolkit.snowkit_do_init"));
    if (datacenterId == null || workerId == null)
      throw new EoException(EnoaTipKit.message("eo.tip.toolkit.snowkit_do_init"));
    return snowflake.nextId();
  }

  public static long longer() {
    return next();
  }

  public static String string() {
    return String.valueOf(next());
  }

}
