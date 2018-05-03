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
package io.enoa.example.yosart.anost.ctrl;

import io.enoa.example.yosart.anost.hook.IndexHook;
import io.enoa.json.kit.JsonKit;
import io.enoa.log.kit.LogKit;
import io.enoa.toolkit.ret.EoResp;
import io.enoa.toolkit.ret.Ret;
import io.enoa.yosart.ext.anost.hook.Hook;
import io.enoa.yosart.ext.anost.para.Para;
import io.enoa.yosart.ext.anost.para.Paras;
import io.enoa.yosart.kernel.anno.Action;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.kernel.resources.YeControl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class IndexCtrl extends YeControl<IndexCtrl> {

  @Action("go")
  @Hook(IndexHook.class)
  @Paras({
    @Para(index = 3, value = "ts", format = "yyyy-MM-dd"),
    @Para(index = 1, value = "where", def = "street #1"),
    @Para(value = "zone", def = "9")
  })
  public Ret go(YoRequest request, String where, int zone, Timestamp ts) {
    LogKit.debug("REQUEST: {}", request.data());
    LogKit.debug("WHERE: {}", where);
    LogKit.debug("ZONE: {}", zone);
    LogKit.debug("TS: {}", ts);
    return EoResp.ok("OK");
  }

  @Para("pas")
  public Ret arr0(String[] pas) {
    LogKit.debug(JsonKit.toJson(pas));
    return EoResp.ok();
  }

  @Para("pas")
  public Ret arr1(List<Integer> pas) {
    LogKit.debug(JsonKit.toJson(pas));
    return EoResp.ok();
  }

  @Para("pas")
  public Ret arr2(Set<Integer> pas) {
    LogKit.debug(JsonKit.toJson(pas));
    return EoResp.ok();
  }

  @Para("pas")
  public Ret arr3(TreeSet<Double> pas) {
    LogKit.debug(JsonKit.toJson(pas));
    return EoResp.ok();
  }

  @Para("pas")
  public Ret arr4(short... pas) {
    LogKit.debug(JsonKit.toJson(pas));
    return EoResp.ok();
  }

}
