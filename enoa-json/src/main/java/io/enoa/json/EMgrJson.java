///*
// * Copyright (c) 2018, enoa (fewensa@enoa.io)
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
//package io.enoa.json;
//
//import io.enoa.json.provider.enoa.EoJsonProvider;
//
///**
// * enoa - io.enoa.json
// */
//public class EMgrJson {
//
//
//  private static EoJsonFactory defJsonFactory = new EoJsonProvider();
//
//  static String defDatePattern = null;
//
//  public static void defJsonFactory(EoJsonFactory factory) {
//    if (factory == null)
//      throw new IllegalArgumentException("Json factory can not be null.");
//    defJsonFactory = factory;
//  }
//
//  public static void defDatePattern(String pattern) {
//    if (pattern == null || "".equals(pattern))
//      throw new IllegalArgumentException("defaultDatePattern can not be blank.");
//    defDatePattern = pattern;
//  }
//
//  public static EnoaJson json() {
//    return defJsonFactory.json();
//  }
//}
