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
package io.enoa.yosart.kit.inject;


import io.enoa.repeater.http.Request;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.reflect.ReflectKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;

import java.lang.reflect.Method;

/**
 * EoxInjector.
 */
public class EoxInjector {

  public static <T> T injectBean(Class<T> clazz, Request request, boolean skipConvertError) {
    return injectBean(clazz, null, request, skipConvertError);
  }

  @SuppressWarnings("unchecked")
  public static final <T> T injectBean(Class<T> clazz, String beanName, Request request, boolean skipConvertError) {
    Object bean = ReflectKit.newInstance(clazz).build();
    Method[] methods = clazz.getMethods();
    for (Method method : methods) {
      String methodName = method.getName();
      // only setter method
      if (!methodName.startsWith("set") || methodName.length() <= 3)
        continue;

      Class<?>[] types = method.getParameterTypes();
      // only one parameter
      if (types.length != 1)
        continue;

      String attrName = TextKit.firstToLower(methodName.substring(3));
      String paraName = TextKit.isBlank(beanName) ? attrName : TextKit.union(beanName, ".", attrName);

      String paraValue = request.para(paraName, null);
      if (paraValue == null)
        continue;

      try {
        Object value = ConvertKit.to(paraValue, types[0]);
        method.invoke(bean, value);
      } catch (Exception e) {
        if (!skipConvertError) {
          throw new EoException(e.getMessage(), e);
        }
      }
    }

    return (T) bean;
  }

}

