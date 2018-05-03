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

import java.util.Collections;
import java.util.Set;

public class RedisSentinelConfig {


  private final String name;
  private final String masterName;
  private final Set<String> sentinels;
  private final JedisPoolConfig poolConfig;
  private final int connectionTimeout;
  private final int soTimeout;
  private final String passwd;
  private final int database;


  public RedisSentinelConfig(Builder builder) {
    this.name = builder.name;
    this.masterName = builder.masterName;
    this.sentinels = builder.sentinels;
    this.poolConfig = builder.poolConfig;
    this.connectionTimeout = builder.connectionTimeout;
    this.soTimeout = builder.soTimeout;
    this.passwd = builder.passwd;
    this.database = builder.database;
  }


  public String masterName() {
    return masterName;
  }

  public Set<String> sentinels() {
    return sentinels;
  }

  public JedisPoolConfig poolConfig() {
    return poolConfig;
  }

  public int connectionTimeout() {
    return connectionTimeout;
  }

  public int soTimeout() {
    return soTimeout;
  }

  public String passwd() {
    return passwd;
  }

  public int database() {
    return database;
  }

  public String name() {
    return name;
  }

  public static class Builder {
    private String masterName;
    private Set<String> sentinels;
    private JedisPoolConfig poolConfig;
    private int connectionTimeout;
    private int soTimeout;
    private String passwd;
    private int database;
    private String name;

    public Builder() {
      this.sentinels = Collections.emptySet();
      this.connectionTimeout = Protocol.DEFAULT_TIMEOUT;
      this.soTimeout = Protocol.DEFAULT_TIMEOUT;
      this.database = Protocol.DEFAULT_DATABASE;
      this.poolConfig = new JedisPoolConfig();
      this.poolConfig.setMaxIdle(8);
      this.poolConfig.setMaxTotal(10);
    }


    public RedisSentinelConfig build() {
      return new RedisSentinelConfig(this);
    }

    public Builder masterName(String masterName) {
      this.masterName = masterName;
      return this;
    }

    public Builder sentinels(Set<String> sentinels) {
      this.sentinels = sentinels;
      return this;
    }

    public Builder poolConfig(JedisPoolConfig poolConfig) {
      this.poolConfig = poolConfig;
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

    public Builder passwd(String passwd) {
      this.passwd = passwd;
      return this;
    }

    public Builder database(int database) {
      this.database = database;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }
  }

}
