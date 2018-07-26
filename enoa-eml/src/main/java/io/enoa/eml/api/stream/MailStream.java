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
package io.enoa.eml.api.stream;

import io.enoa.eml.entity.Attachment;
import io.enoa.eml.entity.MailPerson;
import io.enoa.toolkit.binary.EnoaBinary;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

interface MailStream extends Serializable {

  Charset charset();

  MailPerson from();

  Set<MailPerson> tos();

  Set<MailPerson> ccs();

  Set<MailPerson> bccs();

  String subject();

  boolean richtext();

  String body();

  List<Attachment> attachments();

  EnoaBinary eml();

}
