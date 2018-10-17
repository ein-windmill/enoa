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
package io.enoa.db.provider.db.mybatis;

import io.enoa.db.EnoaDb;
import io.enoa.db.EnoaDs;
import io.enoa.db.provider.db.mybatis.configuration._EnoaMybatisConfiguration;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

class _Mybatis extends EnoaDb {

  private MybatisConfig config;
  private EnoaDs ds;

  _Mybatis(MybatisConfig config) {
    this.config = config;
    this.ds = this.config.ds().datasource(this.config.dsconfig());
  }

  @Override
  public void start() {
    TransactionFactory transactionFactory = new JdbcTransactionFactory();
    Environment environment = new Environment("development", transactionFactory, this.ds.open());

    Configuration configuration = new _EnoaMybatisConfiguration(environment, this.config.mapper(), this.config.suffix());

    configuration.setJdbcTypeForNull(this.config.jdbcTypeForNull());
    configuration.setCacheEnabled(this.config.cacheEnabled());
    configuration.setLazyLoadingEnabled(this.config.lazyLoadingEnabled());
    configuration.setAggressiveLazyLoading(this.config.aggressiveLazyLoading());
    configuration.setMultipleResultSetsEnabled(this.config.multipleResultSetsEnabled());
    configuration.setUseColumnLabel(this.config.useColumnLabel());
    configuration.setUseGeneratedKeys(this.config.useGeneratedKeys());
    configuration.setAutoMappingBehavior(this.config.autoMappingBehavior());
    configuration.setAutoMappingUnknownColumnBehavior(this.config.autoMappingUnknownColumnBehavior());
    configuration.setDefaultExecutorType(this.config.defaultExecutorType());
    configuration.setDefaultStatementTimeout(this.config.defaultStatementTimeout());
    configuration.setDefaultFetchSize(this.config.defaultFetchSize());
    configuration.setSafeResultHandlerEnabled(this.config.safeResultHandlerEnabled());
    configuration.setSafeRowBoundsEnabled(this.config.safeRowBoundsEnabled());
    configuration.setMapUnderscoreToCamelCase(this.config.mapUnderscoreToCamelCase());
    configuration.setLocalCacheScope(this.config.localCacheScope());
    configuration.setJdbcTypeForNull(this.config.jdbcTypeForNull());
    configuration.setLazyLoadTriggerMethods(this.config.lazyLoadTriggerMethods());
    configuration.setDefaultScriptingLanguage(this.config.defaultScriptingLanguage());
    configuration.setDefaultEnumTypeHandler(this.config.defaultEnumTypeHandler());
    configuration.setCallSettersOnNulls(this.config.callSettersOnNulls());
    configuration.setReturnInstanceForEmptyRow(this.config.returnInstanceForEmptyRow());
    configuration.setLogPrefix(this.config.logPrefix());
    configuration.setLogImpl(this.config.logImpl());
    configuration.setProxyFactory(this.config.proxyFactory());
    configuration.setVfsImpl(this.config.vfsImpl());
    configuration.setUseActualParamName(this.config.useActualParamName());
    configuration.setConfigurationFactory(this.config.configurationFactory());

    if (this.config.interceptors().length > 0) {
      for (Interceptor interceptor : this.config.interceptors()) {
        configuration.addInterceptor(interceptor);
      }
    }

    configuration.addMappers(this.config.scan(), this.config.superType());

    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    SqlSession sqlSession = null;
    if (this.config.defaultExecutorType() != null && this.config.transactionIsolationLevel() != null)
      sqlSession = sqlSessionFactory.openSession(this.config.defaultExecutorType(), this.config.transactionIsolationLevel());

    if (this.config.defaultExecutorType() != null)
      sqlSession = sqlSessionFactory.openSession(this.config.defaultExecutorType(), this.config.autoCommit());

    if (sqlSession == null)
      sqlSession = sqlSessionFactory.openSession(this.config.autoCommit());

    MybatisKit.add(this.config.name(), sqlSession);
  }

  @Override
  public void stop() {
    this.ds.close();
  }
}
