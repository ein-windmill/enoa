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
package io.enoa.trydb.build;

import io.enoa.toolkit.namecase.INameCase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RsBuilder {

  private static class Holder {
    private static final RsBuilder INSTANCE = new RsBuilder();
    private static final IRsBuilder MAP_BUILDER = new MapBuilder();
    private static final IRsBuilder BEAN_BUILDER = new BeanBuilder();
    private static final DefaultBuilder DEFAULT_BUILDER = new DefaultBuilder();

    private static Map<String, Boolean> MAP_JAVA_TYPE = new HashMap<String, Boolean>() {{
      put(String.class.getName(), true);
      put(Integer.class.getName(), true);
      put(Boolean.class.getName(), true);
      put(Long.class.getName(), true);
      put(Short.class.getName(), true);
      put(Byte.class.getName(), true);
      put(Double.class.getName(), true);
      put(Float.class.getName(), true);
      put(char.class.getName(), true);
      put(int.class.getName(), true);
      put(boolean.class.getName(), true);
      put(long.class.getName(), true);
      put(short.class.getName(), true);
      put(byte.class.getName(), true);
      put(double.class.getName(), true);
      put(float.class.getName(), true);

      put(Object.class.getName(), true);
    }};
  }

  private static RsBuilder instance() {
    return Holder.INSTANCE;
  }

  public static <T> List<T> build(ResultSet rs, Class<T> clazz, INameCase namecase) throws SQLException {
    if (clazz == null) {
//      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.trydb.target_clazz_null"));
      return Holder.DEFAULT_BUILDER.build(rs, clazz, namecase);
    }

    Boolean isJavaType = Holder.MAP_JAVA_TYPE.get(clazz.getName());
    if (isJavaType != null && isJavaType) {
      return Holder.DEFAULT_BUILDER.build(rs, clazz, namecase);
    }

    boolean isMap = Map.class.isAssignableFrom(clazz);
    if (isMap) {
      return Holder.MAP_BUILDER.build(rs, clazz, namecase);
    }

    return Holder.BEAN_BUILDER.build(rs, clazz, namecase);
  }

}
