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
package io.enoa.docker.dqp.container;

import io.enoa.docker.dqp.DBack;
import io.enoa.docker.dqp.DPara;
import io.enoa.json.Json;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filters to process on the container list, encoded as JSON (a map[string][]string).
 * For example, {"status": ["paused"]} will only return paused containers. Available
 */
public class FilterListContainer implements DBack<DQPListContainer> {

  private DQPListContainer lc;

  /**
   * ancestor=(<image-name>[:<tag>], <image id>, or <image@digest>)
   */
  private List<String> ancestor;
  /**
   * before=(<container id> or <container name>)
   */
  private List<String> before;
  /**
   * expose=(<port>[/<proto>]|<startport-endport>/[<proto>])
   */
  private List<String> expose;
  /**
   * exited=<int> containers with exit code of <int>
   */
  private List<Integer> exited;
  /**
   * health=(starting|healthy|unhealthy|none)
   */
  private List<String> health;
  /**
   * id=<ID> a container's ID
   */
  private List<String> id;
  /**
   * isolation=(default|process|hyperv) (Windows daemon only)
   */
  private List<String> isolation;
  /**
   * is-task=(true|false)
   */
  private List<Boolean> istask;
  /**
   * label=key or label="key=value" of a container label
   */
  private List<String> label;
  /**
   * name=<name> a container's name
   */
  private List<String> name;
  /**
   * network=(<network id> or <network name>)
   */
  private List<String> network;
  /**
   * publish=(<port>[/<proto>]|<startport-endport>/[<proto>])
   */
  private List<String> publish;
  /**
   * since=(<container id> or <container name>)
   */
  private List<String> since;
  /**
   * status=(created|restarting|running|removing|paused|exited|dead)
   */
  private List<String> status;
  /**
   * volume=(<volume name> or <mount point destination>)
   */
  private List<String> volume;


  FilterListContainer(DQPListContainer lc) {
    this.lc = lc;
  }

  public FilterListContainer ancestor(String ancestor) {
    if (CollectionKit.isEmpty(this.ancestor))
      this.ancestor = new ArrayList<>();
    this.ancestor.add(ancestor);
    return this;
  }

  public FilterListContainer ancestor(List<String> ancestor) {
    this.ancestor = ancestor;
    return this;
  }

  public FilterListContainer before(String before) {
    if (CollectionKit.isEmpty(this.before))
      this.before = new ArrayList<>();
    this.before.add(before);
    return this;
  }

  public FilterListContainer before(List<String> before) {
    this.before = before;
    return this;
  }

  public FilterListContainer expose(String expose) {
    if (CollectionKit.isEmpty(this.expose))
      this.expose = new ArrayList<>();
    this.expose.add(expose);
    return this;
  }

  public FilterListContainer expose(List<String> expose) {
    this.expose = expose;
    return this;
  }

  public FilterListContainer exited(Integer exited) {
    if (CollectionKit.isEmpty(this.exited))
      this.exited = new ArrayList<>();
    this.exited.add(exited);
    return this;
  }

  public FilterListContainer exited(List<Integer> exited) {
    this.exited = exited;
    return this;
  }

  public FilterListContainer health(String health) {
    if (CollectionKit.isEmpty(this.health))
      this.health = new ArrayList<>();
    this.health.add(health);
    return this;
  }

  public FilterListContainer health(List<String> health) {
    this.health = health;
    return this;
  }

  public FilterListContainer id(String id) {
    if (CollectionKit.isEmpty(this.id))
      this.id = new ArrayList<>();
    this.id.add(id);
    return this;
  }

  public FilterListContainer id(List<String> id) {
    this.id = id;
    return this;
  }

  public FilterListContainer isolation(String isolation) {
    if (CollectionKit.isEmpty(this.isolation))
      this.isolation = new ArrayList<>();
    this.isolation.add(isolation);
    return this;
  }

  public FilterListContainer isolation(List<String> isolation) {
    this.isolation = isolation;
    return this;
  }

