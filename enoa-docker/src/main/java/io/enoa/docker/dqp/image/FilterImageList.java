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
package io.enoa.docker.dqp.image;

import io.enoa.docker.dqp.DBack;
import io.enoa.docker.dqp.DQR;
import io.enoa.json.Json;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterImageList implements DBack<DQPImageList> {

  private DQPImageList dqp;


  /**
   * before=(<container id> or <container name>)
   */
  private List<String> before;
  /**
   * dangling=true
   */
  private List<Boolean> dangling;
  /**
   * label=key or label="key=value" of an image label
   */
  private List<String> label;
  /**
   * reference=(<image-name>[:<tag>])
   */
  private List<String> reference;
  /**
   * since=(<image-name>[:<tag>], <image id> or <image@digest>)
   */
  private List<String> since;

  FilterImageList(DQPImageList dqp) {
    this.dqp = dqp;
  }

  public FilterImageList before(String before) {
    if (CollectionKit.isEmpty(this.before))
      this.before = new ArrayList<>();
    this.before.add(before);
    return this;
  }

  public FilterImageList before(List<String> before) {
    this.before = before;
    return this;
  }

  public FilterImageList dangling(Boolean dangling) {
    if (CollectionKit.isEmpty(this.dangling))
      this.dangling = new ArrayList<>();
    this.dangling.add(dangling);
    return this;
  }

  public FilterImageList dangling(List<Boolean> dangling) {
    this.dangling = dangling;
    return this;
  }

  public FilterImageList label(String label) {
    if (CollectionKit.isEmpty(this.label))
      this.label = new ArrayList<>();
    this.label.add(label);
    return this;
  }

  public FilterImageList label(List<String> label) {
    this.label = label;
    return this;
  }

  public FilterImageList reference(String reference) {
    if (CollectionKit.isEmpty(this.reference))
      this.reference = new ArrayList<>();
    this.reference.add(reference);
    return this;
  }

  public FilterImageList reference(List<String> reference) {
    this.reference = reference;
    return this;
  }

  public FilterImageList since(String since) {
    if (CollectionKit.isEmpty(this.since))
      this.since = new ArrayList<>();
    this.since.add(since);
    return this;
  }

  public FilterImageList since(List<String> since) {
    this.since = since;
    return this;
  }

  DQR dqr() {
    Kv kv = Kv.create();
    if (CollectionKit.notEmpty(this.before))
      kv.set("before", this.before);
    if (CollectionKit.notEmpty(this.dangling))
      kv.set("dangling", this.dangling.stream().map(Object::toString).collect(Collectors.toList()));
    if (CollectionKit.notEmpty(this.label))
      kv.set("label", this.label);
    if (CollectionKit.notEmpty(this.reference))
      kv.set("reference", this.reference);
    if (CollectionKit.notEmpty(this.since))
      kv.set("since", this.since);
    String json = Json.toJson(kv);
    CollectionKit.clear(kv);
    return DQR.create()
      .put("filters", json);
  }

  @Override
  public DQPImageList back() {
    return this.dqp;
  }
}
