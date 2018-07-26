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
package io.enoa.yosart.plugin.eml;

import io.enoa.eml.Eml;
import io.enoa.eml.EmlReceiver;
import io.enoa.eml.EmlSender;
import io.enoa.eml.EoEmlSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EmlKit {

  private EmlKit() {
  }

  private static class Holder {
    private static final Map<String, EoEmlSession> HOLDER_SESS = new ConcurrentHashMap<>();
  }

  static void add(String name, EoEmlSession sess) {
    Holder.HOLDER_SESS.put(name, sess);
  }

  static void clear() {
    Holder.HOLDER_SESS.clear();
  }

  public static EoEmlSession sess() {
    return sess("main");
  }

  public static EoEmlSession sess(String name) {
    return Holder.HOLDER_SESS.get(name);
  }

  public static Eml eml(String name) {
    EoEmlSession sess = sess(name);
    if (sess == null)
      return null;
    return Eml.with(sess);
  }

  public static Eml eml() {
    return eml("main");
  }

  public static EmlSender sender() {
    return sender("main");
  }

  public static EmlSender sender(String name) {
    Eml eml = eml(name);
    if (eml == null)
      return null;
    return eml.sender();
  }


  public static EmlReceiver receiver() {
    return receiver("main");
  }

  public static EmlReceiver receiver(String name) {
    Eml eml = eml(name);
    if (eml == null)
      return null;
    return eml.receiver();
  }

}
