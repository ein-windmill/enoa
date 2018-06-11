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
package io.enoa.index.solr.parser;

import ikidou.reflect.TypeBuilder;
import io.enoa.index.solr.ret.SError;
import io.enoa.index.solr.ret.SResponse;
import io.enoa.index.solr.ret.SRet;
import io.enoa.json.EnoaJson;
import io.enoa.json.EoJsonFactory;
import io.enoa.json.Json;

import java.lang.reflect.Type;

public class JsonParser<T> implements SParser<SRet<T>> {

  private EnoaJson json;
  private Type type;

  private JsonParser(EnoaJson json, Type type) {
    this.type = type;
    this.json = json;
  }

  private JsonParser(Type type) {
    this.type = type;
  }

  public static <R> JsonParser<R> create(EoJsonFactory ejf, Class<R> type) {
    return new JsonParser<>(ejf.json(), type);
  }

  public static <R> JsonParser<R> create(EoJsonFactory ejf, Type type) {
    return new JsonParser<>(ejf.json(), type);
  }

  public static <R> JsonParser<R> create(Class<R> type) {
    return new JsonParser<>(type);
  }

  public static <R> JsonParser<R> create(Type type) {
    return new JsonParser<>(type);
  }

  @Override
  public SRet<T> result(String resp) {
    if (resp == null)
      return null;
    String qtime = "\"QTime\"";
    String docs = "\"docs\"";
    String error = "\"error\"";
    String metadata = "\"metadata\"";

    /*
    校驗是否有返回值, 表示爲查詢結果
     */
    Type _type = null;

    int qix = resp.indexOf(qtime);
    if (qix > 0) {

      int hqix = qix + qtime.length();
      int dix = resp.indexOf(docs, hqix);
      if (dix > 0) {
        _type = TypeBuilder.newInstance(SRet.class)
          .beginSubType(SResponse.class)
          .addTypeParam(this.type)
          .endSubType()
          .build();
      }

    /*
    檢查是否有錯誤消息, 判定爲失敗信息
     */
      int eix = resp.indexOf(error, hqix);
      int mix = resp.indexOf(metadata, eix);
      if (eix > 0 && mix > 0) {
        _type = TypeBuilder.newInstance(SRet.class)
          .addTypeParam(SError.class)
          .build();
      }
    } else {

//      int eix = resp.indexOf(error);
//      int mix = resp.indexOf(metadata, eix);
//      if (eix > 0 && mix > 0) {
//
//      }

      _type = SRet.class;
    }


    EnoaJson json = this.json == null ? Json.epm().json() : this.json;
    return json.parse(resp, _type);
  }
}
