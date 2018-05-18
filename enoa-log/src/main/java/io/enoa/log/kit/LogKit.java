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
package io.enoa.log.kit;

import io.enoa.log.EMgrLog;
import io.enoa.log.EnoaLog;

import java.util.HashMap;
import java.util.Map;

/**
 * LogKit
 * <p>
 * ```java
 * LogKit.debug()
 * LogKit.info()
 * LogKit.warn()
 * LogKit.error()
 * <p>
 * LogKit.use("name").debug()
 * LogKit.use("name").info()
 * LogKit.use("name").warn()
 * LogKit.use("name").error()
 * <p>
 * LogKit.use(Class).debug()
 * LogKit.use(Class).info()
 * LogKit.use(Class).warn()
 * LogKit.use(Class).error()
 * ```
 */
public class LogKit {


  private static class Holder {
    private static Map<String, EnoaLog> LOG_CACHE = new HashMap<>();
    private static EnoaLog log = EMgrLog.getLog(LogKit.class);

    private static EnoaLog log() {
      String className = Thread.currentThread().getStackTrace()[4].getClassName();
      return log(className);
    }

    private static EnoaLog log(String className) {
      if (LOG_CACHE.get(className) != null)
        return LOG_CACHE.get(className);
      log = EMgrLog.getLog(className);
      LOG_CACHE.put(className, log);
      return log;
    }
  }

  public static void syncLog() {
    Holder.log = EMgrLog.getLog(LogKit.class);
  }

  public static EnoaLog use(Class<?> clazz) {
    return use(clazz.getName());
  }

  public static EnoaLog use(String name) {
//    Holder.log = EnoaLog.getLog(name);
    return Holder.log(name);
  }

  public static String name() {
    return Holder.log().name();
  }

  public static boolean isTraceEnabled() {
    return Holder.log().isTraceEnabled();
  }

  public static void trace(String msg) {
    Holder.log().trace(msg);
  }

  public static void trace(String format, Object arg) {
    Holder.log().trace(format, arg);
  }

  public static void trace(String format, Object arg1, Object arg2) {
    Holder.log().trace(format, arg1, arg2);
  }

  public static void trace(String format, Object... arguments) {
    Holder.log().trace(format, arguments);
  }

  public static void trace(String msg, Throwable t) {
    Holder.log().trace(msg, t);
  }

  public static boolean isDebugEnabled() {
    return Holder.log().isDebugEnabled();
  }

  public static void debug(String msg) {
    Holder.log().debug(msg);
  }

  public static void debug(String format, Object arg) {
    Holder.log().debug(format, arg);
  }

  public static void debug(String format, Object arg1, Object arg2) {
    Holder.log().debug(format, arg1, arg2);
  }

  public static void debug(String format, Object... arguments) {
    Holder.log().debug(format, arguments);
  }

  public static void debug(String msg, Throwable t) {
    Holder.log().debug(msg, t);
  }

  public static boolean isInfoEnabled() {
    return Holder.log().isInfoEnabled();
  }

  public static void info(String msg) {
    Holder.log().info(msg);
  }

  public static void info(String format, Object arg) {
    Holder.log().info(format, arg);
  }

  public static void info(String format, Object arg1, Object arg2) {
    Holder.log().info(format, arg1, arg2);
  }

  public static void info(String format, Object... arguments) {
    Holder.log().info(format, arguments);
  }

  public static void info(String msg, Throwable t) {
    Holder.log().info(msg, t);
  }

  public static boolean isWarnEnabled() {
    return Holder.log().isWarnEnabled();
  }

  public static void warn(String msg) {
    Holder.log().warn(msg);
  }

  public static void warn(String format, Object arg) {
    Holder.log().warn(format, arg);
  }

  public static void warn(String format, Object... arguments) {
    Holder.log().warn(format, arguments);
  }

  public static void warn(String format, Object arg1, Object arg2) {
    Holder.log().warn(format, arg1, arg2);
  }

  public static void warn(String msg, Throwable t) {
    Holder.log().warn(msg, t);
  }

  public static boolean isErrorEnabled() {
    return Holder.log().isErrorEnabled();
  }

  public static void error(String msg) {
    Holder.log().error(msg);
  }

  public static void error(String format, Object arg) {
    Holder.log().error(format, arg);
  }

  public static void error(String format, Object arg1, Object arg2) {
    Holder.log().error(format, arg1, arg2);
  }

  public static void error(String format, Object... arguments) {
    Holder.log().error(format, arguments);
  }

  public static void error(String msg, Throwable t) {
    Holder.log().error(msg, t);
  }

}
