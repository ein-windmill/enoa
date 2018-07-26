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
package io.enoa.db.provider.db.mybatis.configuration;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

public class _EnoaMybatisConfiguration extends Configuration {

  protected final MapperRegistry mapperRegistry;

  public _EnoaMybatisConfiguration(Environment environment, String location, String suffix) {
    super(environment);
    this.mapperRegistry = new _EnoaMapperRegistry(this, location, suffix);
  }

  public _EnoaMybatisConfiguration(String location, String suffix) {
    super();
    this.mapperRegistry = new _EnoaMapperRegistry(this, location, suffix);
  }

  @Override
  public void addMappers(String packageName, Class<?> superType) {
    this.mapperRegistry.addMappers(packageName, superType);
  }

  @Override
  public void addMappers(String packageName) {
    this.mapperRegistry.addMappers(packageName);
  }

  @Override
  public <T> void addMapper(Class<T> type) {
    this.mapperRegistry.addMapper(type);
  }

  @Override
  public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
    return mapperRegistry.getMapper(type, sqlSession);
  }

  @Override
  public boolean hasMapper(Class<?> type) {
    return mapperRegistry.hasMapper(type);
  }

}
