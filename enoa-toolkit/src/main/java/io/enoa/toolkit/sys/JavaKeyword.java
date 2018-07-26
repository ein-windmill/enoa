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
package io.enoa.toolkit.sys;


import io.enoa.toolkit.text.TextKit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * JavaKeyword.
 */
public class JavaKeyword {

  private String[] keywordArray = {
    "abstract",
    "assert",
    "boolean",
    "break",
    "byte",
    "case",
    "catch",
    "char",
    "class",
    "const",
    "continue",
    "default",
    "do",
    "double",
    "else",
    "enum",
    "extends",
    "final",
    "finally",
    "float",
    "for",
    "goto",
    "if",
    "implements",
    "import",
    "instanceof",
    "int",
    "interface",
    "long",
    "native",
    "new",
    "package",
    "private",
    "protected",
    "public",
    "return",
    "strictfp",
    "short",
    "static",
    "super",
    "switch",
    "synchronized",
    "this",
    "throw",
    "throws",
    "transient",
    "try",
    "void",
    "volatile",
    "while"
  };

  private Set<String> set;


  private static class Holder {
    private static final JavaKeyword instance = new JavaKeyword();
  }

//  public static final JavaKeyword me = createSharedInstance();
//
//  private static JavaKeyword createSharedInstance() {
//    JavaKeyword jk = new JavaKeyword();
//    jk.set = Collections.unmodifiableSet(jk.set);  // 共享对象不让修改
//    return jk;
//  }

  public static JavaKeyword instance() {
    return Holder.instance;
  }

  private JavaKeyword() {
    set = new HashSet<>();
    set.addAll(Arrays.asList(keywordArray));
  }

  public JavaKeyword add(String keyword) {
    if (TextKit.blankn(keyword)) {
      set.add(keyword);
    }
    return this;
  }

  public JavaKeyword remove(String keyword) {
    set.remove(keyword);
    return this;
  }

  public boolean contains(String str) {
    return set.contains(str);
  }
}






