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
package io.enoa.eml;

import io.enoa.eml.api.ISenderReporter;
import io.enoa.eml.entity.Attachment;
import io.enoa.eml.entity.MailPerson;
import io.enoa.promise.DonePromise;
import io.enoa.toolkit.eo.tip.EnoaTipKit;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Collection;

public interface EmlSender {

  EmlSender charset(Charset charset);

  default EmlSender from(String email) {
    return this.from(MailPerson.create(email));
  }

  default EmlSender from(String email, String name) {
    return this.from(MailPerson.create(email, name));
  }

  EmlSender from(MailPerson from);

  EmlSender to(MailPerson person);

  default EmlSender to(String email) {
    return this.to(MailPerson.create(email));
  }

  default EmlSender to(String email, String name) {
    return this.to(MailPerson.create(email, name));
  }

  default EmlSender to(MailPerson... tos) {
    if (tos == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.targets_null"));
    for (MailPerson person : tos) {
      this.to(person);
    }
    return this;
  }

  default EmlSender to(String... tos) {
    if (tos == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.targets_null"));
    for (String to : tos) {
      this.to(to);
    }
    return this;
  }

  default EmlSender to(Collection tos) {
    if (tos == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.targets_null"));
    if (tos.isEmpty())
      return this;

    for (Object to : tos) {
      if (to instanceof String) {
        this.to((String) to);
        continue;
      }
      if (to instanceof MailPerson) {
        this.to((MailPerson) to);
        continue;
      }
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.person_not_support"));
    }
    return this;
  }

  EmlSender cc(MailPerson person);

  default EmlSender cc(String email) {
    return this.cc(MailPerson.create(email));
  }

  default EmlSender cc(String email, String name) {
    return this.cc(MailPerson.create(email, name));
  }

  default EmlSender cc(MailPerson... ccs) {
    if (ccs == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.targets_null"));
    for (MailPerson person : ccs) {
      this.cc(person);
    }
    return this;
  }

  default EmlSender cc(String... ccs) {
    if (ccs == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.targets_null"));
    for (String cc : ccs) {
      this.cc(cc);
    }
    return this;
  }

  default EmlSender cc(Collection ccs) {
    if (ccs == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.targets_null"));
    if (ccs.isEmpty())
      return this;

    for (Object cc : ccs) {
      if (cc instanceof String) {
        this.cc((String) cc);
        continue;
      }
      if (cc instanceof MailPerson) {
        this.cc((MailPerson) cc);
        continue;
      }
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.person_not_support"));
    }
    return this;
  }

  EmlSender bcc(MailPerson person);

  default EmlSender bcc(String email) {
    return this.bcc(MailPerson.create(email));
  }

  default EmlSender bcc(String email, String name) {
    return this.bcc(MailPerson.create(email, name));
  }

  default EmlSender bcc(MailPerson... bccs) {
    if (bccs == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.targets_null"));
    for (MailPerson person : bccs) {
      this.bcc(person);
    }
    return this;
  }

  default EmlSender bcc(String... bccs) {
    if (bccs == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.targets_null"));
    for (String cc : bccs) {
      this.bcc(cc);
    }
    return this;
  }

  default EmlSender bcc(Collection bccs) {
    if (bccs == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.targets_null"));
    if (bccs.isEmpty())
      return this;

    for (Object bcc : bccs) {
      if (bcc instanceof String) {
        this.bcc((String) bcc);
        continue;
      }
      if (bcc instanceof MailPerson) {
        this.bcc((MailPerson) bcc);
        continue;
      }
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.person_not_support"));
    }
    return this;
  }

  EmlSender subject(String subject);

  default EmlSender richtext() {
    return this.richtext(true);
  }

  EmlSender richtext(boolean richtext);

  EmlSender body(String body);

  EmlSender attachment(Attachment attachment);

  default EmlSender attachment(String name, byte[] binary) {
    return this.attachment(Attachment.create(name, binary));
  }

  default EmlSender attachment(String name, byte[] binary, String mime) {
    return this.attachment(Attachment.create(name, binary, mime));
  }

  default EmlSender attachment(Path path) {
    return this.attachment(Attachment.create(path));
  }

  default EmlSender attachment(String name, Path path) {
    return this.attachment(Attachment.create(name, path));
  }

  default EmlSender attachment(Attachment... attachments) {
    if (attachments == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.attachments_null"));
    for (Attachment attachment : attachments) {
      this.attachment(attachment);
    }
    return this;
  }

  default EmlSender attachment(Collection<Attachment> attachments) {
    if (attachments == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.attachments_null"));
    for (Attachment attachment : attachments) {
      this.attachment(attachment);
    }
    return this;
  }

  EmlSender reporter(ISenderReporter reporter);

  void emit();

  DonePromise enqueue();
}
