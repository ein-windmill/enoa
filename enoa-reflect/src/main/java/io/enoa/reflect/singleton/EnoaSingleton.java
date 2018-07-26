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
package io.enoa.reflect.singleton;

import io.enoa.reflect.Component;
import io.enoa.reflect.cache.ReflectCache;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class EnoaSingleton {

  public EnoaSingleton scan(String pkg) {
    if (pkg == null || "".equals(pkg))
      throw new IllegalArgumentException("Scan package can not be null.");
    List<Object> singletons = this.singletons(pkg);
    singletons.forEach(s -> ReflectCache.cache(s.getClass().getName(), s));
    return this;
  }


  private List<Object> singletons(String pkg) {
    FastClasspathScanner scanner = new FastClasspathScanner(pkg);
    ScanResult scan = scanner.scan();
    List<String> names = scan.getNamesOfClassesWithAnnotation(Component.class);
    List<Class<?>> clazzes = scan.classNamesToClassRefs(names);
    List<Object> singletons = new ArrayList<>();
    for (Class<?> clazz : clazzes) {
      try {
        int modifiers = clazz.getModifiers();
        if (Modifier.isAbstract(modifiers)) {
          continue;
        }
        if (Modifier.isInterface(modifiers)) {
          continue;
        }
        try {
          Object esn = clazz.newInstance();
          singletons.add(esn);
        } catch (Exception e) {
          continue;
        }
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage(), e);
      }
    }
    return singletons;
  }


}
