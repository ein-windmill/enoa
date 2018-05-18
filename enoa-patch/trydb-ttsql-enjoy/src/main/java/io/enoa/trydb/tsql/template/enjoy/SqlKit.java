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

package io.enoa.trydb.tsql.template.enjoy;

import com.jfinal.kit.StrKit;
import com.jfinal.template.Engine;
import com.jfinal.template.Template;
import com.jfinal.template.source.ISource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SqlKit
 */
@SuppressWarnings({"unchecked", "rawtypes"})
class SqlKit {

  static final String SQL_TEMPLATE_MAP_KEY = "_SQL_TEMPLATE_MAP_";
  static final String SQL_PARA_KEY = "_SQL_PARA_";
  static final String PARA_ARRAY_KEY = "_PARA_ARRAY_"; // 此参数保持不动，已被用于模板取值 _PARA_ARRAY_[n]

  private String configName;
  private boolean devMode;
  private Engine engine;
  private List<SqlSource> sqlSourceList = new ArrayList<SqlSource>();
  private Map<String, Template> sqlTemplateMap;

  public SqlKit(String configName, boolean devMode) {
    this.configName = configName;
    this.devMode = devMode;

    engine = new Engine(configName);
    engine.setDevMode(devMode);

    engine.addDirective("namespace", NameSpaceDirective.class);
    engine.addDirective("sql", SqlDirective.class);
    engine.addDirective("para", ParaDirective.class);
    engine.addDirective("p", ParaDirective.class);    // 配置 #para 指令的别名指令 #p，不建议使用，在此仅为兼容 3.0 版本
  }

  public SqlKit(String configName) {
    this(configName, false);
  }

  public Engine getEngine() {
    return engine;
  }

  public void setDevMode(boolean devMode) {
    this.devMode = devMode;
    engine.setDevMode(devMode);
  }

  public void setBaseSqlTemplatePath(String baseSqlTemplatePath) {
    engine.setBaseTemplatePath(baseSqlTemplatePath);
  }

  public void addSqlTemplate(String sqlTemplate) {
    if (StrKit.isBlank(sqlTemplate)) {
      throw new IllegalArgumentException("sqlTemplate can not be blank");
    }
    sqlSourceList.add(new SqlSource(sqlTemplate));
  }

  public void addSqlTemplate(ISource sqlTemplate) {
    if (sqlTemplate == null) {
      throw new IllegalArgumentException("sqlTemplate can not be null");
    }
    sqlSourceList.add(new SqlSource(sqlTemplate));
  }

  public synchronized void parseSqlTemplate() {
    Map<String, Template> sqlTemplateMap = new HashMap<String, Template>();
    for (SqlSource ss : sqlSourceList) {
      Template template = ss.isFile() ? engine.getTemplate(ss.file) : engine.getTemplate(ss.source);
      Map<Object, Object> data = new HashMap<Object, Object>();
      data.put(SQL_TEMPLATE_MAP_KEY, sqlTemplateMap);
      template.renderToString(data);
    }
    this.sqlTemplateMap = sqlTemplateMap;
  }

  private void reloadModifiedSqlTemplate() {
    engine.removeAllTemplateCache();  // 去除 Engine 中的缓存，以免 get 出来后重新判断 isModified
    parseSqlTemplate();
  }

  private boolean isSqlTemplateModified() {
    for (Template template : sqlTemplateMap.values()) {
      if (template.isModified()) {
        return true;
      }
    }
    return false;
  }

  private Template getSqlTemplate(String key) {
    Template template = sqlTemplateMap.get(key);
    if (template == null) {  // 此 if 分支，处理起初没有定义，但后续不断追加 sql 的情况
      if (!devMode) {
        return null;
      }
      if (isSqlTemplateModified()) {
        synchronized (this) {
          if (isSqlTemplateModified()) {
            reloadModifiedSqlTemplate();
            template = sqlTemplateMap.get(key);
          }
        }
      }
      return template;
    }

    if (devMode && template.isModified()) {
      synchronized (this) {
        template = sqlTemplateMap.get(key);
        if (template.isModified()) {
          reloadModifiedSqlTemplate();
          template = sqlTemplateMap.get(key);
        }
      }
    }
    return template;
  }

  public String getSql(String key) {
    Template template = getSqlTemplate(key);
    return template != null ? template.renderToString(null) : null;
  }

  /**
   * 示例：
   * 1：sql 定义
   * #sql("key")
   * select * from xxx where id = #para(id) and age > #para(age)
   * #end
   * <p>
   * 2：java 代码
   * Kv cond = Kv.by("id", 123).set("age", 18);
   * getSqlPara("key", cond);
   */
  public SqlPara getSqlPara(String key, Map data) {
    Template template = getSqlTemplate(key);
    if (template == null) {
      return null;
    }

    SqlPara sqlPara = new SqlPara();
    data.put(SQL_PARA_KEY, sqlPara);
    sqlPara.setSql(template.renderToString(data));
    data.remove(SQL_PARA_KEY);  // 避免污染传入的 Map
    return sqlPara;
  }

  /**
   * 示例：
   * 1：sql 定义
   * #sql("key")
   * select * from xxx where a = #para(0) and b = #para(1)
   * #end
   * <p>
   * 2：java 代码
   * getSqlPara("key", 123, 456);
   */
  public SqlPara getSqlPara(String key, Object... paras) {
    Template template = getSqlTemplate(key);
    if (template == null) {
      return null;
    }

    SqlPara sqlPara = new SqlPara();
    Map data = new HashMap();
    data.put(SQL_PARA_KEY, sqlPara);
    data.put(PARA_ARRAY_KEY, paras);
    sqlPara.setSql(template.renderToString(data));
    // data 为本方法中创建，不会污染用户数据，无需移除 SQL_PARA_KEY、PARA_ARRAY_KEY
    return sqlPara;
  }

  public java.util.Set<Map.Entry<String, Template>> getSqlMapEntrySet() {
    return sqlTemplateMap.entrySet();
  }

  public String toString() {
    return "SqlKit for config : " + configName;
  }
}




