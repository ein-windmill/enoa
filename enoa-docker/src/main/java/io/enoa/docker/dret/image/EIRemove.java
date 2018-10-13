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
package io.enoa.docker.dret.image;

import io.enoa.docker.dret.AbstractDRet;

public class EIRemove extends AbstractDRet {

  private final String untagged;
  private final String deleted;

  public EIRemove(Builder builder) {
    this.untagged = builder.untagged;
    this.deleted = builder.deleted;
  }

  public String untagged() {
    return this.untagged;
  }

  public String deleted() {
    return this.deleted;
  }

  public static class Builder {
    private String untagged;
    private String deleted;

    public EIRemove build() {
      return new EIRemove(this);
    }

    public Builder deleted(String deleted) {
      this.deleted = deleted;
      return this;
    }

    public Builder untagged(String untagged) {
      this.untagged = untagged;
      return this;
    }
  }
}
