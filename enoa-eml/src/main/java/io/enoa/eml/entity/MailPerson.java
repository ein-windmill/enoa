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
package io.enoa.eml.entity;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.text.TextKit;

import java.io.Serializable;

public class MailPerson implements Serializable {

  private String email;
  private String name;

  public MailPerson(String email) {
    this(email, null);
  }

  public MailPerson(String email, String name) {
    if (email == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.person_email_null"));
    this.email = email;
    this.name = name;
  }

  public static MailPerson create(String email) {
    return new MailPerson(email);
  }

  public static MailPerson create(String email, String name) {
    return new MailPerson(email, name);
  }

  public String email() {
    return this.email;
  }

  public String name() {
    return this.name;
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + this.email.hashCode();
    if (this.name == null)
      return result;
    result = 31 * result + this.name.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object other) {
    if (other == null)
      return false;
    if (!(other instanceof MailPerson))
      return false;
    if (this == other)
      return true;
    MailPerson that = (MailPerson) other;
    if (!this.email.equals(that.email))
      return false;
    if (this.name == null && that.name == null)
      return true;
    return (this.name != null) && this.name.equals(that.name);
  }

  @Override
  public String toString() {
    return TextKit.union(this.email, this.name == null ? "" : (TextKit.union("::", this.name)));
  }
}
