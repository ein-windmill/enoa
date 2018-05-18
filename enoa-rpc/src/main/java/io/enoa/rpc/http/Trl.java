///*
// * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package io.enoa.rpc.http;
//
//import io.enoa.http.EnoaUrl;
//import io.enoa.http.protocol.HttpPara;
//import io.enoa.toolkit.http.UriKit;
//
//import java.nio.charset.Charset;
//import java.util.Collection;
//
//public class Trl extends EnoaUrl {
//
//  private Trl(String trl) {
//    super(false, trl);
//  }
//
//  public static Trl with(String trl) {
//    return new Trl(UriKit.correct(trl));
//  }
//
//  @Override
//  public Trl charset(Charset charset) {
//    super.charset(charset);
//    return this;
//  }
//
//  @Override
//  public Trl subpath(String subpath) {
//    super.subpath(subpath);
//    return this;
//  }
//
//  @Override
//  public Trl subpath(String... subpath) {
//    super.subpath(subpath);
//    return this;
//  }
//
//  @Override
//  public Trl para(String name, Object value) {
//    super.para(name, value);
//    return this;
//  }
//
//  @Override
//  public Trl para(String name, Object[] values) {
//    super.para(name, values);
//    return this;
//  }
//
//  @Override
//  public Trl para(String name, Collection values) {
//    super.para(name, values);
//    return this;
//  }
//
//  @Override
//  public Trl traditional(boolean traditional) {
//    super.traditional(traditional);
//    return this;
//  }
//
//  @Override
//  public Trl encode(boolean encode) {
//    super.encode(encode);
//    return this;
//  }
//
//  @Override
//  public Trl traditional() {
//    super.traditional();
//    return this;
//  }
//
//  @Override
//  public Trl encode() {
//    super.encode();
//    return this;
//  }
//
//  @Override
//  public HttpPara[] paras() {
//    return super.paras();
//  }
//
//  @Override
//  public String end() {
//    return super.end();
//  }
//}
