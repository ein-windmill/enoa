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
package io.enoa.template;

import io.enoa.template.compressor.EoCompressorFactory;
import io.enoa.template.compressor.provider.htmlcompressor.HtmlCompressorProvider;
import io.enoa.template.provider.beetl.BeetlConfig;
import io.enoa.template.provider.beetl.BeetlProvider;
import io.enoa.template.provider.enjoy.EnjoyConfig;
import io.enoa.template.provider.enjoy.EnjoyProvider;
import io.enoa.template.provider.freemarker.FreemarkerConfig;
import io.enoa.template.provider.freemarker.FreemarkerProvider;
import io.enoa.template.provider.velocity.VelocityConfig;
import io.enoa.template.provider.velocity.VelocityProvider;
import io.enoa.toolkit.path.PathKit;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ein on 2017/8/20.
 */
public class EnoaTemplateTest {

  private Map<String, Object> data() {
    Map<String, Object> data = new HashMap<>();
    data.put("name", "Jen");
    data.put("age", 235);
    data.put("gender", 1);
    data.put("score", 23.23D);
    data.put("now", new Date());

    Map<String, Object> o = new HashMap<>();
    o.put("clazz", "3B");
    o.put("year", 2017);
    o.put("fa", "Kun");
    data.put("o", o);
    return data;
  }

  @Test
  public void testTpl() {
    this.testEnjoy();
    System.out.println("================================================\n");
    this.testBeetl();
    System.out.println("================================================\n");
    this.testFreemarker();
    System.out.println("================================================\n");
    this.testVelocity();
//    System.out.println("================================================\n");
//    this.testRocker();
  }

  private enum ENGINE {
    BEETL, ENJOY, FREEMARKER, ROCKER, VELOCITY
  }

  private EoEngineConfig genConfig(ENGINE engine) {
    String viewPath = String.format("%s/src/test/tpl", PathKit.bootPath());
    boolean compress = true;
    EoCompressorFactory compressor = new HtmlCompressorProvider();
    switch (engine) {
      case BEETL:
        return new BeetlConfig.Builder().viewPath(viewPath).compress().compressor(compressor).build();
      case ENJOY:
        return new EnjoyConfig.Builder().viewPath(viewPath).compress().compressor(compressor).build();
//      case ROCKER:
//        return new RockerConfig.Builder().viewPath(viewPath).compress().compressor(compressor).build();
      case VELOCITY:
        return new VelocityConfig.Builder().viewPath(viewPath).compress().compressor(compressor).build();
      case FREEMARKER:
        return new FreemarkerConfig.Builder().viewPath(viewPath).compress().compressor(compressor).build();
      default:
        throw new RuntimeException("Unknown engine");
    }
  }


  private void testEnjoy() {
    System.out.println("===================== ENJOY START");
    EnoaTemplateMgr.instance().defTemplateFactory(new EnjoyProvider()).defConfig(this.genConfig(ENGINE.ENJOY));
    EnoaEngine engine = EnoaTemplateMgr.instance().defEngine();

    EnoaTemplate a = engine.template("/enjoy/a.html");
    System.out.println(a.render(data()));

    EnoaTemplate b = engine.template("/enjoy/b.html");
    System.out.println(b.render(data()));
    System.out.println("===================== ENJOY END");
  }

  private void testBeetl() {
    System.out.println("===================== BEETL START");
    EnoaTemplateMgr.instance().defTemplateFactory(new BeetlProvider()).defConfig(this.genConfig(ENGINE.BEETL));
    EnoaEngine engine = EnoaTemplateMgr.instance().defEngine();

    EnoaTemplate a = engine.template("/beetl/a.html");
    System.out.println(a.render(data()));

    EnoaTemplate b = engine.template("/beetl/b.html");
    System.out.println(b.render(data()));

    System.out.println("===================== BEETL END");
  }

  private void testFreemarker() {
    System.out.println("===================== FREEMARKER START");
    EnoaTemplateMgr.instance().defTemplateFactory(new FreemarkerProvider()).defConfig(this.genConfig(ENGINE.FREEMARKER));
    EnoaEngine engine = EnoaTemplateMgr.instance().defEngine();

    EnoaTemplate a = engine.template("/freemarker/a.ftl");
    System.out.println(a.render(data()));

    EnoaTemplate b = engine.template("/freemarker/b.ftl");
    System.out.println(b.render(data()));

    System.out.println("===================== FREEMARKER END");
  }

  private void testVelocity() {
    System.out.println("===================== VELOCITY START");
    EnoaTemplateMgr.instance().defTemplateFactory(new VelocityProvider()).defConfig(this.genConfig(ENGINE.VELOCITY));
    EnoaEngine engine = EnoaTemplateMgr.instance().defEngine();

    EnoaTemplate a = engine.template("/velocity/a.vm");
    System.out.println(a.render(data()));

    EnoaTemplate b = engine.template("/velocity/b.vm");
    System.out.println(b.render(data()));

    System.out.println("===================== VELOCITY END");
  }

//  private void testRocker() {
//    System.out.println("===================== ROCKER START");
//    EnoaTemplateMgr.instance().defTemplateFactory(new RockerProvider()).defConfig(this.genConfig(ENGINE.ROCKER));
//    EnoaEngine engine = EnoaTemplateMgr.instance().defEngine();
//
//    EnoaTemplate a = engine.template("/rocker/a.rocker.html");
//    System.out.println(a.render(data()));
//
//    EnoaTemplate b = engine.template("/rocker/b.rocker.html");
//    System.out.println(b.render(data()));
//
//    System.out.println("===================== ROCKER END");
//  }
}
