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
package io.enoa.log.provider.slf4j;

import io.enoa.log.EnoaLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ein on 2017/8/13.
 */
class _Slf4jLog extends EnoaLog {

  private Logger log;
  private String clazzName;

  _Slf4jLog(Class<?> clazz) {
    this(clazz.getName());
  }

  _Slf4jLog(String name) {
    this.log = LoggerFactory.getLogger(name);
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
    this.log.trace(msg);
  }

  @Override
  public void trace(String format, Object arg) {
    this.log.trace(format, arg);
  }

  @Override
  public void trace(String format, Object arg1, Object arg2) {
    this.log.trace(format, arg1, arg2);
  }

  @Override
  public void trace(String format, Object... arguments) {
    this.log.trace(format, arguments);
  }

  @Override
  public void trace(String msg, Throwable t) {
    this.log.trace(msg, t);
  }

  @Override
  public boolean isDebugEnabled() {
    return log.isDebugEnabled();
  }

  @Override
  public void debug(String msg) {
    this.log.debug(msg);
  }

  @Override
  public void debug(String format, Object arg) {
    this.log.debug(format, arg);
  }

  @Override
  public void debug(String format, Object arg1, Object arg2) {
    this.log.debug(format, arg1, arg2);
  }

  @Override
  public void debug(String format, Object... arguments) {
    this.log.debug(format, arguments);
  }

  @Override
  public void debug(String msg, Throwable t) {
    this.log.debug(msg, t);
  }

  @Override
  public boolean isInfoEnabled() {
    return log.isInfoEnabled();
  }

  @Override
  public void info(String msg) {
    this.log.info(msg);
  }

  @Override
  public void info(String format, Object arg) {
    this.log.info(format, arg);
  }

  @Override
  public void info(String format, Object arg1, Object arg2) {
    this.log.info(format, arg1, arg2);
  }

  @Override
  public void info(String format, Object... arguments) {
    this.log.info(format, arguments);
  }

  @Override
  public void info(String msg, Throwable t) {
    this.log.info(msg, t);
  }

  @Override
  public boolean isWarnEnabled() {
    return log.isWarnEnabled();
  }

  @Override
  public void warn(String msg) {
    this.log.warn(msg);
  }

  @Override
  public void warn(String format, Object arg) {
    this.log.warn(format, arg);
  }

  @Override
  public void warn(String format, Object... arguments) {
    this.log.warn(format, arguments);
  }

  @Override
  public void warn(String format, Object arg1, Object arg2) {
    this.log.warn(format, arg1, arg2);
  }

  @Override
  public void warn(String msg, Throwable t) {
    this.log.warn(msg, t);
  }

  @Override
  public boolean isErrorEnabled() {
    return log.isErrorEnabled();
  }

  @Override
  public void error(String msg) {
    this.log.error(msg);
  }

  @Override
  public void error(String format, Object arg) {
    this.log.error(format, arg);
  }

  @Override
  public void error(String format, Object arg1, Object arg2) {
    this.log.error(format, arg1, arg2);
  }

  @Override
  public void error(String format, Object... arguments) {
    this.log.error(format, arguments);
  }

  @Override
  public void error(String msg, Throwable t) {
    this.log.error(msg, t);
  }
}
