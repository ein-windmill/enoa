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
package io.enoa.tryjson.eson.parser.def;

import io.enoa.tryjson.Tsonfig;
import io.enoa.tryjson.json.Jo;
import io.enoa.tryjson.thr.TryjsonException;

import java.util.ArrayList;
import java.util.List;

class JsonObjectParser extends AbstractJsonParser<Jo> {


  private static class Holder {
    private static final JsonObjectParser INSTANCE = new JsonObjectParser();
  }

  static JsonObjectParser instance() {
    return Holder.INSTANCE;
  }

  private JsonObjectParser() {

  }

  @Override
  public Jo parse(String json, Tsonfig config) throws TryjsonException {
    Jo jo = null;
    int len = json.length();
    List<Symbol> sblstack = new ArrayList<>();
    sblstack.add(Symbol.NONE);

    StringBuilder _key = new StringBuilder();
    StringBuilder _value = new StringBuilder();

    boolean stringValue = false;
    boolean objectValue = false;
    int ix = 0;
//    JSONFOREARCH:
    for (; ix < len; ix++) {
      char ch0 = json.charAt(ix);

      Symbol lastSymbol = sblstack.get(sblstack.size() - 1);
      switch (lastSymbol) {
          /*
          开始解析
           */
        case NONE:
          // 無效字符跳過
          if (super.skip(ch0))
            break;
          if (ch0 == '{') {
            sblstack.add(Symbol.JO_OPEN);
            jo = Jo.create();
            break;
          }
          throw new TryjsonException(""); // json 语法错误, 对象仅能以 { 开始, json 串中不存在

          /*
          进入 json object
           */
        case JO_OPEN:
          if (super.skip(ch0))
            break;
          if (ch0 != '"')
            throw new TryjsonException(""); // json 语法错误, 进入 json object , key 必须要从 " 开始
          sblstack.add(Symbol.SBL_STRING_START);
          break;

          /*
            字符串 开始
           */
        case SBL_STRING_START:
          if (ch0 == '"') {
            // 获取 " 前面的字符, 用于判定是否转义
            char ch1 = json.charAt(ix - 1);
            // 如果不是转义 " 断定后面第一个有效字符是否 :
            if (ch1 != '\\') {
              sblstack.add(Symbol.SBL_STRING_END);
              break;
            }
          }
          _key.append(ch0);
          break;

          /*
          字符串結束
           */
        case SBL_STRING_END:

          // 从 " 后面的第一个字符开始
          for (int sy = ix; sy < len; sy++) {
            ix = sy;
            char ch2 = json.charAt(sy);
            if (super.skip(ch2)) {
              continue;
            }
            if (ch2 != ':')
              throw new TryjsonException(""); // json 语法错误, key value 对称符号丢失
            sblstack.remove(sblstack.size() - 1);
            sblstack.remove(sblstack.size() - 1);

            sblstack.add(Symbol.JN_SYMMETRY);
//            continue JSONFOREARCH;
            break;
          }
          break;
          /*
          如果鉴定是 : 对称符, 将内容写入到 value 中
           */
        case JN_SYMMETRY:
          if (!stringValue && super.skip(ch0))
            break;
          if (jo == null)
            throw new TryjsonException(); // json 语法错误, 未进入正常 json 语法校验

          if (ch0 == ',' || ch0 == '}') {
            sblstack.remove(sblstack.size() - 1);
            if (ch0 == '}')
              sblstack.remove(sblstack.size() - 1);
            if (!objectValue)
              this.fillValue(jo, _key, this.stringValue(stringValue, _value), config);
            _key.delete(0, _key.length());
            _value.delete(0, _value.length());
            stringValue = false;
            objectValue = false;
            break;
          }
          if (ch0 == '{') {
            Block block = super.extraBlock('{', ix, json);
            Jo _jo0 = parse(block.json(), config);
            this.fillValue(jo, _key, _jo0, config);
            ix = block.end();
            _key.delete(0, _key.length());
            _value.delete(0, _value.length());
            stringValue = false;
            objectValue = true;
            break;
          }
          if (ch0 == '[') {

          }
          char ch1 = json.charAt(ix - 1);
          if (ch0 == '"' && ch1 == '\\' && stringValue) {
            Character ch2 = ix + 1 >= json.length() ? null : json.charAt(ix + 1);
            if (ch2 == null)
              break;
            if (ch2 == ' ' || ch2 == ':' || ch2 == '\n' || ch2 == '\r' || ch2 == '\t' || ch2 == ',')
              break;
            _value.append(ch0);
            break;
          }
          if (ch0 == '"' && ch1 != '\\' && stringValue) {
            break;
          }
          if (ch0 == '"') {
            stringValue = true;
            break;
          }
          _value.append(ch0);
          break;
      }
    }

    _key.delete(0, _key.length());
    _value.delete(0, _value.length());
    return jo;
  }

  private void fillValue(Jo jo, StringBuilder _key, Object value, Tsonfig config) {
    String key = _key.toString();
    if (config.skipNull() && value == null)
      return;

    if (value instanceof String) {
      Object _final = config.detector().detect((String) value, config);
      if (config.skipNull() && _final == null)
        return;

      jo.set(key, _final);
      return;
    }

    jo.set(key, value);
  }

  private String stringValue(boolean stringValue, StringBuilder value) {
    String val = value.toString();
    if ("null".equalsIgnoreCase(val) && !stringValue) {
      val = null;
    }
    return val;
  }

}
