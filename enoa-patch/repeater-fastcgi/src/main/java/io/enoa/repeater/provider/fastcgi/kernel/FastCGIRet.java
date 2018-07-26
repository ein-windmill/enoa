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

class FastCGIRet {


  private final int requestId;
  private final boolean closeConn;
  private final FastCGIRequest request;


  private FastCGIRet(Builder builder) {
    this.request = builder.request;
    this.requestId = builder.requestId;
    this.closeConn = builder.closeConn;
  }

  public int requestId() {
    return this.requestId;
  }

  public boolean closeConn() {
    return this.closeConn;
  }

  public FastCGIRequest request() {
    return this.request;
  }


  static class Builder {

    private int requestId;
    private boolean closeConn;
    private FastCGIRequest request;

    FastCGIRet build() {
      return new FastCGIRet(this);
    }

    Builder requestId(int requestId) {
      this.requestId = requestId;
      return this;
    }

    Builder closeConn(boolean closeConn) {
      this.closeConn = closeConn;
      return this;
    }

    Builder request(FastCGIRequest request) {
      this.request = request;
      return this;
    }

  }


}
