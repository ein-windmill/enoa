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
package io.enoa.json.provider.enoa;

import io.enoa.json.EnoaJson;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Json 转换 JFinal 实现.
 * <p>
 * https://github.com/jfinal/jfinal/blob/master/src/main/java/com/jfinal/json/JFinalJson.java
 * <p>
 * json 到 java 类型转换规则:
 * string			java.lang.String
 * number			java.lang.Number
 * true|false		java.lang.Boolean
 * null				null
 * array			java.util.List
 * object			java.util.Map
 */
@SuppressWarnings({"rawtypes", "unchecked"})
class _JFinalJson extends EnoaJson {

  private static int defaultConvertDepth = 15;

  private int convertDepth = defaultConvertDepth;
  //  private String timestampPattern = "yyyy-MM-dd HH:mm:ss";
  private static final String DEF_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

//  @Override
//  public Object origin() {
//    return null;
//  }

  @Override
  public String toJson(Object object) {
    return toJson(object, convertDepth, null);
  }

  @Override
  public String toJson(Object object, String datePattern) {
    return this.toJson(object, convertDepth, datePattern);
  }

  @Override
  public <T> T parse(String json, Class<T> type) {
    return null;
  }

  @Override
  public <T> T parse(String json, Type type) {
    return null;
  }

  @Override
  public <T> List<T> parseArray(String json, Class<T> type) {
    return null;
  }


  private String toJson(Object value, int depth) {
    return this.toJson(value, depth, null);
  }

  private String toJson(Object value, int depth, String datePattern) {
    if (value == null || (depth--) < 0)
      return "null";

    if (value instanceof String)
      return "\"" + escape((String) value) + "\"";

    if (value instanceof Double) {
      if (((Double) value).isInfinite() || ((Double) value).isNaN())
        return "null";
      else
        return value.toString();
    }

    if (value instanceof Float) {
      if (((Float) value).isInfinite() || ((Float) value).isNaN())
        return "null";
      else
        return value.toString();
    }

    if (value instanceof Number)
      return value.toString();

    if (value instanceof Boolean)
      return value.toString();

    if (value instanceof java.util.Date) {
//      if (value instanceof java.sql.Timestamp) {
//        return "\"" + new SimpleDateFormat(timestampPattern).format(value) + "\"";
//      }
//      if (value instanceof java.sql.Time) {
//        return "\"" + value.toString() + "\"";
//      }
//      String dp = this.getDateFormat();
//      if (dp != null) {
//        return "\"" + new SimpleDateFormat(dp).format(value) + "\"";
//      } else {
//        return "" + ((java.util.Date) value).getTime();
//      }
      if (datePattern == null)
        return String.valueOf(((Date) value).getTime());
      return "\"" + new SimpleDateFormat(datePattern).format(value) + "\"";
    }

    if (value instanceof Collection) {
      return iteratorToJson(((Collection) value).iterator(), depth);
    }

    if (value instanceof Map) {
      return mapToJson((Map) value, depth);
    }

    String result = otherToJson(value, depth);
    if (result != null)
      return result;

    // 类型无法处理时当作字符串处理,否则ajax调用返回时js无法解析
    // return value.toString();
    return "\"" + escape(value.toString()) + "\"";
  }

//  private String getDateFormat() {
//    if (datePattern != null)
//      return datePattern;
//    if (defaultDatePattern() != null)
//      return defaultDatePattern();
//    return DEF_DATE_PATTERN;
//  }
//
//
//  public EnoaJson datePattern(String datePattern) {
//    if (datePattern == null || "".equals(datePattern))
//      throw new IllegalArgumentException("datePattern can not be blank.");
//
//    this.datePattern = datePattern;
//    return this;
//  }

  /**
   * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F).
   */
  private String escape(String s) {
    if (s == null)
      return null;
    StringBuilder sb = new StringBuilder();
    escape(s, sb);
    return sb.toString();
  }

