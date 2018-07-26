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
package io.enoa.log.provider.log4j;

import io.enoa.log.EnoaLog;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Created by ein on 2017/8/13.
 */
class _Log4jLog extends EnoaLog {

  private Logger log;
  private String clazzName;
  private static final String callerFQCN = _Log4jLog.class.getName();

  _Log4jLog(Class<?> clazz) {
    this(clazz.getName());
  }

  _Log4jLog(String name) {
    this.log = Logger.getLogger(name);
    this.clazzName = name;
  }

  private String formatMsg(String msg, Object... args) {
    if (args == null)
      return msg;
    for (Object arg : args) {
      int ix = msg.indexOf("{}");
      if (ix == -1)
        continue;
      String left = msg.substring(0, ix),
        right = msg.substring(ix + 2, msg.length());
      msg = left.concat(arg == null ? "" : arg.toString()).concat(right);
    }
    return msg;
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
    this.log.log(callerFQCN, Level.TRACE, msg, null);
  }

  @Override
  public void trace(String format, Object arg) {
    this.log.log(callerFQCN, Level.TRACE, this.formatMsg(format, arg), (arg instanceof Throwable) ? (Throwable) arg : null);
  }

  @Override
  public void trace(String format, Object arg1, Object arg2) {
    this.log.log(callerFQCN, Level.TRACE, this.formatMsg(format, arg1, arg2), (arg2 instanceof Throwable) ? (Throwable) arg2 : null);
  }

  @Override
  public void trace(String format, Object... arguments) {
    this.log.log(callerFQCN, Level.TRACE, this.formatMsg(format, arguments),
      (arguments.length > 0 && (arguments[arguments.length - 1] instanceof Throwable)) ? (Throwable) arguments[arguments.length - 1] : null);
  }

  @Override
  public void trace(String msg, Throwable t) {
    this.log.log(callerFQCN, Level.TRACE, msg, t);
  }

  @Override
  public boolean isDebugEnabled() {
    return log.isDebugEnabled();
  }

  @Override
  public void debug(String msg) {
    this.log.log(callerFQCN, Level.DEBUG, msg, null);
  }

  @Override
  public void debug(String format, Object arg) {
    this.log.log(callerFQCN, Level.DEBUG, this.formatMsg(format, arg), (arg instanceof Throwable) ? (Throwable) arg : null);
  }

  @Override
  public void debug(String format, Object arg1, Object arg2) {
    this.log.log(callerFQCN, Level.DEBUG, this.formatMsg(format, arg1, arg2), (arg2 instanceof Throwable) ? (Throwable) arg2 : null);
  }

  @Override
  public void debug(String format, Object... arguments) {
    this.log.log(callerFQCN, Level.DEBUG, this.formatMsg(format, arguments),
      (arguments.length > 0 && (arguments[arguments.length - 1] instanceof Throwable)) ? (Throwable) arguments[arguments.length - 1] : null);
  }

  @Override
  public void debug(String msg, Throwable t) {
    this.log.log(callerFQCN, Level.DEBUG, msg, null);
  }

  @Override
  public boolean isInfoEnabled() {
    return log.isInfoEnabled();
  }

  @Override
  public void info(String msg) {
    this.log.log(callerFQCN, Level.INFO, msg, null);
  }

  @Override
  public void info(String format, Object arg) {
    this.log.log(callerFQCN, Level.INFO, this.formatMsg(format, arg), (arg instanceof Throwable) ? (Throwable) arg : null);
  }

  @Override
  public void info(String format, Object arg1, Object arg2) {
    this.log.log(callerFQCN, Level.INFO, this.formatMsg(format, arg1, arg2), (arg2 instanceof Throwable) ? (Throwable) arg2 : null);
  }

  @Override
  public void info(String format, Object... arguments) {
    this.log.log(callerFQCN, Level.INFO, this.formatMsg(format, arguments),
      (arguments.length > 0 && (arguments[arguments.length - 1] instanceof Throwable)) ? (Throwable) arguments[arguments.length - 1] : null);
  }

  @Override
  public void info(String msg, Throwable t) {
    this.log.log(callerFQCN, Level.INFO, msg, null);
  }

  @Override
  public boolean isWarnEnabled() {
    return log.isEnabledFor(Level.WARN);
  }

  @Override
  public void warn(String msg) {
    this.log.log(callerFQCN, Level.WARN, msg, null);
  }

  @Override
  public void warn(String format, Object arg) {
    this.log.log(callerFQCN, Level.WARN, this.formatMsg(format, arg), (arg instanceof Throwable) ? (Throwable) arg : null);
  }

  @Override
  public void warn(String format, Object... arguments) {
    this.log.log(callerFQCN, Level.WARN, this.formatMsg(format, arguments),
      (arguments.length > 0 && (arguments[arguments.length - 1] instanceof Throwable)) ? (Throwable) arguments[arguments.length - 1] : null);
  }

  @Override
  public void warn(String format, Object arg1, Object arg2) {
    this.log.log(callerFQCN, Level.WARN, this.formatMsg(format, arg1, arg2), (arg2 instanceof Throwable) ? (Throwable) arg2 : null);
  }

  @Override
  public void warn(String msg, Throwable t) {
    this.log.log(callerFQCN, Level.WARN, msg, null);
  }

  @Override
  public boolean isErrorEnabled() {
    return log.isEnabledFor(Level.ERROR);
  }

  @Override
  public void error(String msg) {
    this.log.log(callerFQCN, Level.ERROR, msg, null);
  }

  @Override
  public void error(String format, Object arg) {
    this.log.log(callerFQCN, Level.ERROR, this.formatMsg(format, arg), (arg instanceof Throwable) ? (Throwable) arg : null);
  }

  @Override
  public void error(String format, Object arg1, Object arg2) {
    this.log.log(callerFQCN, Level.ERROR, this.formatMsg(format, arg1, arg2), (arg2 instanceof Throwable) ? (Throwable) arg2 : null);
  }

  @Override
  public void error(String format, Object... arguments) {
    this.log.log(callerFQCN, Level.ERROR, this.formatMsg(format, arguments),
      (arguments.length > 0 && (arguments[arguments.length - 1] instanceof Throwable)) ? (Throwable) arguments[arguments.length - 1] : null);
  }

  @Override
  public void error(String msg, Throwable t) {
    this.log.log(callerFQCN, Level.ERROR, msg, null);
  }
}
