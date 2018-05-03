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
package io.enoa.yosart.kernel.resources;

import io.enoa.toolkit.digest.DigestKit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class OyControlResource implements OyResource {

  /**
   * resource type, CONTROL
   */
  private final Type type;
  /**
   * control class
   */
  private final Class<? extends YaControl> control;
  /**
   * route uri
   */
  private final String route;
  /**
   * http methods
   */
  private final io.enoa.repeater.http.Method[] methods;
  /**
   * function name
   */
  private final String funcName;
  /**
   * identity function name, like io.enoa.Control#method(arg0::java.util.String)
   */
  private final String identityFuncName;
  /**
   * function
   */
  private final Method func;
  /**
   * function annotations
   */
  private final Annotation[] annos;
  /**
   * function arguments
   */
  private final Parameter[] paras;
  /**
   * path variable names
   */
  private final String[] vars;
  /**
   * classname
   */
  private final String className;
  /**
   * identity hash name
   */
  private final String hashName;

  OyControlResource(Class<? extends YaControl> control, String route, String funcName,
                    io.enoa.repeater.http.Method[] methods, Method func, Annotation[] annos,
                    Parameter[] paras, String[] vars) {
    this.type = Type.CONTROL;
    this.control = control;
    this.route = route;
    this.methods = methods;
    this.func = func;
    this.funcName = funcName;
    this.annos = annos;
    this.paras = paras;
    this.vars = vars;
    this.className = control.getName();

    StringBuilder pasb = new StringBuilder();
    int i = 0;
    for (Parameter para : paras) {
      pasb.append(para.getName()).append("::").append(para.getType().getName());
      if (i + 1 < paras.length)
        pasb.append(", ");
      i += 1;
    }
    this.identityFuncName = String.format("%s#%s(%s)", control.getName(), funcName, pasb.toString());
    this.hashName = DigestKit.md5(this.identityFuncName);
  }

  @Override
  public Type type() {
    return this.type;
  }

  public Class<? extends YaControl> control() {
    return this.control;
  }

  @Override
  public String route() {
    return this.route;
  }

  @Override
  public String className() {
    return this.className;
  }

  @Override
  public io.enoa.repeater.http.Method[] methods() {
    return this.methods;
  }

  @Override
  public Method func() {
    return this.func;
  }

  @Override
  public String funcName() {
    return this.funcName;
  }

  public String identityFuncName() {
    return this.identityFuncName;
  }

  @Override
  public String hashName() {
    return this.hashName;
  }

  public Annotation[] annos() {
    return this.annos;
  }

  public Parameter[] paras() {
    return this.paras;
  }

  @Override
  public String[] vars() {
    return this.vars;
  }

  @Override
  public String toString() {
    return String.format("`%s` => %s", this.route, this.identityFuncName);
  }
}
