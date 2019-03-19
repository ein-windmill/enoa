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
package io.enoa.yosart.ext.anost;

import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.UFile;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.date.DateKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.number.NumberKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.yosart.ext.anost.para.Para;
import io.enoa.yosart.ext.anost.para.Paras;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.kernel.resources.OyControlResource;
import io.enoa.yosart.resp.Renderer;
import io.enoa.yosart.resp.Resp;

import java.io.File;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AnostParasBuilder {

  private static Map<String, List<ParaVal>> PVALS = new HashMap<>();

  /**
   * 构建控制器参数
   *
   * @param request  http request
   * @param resp     Response builder
   * @param resource controller resources
   * @return Object[]
   */
  public Object[] build(YoRequest request, Resp resp, OyControlResource resource) {
    // 取出
//    List<ParaVal> pvals = this.pvals(resource);
    Parameter[] paras = resource.paras();
    Object[] args = new Object[paras.length];
    for (int i = 0; i < paras.length; i++) {
      Parameter para = paras[i];
      ParaVal pval = this.pval(resource, i);
      if (pval == null)
        continue;
      args[i] = this.fillValue(request, resp, para, pval);
    }
    return args;
  }

  /**
   * 提取請求參數值
   * 會首先從 para 中獲取, 如果不能獲取嘗試通過 restful 風格 url 中提取參數
   *
   * @param request request
   * @param pval    ParaVal
   * @return String
   */
  private String parseValue(YoRequest request, ParaVal pval) {
    String val = request.para(pval.name);
    if (TextKit.blankn(val))
      return val;
    val = request.variable(pval.name);
    return val == null ? pval.def : val;
  }

  private Object fillValue(YoRequest request, Resp resp, Parameter parameter, ParaVal pval) {
    // handle request
    Class<?> type = parameter.getType();

    Object data;
    if (Collection.class.isAssignableFrom(type) || type.isArray()) {
      return this.parseArray(type.isArray(), request, parameter, type, pval);
    }

    data = this.parseObject(type, request, resp, pval);
//    if (data != null)
//      return data;

    // can not support now.

//    try {
//      // handle inject bean
//      return request.bean(type, "");
//    } catch (EoReflectException ignored) {
//      // if not inject exception, it's not a java bean. can not identity this type. other exception throw it.
//      return null;
//    }

    return data;
  }

  private Object parseArray(boolean isArray, YoRequest request, Parameter parameter, Class<?> type, ParaVal pval) {

//    Class<?> componentType = type.getComponentType();

    // ident component type
    Class<?> componentType;
    try {
      if (isArray) {
        componentType = type.getComponentType();
      } else {
        Type ptype = parameter.getParameterizedType();
        ParameterizedType parameterizedType = (ParameterizedType) ptype;
        Type[] argTypes = parameterizedType.getActualTypeArguments();
        componentType = (Class<?>) argTypes[0];
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }

    String[] vals = request.paraValues(pval.name);
    if (CollectionKit.isEmpty(vals))
      return CollectionKit.emptyArray(componentType);

    // 數組解析
    if (isArray) {
      Object ret = Array.newInstance(componentType, vals.length);
      for (int i = vals.length; i-- > 0; ) {
        Array.set(ret, i, ConvertKit.to(vals[i], componentType));
      }
      return ret;
    }

    // collection 解析
    Collection colecs;
    try {
      colecs = this.newCollection(type);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }

    if (UFile.class.isAssignableFrom(componentType)) {
      colecs.add(request.files(pval.name));
      return colecs;
    }
    if (File.class.isAssignableFrom(componentType)) {
      UFile[] ufiles = request.files(pval.name);
      if (CollectionKit.isEmpty(ufiles))
        return colecs;
      for (UFile ufile : ufiles) {
        colecs.add(ufile.file());
      }
      return colecs;
    }

    for (String val : vals)
      colecs.add(ConvertKit.to(val, componentType));

    return colecs;
  }

  private Collection newCollection(Class<?> clazz) throws Exception {
    if (clazz == List.class)
      return new ArrayList();
    if (clazz == Set.class)
      return new HashSet();
    if (clazz == Collection.class)
      return new ArrayList();
    return (Collection) clazz.newInstance();
  }

  private Object parseObject(Class<?> type, YoRequest request, Resp resp, ParaVal pval) {
    if (type == YoRequest.class || type == Request.class)
      return request;
    // handle response
//    if (type == Resp.class || type == Renderer.class)
    if (Renderer.class.isAssignableFrom(type))
      return resp;
    if (UFile.class.isAssignableFrom(type))
      return request.file(pval.name);
    if (File.class.isAssignableFrom(type)) {
      UFile ufile = request.file(pval.name);
      return ufile == null ? null : ufile.file();
    }

    String value = this.parseValue(request, pval);

    // handle java data type
    if (type == String.class)
      return value;
    if (type == Boolean.class)
      return ConvertKit.bool(value, null);
    if (type == Integer.class)
      return ConvertKit.integer(value, null);
    if (type == Double.class)
      return ConvertKit.doubler(value, null);
    if (type == Float.class)
      return NumberKit.floater(value);
    if (type == Long.class)
      return NumberKit.longer(value);
    if (type == Short.class)
      return NumberKit.shorter(value);
    if (type == int.class)
      return value == null ? 0 : NumberKit.integer(value);
    if (type == double.class)
      return value == null ? 0D : NumberKit.doubler(value);
    if (type == float.class)
      return value == null ? 0F : NumberKit.floater(value);
    if (type == long.class)
      return value == null ? 0L : NumberKit.longer(value);
    if (type == short.class)
      return value == null ? (short) 0 : NumberKit.shorter(value);
    if (type == boolean.class)
      return ConvertKit.bool(value, false);

    if (type == Number.class)
      return NumberKit.to(value, Number.class);
    if (type == BigDecimal.class)
      return NumberKit.bigdecimal(value);
    // handle date & timestamp
    if (type == Date.class)
      return DateKit.parse(value, pval.format);
    if (type == java.sql.Date.class) {
      Date date = DateKit.parse(value, pval.format);
      return date == null ? null : new java.sql.Date(date.getTime());
    }
    if (type == Timestamp.class) {
      Date date = DateKit.parse(value, pval.format);
      return date == null ? null : new Timestamp(date.getTime());
    }

    return value;
  }

  private ParaVal pval(OyControlResource resource, int ix) {
    List<ParaVal> pvals = this.pvals(resource);
    ParaVal ret = null;
    for (ParaVal pval : pvals) {
      if (pval.ix == null)
        continue;
      if (!pval.ix.equals(ix))
        continue;
      ret = pval;
      break;
    }
    if (ret != null)
      return ret;
    if (ix > pvals.size() - 1)
      return null;
    return pvals.get(ix);
  }

  private List<ParaVal> pvals(OyControlResource resource) {
    List<ParaVal> pvals = PVALS.get(resource.hashName());
    if (pvals != null)
      return pvals;

    Method func = resource.func();
    Paras paras = func.getAnnotation(Paras.class);
    Para para = func.getAnnotation(Para.class);
    List<Para> pas = null;
    if (paras != null) {
      pas = Stream.of(paras.value()).collect(Collectors.toList());
    }
    if (para != null) {
      if (pas == null)
        pas = new ArrayList<>();
      pas.add(para);
    }

    if (CollectionKit.isEmpty(pas))
      return Collections.emptyList();

    if (pas != null) {
      pvals = pas.stream()
        .map(p ->
          new ParaVal(
            p.index() == -1 ? null : p.index(),
            TextKit.blanky(p.value()) ? null : p.value(),
            TextKit.blanky(p.def()) ? null : p.def(),
            TextKit.blanky(p.format()) ? null : p.format())
        ).collect(Collectors.toList());
    }

    PVALS.put(resource.hashName(), pvals);
    return pvals;
  }

  /**
   * Paras 注解填入的值解析结果
   */
  private static class ParaVal {
    private Integer ix;
    private String name;
    private String def;
    private String format;

    private ParaVal(Integer ix, String name, String def) {
      this(ix, name, def, null);
    }

    private ParaVal(Integer ix, String name, String def, String format) {
      if (TextKit.nully(name))
        throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.yosart.ext.anost.paras_val_name_null"));
      this.ix = ix;
      this.name = name;
      this.def = def;
      this.format = format;
    }

//    private static ParaVal of(String text) {
//      if (TextKit.nully(text))
//        return null;
//      int sym0Ix = text.indexOf(":");
//      Integer ix = null;
//      if (sym0Ix != -1) {
//        ix = NumberKit.integer(text.substring(0, sym0Ix));
//        text = text.substring(sym0Ix + 1);
//      }
//      int sym1Ix = text.indexOf("#");
//      String def = null;
//      if (sym1Ix != -1) {
//        def = text.substring(sym1Ix + 1);
//        text = text.substring(0, sym1Ix);
//      }
//      return new ParaVal(ix, text, def);
//    }

//    public int ix() {
//      return this.ix;
//    }
//
//    public String name() {
//      return this.name;
//    }
//
//    public String def() {
//      return this.def;
//    }


    @Override
    public String toString() {
      return "ParaVal{" +
        "ix=" + ix +
        ", name='" + name + '\'' +
        ", def='" + def + '\'' +
        '}';
    }
  }

}
