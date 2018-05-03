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
package io.enoa.log.provider.jdk;

import io.enoa.log.EnoaLog;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ein on 2017/8/13.
 */
class _JdkLog extends EnoaLog {

  private Logger log;
  private String clazzName;

  _JdkLog(Class<?> clazz) {
    this(clazz.getName());
  }

  _JdkLog(String name) {
    this.log = Logger.getLogger(name);
    this.clazzName = name;
  }

  private String getMethodName() {
    return Thread.currentThread().getStackTrace()[2].getMethodName();
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
    return log.isLoggable(Level.SEVERE);
  }

  @Override
  public void trace(String msg) {
    this.log.logp(Level.SEVERE, clazzName, this.getMethodName(), msg);
  }

  @Override
  public void trace(String format, Object arg) {
    this.log.logp(Level.SEVERE, clazzName, this.getMethodName(), this.formatMsg(format, arg));
  }

  @Override
  public void trace(String format, Object arg1, Object arg2) {
    this.log.logp(Level.SEVERE, clazzName, this.getMethodName(), this.formatMsg(format, arg1, arg2));
  }

  @Override
  public void trace(String format, Object... arguments) {
    this.log.logp(Level.SEVERE, clazzName, this.getMethodName(), this.formatMsg(format, arguments));
  }

  @Override
  public void trace(String msg, Throwable t) {
    this.log.logp(Level.SEVERE, clazzName, this.getMethodName(), msg, t);
  }

  @Override
  public boolean isDebugEnabled() {
    return log.isLoggable(Level.FINE);
  }

  @Override
  public void debug(String msg) {
    this.log.logp(Level.FINE, clazzName, this.getMethodName(), msg);
  }

  @Override
  public void debug(String format, Object arg) {
    this.log.logp(Level.FINE, clazzName, this.getMethodName(), this.formatMsg(format, arg));
  }

  @Override
  public void debug(String format, Object arg1, Object arg2) {
    this.log.logp(Level.FINE, clazzName, this.getMethodName(), this.formatMsg(format, arg1, arg2));
  }

  @Override
  public void debug(String format, Object... arguments) {
    this.log.logp(Level.FINE, clazzName, this.getMethodName(), this.formatMsg(format, arguments));
  }

  @Override
  public void debug(String msg, Throwable t) {
    this.log.logp(Level.FINE, clazzName, this.getMethodName(), msg, t);
  }

  @Override
  public boolean isInfoEnabled() {
    return log.isLoggable(Level.INFO);
  }

  @Override
  public void info(String msg) {
    this.log.logp(Level.INFO, clazzName, this.getMethodName(), msg);
  }

  @Override
  public void info(String format, Object arg) {
    this.log.logp(Level.INFO, clazzName, this.getMethodName(), this.formatMsg(format, arg));
  }

  @Override
  public void info(String format, Object arg1, Object arg2) {
    this.log.logp(Level.INFO, clazzName, this.getMethodName(), this.formatMsg(format, arg1, arg2));
  }

  @Override
  public void info(String format, Object... arguments) {
    this.log.logp(Level.INFO, clazzName, this.getMethodName(), this.formatMsg(format, arguments));
  }

  @Override
  public void info(String msg, Throwable t) {
    this.log.logp(Level.INFO, clazzName, this.getMethodName(), msg, t);
  }

  @Override
  public boolean isWarnEnabled() {
    return log.isLoggable(Level.WARNING);
  }

  @Override
  public void warn(String msg) {
    this.log.logp(Level.WARNING, clazzName, this.getMethodName(), msg);
  }

  @Override
  public void warn(String format, Object arg) {
    this.log.logp(Level.WARNING, clazzName, this.getMethodName(), this.formatMsg(format, arg));
  }

  @Override
  public void warn(String format, Object... arguments) {
    this.log.logp(Level.WARNING, clazzName, this.getMethodName(), this.formatMsg(format, arguments));
  }

  @Override
  public void warn(String format, Object arg1, Object arg2) {
    this.log.logp(Level.WARNING, clazzName, this.getMethodName(), this.formatMsg(format, arg1, arg2));
  }

  @Override
  public void warn(String msg, Throwable t) {
    this.log.logp(Level.WARNING, clazzName, this.getMethodName(), msg, t);
  }

  @Override
  public boolean isErrorEnabled() {
    return log.isLoggable(Level.SEVERE);
  }

  @Override
  public void error(String msg) {
    this.log.logp(Level.SEVERE, clazzName, this.getMethodName(), msg);
  }

  @Override
  public void error(String format, Object arg) {
    this.log.logp(Level.SEVERE, clazzName, this.getMethodName(), this.formatMsg(format, arg));
  }

  @Override
  public void error(String format, Object arg1, Object arg2) {
    this.log.logp(Level.SEVERE, clazzName, this.getMethodName(), this.formatMsg(format, arg1, arg2));
  }

  @Override
  public void error(String format, Object... arguments) {
    this.log.logp(Level.SEVERE, clazzName, this.getMethodName(), this.formatMsg(format, arguments));
  }

  @Override
  public void error(String msg, Throwable t) {
    this.log.logp(Level.SEVERE, clazzName, this.getMethodName(), msg, t);
  }
}