  public FilterListContainer istask(Boolean istask) {
    if (CollectionKit.isEmpty(this.istask))
      this.istask = new ArrayList<>();
    this.istask.add(istask);
    return this;
  }

  public FilterListContainer istask(List<Boolean> istask) {
    this.istask = istask;
    return this;
  }

  public FilterListContainer label(String label) {
    if (CollectionKit.isEmpty(this.label))
      this.label = new ArrayList<>();
    this.label.add(label);
    return this;
  }

  public FilterListContainer label(List<String> label) {
    this.label = label;
    return this;
  }

  public FilterListContainer name(String name) {
    if (CollectionKit.isEmpty(this.name))
      this.name = new ArrayList<>();
    this.name.add(name);
    return this;
  }

  public FilterListContainer name(List<String> name) {
    this.name = name;
    return this;
  }

  public FilterListContainer network(String network) {
    if (CollectionKit.isEmpty(this.network))
      this.network = new ArrayList<>();
    this.network.add(network);
    return this;
  }

  public FilterListContainer network(List<String> network) {
    this.network = network;
    return this;
  }

  public FilterListContainer publish(String publish) {
    if (CollectionKit.isEmpty(this.publish))
      this.publish = new ArrayList<>();
    this.publish.add(publish);
    return this;
  }

  public FilterListContainer publish(List<String> publish) {
    this.publish = publish;
    return this;
  }

  public FilterListContainer since(String since) {
    if (CollectionKit.isEmpty(this.since))
      this.since = new ArrayList<>();
    this.since.add(since);
    return this;
  }

  public FilterListContainer since(List<String> since) {
    this.since = since;
    return this;
  }

  public FilterListContainer status(String status) {
    if (CollectionKit.isEmpty(this.status))
      this.status = new ArrayList<>();
    this.status.add(status);
    return this;
  }

  public FilterListContainer status(List<String> status) {
    this.status = status;
    return this;
  }

  public FilterListContainer volume(String volume) {
    if (CollectionKit.isEmpty(this.volume))
      this.volume = new ArrayList<>();
    this.volume.add(volume);
    return this;
  }

  public FilterListContainer volume(List<String> volume) {
    this.volume = volume;
    return this;
  }

  @Override
  public DQPListContainer back() {
    return this.lc;
  }

  DPara dqr() {
    Kv kv = Kv.create();
    if (CollectionKit.notEmpty(this.ancestor))
      kv.set("ancestor", this.ancestor);
    if (CollectionKit.notEmpty(this.before))
      kv.set("before", this.before);
    if (CollectionKit.notEmpty(this.expose))
      kv.set("expose", this.expose);
    if (CollectionKit.notEmpty(this.exited))
      kv.set("exited", this.exited.stream().map(Object::toString).collect(Collectors.toList()));
    if (CollectionKit.notEmpty(this.health))
      kv.set("health", this.health);
    if (CollectionKit.notEmpty(this.id))
      kv.set("id", this.id);
    if (CollectionKit.notEmpty(this.isolation))
      kv.set("isolation", this.isolation);
    if (CollectionKit.notEmpty(this.istask))
      kv.set("is-task", this.istask.stream().map(Object::toString).collect(Collectors.toList()));
    if (CollectionKit.notEmpty(this.label))
      kv.set("label", this.label);
    if (CollectionKit.notEmpty(this.name))
      kv.set("name", this.name);
    if (CollectionKit.notEmpty(this.network))
      kv.set("network", this.network);
    if (CollectionKit.notEmpty(this.publish))
      kv.set("publish", this.publish);
    if (CollectionKit.notEmpty(this.since))
      kv.set("since", this.since);
    if (CollectionKit.notEmpty(this.status))
      kv.set("status", this.status);
    if (CollectionKit.notEmpty(this.volume))
      kv.set("volume", this.volume);
    String json = Json.toJson(kv);
    kv.clear();
    return DPara.create()
      .put("filters", json);
  }

//  public FilterListContainer clear() {
//    return this;
//  }

}
