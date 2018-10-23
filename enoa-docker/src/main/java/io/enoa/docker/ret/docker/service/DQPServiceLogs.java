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
package io.enoa.docker.ret.docker.service;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;

public class DQPServiceLogs implements DQP {

  /**
   * boolean
   * default false
   * <p>
   * Show service context and extra details provided to logs.
   */
  private Boolean details;

  /**
   * boolean
   * default false
   * <p>
   * Return the logs as a stream.
   * <p>
   * This will return a 101 HTTP response with a Connection: upgrade header, then hijack the HTTP connection to send raw output.
   * For more information about hijacking and the stream format,
   * <a href="https://docs.docker.com/engine/api/v1.37/#operation/ContainerAttach">see the documentation for the attach endpoint.</a>
   */
  private Boolean follow;

  /**
   * boolean
   * default false
   * <p>
   * Return logs from stdout
   */
  private Boolean stdout;

  /**
   * boolean
   * default false
   * <p>
   * Return logs from stderr
   */
  private Boolean stderr;

  /**
   * integer
   * default 0
   * <p>
   * Only return logs since this time, as a UNIX timestamp
   */
  private int since;

  /**
   * boolean
   * default false
   * <p>
   * Add timestamps to every log line
   */
  private Boolean timestamps;

  /**
   * string
   * default "all"
   * <p>
   * Only return this number of log lines from the end of the logs. Specify as an integer or all to output all log lines.
   */
  private String tail;

  public static DQPServiceLogs create() {
    return new DQPServiceLogs();
  }

  public DQPServiceLogs() {
    this.since = 0;
    this.tail = "all";
    this.details = Boolean.FALSE;
    this.follow = Boolean.FALSE;
    this.stdout = Boolean.FALSE;
    this.stderr = Boolean.FALSE;
    this.timestamps = Boolean.FALSE;
  }

  public DQPServiceLogs follow() {
    return this.follow(true);
  }

  public DQPServiceLogs follow(boolean follow) {
    this.follow = follow;
    return this;
  }

  public DQPServiceLogs stdout() {
    return this.stdout(true);
  }

  public DQPServiceLogs stdout(boolean stdout) {
    this.stdout = stdout;
    return this;
  }

  public DQPServiceLogs stderr() {
    return this.stderr(true);
  }

  public DQPServiceLogs stderr(boolean stderr) {
    this.stderr = stderr;
    return this;
  }

  public DQPServiceLogs since(int since) {
    this.since = since;
    return this;
  }

  public DQPServiceLogs timestamps() {
    return this.timestamps(true);
  }

  public DQPServiceLogs timestamps(boolean timestamps) {
    this.timestamps = timestamps;
    return this;
  }

  public DQPServiceLogs tail(String tail) {
    this.tail = tail;
    return this;
  }

  public DQPServiceLogs details(boolean details) {
    this.details = details;
    return this;
  }

  @Override
  public DQR dqr() {
    return DQR.create()
      .put("details", this.details)
      .put("follow", this.follow)
      .put("stdout", this.stdout)
      .put("stderr", this.stderr)
      .put("since", this.since)
      .put("timestamps", this.timestamps)
      .put("tail", this.tail);

  }

}
