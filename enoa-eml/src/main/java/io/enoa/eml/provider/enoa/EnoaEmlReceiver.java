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
package io.enoa.eml.provider.enoa;

import io.enoa.eml.EmlConfig;
import io.enoa.eml.EmlProtocol;
import io.enoa.eml.EmlReceiver;
import io.enoa.eml.EoEmlSession;
import io.enoa.eml.api.ReceiverHandler;
import io.enoa.eml.thr.EoEmailException;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

class EnoaEmlReceiver implements EmlReceiver {

  private EoEmlSession sess;
  private EmlProtocol protocol;
  private ExecutorService es;
  private TimeUnit unit;
  private long duration;
  private Set<String> folders;
  private List<ReceiverHandler> handlers;

  EnoaEmlReceiver(EoEmlSession sess, EmlProtocol protocol) {
    this.sess = sess;
    this.protocol = protocol;
  }


  @Override
  public EmlReceiver executor(ExecutorService es) {
    this.es = es;
    return this;
  }

  @Override
  public EmlReceiver cron(TimeUnit unit, long duration) {
    this.unit = unit;
    this.duration = duration;
    return this;
  }

  @Override
  public EmlReceiver folder(String folder) {
    if (this.folders == null)
      this.folders = new HashSet<>();
    this.folders.add(folder.toLowerCase());
    return this;
  }

  @Override
  public EmlReceiver handle(ReceiverHandler handler) {
    if (this.handlers == null)
      this.handlers = new ArrayList<>();
    this.handlers.add(handler);
    return this;
  }

  @Override
  public void receive() {
    if (this.handlers == null)
      return;
    if (this.folders == null) {
      this.inbox();
    }
    EmlConfig config = this.sess.config();
    Session session = this.sess.sess(this.protocol);

    Store store = null;
    try {
      store = session.getStore(this.protocol.val());
      store.connect(config.user(), config.passwd());
      for (String fer : this.folders) {
        Folder folder = store.getFolder(fer);
        folder.open(Folder.READ_ONLY);
        int size = folder.getMessageCount();
        Message[] messages = folder.getMessages();
//        Message message = folder.getMessage(size);
//        System.out.println(size);

        folder.close(false);
      }
    } catch (Exception e) {
      if (!config.skipError())
        throw new EoEmailException(e.getMessage(), e);
      else
        e.printStackTrace();
    } finally {
      if (store != null)
        try {
          store.close();
        } catch (Exception ignored) {
        }
    }
  }
}
