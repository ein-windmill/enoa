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
package io.enoa.provider.db.beetlsql;

import org.beetl.sql.core.*;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.KeyHolder;
import org.beetl.sql.core.db.MetadataManager;
import org.beetl.sql.core.engine.Beetl;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.kit.ConstantEnum;
import org.beetl.sql.core.mapper.MapperBuilder;
import org.beetl.sql.core.mapper.builder.MapperConfig;
import org.beetl.sql.core.mapping.BeanProcessor;
import org.beetl.sql.core.query.Query;
import org.beetl.sql.ext.gen.GenConfig;
import org.beetl.sql.ext.gen.GenFilter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class BeetlSQLMgr {

  private SQLManager manager;

  public BeetlSQLMgr(SQLManager manager) {
    this.manager = manager;
  }

  public SQLManager manager() {
    return this.manager;
  }

  public <T> Query<T> query(Class<T> clazz) {
    return this.manager.query(clazz);
  }

  public boolean isOffsetStartZero() {
    return this.manager.isOffsetStartZero();
  }

  public boolean isProductMode() {
    return this.manager.isProductMode();
  }

  public SQLResult getSQLResult(String id, Map<String, Object> paras) {
    return this.manager.getSQLResult(id, paras);
  }

  public SQLResult getSQLResult(String id, Object paras) {
    return this.manager.getSQLResult(id, paras);
  }

  public SQLResult getSQLResult(SQLSource source, Map inputParas) {
    return this.manager.getSQLResult(source, inputParas);
  }

  public SQLResult getSQLResult(String id, Map<String, Object> paras, String parentId) {
    return this.manager.getSQLResult(id, paras, parentId);
  }

  public SQLScript getScript(String id) {
    return this.manager.getScript(id);
  }

  public SQLScript getScript(Class<?> cls, ConstantEnum constantEnum) {
    return this.manager.getScript(cls, constantEnum);
  }

  public SQLScript getPageSqlScript(String selectId) {
    return this.manager.getPageSqlScript(selectId);
  }

  public <T> List<T> select(String sqlId, Class<T> clazz, Map<String, Object> paras) {
    return this.manager.select(sqlId, clazz, paras);
  }

  public <T> List<T> select(String sqlId, Class<T> clazz, Map<String, Object> paras, RowMapper<T> mapper) {
    return this.manager.select(sqlId, clazz, paras, mapper);
  }

  public <T> List<T> select(String sqlId, Class<T> clazz, Object paras) {
    return this.manager.select(sqlId, clazz, paras);
  }

  public <T> List<T> select(String sqlId, Class<T> clazz) {
    return this.manager.select(sqlId, clazz);
  }

  public <T> List<T> select(String sqlId, Class<T> clazz, Object paras, RowMapper<T> mapper) {
    return this.manager.select(sqlId, clazz, paras, mapper);
  }

  public <T> List<T> select(String sqlId, Class<T> clazz, Object paras, long start, long size) {
    return this.manager.select(sqlId, clazz, paras, start, size);
  }

  public <T> List<T> select(String sqlId, Class<T> clazz, Object paras, RowMapper<T> mapper, long start, long size) {
    return this.manager.select(sqlId, clazz, paras, mapper, start, size);
  }

  public <T> List<T> select(String sqlId, Class<T> clazz, Map<String, Object> paras, long start, long size) {
    return this.manager.select(sqlId, clazz, paras, start, size);
  }

  public <T> List<T> select(String sqlId, Class<T> clazz, Map<String, Object> paras, RowMapper<T> mapper, long start, long size) {
    return this.manager.select(sqlId, clazz, paras, mapper, start, size);
  }

  public <T> PageQuery<T> pageQuery(String sqlId, Class<T> clazz, PageQuery<T> query) {
    return this.manager.pageQuery(sqlId, clazz, query);
  }

  public <T> PageQuery<T> pageQuery(String sqlId, Class<T> clazz, PageQuery query, RowMapper<T> mapper) {
    return this.manager.pageQuery(sqlId, clazz, query, mapper);
  }

  public <T> T unique(Class<T> clazz, Object pk) {
    return this.manager.unique(clazz, pk);
  }

  public <T> T unique(Class<T> clazz, RowMapper<T> mapper, Object pk) {
    return this.manager.unique(clazz, mapper, pk);
  }

  public <T> T single(Class<T> clazz, Object pk) {
    return this.manager.single(clazz, pk);
  }

  public <T> T lock(Class<T> clazz, Object pk) {
    return this.manager.lock(clazz, pk);
  }

  public <T> List<T> all(Class<T> clazz) {
    return this.manager.all(clazz);
  }

  public <T> List<T> all(Class<T> clazz, long start, long size) {
    return this.manager.all(clazz, start, size);
  }

  public long allCount(Class<?> clazz) {
    return this.manager.allCount(clazz);
  }

  public <T> List<T> all(Class<T> clazz, RowMapper<T> mapper, long start, int end) {
    return this.manager.all(clazz, mapper, start, end);
  }

  public <T> List<T> all(Class<T> clazz, RowMapper<T> mapper) {
    return this.manager.all(clazz, mapper);
  }

  public <T> List<T> template(T t) {
    return this.manager.template(t);
  }

  public <T> T templateOne(T t) {
    return this.manager.templateOne(t);
  }

  public <T> List<T> template(T t, RowMapper mapper) {
    return this.manager.template(t, mapper);
  }

  public <T> List<T> template(T t, long start, long size) {
    return this.manager.template(t, start, size);
  }

  public <T> List<T> template(Class<T> target, Object paras, long start, long size) {
    return this.manager.template(target, paras, start, size);
  }

  public <T> List<T> template(T t, RowMapper mapper, long start, long size) {
    return this.manager.template(t, mapper, start, size);
  }

  public <T> List<T> template(Class<T> target, Object paras, RowMapper mapper, long start, long size) {
    return this.manager.template(target, paras, mapper, start, size);
  }

  public <T> long templateCount(T t) {
    return this.manager.templateCount(t);
  }

  public <T> long templateCount(Class<T> target, Object paras) {
    return this.manager.templateCount(target, paras);
  }

  public Long longValue(String id, Map<String, Object> paras) {
    return this.manager.longValue(id, paras);
  }

  public Long longValue(String id, Object paras) {
    return this.manager.longValue(id, paras);
  }

  public Integer intValue(String id, Object paras) {
    return this.manager.intValue(id, paras);
  }

  public Integer intValue(String id, Map<String, Object> paras) {
    return this.manager.intValue(id, paras);
  }

  public BigDecimal bigDecimalValue(String id, Object paras) {
    return this.manager.bigDecimalValue(id, paras);
  }

  public BigDecimal bigDecimalValue(String id, Map<String, Object> paras) {
    return this.manager.bigDecimalValue(id, paras);
  }

  public <T> T selectSingle(String sqlId, Object paras, Class<T> target) {
    return this.manager.selectSingle(sqlId, paras, target);
  }

  public <T> T selectSingle(String sqlId, Map<String, Object> paras, Class<T> target) {
    return this.manager.selectSingle(sqlId, paras, target);
  }

  public <T> T selectUnique(String id, Object paras, Class<T> target) {
    return this.manager.selectUnique(id, paras, target);
  }

  public <T> T selectUnique(String id, Map<String, Object> paras, Class<T> target) {
    return this.manager.selectUnique(id, paras, target);
  }

  public int deleteById(Class<?> clazz, Object pkValue) {
    return this.manager.deleteById(clazz, pkValue);
  }

  public int deleteObject(Object obj) {
    return this.manager.deleteObject(obj);
  }

  public int insert(Object paras) {
    return this.manager.insert(paras);
  }

  public int insert(Object paras, boolean autoDbAssignKey) {
    return this.manager.insert(paras, autoDbAssignKey);
  }

  public int insertTemplate(Object paras) {
    return this.manager.insertTemplate(paras);
  }

  public int insertTemplate(Object paras, boolean autoDbAssignKey) {
    return this.manager.insertTemplate(paras, autoDbAssignKey);
  }

  public int insert(Class clazz, Object paras, boolean autoDbAssignKey) {
    return this.manager.insert(clazz, paras, autoDbAssignKey);
  }

  public int insertTemplate(Class clazz, Object paras, boolean autoDbAssignKey) {
    return this.manager.insertTemplate(clazz, paras, autoDbAssignKey);
  }

  public int insert(Class<?> clazz, Object paras) {
    return this.manager.insert(clazz, paras);
  }

  public int[] insertBatch(Class clazz, List<?> list) {
    return this.manager.insertBatch(clazz, list);
  }

  public int insert(Class<?> clazz, Object paras, KeyHolder holder) {
    return this.manager.insert(clazz, paras, holder);
  }

  public int insertTemplate(Class<?> clazz, Object paras, KeyHolder holder) {
    return this.manager.insertTemplate(clazz, paras, holder);
  }

  public int insert(String sqlId, Object paras, KeyHolder holder, String keyName) {
    return this.manager.insert(sqlId, paras, holder, keyName);
  }

  public int insert(String sqlId, Object paras, KeyHolder holder) {
    return this.manager.insert(sqlId, paras, holder);
  }

  public int insert(String sqlId, Class<?> clazz, Map paras, KeyHolder holder) {
    return this.manager.insert(sqlId, clazz, paras, holder);
  }

  public int insert(String sqlId, Map paras, KeyHolder holder, String keyName) {
    return this.manager.insert(sqlId, paras, holder, keyName);
  }

  public int updateById(Object obj) {
    return this.manager.updateById(obj);
  }

  public int updateTemplateById(Object obj) {
    return this.manager.updateTemplateById(obj);
  }

  public int updateTemplateById(Class c, Map paras) {
    return this.manager.updateTemplateById(c, paras);
  }

  public int updateTemplateById(Class c, Object obj) {
    return this.manager.updateTemplateById(c, obj);
  }

  public int[] updateByIdBatch(List<?> list) {
    return this.manager.updateByIdBatch(list);
  }

  public int update(String sqlId, Object obj) {
    return this.manager.update(sqlId, obj);
  }

  public int update(String sqlId) {
    return this.manager.update(sqlId);
  }

  public int update(String sqlId, Map<String, Object> paras) {
    return this.manager.update(sqlId, paras);
  }

  public int[] updateBatch(String sqlId, List<?> list) {
    return this.manager.updateBatch(sqlId, list);
  }

  public int[] updateBatchTemplateById(Class clz, List<?> list) {
    return this.manager.updateBatchTemplateById(clz, list);
  }

  public int[] updateBatch(String sqlId, Map<String, Object>[] maps) {
    return this.manager.updateBatch(sqlId, maps);
  }

  public int updateAll(Class<?> clazz, Object param) {
    return this.manager.updateAll(clazz, param);
  }

  public void useMaster(DBRunner f) {
    this.manager.useMaster(f);
  }

  public void useSlave(DBRunner f) {
    this.manager.useSlave(f);
  }

  public <T> List<T> execute(String sqlTemplate, Class<T> clazz, Object paras) {
    return this.manager.execute(sqlTemplate, clazz, paras);
  }

  public <T> List<T> execute(String sqlTemplate, Class<T> clazz, Map paras) {
    return this.manager.execute(sqlTemplate, clazz, paras);
  }

  public <T> List<T> execute(String sqlTemplate, Class<T> clazz, Map paras, long start, long size) {
    return this.manager.execute(sqlTemplate, clazz, paras, start, size);
  }

  public <T> List<T> execute(String sqlTemplate, Class<T> clazz, Object paras, long start, long size) {
    return this.manager.execute(sqlTemplate, clazz, paras, start, size);
  }

  public int executeUpdate(String sqlTemplate, Object paras) {
    return this.manager.executeUpdate(sqlTemplate, paras);
  }

  public int executeUpdate(String sqlTemplate, Map paras) {
    return this.manager.executeUpdate(sqlTemplate, paras);
  }

  public <T> List<T> execute(SQLReady p, Class<T> clazz) {
    return this.manager.execute(p, clazz);
  }

  public <T> PageQuery<T> execute(SQLReady p, Class<T> clazz, PageQuery<T> pageQuery) {
    return this.manager.execute(p, clazz, pageQuery);
  }

  public int executeUpdate(SQLReady p) {
    return this.manager.executeUpdate(p);
  }

  public <T> T executeOnConnection(OnConnection<T> onConnection) {
    return this.manager.executeOnConnection(onConnection);
  }

  public void genPojoCode(String table, String pkg, String srcPath, GenConfig config) throws Exception {
    this.manager.genPojoCode(table, pkg, srcPath, config);
  }

  public void genPojoCode(String table, String pkg, GenConfig config) throws Exception {
    this.manager.genPojoCode(table, pkg, config);
  }

  public void genPojoCode(String table, String pkg) throws Exception {
    this.manager.genPojoCode(table, pkg);
  }

  public void genPojoCodeToConsole(String table) throws Exception {
    this.manager.genPojoCodeToConsole(table);
  }

  public void genPojoCodeToConsole(String table, GenConfig config) throws Exception {
    this.manager.genPojoCodeToConsole(table, config);
  }

  public void genSQLFile(String table) throws Exception {
    this.manager.genSQLFile(table);
  }

  public void genSQLFile(String table, String alias) throws Exception {
    this.manager.genSQLFile(table, alias);
  }

  public void genSQLTemplateToConsole(String table) throws Exception {
    this.manager.genSQLTemplateToConsole(table);
  }

  public void genSQLTemplateToConsole(String table, String alias) throws Exception {
    this.manager.genSQLTemplateToConsole(table, alias);
  }

  public void genALL(String pkg, GenConfig config, GenFilter filter) throws Exception {
    this.manager.genALL(pkg, config, filter);
  }

  public void genBuiltInSqlToConsole(Class cls) {
    this.manager.genBuiltInSqlToConsole(cls);
  }

  public <T> T getMapper(Class<T> mapperInterface) {
    return this.manager.getMapper(mapperInterface);
  }

  public SQLLoader getSqlLoader() {
    return this.manager.getSqlLoader();
  }

  public void setSqlLoader(SQLLoader sqlLoader) {
    this.manager.setSqlLoader(sqlLoader);
  }

  public ConnectionSource getDs() {
    return this.manager.getDs();
  }

  public void setDs(ConnectionSource ds) {
    this.manager.setDs(ds);
  }

  public NameConversion getNc() {
    return this.manager.getNc();
  }

  public void setNc(NameConversion nc) {
    this.manager.setNc(nc);
  }

  public DBStyle getDbStyle() {
    return this.manager.getDbStyle();
  }

  public Beetl getBeetl() {
    return this.manager.getBeetl();
  }

  public MetadataManager getMetaDataManager() {
    return this.manager.getMetaDataManager();
  }

  public String getDefaultSchema() {
    return this.manager.getDefaultSchema();
  }

  public void setDefaultSchema(String defaultSchema) {
    this.manager.setDefaultSchema(defaultSchema);
  }

  public MapperBuilder getMapperBuilder() {
    return this.manager.getMapperBuilder();
  }

  public void setMapperBuilder(MapperBuilder mapperBuilder) {
    this.manager.setMapperBuilder(mapperBuilder);
  }

  public Interceptor[] getInters() {
    return this.manager.getInters();
  }

  public void setInters(Interceptor[] inters) {
    this.manager.setInters(inters);
  }

  public void addIdAutonGen(String name, IDAutoGen alorithm) {
    this.manager.addIdAutonGen(name, alorithm);
  }

  public Map<String, BeanProcessor> getProcessors() {
    return this.manager.getProcessors();
  }

  public void setProcessors(Map<String, BeanProcessor> processors) {
    this.manager.setProcessors(processors);
  }

  public BeanProcessor getDefaultBeanProcessors() {
    return this.manager.getDefaultBeanProcessors();
  }

  public void setDefaultBeanProcessors(BeanProcessor defaultBeanProcessors) {
    this.manager.setDefaultBeanProcessors(defaultBeanProcessors);
  }

  public void setSQLIdNameConversion(SQLIdNameConversion sqlIdNc) {
    this.manager.setSQLIdNameConversion(sqlIdNc);
  }

  public SQLIdNameConversion getSQLIdNameConversion() {
    return this.manager.getSQLIdNameConversion();
  }

  public MapperConfig getMapperConfig() {
    return this.manager.getMapperConfig();
  }

  public MapperConfig setBaseMapper(Class c) {
    return this.manager.setBaseMapper(c);
  }

  public String getSQLManagerName() {
    return this.manager.getSQLManagerName();
  }
}
