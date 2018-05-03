/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
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
package io.enoa.eml;

import io.enoa.eml.api.ReceiverHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public interface EmlReceiver {

//  default EmlReceiver daemon() {
//    return this.daemon(true);
//  }
//
//  EmlReceiver daemon(boolean daemon);

  EmlReceiver executor(ExecutorService es);

  /**
   * 定期接收
   *
   * @param unit     间隔时间单位
   * @param duration 间隔时长
   * @return EmlReceiver
   */
  EmlReceiver cron(TimeUnit unit, long duration);

  EmlReceiver folder(String folder);

  default EmlReceiver folder(String... folders) {
    for (String folder : folders) {
      this.folder(folder);
    }
    return this;
  }

  default EmlReceiver inbox() {
    return this.folder("inbox");
  }

  EmlReceiver handle(ReceiverHandler handler);

  void receive();

}
