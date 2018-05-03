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

import io.enoa.repeater.http.Method;
import io.enoa.toolkit.digest.DigestKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.yosart.kernel.http.YoRequest;

public class OyActionResource implements OyResource {

  /**
   * resource ACTION
   */
  private final Type type;
  /**
   * http method
   */
  private final Method[] methods;
  /**
   * YoAction.handle function
   */
  private final java.lang.reflect.Method func;
  /**
   * functoin name   -> handle
   */
  private final String funcName;
  /**
   * route uri
   */
  private final String route;
  /**
   * YoAction instance
   */
  private final YoAction action;
  /**
   * path variable names
   */
  private final String[] vars;
  /**
   * class name
   */
  private final String className;
  /**
   * identity function name, like io.enoa.example.YosartBoot$1#handle
   */
  private final String identityFuncName;
  /**
   * identity hash name
   */
  private final String hashName;

//  OyActionResource(String route, YoAction action) {
//    this(null, route, action);
//  }

  OyActionResource(Method[] methods, String route, YoAction action, java.lang.reflect.Method func,
                   String funcName, String[] vars) {
    this.type = Type.ACTION;
    this.methods = methods;
    this.action = action;
    this.route = route;
    this.func = func;
    this.funcName = funcName;
    this.vars = vars;
    this.className = action.getClass().getName();
    this.identityFuncName = TextKit.union(this.className, "#", this.funcName, "(req::", YoRequest.class.getName(), ")");
    this.hashName = DigestKit.md5(this.identityFuncName);
  }

  @Override
  public Type type() {
    return type;
  }

  @Override
  public Method[] methods() {
    return methods;
  }

  public YoAction action() {
    return action;
  }

  @Override
  public String route() {
    return route;
  }

  @Override
  public String className() {
    return this.className;
  }

  @Override
  public java.lang.reflect.Method func() {
    return this.func;
  }

  @Override
  public String funcName() {
    return this.funcName;
  }

  @Override
  public String identityFuncName() {
    return this.identityFuncName;
  }

  @Override
  public String hashName() {
    return this.hashName;
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
