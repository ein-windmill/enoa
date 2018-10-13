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
package io.enoa.docker.stream;

import java.io.Serializable;

public class DStream<T> implements Serializable {


  private IDStreamRunner<T> runner;
  private IDStreamStopper stopper;

  public static <J> Builder<J> builder(IDStreamRunner<J> runner) {
    return new Builder<>(runner);
  }

  private DStream(Builder<T> builder) {
    this.runner = builder.runner;
    this.stopper = builder.stopper;
  }

  public IDStreamRunner<T> runner() {
    return runner;
  }

  public IDStreamStopper stopper() {
    return stopper;
  }

  public static class Builder<T> {
    private IDStreamRunner<T> runner;
    private IDStreamStopper stopper;

    public Builder(IDStreamRunner<T> runner) {
      this.runner = runner;
    }

    public DStream<T> build() {
      return new DStream<>(this);
    }

    public Builder<T> runner(IDStreamRunner<T> runner) {
      this.runner = runner;
      return this;
    }

    public Builder<T> stopper(IDStreamStopper stopper) {
      this.stopper = stopper;
      return this;
    }
  }


}
