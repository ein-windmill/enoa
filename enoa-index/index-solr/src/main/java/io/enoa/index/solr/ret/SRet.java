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
package io.enoa.index.solr.ret;

import io.enoa.toolkit.sys.ObjectKit;

public class SRet<T> implements _Ret {

  private SHeader responseHeader;
  private SResponse<T> response;
  private SError error;

  public SHeader getResponseHeader() {
    return responseHeader;
  }

  public void setResponseHeader(SHeader responseHeader) {
    this.responseHeader = responseHeader;
  }

  public SResponse<T> getResponse() {
    return response;
  }

  public void setResponse(SResponse<T> response) {
    this.response = response;
  }

  public SError getError() {
    return error;
  }

  public void setError(SError error) {
    this.error = error;
  }

  @Override
  public String toString() {
    return ObjectKit.buildToString(this);
  }
}