  private void escape(String s, StringBuilder sb) {
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      switch (ch) {
        case '"':
          sb.append("\\\"");
          break;
        case '\\':
          sb.append("\\\\");
          break;
        case '\b':
          sb.append("\\b");
          break;
        case '\f':
          sb.append("\\f");
          break;
        case '\n':
          sb.append("\\n");
          break;
        case '\r':
          sb.append("\\r");
          break;
        case '\t':
          sb.append("\\t");
          break;
        //case '/':
        //	sb.append("\\/");
        //	break;
        default:
          if ((ch >= '\u0000' && ch <= '\u001F') || (ch >= '\u007F' && ch <= '\u009F') || (ch >= '\u2000' && ch <= '\u20FF')) {
            String str = Integer.toHexString(ch);
            sb.append("\\u");
            for (int k = 0; k < 4 - str.length(); k++) {
              sb.append('0');
            }
            sb.append(str.toUpperCase());
          } else {
            sb.append(ch);
          }
      }
    }
  }

  private String mapToJson(Map map, int depth) {
    if (map == null) {
      return "null";
    }
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    Iterator iter = map.entrySet().iterator();

    sb.append('{');
    while (iter.hasNext()) {
      if (first)
        first = false;
      else
        sb.append(',');

      Map.Entry entry = (Map.Entry) iter.next();
      toKeyValue(String.valueOf(entry.getKey()), entry.getValue(), sb, depth);
    }
    sb.append('}');
    return sb.toString();
  }

  private void toKeyValue(String key, Object value, StringBuilder sb, int depth) {
    sb.append('\"');
    if (key == null)
      sb.append("null");
    else
      escape(key, sb);
    sb.append('\"').append(':');
    sb.append(toJson(value, depth));
  }


  private String iteratorToJson(Iterator iter, int depth) {
    boolean first = true;
    StringBuilder sb = new StringBuilder();

    sb.append('[');
    while (iter.hasNext()) {
      if (first)
        first = false;
      else
        sb.append(',');

      Object value = iter.next();
      if (value == null) {
        sb.append("null");
        continue;
      }
      sb.append(toJson(value, depth));
    }
    sb.append(']');
    return sb.toString();
  }


  private String otherToJson(Object value, int depth) {
    if (value instanceof Character) {
      return "\"" + escape(value.toString()) + "\"";
    }

//    if (value instanceof Model) {
//      Map map = com.jfinal.plugin.activerecord.CPI.getAttrs((Model)value);
//      return mapToJson(map, depth);
//    }
//    if (value instanceof Record) {
//      Map map = ((Record)value).getColumns();
//      return mapToJson(map, depth);
//    }
    if (value.getClass().isArray()) {
      int len = Array.getLength(value);
      List<Object> list = new ArrayList<>(len);
      for (int i = 0; i < len; i++) {
        list.add(Array.get(value, i));
      }
      return iteratorToJson(list.iterator(), depth);
    }
    if (value instanceof Iterator) {
      return iteratorToJson((Iterator) value, depth);
    }
    if (value instanceof Enumeration) {
      ArrayList<?> list = Collections.list((Enumeration<?>) value);
      return iteratorToJson(list.iterator(), depth);
    }
    if (value instanceof Enum) {
      return "\"" + value.toString() + "\"";
    }

    return beanToJson(value, depth);
  }

  private String beanToJson(Object model, int depth) {
    Map map = new HashMap();
    Method[] methods = model.getClass().getMethods();
    for (Method m : methods) {
      String methodName = m.getName();
      int indexOfGet = methodName.indexOf("get");
      if (indexOfGet == 0 && methodName.length() > 3) {  // Only getter
        String attrName = methodName.substring(3);
        if (!attrName.equals("Class")) {        // Ignore Object.getClass()
          Class<?>[] types = m.getParameterTypes();
          if (types.length == 0) {
            try {
              Object value = m.invoke(model);
              map.put(this.firstCharToLowerCase(attrName), value);
            } catch (Exception e) {
              throw new RuntimeException(e.getMessage(), e);
            }
          }
        }
      } else {
        int indexOfIs = methodName.indexOf("is");
        if (indexOfIs == 0 && methodName.length() > 2) {
          String attrName = methodName.substring(2);
          Class<?>[] types = m.getParameterTypes();
          if (types.length == 0) {
            try {
              Object value = m.invoke(model);
              map.put(this.firstCharToLowerCase(attrName), value);
            } catch (Exception e) {
              throw new RuntimeException(e.getMessage(), e);
            }
          }
        }
      }
    }
    return mapToJson(map, depth);
  }

  private String firstCharToLowerCase(String str) {
    char firstChar = str.charAt(0);
    if (firstChar >= 'A' && firstChar <= 'Z') {
      char[] arr = str.toCharArray();
      arr[0] += ('a' - 'A');
      return new String(arr);
    }
    return str;
  }
}
