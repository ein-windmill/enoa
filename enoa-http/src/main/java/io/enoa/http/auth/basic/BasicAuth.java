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
package io.enoa.http.auth.basic;

import io.enoa.http.HttpAuth;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;
import java.util.Base64;

public class BasicAuth implements HttpAuth {

  private static Boolean HIGHT;

  private String user;
  private String passwd;
  private String token;

  public BasicAuth(String user, String passwd) {
    if (user == null)
      throw new IllegalArgumentException("Basic auth user can not be null.");
    if (passwd == null)
      throw new IllegalArgumentException("Basic auth passwd can not be null.");

    this.user = user;
    this.passwd = passwd;
    this.token = this.encode(user, passwd);
  }

  @Override
  public String identity() {
    return "Basic";
  }

  @Override
  public String token() {
    return this.token;
  }


  protected String encode(String user, String passwd) {
    byte[] auth = user.concat(":").concat(passwd).getBytes(Charset.forName("UTF-8"));
    if (HIGHT != null) {
      return HIGHT ?
        this.java8Encode(auth) :
        this.lowJavaEncode(auth);
    }

    try {
      Class.forName("java.util.Base64");
      HIGHT = true;
      return this.java8Encode(auth);
    } catch (ClassNotFoundException e) {
      HIGHT = false;
      return this.lowJavaEncode(auth);
    }
  }

  private String java8Encode(byte[] auth) {
    return Base64.getEncoder().encodeToString(auth);
  }

  private String lowJavaEncode(byte[] auth) {
    return DatatypeConverter.printBase64Binary(auth);
  }

  @Override
  public String toString() {
    return "BasicAuth{" +
      "user='" + user + '\'' +
      ", passwd='" + passwd + '\'' +
      '}';
  }
}
