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
package io.enoa.yosart.ext.yemgr;

import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Response;
import io.enoa.template.EnoaEngine;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.text.TextKit;
import io.enoa.yosart.Oysart;
import io.enoa.yosart.YoExt;
import io.enoa.yosart.YoPlugin;
import io.enoa.yosart.kernel.data.YdMount;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.kernel.mount.OyMountd;
import io.enoa.yosart.kernel.resources.*;
import io.enoa.yosart.resp.Renderer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Yemgr extends YoRouter {

  private final String styles;
  private EnoaEngine engine;

  Yemgr(EnoaEngine engine) {
    this.engine = engine;
    styles = this.engine.template("styles.min.css").render();
  }

  @Override
  public String context() {
    return "/~";
  }

  @Override
  protected void register() {
    super.add("/", this::home)
      .add("/routers", this::routers)
      .add("/exts", this::exts)
      .add("/plugins", this::plugins)
      .add("/mounts", this::mounts);

    List<YdMount> mounts = OyMountd.mounts();
    if (Is.empty(mounts))
      return;
    mounts.forEach(m -> {
//      String uri = String.format("/mounts/%s", m.uri().startsWith("/") ? m.uri().substring(1, m.uri().length()) : m.uri());
      String uri = TextKit.union("/mounts/", m.uri().startsWith("/") ? m.uri().substring(1, m.uri().length()) : m.uri());
      YaResource resource = m.resource();
      if (resource instanceof YoAction)
        super.add(uri, (YoAction) m.resource());
      if (resource instanceof YoRouter)
        super.add(uri, (YoRouter) resource);
    });
  }

  private List<Kv> menus() {
    List<Kv> menus = new ArrayList<>();
    menus.add(Kv.by("uri", "/").set("name", "Home"));
    menus.add(Kv.by("uri", "/routers").set("name", "Routers"));
    menus.add(Kv.by("uri", "/exts").set("name", "Extensions"));
    menus.add(Kv.by("uri", "/plugins").set("name", "Plugins"));
    menus.add(Kv.by("uri", "/mounts").set("name", "Mounts"));
    return menus;
  }


  private Response home(YoRequest request) {
    Kv attr = Kv.by("subtitle", "Home")
      .set("body", "Home body");
    try {
      return Renderer.with(request).renderHtml(this.render(request, attr, "home")).end();
    } catch (Exception e) {
      return Renderer.with(request).renderError(HttpStatus.INTERNAL_ERROR, e).end();
    }
  }

  private Response routers(YoRequest request) {
    try {

      Kv attr = Kv.by("subtitle", "Routers");
      Map<String, OyResource[]> resources = Oysart.resources();
      List<Kv> rets = new ArrayList<>();
      resources.forEach((uri, res) -> {
        for (OyResource resource : res) {
          Kv routers = Kv.create();

          routers.set("uri", uri);
          routers.set("type", resource.type().name());
          String methods = String.join(", ", Stream.of(resource.methods()).map(Enum::name).collect(Collectors.toSet()));
          routers.set("methods", methods);
          routers.set("clazz", resource.type() == OyResource.Type.ACTION ? ((OyActionResource) resource).action().getClass().getName() : ((OyControlResource) resource).control().getName());
          routers.set("func", resource.type() == OyResource.Type.ACTION ? ((OyActionResource) resource).funcName() : ((OyControlResource) resource).funcName());
          String annos;
          switch (resource.type()) {
            case ACTION:
              Annotation[] aos0 = ((OyActionResource) resource).func().getAnnotations();
              annos = String.join(", ", Stream.of(aos0).map(a -> a.annotationType().getName()).collect(Collectors.toSet()));
              break;
            case CONTROL:
              Annotation[] aos1 = ((OyControlResource) resource).annos();
              annos = String.join(", ", Stream.of(aos1).map(a -> a.annotationType().getName()).collect(Collectors.toSet()));
              break;
            default:
              annos = "";
          }
          routers.set("annos", annos);
          String paras;
          switch (resource.type()) {
            case ACTION:
              paras = YoRequest.class.getName();
              break;
            case CONTROL:
              Parameter[] p0 = ((OyControlResource) resource).paras();
              paras = String.join(", ", Stream.of(p0).map(p -> p.getName().concat("::").concat(p.getType().getName())).collect(Collectors.toSet()));
              break;
            default:
              paras = "";
          }
          routers.set("paras", paras);
          routers.set("vars", String.join(", ", resource.vars()));
          rets.add(routers);
        }
      });
      attr.set("routers", rets);
      return Renderer.with(request).renderHtml(this.render(request, attr, "router")).end();
    } catch (Exception e) {
      return Renderer.with(request).renderError(HttpStatus.INTERNAL_ERROR, e).end();
    }
  }

  private Response exts(YoRequest request) {
    Kv attr = Kv.by("subtitle", "Extensions");

    YoExt[] exts = Oysart.exts();
    List<Kv> rets = new ArrayList<>();
    for (YoExt ext : exts) {
      if (rets.stream().anyMatch(r -> r.string("clazz").equals(ext.getClass().getName())))
        continue;
      Kv data = Kv.create();
      data.set("name", ext.name())
        .set("type", ext.type())
        .set("weight", ext.weight())
        .set("version", ext.version())
        .set("clazz", ext.getClass().getName());
      rets.add(data);
    }
    attr.set("exts", rets);
    try {
      return Renderer.with(request).renderHtml(this.render(request, attr, "ext")).end();
    } catch (Exception e) {
      return Renderer.with(request).renderError(HttpStatus.INTERNAL_ERROR, e).end();
    }
  }

  private Response plugins(YoRequest request) {
    Kv attr = Kv.by("subtitle", "Plugins");
    YoPlugin[] plugins = Oysart.plugins();
    List<Kv> rets = new ArrayList<>();
    for (YoPlugin plugin : plugins) {
      if (rets.stream().anyMatch(r -> r.string("clazz").equals(plugin.getClass().getName())))
        continue;
      Kv data = Kv.create();
      String name = plugin.name();
      if (plugin.name() == null) {
        name = plugin.getClass().getName();
        name = name.substring(name.lastIndexOf(".") + 1, name.length());
      }
      String version = plugin.version() == null ? "?" : plugin.version();
      data.set("name", name)
        .set("version", version)
        .set("description", plugin.description())
        .set("clazz", plugin.getClass().getName());
      rets.add(data);
    }
    attr.set("plugins", rets);
    try {
      return Renderer.with(request).renderHtml(this.render(request, attr, "plugin")).end();
    } catch (Exception e) {
      return Renderer.with(request).renderError(HttpStatus.INTERNAL_ERROR, e).end();
    }
  }

  private Response mounts(YoRequest request) {
    Kv attr = Kv.by("subtitle", "Mounts");
    List<YdMount> mounts = OyMountd.mounts();

    Kv mts = Kv.create();
    mounts.forEach(m -> {
      List<Kv> ms = mts.as(m.group());
      if (ms == null)
        ms = new ArrayList<>();
      ms.add(Kv.by("uri", m.uri())
        .set("name", m.name())
        .set("description", m.description()));
      mts.set(m.group(), ms);
    });

    attr.set("mount", mts);
    try {
      return Renderer.with(request).renderHtml(this.render(request, attr, "mount")).end();
    } catch (Exception e) {
      return Renderer.with(request).renderError(HttpStatus.INTERNAL_ERROR, e).end();
    }
  }

  private String render(YoRequest request, Kv attr, String viewName) {
    attr.set("name", Oysart.name())
      .set("version", Oysart.version())
      .set("styles", this.styles)
      .set("menus", this.menus())
      .set("req", request.data())
      .set("yemgrc", this.context())
      .set("context", request.context().concat(this.context()));
    return this.engine.template(viewName.concat(".html")).render(attr);
  }

}
