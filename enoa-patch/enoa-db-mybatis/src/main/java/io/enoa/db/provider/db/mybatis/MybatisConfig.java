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
package io.enoa.db.provider.db.mybatis;

import io.enoa.db.EoDbConfig;
import io.enoa.db.EoDsConfig;
import io.enoa.db.EoDsFactory;
import org.apache.ibatis.executor.loader.ProxyFactory;
import org.apache.ibatis.executor.loader.javassist.JavassistProxyFactory;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.*;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.util.HashSet;
import java.util.Set;

public class MybatisConfig implements EoDbConfig {

  private final String name;
  private final EoDsFactory ds;
  private final EoDsConfig dsConfig;
  private final String scan;
  private final String mapper;
  private final String suffix;
  private final boolean autoCommit;
  private final TransactionIsolationLevel transactionIsolationLevel;

  private final boolean cacheEnabled;
  private final boolean lazyLoadingEnabled;
  private final boolean aggressiveLazyLoading;
  private final boolean multipleResultSetsEnabled;
  private final boolean useColumnLabel;
  private final boolean useGeneratedKeys;
  private final AutoMappingBehavior autoMappingBehavior;
  private final AutoMappingUnknownColumnBehavior autoMappingUnknownColumnBehavior;
  private final ExecutorType defaultExecutorType;
  private final Integer defaultStatementTimeout;
  private final Integer defaultFetchSize;
  private final boolean safeRowBoundsEnabled;
  private final boolean safeResultHandlerEnabled;
  private final boolean mapUnderscoreToCamelCase;
  private final LocalCacheScope localCacheScope;
  private final JdbcType jdbcTypeForNull;
  private final Set<String> lazyLoadTriggerMethods;
  private final Class<?> defaultScriptingLanguage;
  private final Class<? extends TypeHandler> defaultEnumTypeHandler;
  private final boolean callSettersOnNulls;
  private final boolean returnInstanceForEmptyRow;
  private final String logPrefix;
  private final Class<? extends Log> logImpl;
  private final ProxyFactory proxyFactory;
  private final Class<? extends VFS> vfsImpl;
  private final boolean useActualParamName;
  private final Class<?> configurationFactory;
  private final Interceptor[] interceptors;
  private final Class<?> superType;

