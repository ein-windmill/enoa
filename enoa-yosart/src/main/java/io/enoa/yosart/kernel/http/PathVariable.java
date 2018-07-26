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
package io.enoa.yosart.kernel.http;

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PathVariable {

  private final String uri;
  private final Map<String, Object> var;

  private PathVariable(Builder builder) {
    this.uri = builder.uri;
    this.var = builder.var;
  }

  public String uri() {
    return this.uri;
  }

  public Object value(String name) {
    if (TextKit.isBlank(name))
      return null;
    return this.var.get(name);
  }

  public String[] names() {
    if (this.var == null)
      return CollectionKit.emptyArray(String.class);
    Set<String> names = this.var.keySet();
    return names.toArray(new String[names.size()]);
  }

  public void clear() {
    CollectionKit.clear(this.var);
  }

  public static class Builder {
    private String uri;
    private Map<String, Object> var = new HashMap<>();

    public PathVariable build() {
      return new PathVariable(this);
    }

    public Builder uri(String uri) {
      this.uri = uri;
      return this;
    }

    public Builder set(String name, Object value) {
      if (TextKit.isBlank(name))
        throw new EoException(EnoaTipKit.message("eo.tip.yosart.path_var_name_not_null"));
      this.var.put(name, value);
      return this;
    }

    public Builder set(Map var) {
      var.forEach((k, v) -> this.set(k.toString(), v));
      CollectionKit.clear(var);
      return this;
    }

  }

  @Override
  public String toString() {
    return this.var.toString();
  }
}
