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

public class ECredentialSpec extends AbstractDRRet {


  /**
   * string
   * <p>
   * Load credential spec from this file. The file is read by the daemon, and must be present in the CredentialSpecs subdirectory in the docker data directory, which defaults to C:\ProgramData\Docker\ on Windows.
   * <p>
   * For example, specifying spec.json loads C:\ProgramData\Docker\CredentialSpecs\spec.json.
   * <p>
   * <p>
   * Note: CredentialSpec.File and CredentialSpec.Registry are mutually exclusive.
   */
  private String file;
  /**
   * string
   * <p>
   * Load credential spec from this value in the Windows registry. The specified registry value must be located in:
   * <p>
   * HKLM\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Virtualization\Containers\CredentialSpecs
   * <p>
   * <p>
   * Note: CredentialSpec.File and CredentialSpec.Registry are mutually exclusive.
   */
  private String registry;

  public ECredentialSpec(Builder builder) {
    this.file = builder.file;
    this.registry = builder.registry;
  }

  public String file() {
    return file;
  }

  public String registry() {
    return registry;
  }

  public static class Builder {

    private String file;
    private String registry;

    public ECredentialSpec build() {
      return new ECredentialSpec(this);
    }

    public Builder file(String file) {
      this.file = file;
      return this;
    }

    public Builder registry(String registry) {
      this.registry = registry;
      return this;
    }
  }
}
