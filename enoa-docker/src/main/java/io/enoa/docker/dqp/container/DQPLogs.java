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

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DPara;

public class DQPLogs implements DQP {

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
  private boolean follow;

  /**
   * boolean
   * default false
   * <p>
   * Return logs from stdout
   */
  private boolean stdout;

  /**
   * boolean
   * default false
   * <p>
   * Return logs from stderr
   */
  private boolean stderr;

  /**
   * integer
   * default 0
   * <p>
   * Only return logs since this time, as a UNIX timestamp
   */
  private int since;

  /**
   * integer
   * default 0
   * <p>
   * Only return logs before this time, as a UNIX timestamp
   */
  private int unit;

  /**
   * boolean
   * default false
   * <p>
   * Add timestamps to every log line
   */
  private boolean timestamps;

  /**
   * string
   * default "all"
   * <p>
   * Only return this number of log lines from the end of the logs. Specify as an integer or all to output all log lines.
   */
  private String tail;

  public static DQPLogs create() {
    return new DQPLogs();
  }

  public DQPLogs() {
    this.since = 0;
    this.unit = 0;
    this.tail = "all";
  }

  public DQPLogs follow() {
    return this.follow(true);
  }

  public DQPLogs follow(boolean follow) {
    this.follow = follow;
    return this;
  }

  public DQPLogs stdout() {
    return this.stdout(true);
  }

  public DQPLogs stdout(boolean stdout) {
    this.stdout = stdout;
    return this;
  }

  public DQPLogs stderr() {
    return this.stderr(true);
  }

  public DQPLogs stderr(boolean stderr) {
    this.stderr = stderr;
    return this;
  }

  public DQPLogs since(int since) {
    this.since = since;
    return this;
  }

  public DQPLogs unit(int unit) {
    this.unit = unit;
    return this;
  }

  public DQPLogs timestamps() {
    return this.timestamps(true);
  }

  public DQPLogs timestamps(boolean timestamps) {
    this.timestamps = timestamps;
    return this;
  }

  public DQPLogs tail(String tail) {
    this.tail = tail;
    return this;
  }

  @Override
  public DPara para() {
    DPara dqr = DPara.create();
    if (this.follow)
      dqr.put("follow", this.follow);
    if (this.stdout)
      dqr.put("stdout", this.stdout);
    if (this.stderr)
      dqr.put("stderr", this.stderr);
    if (this.timestamps)
      dqr.put("timestamps", this.timestamps);
    if (this.since != 0)
      dqr.put("since", this.since);
    if (this.unit != 0)
      dqr.put("unit", this.unit);
    if (!this.tail.equals("all"))
      dqr.put("tail", this.tail);
    return dqr;
  }

}
