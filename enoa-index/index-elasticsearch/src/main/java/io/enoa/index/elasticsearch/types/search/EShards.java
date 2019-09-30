/*
 * Copyright 2016 ikidou
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
package io.enoa.index.elasticsearch.types.search;

public class EShards {

  private Long total;
  private Long successful;
  private Long skipped;
  private Long failed;


  public Long getTotal() {
    return total;
  }

  public EShards setTotal(Long total) {
    this.total = total;
    return this;
  }

  public Long getSuccessful() {
    return successful;
  }

  public EShards setSuccessful(Long successful) {
    this.successful = successful;
    return this;
  }

  public Long getSkipped() {
    return skipped;
  }

  public EShards setSkipped(Long skipped) {
    this.skipped = skipped;
    return this;
  }

  public Long getFailed() {
    return failed;
  }

  public EShards setFailed(Long failed) {
    this.failed = failed;
    return this;
  }

  @Override
  public String toString() {
    return "EShards{" +
      "total=" + total +
      ", successful=" + successful +
      ", skipped=" + skipped +
      ", failed=" + failed +
      '}';
  }
}
