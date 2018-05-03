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
package io.enoa.db.provider.db.mybatis.configuration;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Set;

class _EnoaMapperRegistry extends MapperRegistry {


  private final Configuration config;
  private final String location;
  private final String suffix;

  _EnoaMapperRegistry(Configuration config, String location, String suffix) {
    super(config);
    this.config = config;
    this.location = location;
    this.suffix = suffix;
  }

  /**
   * @since 3.2.2
   */
  public void addMappers(String packageName, Class<?> superType) {
    ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<>();
    resolverUtil.find(new ResolverUtil.IsA(superType), packageName);
    Set<Class<? extends Class<?>>> mapperSet = resolverUtil.getClasses();
    for (Class<?> mapperClass : mapperSet) {
      addMapper(mapperClass);
    }

    File folder = new File(this.location);
    File[] files = folder.listFiles(file -> file.getAbsolutePath().endsWith(this.suffix));
    if (files == null || files.length == 0)
      return;

    // load custom path xml mapper
    for (File file : files) {
      InputStream stream;
      try {
        stream = new FileInputStream(file);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e.getMessage(), e);
      }
      XMLMapperBuilder parser = new XMLMapperBuilder(stream, this.config, file.getAbsolutePath(), this.config.getSqlFragments());
      parser.parse();
    }
  }

  /**
   * @since 3.2.2
   */
  public void addMappers(String packageName) {
    addMappers(packageName, Object.class);
  }

}
