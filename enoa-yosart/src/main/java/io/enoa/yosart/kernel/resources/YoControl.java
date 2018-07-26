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
package io.enoa.yosart.kernel.resources;

import io.enoa.repeater.http.*;
import io.enoa.yosart.kernel.http.Session;

import java.util.Date;
import java.util.Map;

public abstract class YoControl<CTL> extends YeControl<CTL> {

  protected <T> T bean(Class<T> clazz) {
    return super.request().bean(clazz);
  }

  protected <T> T bean(Class<T> clazz, boolean skipConvertError) {
    return super.request().bean(clazz, skipConvertError);
  }

  protected <T> T bean(Class<T> clazz, String name) {
    return super.request().bean(clazz, name);
  }

  protected <T> T bean(Class<T> clazz, String name, boolean skipConvertError) {
    return super.request().bean(clazz, name, skipConvertError);
  }

  protected String[] variableNames() {
    return super.request().variableNames();
  }

  protected String variable(String name) {
    return super.request().variable(name);
  }

  protected String variable(String name, String def) {
    return super.request().variable(name, def);
  }

  protected Integer variableToInt(String name) {
    return super.request().variableToInt(name);
  }

  protected Integer variableToInt(String name, Integer def) {
    return super.request().variableToInt(name, def);
  }

  protected Boolean variableToBoolean(String name) {
    return super.request().variableToBoolean(name);
  }

  protected Boolean variableToBoolean(String name, Boolean def) {
    return super.request().variableToBoolean(name, def);
  }

  protected Object originRequest() {
    return super.request().originRequest();
  }

  protected Method method() {
    return super.request().method();
  }

  protected String context() {
    return super.request().context();
  }

  protected String uri() {
    return super.request().uri();
  }

  protected String url() {
    return super.request().url();
  }

  protected RequestBody body() {
    return super.request().body();
  }

  protected Cookie[] cookies() {
    return super.request().cookies();
  }

  protected Cookie cookieObject(String name) {
    return super.request().cookieObject(name);
  }

  protected String cookie(String name) {
    return super.request().cookie(name);
  }

  protected String cookie(String name, String def) {
    return super.request().cookie(name, def);
  }

  protected Integer cookieToInt(String name) {
    return super.request().cookieToInt(name);
  }

  protected Integer cookieToInt(String name, Integer def) {
    return super.request().cookieToInt(name, def);
  }

  protected Long cookieToLong(String name) {
    return super.request().cookieToLong(name);
  }

  protected Long cookieToLong(String name, Long def) {
    return super.request().cookieToLong(name, def);
  }

  protected String[] headerNames() {
    return super.request().headerNames();
  }

  protected String header(String name) {
    return super.request().header(name);
  }

  protected String para(String name) {
    return super.request().para(name);
  }

  protected String para(String name, String def) {
    return super.request().para(name, def);
  }

  protected Integer paraToInt(String name) {
    return super.request().paraToInt(name);
  }

  protected Integer paraToInt(String name, Integer def) {
    return super.request().paraToInt(name, def);
  }

  protected Long paraToLong(String name) {
    return super.request().paraToLong(name);
  }

  protected Long paraToLong(String name, Long def) {
    return super.request().paraToLong(name, def);
  }

  protected Boolean paraToBoolean(String name) {
    return super.request().paraToBoolean(name);
  }

  protected Boolean paraToBoolean(String name, Boolean def) {
    return super.request().paraToBoolean(name, def);
  }

  protected Double paraToDouble(String name) {
    return super.request().paraToDouble(name);
  }

  protected Double paraToDouble(String name, Double def) {
    return super.request().paraToDouble(name, def);
  }

  protected Date paraToDate(String name) {
    return super.request().paraToDate(name);
  }

  protected Date paraToDate(String name, String format) {
    return super.request().paraToDate(name, format);
  }

  protected Date paraToDate(String name, String format, Date def) {
    return super.request().paraToDate(name, format, def);
  }

  protected Map<String, String[]> paraMap() {
    return super.request().paraMap();
  }

  protected String[] paraNames() {
    return super.request().paraNames();
  }

  protected String[] paraValues(String name) {
    return super.request().paraValues(name);
  }

  protected Integer[] paraValuesToInt(String name) {
    return super.request().paraValuesToInt(name);
  }

  protected Long[] paraValuesToLong(String name) {
    return super.request().paraValuesToLong(name);
  }

  protected <T> T attr(String name) {
    return super.request().attr(name);
  }

  protected <T> CTL attr(String name, T data) {
    super.request().attr(name, data);
    return (CTL) this;
  }

  protected String[] attrNames() {
    return super.request().attrNames();
  }

  protected Request rmAttr(String name) {
    return super.request().rmAttr(name);
  }

  protected UFile[] files() {
    return super.request().files();
  }

  protected UFile[] files(String name) {
    return super.request().files(name);
  }

  protected UFile file(String name) {
    return super.request().file(name);
  }

  protected Session session() {
    return super.request().session();
  }

}
