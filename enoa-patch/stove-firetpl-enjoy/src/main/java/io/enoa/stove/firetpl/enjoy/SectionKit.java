/**
 * Copyright (c) 2011-2017, James Zhan 詹波 (jfinal@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.enoa.stove.firetpl.enjoy;

import com.jfinal.kit.StrKit;
import com.jfinal.template.Engine;
import com.jfinal.template.Template;
import com.jfinal.template.source.ISource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SectionKit
 */
@SuppressWarnings({"unchecked", "rawtypes"})
class SectionKit {

  static final String SECTION_TEMPLATE_MAP_KEY = "_SECTION_TEMPLATE_MAP_";
  static final String SECTION_PARA_KEY = "_SECTION_PARA_";
  static final String PARA_ARRAY_KEY = "_PARA_ARRAY_"; // 此参数保持不动，已被用于模板取值 _PARA_ARRAY_[n]

  private String configName;
  private boolean devMode;
  private Engine engine;
  private List<SectionSource> sectionSourceList = new ArrayList<>();
  private Map<String, Template> sectionTemplateMap;

  public SectionKit(String configName, boolean devMode) {
    this.configName = configName;
    this.devMode = devMode;

    engine = new Engine(configName);
    engine.setDevMode(devMode);

    engine.addDirective("namespace", NameSpaceDirective.class);
    engine.addDirective("section", SectionDirective.class);
    engine.addDirective("para", ParaDirective.class);
    engine.addDirective("p", ParaDirective.class);    // 配置 #para 指令的别名指令 #p，不建议使用，在此仅为兼容 3.0 版本
  }

  public SectionKit(String configName) {
    this(configName, false);
  }

  public Engine getEngine() {
    return engine;
  }

  public void setDevMode(boolean devMode) {
    this.devMode = devMode;
    engine.setDevMode(devMode);
  }

  public void setBaseSectionTemplatePath(String baseSqlTemplatePath) {
    engine.setBaseTemplatePath(baseSqlTemplatePath);
  }

  public void addSectionTemplate(String sqlTemplate) {
    if (StrKit.isBlank(sqlTemplate)) {
      throw new IllegalArgumentException("sqlTemplate can not be blank");
    }
    sectionSourceList.add(new SectionSource(sqlTemplate));
  }

  public void addSectionTemplate(ISource sqlTemplate) {
    if (sqlTemplate == null) {
      throw new IllegalArgumentException("sqlTemplate can not be null");
    }
    sectionSourceList.add(new SectionSource(sqlTemplate));
  }

  public synchronized void parseSectionTemplate() {
    Map<String, Template> sqlTemplateMap = new HashMap<String, Template>();
    for (SectionSource ss : sectionSourceList) {
      Template template = ss.isFile() ? engine.getTemplate(ss.file) : engine.getTemplate(ss.source);
      Map<Object, Object> data = new HashMap<Object, Object>();
      data.put(SECTION_TEMPLATE_MAP_KEY, sqlTemplateMap);
      template.renderToString(data);
    }
    this.sectionTemplateMap = sqlTemplateMap;
  }

  private void reloadModifiedSqlTemplate() {
    engine.removeAllTemplateCache();  // 去除 Engine 中的缓存，以免 get 出来后重新判断 isModified
    parseSectionTemplate();
  }

  private boolean isSqlTemplateModified() {
    for (Template template : sectionTemplateMap.values()) {
      if (template.isModified()) {
        return true;
      }
    }
    return false;
  }

  private Template getSectionTemplate(String key) {
    Template template = sectionTemplateMap.get(key);
    if (template == null) {  // 此 if 分支，处理起初没有定义，但后续不断追加 sql 的情况
      if (!devMode) {
        return null;
      }
      if (isSqlTemplateModified()) {
        synchronized (this) {
          if (isSqlTemplateModified()) {
            reloadModifiedSqlTemplate();
            template = sectionTemplateMap.get(key);
          }
        }
      }
      return template;
    }

    if (devMode && template.isModified()) {
      synchronized (this) {
        template = sectionTemplateMap.get(key);
        if (template.isModified()) {
          reloadModifiedSqlTemplate();
          template = sectionTemplateMap.get(key);
        }
      }
    }
    return template;
  }

  public String getSection(String key) {
    Template template = getSectionTemplate(key);
    return template != null ? template.renderToString(null) : null;
  }

  /**
   * 示例：
   * 1：section 定义
   * #section("key")
   * select * from xxx where id = #para(id) and age > #para(age)
   * #end
   * <p>
   * 2：java 代码
   * Kv cond = Kv.by("id", 123).set("age", 18);
   * getSectionPara("key", cond);
   */
  public SectionPara getSectionPara(String key, Map data) {
    Template template = getSectionTemplate(key);
    if (template == null) {
      return null;
    }

    SectionPara sectionPara = new SectionPara();
    data.put(SECTION_PARA_KEY, sectionPara);
    sectionPara.setSql(template.renderToString(data));
    data.remove(SECTION_PARA_KEY);  // 避免污染传入的 Map
    return sectionPara;
  }

  /**
   * 示例：
   * 1：section 定义
   * #section("key")
   * select * from xxx where a = #para(0) and b = #para(1)
   * #end
   * <p>
   * 2：java 代码
   * getSectionPara("key", 123, 456);
   */
  public SectionPara getSectionPara(String key, Object... paras) {
    Template template = getSectionTemplate(key);
    if (template == null) {
      return null;
    }

    SectionPara sectionPara = new SectionPara();
    Map data = new HashMap();
    data.put(SECTION_PARA_KEY, sectionPara);
    data.put(PARA_ARRAY_KEY, paras);
    sectionPara.setSql(template.renderToString(data));
    // data 为本方法中创建，不会污染用户数据，无需移除 SECTION_PARA_KEY、PARA_ARRAY_KEY
    return sectionPara;
  }

  public java.util.Set<Map.Entry<String, Template>> getSqlMapEntrySet() {
    return sectionTemplateMap.entrySet();
  }

  public String toString() {
    return "SectionKit for config : " + configName;
  }
}




