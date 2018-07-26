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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BeetlSQLKit {

  private static Map<String, BeetlSQLMgr> MANAGER_MAP = new ConcurrentHashMap<>();

  private BeetlSQLKit() {
  }

  static void add(String name, SQLManager manager) {
    Set<String> names = MANAGER_MAP.keySet();
    if (names.stream().anyMatch(k -> k.equals(name)))
      throw new RuntimeException(String.format("Already exists this name: [%s] -> %s", String.join(", ", name), name));
    MANAGER_MAP.put(name, new BeetlSQLMgr(manager));
  }

  private static BeetlSQLMgr use() {
    return use("main");
  }

  public static BeetlSQLMgr use(String name) {
    BeetlSQLMgr mgr = MANAGER_MAP.get(name);
    if (mgr == null)
      throw new RuntimeException(String.format("This name is not exists. [%s] -> %s", String.join(", ", name), name));
    return MANAGER_MAP.get(name);
  }

  public static SQLManager manager() {
    return use().manager();
  }

  public static <T> Query<T> query(Class<T> clazz) {
    return use().query(clazz);
  }

  public static boolean isOffsetStartZero() {
    return use().isOffsetStartZero();
  }

  public static boolean isProductMode() {
    return use().isProductMode();
  }

  public static SQLResult getSQLResult(String id, Map<String, Object> paras) {
    return use().getSQLResult(id, paras);
  }

  public static SQLResult getSQLResult(String id, Object paras) {
    return use().getSQLResult(id, paras);
  }

  public static SQLResult getSQLResult(SQLSource source, Map inputParas) {
    return use().getSQLResult(source, inputParas);
  }

  public static SQLResult getSQLResult(String id, Map<String, Object> paras, String parentId) {
    return use().getSQLResult(id, paras, parentId);
  }

  public static SQLScript getScript(String id) {
    return use().getScript(id);
  }

  public static SQLScript getScript(Class<?> cls, ConstantEnum constantEnum) {
    return use().getScript(cls, constantEnum);
  }

  public static SQLScript getPageSqlScript(String selectId) {
    return use().getPageSqlScript(selectId);
  }

  public static <T> List<T> select(String sqlId, Class<T> clazz, Map<String, Object> paras) {
    return use().select(sqlId, clazz, paras);
  }

  public static <T> List<T> select(String sqlId, Class<T> clazz, Map<String, Object> paras, RowMapper<T> mapper) {
    return use().select(sqlId, clazz, paras, mapper);
  }

  public static <T> List<T> select(String sqlId, Class<T> clazz, Object paras) {
    return use().select(sqlId, clazz, paras);
  }

  public static <T> List<T> select(String sqlId, Class<T> clazz) {
    return use().select(sqlId, clazz);
  }

  public static <T> List<T> select(String sqlId, Class<T> clazz, Object paras, RowMapper<T> mapper) {
    return use().select(sqlId, clazz, paras, mapper);
  }

  public static <T> List<T> select(String sqlId, Class<T> clazz, Object paras, long start, long size) {
    return use().select(sqlId, clazz, paras, start, size);
  }

  public static <T> List<T> select(String sqlId, Class<T> clazz, Object paras, RowMapper<T> mapper, long start, long size) {
    return use().select(sqlId, clazz, paras, mapper, start, size);
  }

  public static <T> List<T> select(String sqlId, Class<T> clazz, Map<String, Object> paras, long start, long size) {
    return use().select(sqlId, clazz, paras, start, size);
  }

  public static <T> List<T> select(String sqlId, Class<T> clazz, Map<String, Object> paras, RowMapper<T> mapper, long start, long size) {
    return use().select(sqlId, clazz, paras, mapper, start, size);
  }

  public static <T> PageQuery<T> pageQuery(String sqlId, Class<T> clazz, PageQuery<T> query) {
    return use().pageQuery(sqlId, clazz, query);
  }

  public static <T> PageQuery<T> pageQuery(String sqlId, Class<T> clazz, PageQuery query, RowMapper<T> mapper) {
    return use().pageQuery(sqlId, clazz, query, mapper);
  }

  public static <T> T unique(Class<T> clazz, Object pk) {
    return use().unique(clazz, pk);
  }

  public static <T> T unique(Class<T> clazz, RowMapper<T> mapper, Object pk) {
    return use().unique(clazz, mapper, pk);
  }

  public static <T> T single(Class<T> clazz, Object pk) {
    return use().single(clazz, pk);
  }

  public static <T> T lock(Class<T> clazz, Object pk) {
    return use().lock(clazz, pk);
  }

  public static <T> List<T> all(Class<T> clazz) {
    return use().all(clazz);
  }

  public static <T> List<T> all(Class<T> clazz, long start, long size) {
    return use().all(clazz, start, size);
  }

  public static long allCount(Class<?> clazz) {
    return use().allCount(clazz);
  }

  public static <T> List<T> all(Class<T> clazz, RowMapper<T> mapper, long start, int end) {
    return use().all(clazz, mapper, start, end);
  }

  public static <T> List<T> all(Class<T> clazz, RowMapper<T> mapper) {
    return use().all(clazz, mapper);
  }

  public static <T> List<T> template(T t) {
    return use().template(t);
  }

  public static <T> T templateOne(T t) {
    return use().templateOne(t);
  }

  public static <T> List<T> template(T t, RowMapper mapper) {
    return use().template(t, mapper);
  }

  public static <T> List<T> template(T t, long start, long size) {
    return use().template(t, start, size);
  }

  public static <T> List<T> template(Class<T> target, Object paras, long start, long size) {
    return use().template(target, paras, start, size);
  }

  public static <T> List<T> template(T t, RowMapper mapper, long start, long size) {
    return use().template(t, mapper, start, size);
  }

  public static <T> List<T> template(Class<T> target, Object paras, RowMapper mapper, long start, long size) {
    return use().template(target, paras, mapper, start, size);
  }

  public static <T> long templateCount(T t) {
    return use().templateCount(t);
  }

  public static <T> long templateCount(Class<T> target, Object paras) {
    return use().templateCount(target, paras);
  }

  public static Long longValue(String id, Map<String, Object> paras) {
    return use().longValue(id, paras);
  }

  public static Long longValue(String id, Object paras) {
    return use().longValue(id, paras);
  }

  public static Integer intValue(String id, Object paras) {
    return use().intValue(id, paras);
  }

  public static Integer intValue(String id, Map<String, Object> paras) {
    return use().intValue(id, paras);
  }

  public static BigDecimal bigDecimalValue(String id, Object paras) {
    return use().bigDecimalValue(id, paras);
  }

  public static BigDecimal bigDecimalValue(String id, Map<String, Object> paras) {
    return use().bigDecimalValue(id, paras);
  }

  public static <T> T selectSingle(String sqlId, Object paras, Class<T> target) {
    return use().selectSingle(sqlId, paras, target);
  }

  public static <T> T selectSingle(String sqlId, Map<String, Object> paras, Class<T> target) {
    return use().selectSingle(sqlId, paras, target);
  }

  public static <T> T selectUnique(String id, Object paras, Class<T> target) {
    return use().selectUnique(id, paras, target);
  }

  public static <T> T selectUnique(String id, Map<String, Object> paras, Class<T> target) {
    return use().selectUnique(id, paras, target);
  }

  public static int deleteById(Class<?> clazz, Object pkValue) {
    return use().deleteById(clazz, pkValue);
  }

  public static int deleteObject(Object obj) {
    return use().deleteObject(obj);
  }

  public static int insert(Object paras) {
    return use().insert(paras);
  }

  public static int insert(Object paras, boolean autoDbAssignKey) {
    return use().insert(paras, autoDbAssignKey);
  }

  public static int insertTemplate(Object paras) {
    return use().insertTemplate(paras);
  }

  public static int insertTemplate(Object paras, boolean autoDbAssignKey) {
    return use().insertTemplate(paras, autoDbAssignKey);
  }

  public static int insert(Class clazz, Object paras, boolean autoDbAssignKey) {
    return use().insert(clazz, paras, autoDbAssignKey);
  }

  public static int insertTemplate(Class clazz, Object paras, boolean autoDbAssignKey) {
    return use().insertTemplate(clazz, paras, autoDbAssignKey);
  }

  public static int insert(Class<?> clazz, Object paras) {
    return use().insert(clazz, paras);
  }

  public static int[] insertBatch(Class clazz, List<?> list) {
    return use().insertBatch(clazz, list);
  }

  public static int insert(Class<?> clazz, Object paras, KeyHolder holder) {
    return use().insert(clazz, paras, holder);
  }

  public static int insertTemplate(Class<?> clazz, Object paras, KeyHolder holder) {
    return use().insertTemplate(clazz, paras, holder);
  }

  public static int insert(String sqlId, Object paras, KeyHolder holder, String keyName) {
    return use().insert(sqlId, paras, holder, keyName);
  }

  public static int insert(String sqlId, Object paras, KeyHolder holder) {
    return use().insert(sqlId, paras, holder);
  }

  public static int insert(String sqlId, Class<?> clazz, Map paras, KeyHolder holder) {
    return use().insert(sqlId, clazz, paras, holder);
  }

  public static int insert(String sqlId, Map paras, KeyHolder holder, String keyName) {
    return use().insert(sqlId, paras, holder, keyName);
  }

  public static int updateById(Object obj) {
    return use().updateById(obj);
  }

  public static int updateTemplateById(Object obj) {
    return use().updateTemplateById(obj);
  }

  public static int updateTemplateById(Class c, Map paras) {
    return use().updateTemplateById(c, paras);
  }

  public static int updateTemplateById(Class c, Object obj) {
    return use().updateTemplateById(c, obj);
  }

  public static int[] updateByIdBatch(List<?> list) {
    return use().updateByIdBatch(list);
  }

  public static int update(String sqlId, Object obj) {
    return use().update(sqlId, obj);
  }

  public static int update(String sqlId) {
    return use().update(sqlId);
  }

  public static int update(String sqlId, Map<String, Object> paras) {
    return use().update(sqlId, paras);
  }

  public static int[] updateBatch(String sqlId, List<?> list) {
    return use().updateBatch(sqlId, list);
  }

  public static int[] updateBatchTemplateById(Class clz, List<?> list) {
    return use().updateBatchTemplateById(clz, list);
  }

  public static int[] updateBatch(String sqlId, Map<String, Object>[] maps) {
    return use().updateBatch(sqlId, maps);
  }

  public static int updateAll(Class<?> clazz, Object param) {
    return use().updateAll(clazz, param);
  }

  public static void useMaster(DBRunner f) {
    use().useMaster(f);
  }

  public static void useSlave(DBRunner f) {
    use().useSlave(f);
  }

  public static <T> List<T> execute(String sqlTemplate, Class<T> clazz, Object paras) {
    return use().execute(sqlTemplate, clazz, paras);
  }

  public static <T> List<T> execute(String sqlTemplate, Class<T> clazz, Map paras) {
    return use().execute(sqlTemplate, clazz, paras);
  }

  public static <T> List<T> execute(String sqlTemplate, Class<T> clazz, Map paras, long start, long size) {
    return use().execute(sqlTemplate, clazz, paras, start, size);
  }

  public static <T> List<T> execute(String sqlTemplate, Class<T> clazz, Object paras, long start, long size) {
    return use().execute(sqlTemplate, clazz, paras, start, size);
  }

  public static int executeUpdate(String sqlTemplate, Object paras) {
    return use().executeUpdate(sqlTemplate, paras);
  }

  public static int executeUpdate(String sqlTemplate, Map paras) {
    return use().executeUpdate(sqlTemplate, paras);
  }

  public static <T> List<T> execute(SQLReady p, Class<T> clazz) {
    return use().execute(p, clazz);
  }

  public static <T> PageQuery<T> execute(SQLReady p, Class<T> clazz, PageQuery<T> pageQuery) {
    return use().execute(p, clazz, pageQuery);
  }

  public static int executeUpdate(SQLReady p) {
    return use().executeUpdate(p);
  }

  public static <T> T executeOnConnection(OnConnection<T> onConnection) {
    return use().executeOnConnection(onConnection);
  }

  public static void genPojoCode(String table, String pkg, String srcPath, GenConfig config) throws Exception {
    use().genPojoCode(table, pkg, srcPath, config);
  }

  public static void genPojoCode(String table, String pkg, GenConfig config) throws Exception {
    use().genPojoCode(table, pkg, config);
  }

  public static void genPojoCode(String table, String pkg) throws Exception {
    use().genPojoCode(table, pkg);
  }

  public static void genPojoCodeToConsole(String table) throws Exception {
    use().genPojoCodeToConsole(table);
  }

  public static void genPojoCodeToConsole(String table, GenConfig config) throws Exception {
    use().genPojoCodeToConsole(table, config);
  }

  public static void genSQLFile(String table) throws Exception {
    use().genSQLFile(table);
  }

  public static void genSQLFile(String table, String alias) throws Exception {
    use().genSQLFile(table, alias);
  }

  public static void genSQLTemplateToConsole(String table) throws Exception {
    use().genSQLTemplateToConsole(table);
  }

  public static void genSQLTemplateToConsole(String table, String alias) throws Exception {
    use().genSQLTemplateToConsole(table, alias);
  }

  public static void genALL(String pkg, GenConfig config, GenFilter filter) throws Exception {
    use().genALL(pkg, config, filter);
  }

  public static void genBuiltInSqlToConsole(Class cls) {
    use().genBuiltInSqlToConsole(cls);
  }

  public static <T> T getMapper(Class<T> mapperInterface) {
    return use().getMapper(mapperInterface);
  }

  public static SQLLoader getSqlLoader() {
    return use().getSqlLoader();
  }

  public static void setSqlLoader(SQLLoader sqlLoader) {
    use().setSqlLoader(sqlLoader);
  }

  public static ConnectionSource getDs() {
    return use().getDs();
  }

  public static void setDs(ConnectionSource ds) {
    use().setDs(ds);
  }

  public static NameConversion getNc() {
    return use().getNc();
  }

  public static void setNc(NameConversion nc) {
    use().setNc(nc);
  }

  public static DBStyle getDbStyle() {
    return use().getDbStyle();
  }

  public static Beetl getBeetl() {
    return use().getBeetl();
  }

  public static MetadataManager getMetaDataManager() {
    return use().getMetaDataManager();
  }

  public static String getDefaultSchema() {
    return use().getDefaultSchema();
  }

  public static void setDefaultSchema(String defaultSchema) {
    use().setDefaultSchema(defaultSchema);
  }

  public static MapperBuilder getMapperBuilder() {
    return use().getMapperBuilder();
  }

  public static void setMapperBuilder(MapperBuilder mapperBuilder) {
    use().setMapperBuilder(mapperBuilder);
  }

  public static Interceptor[] getInters() {
    return use().getInters();
  }

  public static void setInters(Interceptor[] inters) {
    use().setInters(inters);
  }

  public static void addIdAutonGen(String name, IDAutoGen alorithm) {
    use().addIdAutonGen(name, alorithm);
  }

  public static Map<String, BeanProcessor> getProcessors() {
    return use().getProcessors();
  }

  public static void setProcessors(Map<String, BeanProcessor> processors) {
    use().setProcessors(processors);
  }

  public static BeanProcessor getDefaultBeanProcessors() {
    return use().getDefaultBeanProcessors();
  }

  public static void setDefaultBeanProcessors(BeanProcessor defaultBeanProcessors) {
    use().setDefaultBeanProcessors(defaultBeanProcessors);
  }

  public static void setSQLIdNameConversion(SQLIdNameConversion sqlIdNc) {
    use().setSQLIdNameConversion(sqlIdNc);
  }

  public static SQLIdNameConversion getSQLIdNameConversion() {
    return use().getSQLIdNameConversion();
  }

  public static MapperConfig getMapperConfig() {
    return use().getMapperConfig();
  }

  public static MapperConfig setBaseMapper(Class c) {
    return use().setBaseMapper(c);
  }

  public static String getSQLManagerName() {
    return use().getSQLManagerName();
  }
}
