/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
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
package io.enoa.example.tryjson.entity;

public class Bean93 extends Bean92 {

  private Integer province;
  private String city;
  public String address;
  private BTypeIx typeIx;
  private BTypeVal typeVal;
  private BTypeOrigin typeOrigin;

  public Bean93() {
  }

  public Bean93(Bean92 bean92) {
    super.ctime = bean92.ctime;
    super.appId(bean92.appId())
      .websiteHost(bean92.websiteHost())
      .expire_time(bean92.expire_time())
      .token(bean92.token());
  }



  public Integer province() {
    return this.province;
  }

  public Bean93 province(Integer province) {
    this.province = province;
    return this;
  }

  public String city() {
    return this.city;
  }

  public Bean93 city(String city) {
    this.city = city;
    return this;
  }

  public BTypeIx typeIx() {
    return this.typeIx;
  }

  public Bean93 typeIx(BTypeIx typeIx) {
    this.typeIx = typeIx;
    return this;
  }

  public BTypeVal typeVal() {
    return this.typeVal;
  }

  public Bean93 typeVal(BTypeVal typeVal) {
    this.typeVal = typeVal;
    return this;
  }

  public BTypeOrigin typeOrigin() {
    return this.typeOrigin;
  }

  public Bean93 typeOrigin(BTypeOrigin typeOrigin) {
    this.typeOrigin = typeOrigin;
    return this;
  }
}
