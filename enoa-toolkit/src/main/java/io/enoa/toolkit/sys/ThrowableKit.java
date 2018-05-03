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
package io.enoa.toolkit.sys;

import io.enoa.toolkit.thr.EnoaException;
import io.enoa.toolkit.thr.EoException;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowableKit {

  private ThrowableKit() {
  }

  public static String string(Throwable e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    return sw.toString();
  }

  public static Throwable accurate(Throwable e) {
    return accurate(e, true);
  }

  /**
   * 精確錯誤定位, 從異常棧中取出最終報錯的位置
   *
   * @param e        異常
   * @param priority 是否優先考慮 enoa 異常
   * @return Throwable
   */
  public static Throwable accurate(Throwable e, boolean priority) {
    if (e == null)
      throw new EoException("Throwable == null");
    if (priority && (e instanceof EoException || e instanceof EnoaException))
      return e;
    if (e.getCause() == null)
      return e;
    return accurate(e.getCause(), priority);
  }

  /**
   * 定位異常棧中某個異常
   *
   * @param e      異常
   * @param target 定位異常
   * @return Throwable
   */
  public static Throwable position(Throwable e, Class<? extends Throwable> target) {
    if (e == null)
      throw new EoException("Throwable == null");
    if (e.getClass().getName().equals(target.getName()))
      return e;
    if (e.getCause() == null)
      return null;
    return position(e.getCause(), target);
  }

}
