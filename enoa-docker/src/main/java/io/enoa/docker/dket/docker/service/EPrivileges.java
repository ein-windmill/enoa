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
package io.enoa.docker.dket.docker.service;

import io.enoa.docker.dket.AbstractDRRet;

public class EPrivileges extends AbstractDRRet {

  private ECredentialSpec credentialspec;
  private ESELinuxContext selinuxcontext;

  public EPrivileges(Builder builder) {
    this.credentialspec = builder.credentialspec;
    this.selinuxcontext = builder.selinuxcontext;
  }

  public ECredentialSpec credentialspec() {
    return credentialspec;
  }

  public ESELinuxContext selinuxcontext() {
    return selinuxcontext;
  }

  public static class Builder {

    private ECredentialSpec credentialspec;
    private ESELinuxContext selinuxcontext;

    public EPrivileges build() {
      return new EPrivileges(this);
    }

    public Builder credentialspec(ECredentialSpec credentialspec) {
      this.credentialspec = credentialspec;
      return this;
    }

    public Builder selinuxcontext(ESELinuxContext selinuxcontext) {
      this.selinuxcontext = selinuxcontext;
      return this;
    }
  }

}
