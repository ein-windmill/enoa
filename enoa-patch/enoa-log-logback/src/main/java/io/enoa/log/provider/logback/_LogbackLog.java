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
package io.enoa.log.provider.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import io.enoa.log.EnoaLog;

/**
 * Created by ein on 2017/8/13.
 */
class _LogbackLog extends EnoaLog {

  private Logger log;
  private String clazzName;
  private static final String callerFQCN = _LogbackLog.class.getName();

  _LogbackLog(Class<?> clazz) {
    this(clazz.getName());
  }

  _LogbackLog(String name) {
    this.log = new LoggerContext().getLogger(name);
    this.clazzName = name;
  }

  @Override
  public String name() {
    return this.clazzName;
  }

  @Override
  public boolean isTraceEnabled() {
    return log.isTraceEnabled();
  }

  @Override
  public void trace(String msg) {
    this.log.log(null, callerFQCN, Level.TRACE_INT, msg, null, null);
  }

  @Override
  public void trace(String format, Object arg) {
    this.log.log(null, callerFQCN, Level.TRACE_INT, format,
      (arg instanceof Throwable) ? null : new Object[]{arg},
      (arg instanceof Throwable) ? (Throwable) arg : null);
  }

  @Override
  public void trace(String format, Object arg1, Object arg2) {
    this.log.log(null, callerFQCN, Level.TRACE_INT, format,
      (arg2 instanceof Throwable) ? new Object[]{arg1} : new Object[]{arg1, arg2},
      (arg2 instanceof Throwable) ? (Throwable) arg2 : null);
  }

  @Override
  public void trace(String format, Object... arguments) {
    boolean lastIsThrowable = arguments.length > 0 && arguments[arguments.length - 1] instanceof Throwable;
    Object[] argArr = new Object[lastIsThrowable ? arguments.length - 1 : arguments.length];
    System.arraycopy(arguments, 0, argArr, 0, argArr.length);
    this.log.log(null, callerFQCN, Level.TRACE_INT, format,
      argArr, lastIsThrowable ? (Throwable) arguments[arguments.length - 1] : null);
  }

  @Override
  public void trace(String msg, Throwable t) {
    this.log.log(null, callerFQCN, Level.TRACE_INT, msg, null, t);
  }

  @Override
  public boolean isDebugEnabled() {
    return log.isDebugEnabled();
  }

  @Override
  public void debug(String msg) {
//    this.log.log(null, callerFQCN, Level.DEBUG_INT, msg, null, null);
    this.log.debug(msg);
  }

  @Override
  public void debug(String format, Object arg) {
//    this.log.log(null, callerFQCN, Level.DEBUG_INT, format,
//      (arg instanceof Throwable) ? null : new Object[]{arg},
//      (arg instanceof Throwable) ? (Throwable) arg : null);
    this.log.debug(format, arg);
  }

  @Override
  public void debug(String format, Object arg1, Object arg2) {
//    this.log.log(null, callerFQCN, Level.DEBUG_INT, format,
//      (arg2 instanceof Throwable) ? new Object[]{arg1} : new Object[]{arg1, arg2},
//      (arg2 instanceof Throwable) ? (Throwable) arg2 : null);
    this.log.debug(format, arg1, arg2);
  }

  @Override
  public void debug(String format, Object... arguments) {
//    boolean lastIsThrowable = arguments.length > 0 && arguments[arguments.length - 1] instanceof Throwable;
//    Object[] argArr = new Object[lastIsThrowable ? arguments.length - 1 : arguments.length];
//    System.arraycopy(arguments, 0, argArr, 0, argArr.length);
//    this.log.log(null, callerFQCN, Level.DEBUG_INT, format,
//      argArr, lastIsThrowable ? (Throwable) arguments[arguments.length - 1] : null);
    this.log.debug(format, arguments);
  }

  @Override
  public void debug(String msg, Throwable t) {
//    this.log.log(null, callerFQCN, Level.DEBUG_INT, msg, null, t);
    this.log.debug(msg, t);
  }

  @Override
  public boolean isInfoEnabled() {
    return log.isInfoEnabled();
  }

  @Override
  public void info(String msg) {
//    this.log.log(null, callerFQCN, Level.INFO_INT, msg, null, null);
    this.log.info(msg);
  }

  @Override
  public void info(String format, Object arg) {
//    this.log.log(null, callerFQCN, Level.INFO_INT, format,
//      (arg instanceof Throwable) ? null : new Object[]{arg},
//      (arg instanceof Throwable) ? (Throwable) arg : null);
    this.log.info(format, arg);
  }

