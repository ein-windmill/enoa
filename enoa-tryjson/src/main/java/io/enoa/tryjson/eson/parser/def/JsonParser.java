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
//package io.enoa.tryjson.eson.parser.def;
//
//import io.enoa.tryjson.Tsonfig;
//import io.enoa.tryjson.json.Ja;
//import io.enoa.tryjson.json.Jo;
//import io.enoa.tryjson.thr.TryjsonException;
//
//class JsonParser {
//
//  // fixme instance this class
//  static JsonParser with(ParseType type, String json) {
//    return new JsonParser(type, json);
//  }
//
//  private final StringBuilder json;
//  private Ja ja;
//  private Jo jo;
//  private ParseType type;
//  private Tsonfig config;
//
//  private JsonParser(ParseType type, String json) {
//    this.json = new StringBuilder(json);
//    switch (type) {
//      case ARRAY:
//        this.ja = Ja.create();
//        break;
//      case OBJECT:
//        this.jo = Jo.create();
//        break;
//    }
//    this.type = type;
//    this.parse();
//  }
//
//  JsonParser config(Tsonfig config) {
//    this.config = config;
//    return this;
//  }
//
//  Jo jo() {
//    return this.jo;
//  }
//
//  Ja ja() {
//    return this.ja;
//  }
//
//  private void parse() {
//    switch (this.type) {
//      case OBJECT:
//        this.jo = new JsonObjectParser(this.json, this.config).parse();
//        break;
//      case ARRAY:
//        this.ja = new JsonArrayParser(this.json, this.config).parse();
//        break;
//      default:
//        throw new TryjsonException("not support type");
//    }
//  }
//
//}
