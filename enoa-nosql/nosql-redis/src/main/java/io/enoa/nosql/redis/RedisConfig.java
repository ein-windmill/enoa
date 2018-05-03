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
package io.enoa.nosql.redis;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;

public class RedisConfig {

  private final String name;
  private final String host;
  private final int port;
  private final String passwd;
  private final Integer connectionTimeout;
  private final Integer soTimeout;
  private final Integer database;
  private final boolean ssl;
  private final SSLSocketFactory sslSocketFactory;
  private final SSLParameters sslParameters;
  private final HostnameVerifier hostnameVerifier;
  private final JedisPoolConfig poolConfig;


  private RedisConfig(Builder builder) {
    this.name = builder.name;
    this.host = builder.host;
    this.port = builder.port;
    this.passwd = builder.passwd;
    this.connectionTimeout = builder.connectionTimeout;
    this.soTimeout = builder.soTimeout;
    this.database = builder.database;
    this.ssl = builder.ssl;
    this.sslSocketFactory = builder.sslSocketFactory;
    this.sslParameters = builder.sslParameters;
    this.hostnameVerifier = builder.hostnameVerifier;
    this.poolConfig = builder.poolConfig;
  }

  public String name() {
    return name;
  }

  public String host() {
    return host;
  }

  public int port() {
    return port;
  }

  public String passwd() {
    return passwd;
  }

  public Integer connectionTimeout() {
    return connectionTimeout;
  }

  public Integer soTimeout() {
    return soTimeout;
  }

  public Integer database() {
    return database;
  }

  public boolean ssl() {
    return ssl;
  }

  public SSLSocketFactory sslSocketFactory() {
    return sslSocketFactory;
  }

  public SSLParameters sslParameters() {
    return sslParameters;
  }

  public HostnameVerifier hostnameVerifier() {
    return hostnameVerifier;
  }

  public JedisPoolConfig poolConfig() {
    return poolConfig;
  }

  public static class Builder {
    private String name;
    private String host;
    private int port;
    private String passwd;
    private Integer connectionTimeout;
    private Integer soTimeout;
    private Integer database;
    private boolean ssl;
    private SSLSocketFactory sslSocketFactory;
    private SSLParameters sslParameters;
    private HostnameVerifier hostnameVerifier;
    private JedisPoolConfig poolConfig;


    public Builder() {
      this.name = "main";
      this.port = 6379;
      this.ssl = false;
      this.connectionTimeout = Protocol.DEFAULT_TIMEOUT;
      this.soTimeout = Protocol.DEFAULT_TIMEOUT;
      this.poolConfig = new JedisPoolConfig();
      this.poolConfig.setMaxIdle(8);
      this.poolConfig.setMaxTotal(10);
    }

    public RedisConfig build() {
      return new RedisConfig(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder host(String host) {
      this.host = host;
      return this;
    }

    public Builder port(int port) {
      this.port = port;
      return this;
    }

    public Builder passwd(String passwd) {
      this.passwd = passwd;
      return this;
    }

    public Builder connectionTimeout(int connectionTimeout) {
      this.connectionTimeout = connectionTimeout;
      return this;
    }

    public Builder soTimeout(int soTimeout) {
      this.soTimeout = soTimeout;
      return this;
    }

    public Builder database(int database) {
      this.database = database;
      return this;
    }

    public Builder ssl(boolean ssl) {
      this.ssl = ssl;
      return this;
    }

    public Builder sslSocketFactory(SSLSocketFactory sslSocketFactory) {
      this.sslSocketFactory = sslSocketFactory;
      return this;
    }

    public Builder sslParameters(SSLParameters sslParameters) {
      this.sslParameters = sslParameters;
      return this;
    }

    public Builder hostnameVerifier(HostnameVerifier hostnameVerifier) {
      this.hostnameVerifier = hostnameVerifier;
      return this;
    }

    public Builder poolConfig(JedisPoolConfig poolConfig) {
      this.poolConfig = poolConfig;
      return this;
    }
  }


}
