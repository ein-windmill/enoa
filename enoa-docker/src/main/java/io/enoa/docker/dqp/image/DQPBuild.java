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

import io.enoa.docker.dqp.DQH;
import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;

public class DQPBuild implements DQP {

  /**
   * string
   * default "Dockerfile"
   * <p>
   * Path within the build context to the Dockerfile. This is ignored if remote is specified and points to an external Dockerfile.
   */
  private String dockerfile;
  /**
   * string
   * <p>
   * A name and optional tag to apply to the image in the name:tag format.
   * If you omit the tag the default latest value is assumed. You can provide several t parameters.
   */
  private String t;
  /**
   * string
   * <p>
   * Extra hosts to add to /etc/hosts
   */
  private String extrahosts;
  /**
   * string
   * <p>
   * A Git repository URI or HTTP/HTTPS context URI. If the URI points to a single text file,
   * the file’s contents are placed into a file called Dockerfile and the image is built from that file.
   * If the URI points to a tarball, the file is downloaded by the daemon and the contents therein used as the context for the build.
   * If the URI points to a tarball and the dockerfile parameter is also specified,
   * there must be a file with the corresponding path inside the tarball.
   */
  private String remote;
  /**
   * boolean
   * default false
   * <p>
   * Suppress verbose build output.
   */
  private Boolean q;
  /**
   * boolean
   * default false
   * <p>
   * Do not use the cache when building the image.
   */
  private Boolean nocache;
  /**
   * string
   * <p>
   * JSON array of images used for build cache resolution.
   */
  private String cachefrom;
  /**
   * string
   * <p>
   * Attempt to pull the image even if an older image exists locally.
   */
  private String pull;
  /**
   * boolean
   * default true
   * <p>
   * Remove intermediate containers after a successful build.
   */
  private Boolean rm;
  /**
   * boolean
   * default false
   * <p>
   * Always remove intermediate containers, even upon failure.
   */
  private Boolean forcerm;
  /**
   * integer
   * <p>
   * Set memory limit for build.
   */
  private Integer memory;
  /**
   * integer
   * <p>
   * Total memory (memory + swap). Set as -1 to disable swap.
   */
  private Integer memswap;
  /**
   * integer
   * <p>
   * CPU shares (relative weight).
   */
  private Integer cpushares;
  /**
   * string
   * <p>
   * CPUs in which to allow execution (e.g., 0-3, 0,1).
   */
  private String cpusetcpus;
  /**
   * integer
   * <p>
   * The length of a CPU period in microseconds.
   */
  private Integer cpuperiod;
  /**
   * integer
   * <p>
   * Microseconds of CPU time that the container can get in a CPU period.
   */
  private Integer cpuquota;
  /**
   * string
   * <p>
   * JSON map of string pairs for build-time variables.
   * Users pass these values at build-time. Docker uses the buildargs as the environment context for commands run via
   * the Dockerfile RUN instruction, or for variable expansion in other Dockerfile instructions.
   * This is not meant for passing secret values.
   * <p>
   * For example, the build arg FOO=bar would become {"FOO":"bar"} in JSON.
   * This would result in the the query parameter buildargs={"FOO":"bar"}.
   * Note that {"FOO":"bar"} should be URI component encoded.
   *
   * <a href="https://docs.docker.com/engine/reference/builder/#arg">Read more about the buildargs instruction.</a>
   */
  private String buildargs;
  /**
   * integer
   * <p>
   * Size of /dev/shm in bytes. The size must be greater than 0. If omitted the system uses 64MB.
   */
  private Integer shmsize;
  /**
   * boolean
   * <p>
   * Squash the resulting images layers into a single layer. (Experimental release only.)
   */
  private Boolean squash;
  /**
   * string
   * <p>
   * Arbitrary key/value labels to set on the image, as a JSON map of string pairs.
   */
  private String labels;
  /**
   * string
   * <p>
   * Sets the networking mode for the run commands during build. Supported standard values are:
   * bridge, host, none, and container:<name|id>. Any other value is taken as a custom network's name to
   * which this container should connect to.
   */
  private String networkmode;
  /**
   * string
   * default ""
   * <p>
   * Platform in the format os[/arch[/variant]]
   */
  private String platform;


  /**
   * string
   * default "application/x-tar"
   */
  private String contenttype;

  /**
   * X-Registry-Config
   * string
   * <p>
   * This is a base64-encoded JSON object with auth configurations for multiple registries that a build may refer to.
   * <p>
   * The key is a registry URL, and the value is an auth configuration object, as described in the authentication section. For example:
   * <p>
   * {
   * "docker.example.com": {
   * "username": "janedoe",
   * "password": "hunter2"
   * },
   * "https://index.docker.io/v1/": {
   * "username": "mobydock",
   * "password": "conta1n3rize14"
   * }
   * }
   * <p>
   * Only the registry domain name (and port if not the default 443) are required.
   * However, for legacy reasons, the Docker Hub registry must be specified
   * with both a https:// prefix and a /v1/ suffix even though Docker will prefer to use the v2 registry API.
   */
  private String registryconfig;

