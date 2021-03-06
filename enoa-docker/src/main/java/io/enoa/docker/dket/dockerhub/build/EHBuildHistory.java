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
package io.enoa.docker.dket.dockerhub.build;

import io.enoa.docker.dket.dockerhub.EHubPage;

import java.util.List;

public class EHBuildHistory extends EHubPage {


  private final List<EHHistory> results;

  public EHBuildHistory(Builder builder) {
    super(builder);
    this.results = builder.results;
  }

  public List<EHHistory> results() {
    return this.results;
  }

  public static class Builder extends EHubPage.Builder<Builder> {

    private List<EHHistory> results;

    @Override
    public EHBuildHistory build() {
      return new EHBuildHistory(this);
    }

    public Builder results(List<EHHistory> results) {
      this.results = results;
      return this;
    }
  }
}
