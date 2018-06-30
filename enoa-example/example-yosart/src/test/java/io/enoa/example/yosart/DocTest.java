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
package io.enoa.example.yosart;

import com.sun.javadoc.*;
import com.sun.tools.javadoc.Main;
import io.enoa.toolkit.path.PathKit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DocTest {


  /**
   * 启动
   *
   * @param args args
   */
  public static void main(String[] args) throws IOException {
    System.out.println(PathKit.debugPath());
    Path example = PathKit.debugPath();
    List<String> jfs = new ArrayList<>();
    listFile(jfs, example);
    Main.execute("", jfs.toArray(new String[jfs.size()]));
  }


  private static List<String> listFile(List<String> jfs, Path path) throws IOException {
    Files.list(path).forEach(p -> {
      if (Files.isDirectory(p)) {
        try {
          listFile(jfs, p);
        } catch (IOException ignored) {
        }
        return;
      }
      if (!p.toString().endsWith(".java"))
        return;
      jfs.add(p.toString());
    });
    return jfs;
  }

  public static class Analyzer extends Doclet {

    /**
     * javadoc 解析
     *
     * @param root RootDoc
     * @return boolean
     */
    public static boolean start(RootDoc root) {
//      for (ClassDoc classDoc : root.classes()) {
//        System.out.println("Class: " + classDoc.qualifiedName());
//
//        for (MethodDoc methodDoc : classDoc.methods()) {
//          AnnotationDesc[] annotations = methodDoc.annotations();
//          System.out.println(methodDoc.commentText());
//          System.out.println(methodDoc.getRawCommentText());
//          System.out.println("  " + methodDoc.returnType() + " " + methodDoc.name() + methodDoc.signature());
//        }
//      }
//      return false;


      for (ClassDoc c : root.classes()) {
        print(c.qualifiedName(), c.commentText());
        for (FieldDoc f : c.fields(false)) {
          print(f.qualifiedName(), f.commentText());
        }
        for (MethodDoc m : c.methods(false)) {
          print(m.qualifiedName(), m.commentText());
          if (m.commentText() != null && m.commentText().length() > 0) {
            for (ParamTag p : m.paramTags())
              print(m.qualifiedName() + "@" + p.parameterName(), p.parameterComment());
            for (Tag t : m.tags("return")) {
              if (t.text() != null && t.text().length() > 0)
                print(m.qualifiedName() + "@return", t.text());
            }
          }
        }
      }
      return true;
    }
  }


  private static void print(String name, String comment) {
    if (comment != null && comment.length() > 0) {
      System.out.println(name + ": " + comment);
    }
  }

}
