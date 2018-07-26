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
package io.enoa.example.tryjson.entity;

import java.util.List;

public class Bean94<T> {

  private String com;
  private Character stat;
  private Byte type;
  private Bean95 item;
  private List<Bean96> marks;
  private boolean open;
  private T unknown;

  public T unknown() {
    return unknown;
  }

  public Bean94<T> unknown(T unknown) {
    this.unknown = unknown;
    return this;
  }

  public List<Bean96> getMarks() {
    return marks;
  }

//  public Bean94 setMarks(List<Bean96> marks) {
//    this.marks = marks;
//    return this;
//  }
//
//  public Character getStat() {
//    return stat;
//  }
//
//  public Bean94 setStat(Character stat) {
//    this.stat = stat;
//    return this;
//  }
//
//  public Byte getType() {
//    return type;
//  }
//
//  public Bean94 setType(Byte type) {
//    this.type = type;
//    return this;
//  }
//
//  public String getCom() {
//    return com;
//  }
//
//  public Bean94 setCom(String com) {
//    this.com = com;
//    return this;
//  }
//
//  public Bean95 getItem() {
//    return item;
//  }
//
//  public Bean94 setItem(Bean95 item) {
//    this.item = item;
//    return this;
//  }
//
//  public boolean open() {
//    return open;
//  }
//
//  public Bean94 open(boolean open) {
//    this.open = open;
//    return this;
//  }
}
