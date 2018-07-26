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
package io.enoa.repeater.provider.fastcgi.kernel;

import io.enoa.toolkit.collection.CollectionKit;

import java.util.Map;

public class FastCGIRequest {

  private Map<String, String> prop;
  private byte[] data;

  protected FastCGIRequest() {

  }

  private FastCGIRequest(Builder builder) {
    this.prop = builder.prop;
    this.data = builder.data;
  }


  public Map<String, String> prop() {
    return this.prop;
  }

  public byte[] data() {
    return this.data;
  }

  public void clear() {
    CollectionKit.clear(this.prop);
//    CollectionKit.clear(this.data);
  }

  static class Builder {

    private Map<String, String> prop;
    private byte[] data;

    FastCGIRequest build() {
      return new FastCGIRequest(this);
    }

    Builder prop(Map<String, String> prop) {
      this.prop = prop;
      return this;
    }

    Builder data(byte[] data) {
      this.data = data;
      return this;
    }
  }

}