  @Override
  public void info(String format, Object arg1, Object arg2) {
//    this.log.log(null, callerFQCN, Level.INFO_INT, format,
//      (arg2 instanceof Throwable) ? new Object[]{arg1} : new Object[]{arg1, arg2},
//      (arg2 instanceof Throwable) ? (Throwable) arg2 : null);
    this.log.info(format, arg1, arg2);
  }

  @Override
  public void info(String format, Object... arguments) {
//    boolean lastIsThrowable = arguments.length > 0 && arguments[arguments.length - 1] instanceof Throwable;
//    Object[] argArr = new Object[lastIsThrowable ? arguments.length - 1 : arguments.length];
//    System.arraycopy(arguments, 0, argArr, 0, argArr.length);
//    this.log.log(null, callerFQCN, Level.INFO_INT, format,
//      argArr, lastIsThrowable ? (Throwable) arguments[arguments.length - 1] : null);
    this.log.info(format, arguments);
  }

  @Override
  public void info(String msg, Throwable t) {
//    this.log.log(null, callerFQCN, Level.INFO_INT, msg, null, t);
    this.log.info(msg, t);
  }

  @Override
  public boolean isWarnEnabled() {
    return log.isWarnEnabled();
  }

  @Override
  public void warn(String msg) {
//    this.log.log(null, callerFQCN, Level.WARN_INT, msg, null, null);
    this.log.warn(msg);
  }

  @Override
  public void warn(String format, Object arg) {
//    this.log.log(null, callerFQCN, Level.WARN_INT, format,
//      (arg instanceof Throwable) ? null : new Object[]{arg},
//      (arg instanceof Throwable) ? (Throwable) arg : null);
    this.log.warn(format, arg);
  }

  @Override
  public void warn(String format, Object... arguments) {
//    boolean lastIsThrowable = arguments.length > 0 && arguments[arguments.length - 1] instanceof Throwable;
//    Object[] argArr = new Object[lastIsThrowable ? arguments.length - 1 : arguments.length];
//    System.arraycopy(arguments, 0, argArr, 0, argArr.length);
//    this.log.log(null, callerFQCN, Level.WARN_INT, format,
//      argArr, lastIsThrowable ? (Throwable) arguments[arguments.length - 1] : null);
    this.log.warn(format, arguments);
  }

  @Override
  public void warn(String format, Object arg1, Object arg2) {
//    this.log.log(null, callerFQCN, Level.WARN_INT, format,
//      (arg2 instanceof Throwable) ? new Object[]{arg1} : new Object[]{arg1, arg2},
//      (arg2 instanceof Throwable) ? (Throwable) arg2 : null);
    this.log.warn(format, arg1, arg2);
  }

  @Override
  public void warn(String msg, Throwable t) {
//    this.log.log(null, callerFQCN, Level.INFO_INT, msg, null, t);
    this.log.warn(msg, t);
  }

  @Override
  public boolean isErrorEnabled() {
    return log.isErrorEnabled();
  }

  @Override
  public void error(String msg) {
//    this.log.log(null, callerFQCN, Level.ERROR_INT, msg, null, null);
    this.log.error(msg);
  }

  @Override
  public void error(String format, Object arg) {
//    this.log.log(null, callerFQCN, Level.ERROR_INT, format,
//      (arg instanceof Throwable) ? null : new Object[]{arg},
//      (arg instanceof Throwable) ? (Throwable) arg : null);
    this.log.error(format, arg);
  }

  @Override
  public void error(String format, Object arg1, Object arg2) {
//    this.log.log(null, callerFQCN, Level.ERROR_INT, format,
//      (arg2 instanceof Throwable) ? new Object[]{arg1} : new Object[]{arg1, arg2},
//      (arg2 instanceof Throwable) ? (Throwable) arg2 : null);
    this.log.error(format, arg1, arg2);
  }

  @Override
  public void error(String format, Object... arguments) {
//    boolean lastIsThrowable = arguments.length > 0 && arguments[arguments.length - 1] instanceof Throwable;
//    Object[] argArr = new Object[lastIsThrowable ? arguments.length - 1 : arguments.length];
//    System.arraycopy(arguments, 0, argArr, 0, argArr.length);
//    this.log.log(null, callerFQCN, Level.ERROR_INT, format,
//      argArr, lastIsThrowable ? (Throwable) arguments[arguments.length - 1] : null);
    this.log.error(format, arguments);
  }

  @Override
  public void error(String msg, Throwable t) {
//    this.log.log(null, callerFQCN, Level.ERROR_INT, msg, null, t);
    this.log.error(msg, t);
  }
}
