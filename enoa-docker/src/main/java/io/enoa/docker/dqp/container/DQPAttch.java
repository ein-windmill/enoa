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
import io.enoa.toolkit.text.TextKit;

public class DQPAttch implements DQP {

  private String detachkeys;
  private boolean logs;
  private boolean stream;
  private boolean stdin;
  private boolean stdout;
  private boolean stderr;


  public static DQPAttch create() {
    return new DQPAttch();
  }

  public DQPAttch() {
  }


  public DQPAttch detachkeys(String detachkeys) {
    this.detachkeys = detachkeys;
    return this;
  }

  public DQPAttch logs(boolean logs) {
    this.logs = logs;
    return this;
  }

  public DQPAttch stream(boolean stream) {
    this.stream = stream;
    return this;
  }

  public DQPAttch stdin(boolean stdin) {
    this.stdin = stdin;
    return this;
  }

  public DQPAttch stdout(boolean stdout) {
    this.stdout = stdout;
    return this;
  }

  public DQPAttch stderr(boolean stderr) {
    this.stderr = stderr;
    return this;
  }

  @Override
  public DPara para() {
    DPara dqr = DPara.create();
    if (TextKit.blankn(detachkeys))
      dqr.put("detachKeys", this.detachkeys);
    if (this.logs)
      dqr.put("logs", this.logs);
    if (this.stream)
      dqr.put("stream", this.stream);
    if (this.stdin)
      dqr.put("stdin", this.stdin);
    if (this.stdout)
      dqr.put("stdout", this.stdout);
    if (this.stderr)
      dqr.put("stderr", this.stderr);
    return dqr;
  }

}