  public static DQPBuild create() {
    return new DQPBuild();
  }

  public DQPBuild() {
    this.dockerfile = "Dockerfile";
    this.q = Boolean.FALSE;
    this.nocache = Boolean.FALSE;
    this.rm = Boolean.TRUE;
    this.forcerm = Boolean.FALSE;
    this.platform = "";
    this.contenttype = "application/x-tar";
  }

  public DQPBuild dockerfile(String dockerfile) {
    this.dockerfile = dockerfile;
    return this;
  }

  public DQPBuild t(String t) {
    this.t = t;
    return this;
  }

  public DQPBuild extrahosts(String extrahosts) {
    this.extrahosts = extrahosts;
    return this;
  }

  public DQPBuild remote(String remote) {
    this.remote = remote;
    return this;
  }

  public DQPBuild q() {
    return this.q(Boolean.TRUE);
  }

  public DQPBuild q(Boolean q) {
    this.q = q;
    return this;
  }

  public DQPBuild nocache() {
    return this.nocache(Boolean.TRUE);
  }

  public DQPBuild nocache(Boolean nocache) {
    this.nocache = nocache;
    return this;
  }

  public DQPBuild cachefrom(String cachefrom) {
    this.cachefrom = cachefrom;
    return this;
  }

  public DQPBuild pull(String pull) {
    this.pull = pull;
    return this;
  }

  public DQPBuild rm() {
    return this.rm(Boolean.TRUE);
  }

  public DQPBuild rm(Boolean rm) {
    this.rm = rm;
    return this;
  }

  public DQPBuild forcerm() {
    return this.forcerm(Boolean.TRUE);
  }

  public DQPBuild forcerm(Boolean forcerm) {
    this.forcerm = forcerm;
    return this;
  }

  public DQPBuild memory(Integer memory) {
    this.memory = memory;
    return this;
  }

  public DQPBuild memswap(Integer memswap) {
    this.memswap = memswap;
    return this;
  }

  public DQPBuild cpushares(Integer cpushares) {
    this.cpushares = cpushares;
    return this;
  }

  public DQPBuild cpusetcpus(String cpusetcpus) {
    this.cpusetcpus = cpusetcpus;
    return this;
  }

  public DQPBuild cpuperiod(Integer cpuperiod) {
    this.cpuperiod = cpuperiod;
    return this;
  }

  public DQPBuild cpuquota(Integer cpuquota) {
    this.cpuquota = cpuquota;
    return this;
  }

  public DQPBuild buildargs(String buildargs) {
    this.buildargs = buildargs;
    return this;
  }

  public DQPBuild shmsize(Integer shmsize) {
    this.shmsize = shmsize;
    return this;
  }

  public DQPBuild squash() {
    return this.squash(Boolean.TRUE);
  }

  public DQPBuild squash(Boolean squash) {
    this.squash = squash;
    return this;
  }

  public DQPBuild labels(String labels) {
    this.labels = labels;
    return this;
  }

  public DQPBuild networkmode(String networkmode) {
    this.networkmode = networkmode;
    return this;
  }

  public DQPBuild platform(String platform) {
    this.platform = platform;
    return this;
  }

  public DQPBuild contenttype(String contenttype) {
    this.contenttype = contenttype;
    return this;
  }

  public DQPBuild registryconfig(String registryconfig) {
    this.registryconfig = registryconfig;
    return this;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create()
      .put("dockerfile", this.dockerfile)
      .put("q", this.q)
      .put("nocache", this.nocache)
      .put("rm", this.rm)
      .put("forcerm", this.forcerm)
      .putIf("t", this.t)
      .putIf("extrahosts", this.extrahosts)
      .putIf("remote", this.remote)
      .putIf("cachefrom", this.cachefrom)
      .putIf("pull", this.pull)
      .putIf("labels", this.labels)
      .putIf("networkmode", this.networkmode)
      .putIf("platform", this.platform)
      .putIf("cpusetcpus", this.cpusetcpus)
      .putIf("buildargs", this.buildargs);

    if (this.memory != null)
      dqr.put("memory", this.memory);
    if (this.memswap != null)
      dqr.put("memswap", this.memswap);
    if (this.cpushares != null)
      dqr.put("cpushares", this.cpushares);
    if (this.cpuperiod != null)
      dqr.put("cpuperiod", this.cpuperiod);
    if (this.cpuquota != null)
      dqr.put("cpuquota", this.cpuquota);
    if (this.shmsize != null)
      dqr.put("shmsize", this.shmsize);
    if (this.squash != null)
      dqr.put("squash", this.squash);
    return dqr;
  }

  @Override
  public DQH dqh() {
    DQH dqh = DQH.create()
      .add("Content-type", this.contenttype)
      .addIf("X-Registry-Config", this.registryconfig);
    return dqh;
  }
}
