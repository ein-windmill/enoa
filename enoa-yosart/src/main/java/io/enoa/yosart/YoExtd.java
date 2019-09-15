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
package io.enoa.yosart;

import io.enoa.log.Log;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.is.Is;
import io.enoa.yosart.kernel.ext.*;
import io.enoa.yosart.thr.OyExtException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class YoExtd {

  private static final YoExtd yo = new YoExtd();

  private final List<YoExt> exts = new ArrayList<>();

  public static YoExtd yo() {
    return yo;
  }

  private void unload(YoExt newExt) {
    for (int i = this.exts.size(); i-- > 0; ) {
      YoExt oldExt = this.exts.get(i);
      if (newExt.type() != oldExt.type())
        continue;
      if (!oldExt.type().onlyOne())
        continue;
      boolean same = oldExt.getClass().getName().equalsIgnoreCase(newExt.getClass().getName());
      Log.warn(EnoaTipKit.message("eo.tip.yosart.ext_only_one",
        same ? oldExt.toString() : oldExt.getClass().getName(),
        same ? newExt.toString() : newExt.getClass().getName()));
      this.exts.remove(i);
    }
  }

  private void verifyReg(YoExt ext) {
    String superClazz;
    switch (ext.type()) {
      case BOOT_HOOK:
        if (ext instanceof YmBootHookExt)
          return;
        superClazz = YmBootHookExt.class.getName();
        break;
      case BOOT_END:
        if (ext instanceof YmBootExt)
          return;
        superClazz = YmBootExt.class.getName();
        break;
      case EXCEPTION:
        if (ext instanceof YmExceptionExt)
          return;
        superClazz = YmExceptionExt.class.getName();
        break;
      case RENDER:
        if (ext instanceof YmRenderExt)
          return;
        superClazz = YmRenderExt.class.getName();
        break;
      case ASSETS:
        if (ext instanceof YmAssetsExt)
          return;
        superClazz = YmAssetsExt.class.getName();
        break;
      case ROUTER:
        if (ext instanceof YmRouterExt)
          return;
        superClazz = YmRouterExt.class.getName();
        break;
//      case BEFORE_ACTION:
//        if (ext instanceof YmBeforeActionExt)
//          return;
//        superClazz = YmBeforeActionExt.class.getName();
//        break;
      case SESSION:
        if (ext instanceof YmSessionExt)
          return;
        superClazz = YmSessionExt.class.getName();
        break;
      default:
        throw new OyExtException(EnoaTipKit.message("eo.tip.yosart.ext_unrecognized_type", ext.type()));
    }
    throw new OyExtException(EnoaTipKit.message("eo.tip.yosart.ext_must_impl", ext.type(), superClazz, ext.getClass().getName()));
  }

  YoExtd reg(YoExt ext, boolean def) {
    if (ext == null)
      throw new OyExtException(EnoaTipKit.message("eo.tip.yosart.ext_cant_null"));
    if (ext.type() == null)
      throw new OyExtException(EnoaTipKit.message("eo.tip.yosart.ext_type_cant_null"));
    this.verifyReg(ext);
    if (ext instanceof YmRenderExt) {
      if (Is.not().truthy(((YmRenderExt) ext).renderType()))
        throw new OyExtException(EnoaTipKit.message("eo.tip.yosart.ext_render_type_cant_null", ext.getClass().getName()));
    }
    if (Is.not().truthy(ext.name()))
      throw new OyExtException(EnoaTipKit.message("eo.tip.yosart.ext_name_cant_null", ext.getClass().getName()));
    if (Is.not().truthy(ext.version()))
      throw new OyExtException(EnoaTipKit.message("eo.tip.yosart.ext_version_cant_null", ext.getClass().getName()));
    if (ext.weight() < 0 && ext.weight() > 10)
      throw new OyExtException(EnoaTipKit.message("eo.tip.yosart.ext_weight_range", ext.weight(), ext.getClass().getName()));
    if (!def)
      if (ext.weight() < 1 || ext.weight() > 9)
        throw new OyExtException(EnoaTipKit.message("eo.tip.yosart.ext_reserved", ext.weight(), ext.getClass().getName()));

    if (ext.type().onlyOne())
      this.unload(ext);

    Iterator<YoExt> eiterator = this.exts.iterator();
    while (eiterator.hasNext()) {
      YoExt yet = eiterator.next();
      // renew same extension
      if (yet.getClass().getName().equals(ext.getClass().getName())) {
        eiterator.remove();
        continue;
      }

      if (ext instanceof YmRenderExt) {
        if (!(yet instanceof YmRenderExt))
          continue;
        if (((YmRenderExt) yet).renderType().equals(((YmRenderExt) ext).renderType()))
          eiterator.remove();
      }
    }

    this.exts.add(ext);
    return this;
  }

  YoExtd reg(YoExt dext) {
    return this.reg(dext, false);
  }

  YoExt[] exts() {
    return this.exts.toArray(new YoExt[this.exts.size()]);
  }

//  YoExt ext(YoExt.Type type) {
//    YoExt[] exts = this.exts(type);
//    return Is.empty(exts) ? null : exts[0];
//  }
//
//  YoExt[] exts(YoExt.Type type) {
//    return Arrays.stream(this.exts()).filter(e -> e.type() == type).toArray(YoExt[]::new);
//  }

}