  private MybatisConfig(Builder builder) {
    this.name = builder.name;
    this.ds = builder.ds;
    this.dsConfig = builder.dsConfig;
    this.scan = builder.scan;
    this.mapper = builder.mapper;
    this.suffix = builder.suffix;
    this.autoCommit = builder.autoCommit;
    this.transactionIsolationLevel = builder.transactionIsolationLevel;

    this.cacheEnabled = builder.cacheEnabled;
    this.lazyLoadingEnabled = builder.lazyLoadingEnabled;
    this.aggressiveLazyLoading = builder.aggressiveLazyLoading;
    this.multipleResultSetsEnabled = builder.multipleResultSetsEnabled;
    this.useColumnLabel = builder.useColumnLabel;
    this.useGeneratedKeys = builder.useGeneratedKeys;
    this.autoMappingBehavior = builder.autoMappingBehavior;
    this.autoMappingUnknownColumnBehavior = builder.autoMappingUnknownColumnBehavior;
    this.defaultExecutorType = builder.defaultExecutorType;
    this.defaultStatementTimeout = builder.defaultStatementTimeout;
    this.defaultFetchSize = builder.defaultFetchSize;
    this.safeRowBoundsEnabled = builder.safeRowBoundsEnabled;
    this.safeResultHandlerEnabled = builder.safeResultHandlerEnabled;
    this.mapUnderscoreToCamelCase = builder.mapUnderscoreToCamelCase;
    this.localCacheScope = builder.localCacheScope;
    this.jdbcTypeForNull = builder.jdbcTypeForNull;
    this.lazyLoadTriggerMethods = builder.lazyLoadTriggerMethods;
    this.defaultScriptingLanguage = builder.defaultScriptingLanguage;
    this.defaultEnumTypeHandler = builder.defaultEnumTypeHandler;
    this.callSettersOnNulls = builder.callSettersOnNulls;
    this.returnInstanceForEmptyRow = builder.returnInstanceForEmptyRow;
    this.logPrefix = builder.logPrefix;
    this.logImpl = builder.logImpl;
    this.proxyFactory = builder.proxyFactory;
    this.vfsImpl = builder.vfsImpl;
    this.useActualParamName = builder.useActualParamName;
    this.configurationFactory = builder.configurationFactory;
    this.interceptors = builder.interceptors;
    this.superType = builder.superType;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public EoDsFactory ds() {
    return this.ds;
  }

  @Override
  public EoDsConfig dsConfig() {
    return this.dsConfig;
  }

  public String scan() {
    return this.scan;
  }

  public String mapper() {
    return this.mapper;
  }

  public String suffix() {
    return this.suffix;
  }

  public boolean autoCommit() {
    return this.autoCommit;
  }

  public TransactionIsolationLevel transactionIsolationLevel() {
    return this.transactionIsolationLevel;
  }

  public boolean cacheEnabled() {
    return cacheEnabled;
  }

  public boolean lazyLoadingEnabled() {
    return lazyLoadingEnabled;
  }

  public boolean aggressiveLazyLoading() {
    return aggressiveLazyLoading;
  }

  public boolean multipleResultSetsEnabled() {
    return multipleResultSetsEnabled;
  }

  public boolean useColumnLabel() {
    return useColumnLabel;
  }

  public boolean useGeneratedKeys() {
    return useGeneratedKeys;
  }

  public AutoMappingBehavior autoMappingBehavior() {
    return autoMappingBehavior;
  }

  public AutoMappingUnknownColumnBehavior autoMappingUnknownColumnBehavior() {
    return autoMappingUnknownColumnBehavior;
  }

  public ExecutorType defaultExecutorType() {
    return defaultExecutorType;
  }

  public Integer defaultStatementTimeout() {
    return defaultStatementTimeout;
  }

  public Integer defaultFetchSize() {
    return defaultFetchSize;
  }

  public boolean safeRowBoundsEnabled() {
    return safeRowBoundsEnabled;
  }

  public boolean safeResultHandlerEnabled() {
    return safeResultHandlerEnabled;
  }

  public boolean mapUnderscoreToCamelCase() {
    return mapUnderscoreToCamelCase;
  }

  public LocalCacheScope localCacheScope() {
    return localCacheScope;
  }

  public JdbcType jdbcTypeForNull() {
    return jdbcTypeForNull;
  }

  public Set<String> lazyLoadTriggerMethods() {
    return lazyLoadTriggerMethods;
  }

  public Class<?> defaultScriptingLanguage() {
    return defaultScriptingLanguage;
  }

  public Class<? extends TypeHandler> defaultEnumTypeHandler() {
    return defaultEnumTypeHandler;
  }

  public boolean callSettersOnNulls() {
    return callSettersOnNulls;
  }

  public boolean returnInstanceForEmptyRow() {
    return returnInstanceForEmptyRow;
  }

  public String logPrefix() {
    return logPrefix;
  }

  public Class<? extends Log> logImpl() {
    return logImpl;
  }

  public ProxyFactory proxyFactory() {
    return proxyFactory;
  }

  public Class<? extends VFS> vfsImpl() {
    return vfsImpl;
  }

  public boolean useActualParamName() {
    return useActualParamName;
  }

  public Class<?> configurationFactory() {
    return configurationFactory;
  }

  public Interceptor[] interceptors() {
    return this.interceptors;
  }

  public Class<?> superType() {
    return this.superType;
  }

  public static class Builder {
    private String name;
    private EoDsFactory ds;
    private EoDsConfig dsConfig;
    private String scan;
    private String mapper;
    private String suffix;
    private boolean autoCommit;
    private TransactionIsolationLevel transactionIsolationLevel;

    private boolean cacheEnabled;
    private boolean lazyLoadingEnabled;
    private boolean aggressiveLazyLoading;
    private boolean multipleResultSetsEnabled;
    private boolean useColumnLabel;
    private boolean useGeneratedKeys;
    private AutoMappingBehavior autoMappingBehavior;
    private AutoMappingUnknownColumnBehavior autoMappingUnknownColumnBehavior;
    private ExecutorType defaultExecutorType;
    private Integer defaultStatementTimeout;
    private Integer defaultFetchSize;
    private boolean safeRowBoundsEnabled;
    private boolean safeResultHandlerEnabled;
    private boolean mapUnderscoreToCamelCase;
    private LocalCacheScope localCacheScope;
    private JdbcType jdbcTypeForNull;
    private Set<String> lazyLoadTriggerMethods;
    private Class<?> defaultScriptingLanguage;
    private Class<? extends TypeHandler> defaultEnumTypeHandler;
    private boolean callSettersOnNulls;
    private boolean returnInstanceForEmptyRow;
    private String logPrefix;
    private Class<? extends Log> logImpl;
    private ProxyFactory proxyFactory;
    private Class<? extends VFS> vfsImpl;
    private boolean useActualParamName;
    private Class<?> configurationFactory;
    private Interceptor[] interceptors;
    private Class<?> superType;

    public Builder() {
      this.name = "main";
      this.suffix = "xml";
      this.autoCommit = true;

      // http://www.mybatis.org/mybatis-3/zh/configuration.html
      // http://www.mybatis.org/mybatis-3/configuration.html
      this.cacheEnabled = true;
      this.lazyLoadingEnabled = false;
      this.aggressiveLazyLoading = false;
      this.multipleResultSetsEnabled = true;
      this.useColumnLabel = true;
      this.useGeneratedKeys = false;
      this.autoMappingBehavior = AutoMappingBehavior.PARTIAL;
      this.autoMappingUnknownColumnBehavior = AutoMappingUnknownColumnBehavior.NONE;
      this.defaultExecutorType = ExecutorType.SIMPLE;
      this.defaultStatementTimeout = null;
      this.defaultFetchSize = null;
      this.safeRowBoundsEnabled = false;
      this.safeResultHandlerEnabled = true;
      this.mapUnderscoreToCamelCase = false;
      this.localCacheScope = LocalCacheScope.SESSION;
      this.jdbcTypeForNull = JdbcType.OTHER;
      this.lazyLoadTriggerMethods = new HashSet<String>() {{
        add("equals");
        add("clone");
        add("hashCode");
        add("toString");
      }};
      this.defaultScriptingLanguage = XMLLanguageDriver.class;
      this.defaultEnumTypeHandler = EnumTypeHandler.class;
      this.callSettersOnNulls = false;
      this.returnInstanceForEmptyRow = false;
      this.logPrefix = "";
      this.logImpl = null;
      this.proxyFactory = new JavassistProxyFactory();
      this.vfsImpl = null;
      this.useActualParamName = true;
      this.configurationFactory = null;
      this.interceptors = new Interceptor[0];
      this.superType = Object.class;
    }

    public MybatisConfig build() {
      return new MybatisConfig(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder ds(EoDsFactory ds, EoDsConfig config) {
      this.ds = ds;
      this.dsConfig = config;
      return this;
    }

    public Builder scan(String scan) {
      this.scan = scan;
      return this;
    }

    public Builder scan(String scan, Class<?> superType) {
      this.scan = scan;
      this.superType = superType;
      return this;
    }

    public Builder mapper(String mapper) {
      this.mapper = mapper;
      return this;
    }

    public Builder suffix(String suffix) {
      this.suffix = suffix;
      return this;
    }

    public Builder autoCommit(boolean autoCommit) {
      this.autoCommit = autoCommit;
      return this;
    }

    public Builder transactionIsolationLevel(TransactionIsolationLevel transactionIsolationLevel) {
      this.transactionIsolationLevel = transactionIsolationLevel;
      return this;
    }

    public Builder cacheEnabled(boolean cacheEnabled) {
      this.cacheEnabled = cacheEnabled;
      return this;
    }

    public Builder lazyLoadingEnabled(boolean lazyLoadingEnabled) {
      this.lazyLoadingEnabled = lazyLoadingEnabled;
      return this;
    }

    public Builder aggressiveLazyLoading(boolean aggressiveLazyLoading) {
      this.aggressiveLazyLoading = aggressiveLazyLoading;
      return this;
    }

    public Builder multipleResultSetsEnabled(boolean multipleResultSetsEnabled) {
      this.multipleResultSetsEnabled = multipleResultSetsEnabled;
      return this;
    }

    public Builder useColumnLabel(boolean useColumnLabel) {
      this.useColumnLabel = useColumnLabel;
      return this;
    }

    public Builder useGeneratedKeys(boolean useGeneratedKeys) {
      this.useGeneratedKeys = useGeneratedKeys;
      return this;
    }

    public Builder autoMappingBehavior(AutoMappingBehavior autoMappingBehavior) {
      this.autoMappingBehavior = autoMappingBehavior;
      return this;
    }

    public Builder autoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior autoMappingUnknownColumnBehavior) {
      this.autoMappingUnknownColumnBehavior = autoMappingUnknownColumnBehavior;
      return this;
    }

    public Builder defaultExecutorType(ExecutorType defaultExecutorType) {
      this.defaultExecutorType = defaultExecutorType;
      return this;
    }

    public Builder defaultStatementTimeout(Integer defaultStatementTimeout) {
      this.defaultStatementTimeout = defaultStatementTimeout;
      return this;
    }

    public Builder defaultFetchSize(Integer defaultFetchSize) {
      this.defaultFetchSize = defaultFetchSize;
      return this;
    }

    public Builder safeRowBoundsEnabled(boolean safeRowBoundsEnabled) {
      this.safeRowBoundsEnabled = safeRowBoundsEnabled;
      return this;
    }

    public Builder safeResultHandlerEnabled(boolean safeResultHandlerEnabled) {
      this.safeResultHandlerEnabled = safeResultHandlerEnabled;
      return this;
    }

    public Builder mapUnderscoreToCamelCase(boolean mapUnderscoreToCamelCase) {
      this.mapUnderscoreToCamelCase = mapUnderscoreToCamelCase;
      return this;
    }

    public Builder localCacheScope(LocalCacheScope localCacheScope) {
      this.localCacheScope = localCacheScope;
      return this;
    }

    public Builder jdbcTypeForNull(JdbcType jdbcTypeForNull) {
      this.jdbcTypeForNull = jdbcTypeForNull;
      return this;
    }

    public Builder lazyLoadTriggerMethods(Set<String> lazyLoadTriggerMethods) {
      this.lazyLoadTriggerMethods = lazyLoadTriggerMethods;
      return this;
    }

    public Builder defaultScriptingLanguage(Class<?> defaultScriptingLanguage) {
      this.defaultScriptingLanguage = defaultScriptingLanguage;
      return this;
    }

    public Builder defaultEnumTypeHandler(Class<? extends TypeHandler> defaultEnumTypeHandler) {
      this.defaultEnumTypeHandler = defaultEnumTypeHandler;
      return this;
    }

    public Builder callSettersOnNulls(boolean callSettersOnNulls) {
      this.callSettersOnNulls = callSettersOnNulls;
      return this;
    }

    public Builder returnInstanceForEmptyRow(boolean returnInstanceForEmptyRow) {
      this.returnInstanceForEmptyRow = returnInstanceForEmptyRow;
      return this;
    }

    public Builder logPrefix(String logPrefix) {
      this.logPrefix = logPrefix;
      return this;
    }

    public Builder logImpl(Class<? extends Log> logImpl) {
      this.logImpl = logImpl;
      return this;
    }

    public Builder proxyFactory(ProxyFactory proxyFactory) {
      this.proxyFactory = proxyFactory;
      return this;
    }

    public Builder vfsImpl(Class<? extends VFS> vfsImpl) {
      this.vfsImpl = vfsImpl;
      return this;
    }

    public Builder useActualParamName(boolean useActualParamName) {
      this.useActualParamName = useActualParamName;
      return this;
    }

    public Builder configurationFactory(Class<?> configurationFactory) {
      this.configurationFactory = configurationFactory;
      return this;
    }

    public Builder interceptors(Interceptor[] interceptors) {
      this.interceptors = interceptors;
      return this;
    }

  }

}
