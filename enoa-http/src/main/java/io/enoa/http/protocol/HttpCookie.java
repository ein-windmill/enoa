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
package io.enoa.http.protocol;

import java.net.IDN;
import java.util.Date;
import java.util.Locale;

public class HttpCookie {


  private final String name;
  private final String value;
  private final long expires;
  private final String domain;
  private final String path;
  private final boolean secure;
  private final boolean httpOnly;

  private final boolean persistent;
  private final boolean hostOnly;

  private HttpCookie(String name, String value, long expires, String domain, String path,
                     boolean secure, boolean httpOnly, boolean hostOnly, boolean persistent) {
    this.name = name;
    this.value = value;
    this.expires = expires;
    this.domain = domain;
    this.path = path;
    this.secure = secure;
    this.httpOnly = httpOnly;
    this.hostOnly = hostOnly;
    this.persistent = persistent;
  }

  HttpCookie(Builder builder) {
    if (builder.name == null) throw new NullPointerException("builder.name == null");
    if (builder.value == null) throw new NullPointerException("builder.value == null");
//    if (builder.domain == null) throw new NullPointerException("builder.domain == null");

    this.name = builder.name;
    this.value = builder.value;
    this.expires = builder.expiresAt;
    this.domain = builder.domain;
    this.path = builder.path;
    this.secure = builder.secure;
    this.httpOnly = builder.httpOnly;
    this.persistent = builder.persistent;
    this.hostOnly = builder.hostOnly;
  }

  public String name() {
    return name;
  }

  public String value() {
    return value;
  }

  public boolean persistent() {
    return persistent;
  }

  public long expires() {
    return expires;
  }

  public boolean hostOnly() {
    return hostOnly;
  }

  public String domain() {
    return domain;
  }

  public String path() {
    return path;
  }

  public boolean httpOnly() {
    return httpOnly;
  }

  public boolean secure() {
    return secure;
  }


  public static final class Builder {
    String name;
    String value;
    long expiresAt = HttpDate.MAX_DATE;
    String domain;
    String path = "/";
    boolean secure;
    boolean httpOnly;
    boolean persistent;
    boolean hostOnly;

    private static String domainToAscii(String input) {
      try {
        String result = IDN.toASCII(input).toLowerCase(Locale.US);
        if (result.isEmpty()) return null;
        if (containsInvalidHostnameAsciiCodes(result)) {
          return null;
        }
        return result;
      } catch (IllegalArgumentException e) {
        return null;
      }
    }

    private static boolean containsInvalidHostnameAsciiCodes(String hostnameAscii) {
      for (int i = 0; i < hostnameAscii.length(); i++) {
        char c = hostnameAscii.charAt(i);
        if (c <= '\u001f' || c >= '\u007f') {
          return true;
        }
        if (" #%/:?@[\\]".indexOf(c) != -1) {
          return true;
        }
      }
      return false;
    }

    public Builder name(String name) {
      if (name == null) throw new NullPointerException("name == null");
      if (!name.trim().equals(name)) throw new IllegalArgumentException("name is not trimmed");
      this.name = name;
      return this;
    }

    public Builder value(String value) {
      if (value == null) throw new NullPointerException("value == null");
      if (!value.trim().equals(value)) throw new IllegalArgumentException("value is not trimmed");
      this.value = value;
      return this;
    }

    public Builder expires(long expiresAt) {
      if (expiresAt <= 0) expiresAt = Long.MIN_VALUE;
      if (expiresAt > HttpDate.MAX_DATE) expiresAt = HttpDate.MAX_DATE;
      this.expiresAt = expiresAt;
      this.persistent = true;
      return this;
    }

    public Builder domain(String domain) {
      return domain(domain, false);
    }

    /**
     * Set the host-only domain for this cookie. The cookie will match {@code domain} but none of
     * its subdomains.
     */
    public Builder hostOnlyDomain(String domain) {
      return domain(domain, true);
    }

    private Builder domain(String domain, boolean hostOnly) {
      if (domain == null) throw new NullPointerException("domain == null");
      String canonicalDomain = domainToAscii(domain);
      if (canonicalDomain == null) {
        throw new IllegalArgumentException("unexpected domain: " + domain);
      }
      this.domain = canonicalDomain;
      this.hostOnly = hostOnly;
      return this;
    }

    public Builder path(String path) {
      if (path != null && !path.startsWith("/"))
        throw new IllegalArgumentException("path must start with '/'");
      this.path = path;
      return this;
    }

    public Builder secure() {
      this.secure = true;
      return this;
    }

    public Builder httpOnly() {
      this.httpOnly = true;
      return this;
    }

    public HttpCookie build() {
      return new HttpCookie(this);
    }
  }


  @Override
  public String toString() {
    return toString(false);
  }

  /**
   * @param forObsoleteRfc2965 true to include a leading {@code .} on the domain pattern. This is
   *                           necessary for {@code example.com} to match {@code www.example.com} under RFC 2965. This
   *                           extra dot is ignored by more recent specifications.
   */
  String toString(boolean forObsoleteRfc2965) {
    StringBuilder result = new StringBuilder();
    result.append(name);
    result.append('=');
    result.append(value);

    if (persistent) {
      if (expires == Long.MIN_VALUE) {
        result.append("; max-age=0");
      } else {
        result.append("; expires=").append(HttpDate.format(new Date(expires)));
      }
    }

    if (!hostOnly) {
      if (this.domain != null) {
        result.append("; domain=");
        if (forObsoleteRfc2965) {
          result.append(".");
        }
        result.append(domain);
      }
    }

    result.append("; path=").append(path);

    if (secure) {
      result.append("; secure");
    }

    if (httpOnly) {
      result.append("; httponly");
    }

    return result.toString();
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof HttpCookie)) return false;
    HttpCookie that = (HttpCookie) other;
    return that.name.equals(name)
      && that.value.equals(value)
      && (that.domain != null && this.domain != null) ? that.domain.equals(this.domain) : that.domain == null && this.domain == null
      && (that.path != null && this.path != null) ? that.path.equals(this.path) : that.path == null && this.path == null
      && that.expires == expires
      && that.secure == secure
      && that.httpOnly == httpOnly
      && that.persistent == persistent
      && that.hostOnly == hostOnly;
  }

  @Override
  public int hashCode() {
    int hash = 17;
    hash = 31 * hash + name.hashCode();
    hash = 31 * hash + value.hashCode();
    hash = 31 * hash + (domain == null ? 0 : domain.hashCode());
    hash = 31 * hash + (path == null ? 0 : path.hashCode());
    hash = 31 * hash + (int) (expires ^ (expires >>> 32));
    hash = 31 * hash + (secure ? 0 : 1);
    hash = 31 * hash + (httpOnly ? 0 : 1);
    hash = 31 * hash + (persistent ? 0 : 1);
    hash = 31 * hash + (hostOnly ? 0 : 1);
    return hash;
  }
}
