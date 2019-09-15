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
package io.enoa.docker.command.docker.origin;

import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dket.docker.service.DQPServiceLogs;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.service.DQPServiceCreate;
import io.enoa.docker.dqp.docker.service.DQPServiceUpdate;

public interface EOriginDockerService {

  default DResp list() {
    return this.list(null);
  }

  DResp list(DQPFilter dqp);

  default DResp create(String body) {
    return this.create(body, null);
  }

  DResp create(String body, DQPServiceCreate dqp);

  default DResp inspect(String id) {
    return this.inspect(id, Boolean.FALSE);
  }

  DResp inspect(String id, boolean insertDefaults);

  DResp remove(String id);

  DResp update(String id, String body, DQPServiceUpdate dqp);

  default DResp logs(String id) {
    return this.logs(id, null);
  }

  DResp logs(String id, DQPServiceLogs dqp);


}
