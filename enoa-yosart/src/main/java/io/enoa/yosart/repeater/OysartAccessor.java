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
package io.enoa.yosart.repeater;

import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.RequestBody;
import io.enoa.repeater.http.Response;
import io.enoa.repeater.http.UFile;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.date.DateKit;
import io.enoa.toolkit.http.UriKit;
import io.enoa.toolkit.sys.ThrowableKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.yosart.Oysart;
import io.enoa.yosart.YoConfig;
import io.enoa.yosart.YoExt;
import io.enoa.yosart.kernel.data.YdAssets;
import io.enoa.yosart.kernel.ext.YmAssetsExt;
import io.enoa.yosart.kernel.ext.YmRouterExt;
import io.enoa.yosart.kit.tip.OysartTip;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class OysartAccessor implements EoxAccessor {

  private boolean initialized = false;
  private YoConfig config;
  private YmRouterExt routerExt;
  private YmAssetsExt assetsExt;
  private String assetsUri;

  private void init() {
    if (this.initialized)
      return;
    this.config = Oysart.config();
    this.routerExt = (YmRouterExt) Oysart.ext(YoExt.Type.ROUTER);
    this.assetsExt = (YmAssetsExt) Oysart.ext(YoExt.Type.ASSETS);
    this.initialized = true;
  }

  private void printLog(Request request) {
    if (!this.config.info())
      return;

    OysartTip.message("\n{0} {1}", 70, new Object[]{request.method().name(), request.uri()});

//    StringBuilder sb = new StringBuilder();
    long now = System.currentTimeMillis();
    OysartTip.message("TS:      {0} <=> {1}", DateKit.format(new Date(now), EoConst.DEF_FORMAT_DATE), now);
    OysartTip.message("Method:  {0}", request.method());

    OysartTip.message("Context: {0}", request.context());

    Map<String, String[]> paraMap = request.paraMap();
    if (CollectionKit.notEmpty(paraMap)) {
      StringBuilder sb = new StringBuilder();
      sb.append("Paras:   ");
      Set<String> names = paraMap.keySet();
      int i = 0;
      for (String k : names) {
        String[] v = paraMap.get(k);
        sb.append(k).append("=");
        if (v.length == 1) {
          sb.append(v[0]);
        } else {
          sb.append("[");
          sb.append(String.join(", ", v));
          sb.append("]");
        }
        if (i + 1 < names.size())
          sb.append(" ");
        i += 1;
      }
      OysartTip.message(sb.toString());
    }
    RequestBody body = request.body();
    if (body != null) {
      try {
        String data = body.string();
        if (TextKit.blankn(data)) {
          OysartTip.message("Body:    {0}", data);
        }
      } catch (Exception e) {
        OysartTip.message("Can not parse body => {0} => {1}", e.getMessage(), ThrowableKit.string(e));
      }
    }
    UFile[] ufiles = request.files();
    if (CollectionKit.notEmpty(ufiles)) {
      StringBuilder sb = new StringBuilder();
      sb.append("Files:   ");
      if (ufiles.length > 1) {
        sb.append("[");
      }
      int i = 0;
      for (UFile ufile : ufiles) {
        sb.append(ufile.name()).append("={");
        sb.append("filename: ").append(ufile.filename());
        sb.append(", ");
        sb.append("originname: ").append(ufile.originname());
        sb.append(", ");
        sb.append("path: ").append(ufile.path());
        sb.append("}");
        if (i + 1 < request.files().length)
          sb.append(", ");
        i += 1;
      }
      if (ufiles.length > 1) {
        sb.append("]");
      }
      OysartTip.message(sb.toString());
    }
    OysartTip.message("END", 70, CollectionKit.emptyArray(Object.class));
  }

  private String assetsUri() {
    YdAssets assets = Oysart.assets();
    if (assets == null)
      return null;

    if (this.assetsUri != null)
      return this.assetsUri;

    String uri = assets.uri();
    if (!uri.endsWith("/")) {
      uri = uri.concat("/");
    }
    this.assetsUri = uri;
    return this.assetsUri;
  }

  @Override
  public Response access(Request request) {
    this.init();
    this.printLog(request);
    String action = UriKit.rmcontext(request.context(), request.uri());
    String assets = this.assetsUri();
    if (assets != null) {
      if (action.concat("/").startsWith(assets)) {
        action = action.concat("/").replace(assets, "/");
        return this.assetsExt.handle(action, request);
      }
    }
    request.attr("_uri", action);

    return this.routerExt.handle(action, request);
  }

}
