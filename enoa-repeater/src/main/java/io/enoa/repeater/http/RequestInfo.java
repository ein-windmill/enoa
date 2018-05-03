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
//package io.enoa.repeater.http;
//
//public class RequestInfo {
//
//  private final String protocol;
//
//  private final String localName;
//  private final String localAddr;
//  private final Integer localPort;
//
//  private final String remoteName;
//  private final String remoteAddr;
//  private final Integer remotePort;
//
//  private final String serverName;
//  private final Integer serverPort;
//
//
//  private RequestInfo(Builder builder) {
//    this.protocol = builder.protocol;
//    this.localName = builder.localName;
//    this.localPort = builder.localPort;
//    this.localAddr = builder.localAddr;
//    this.remoteName = builder.remoteName;
//    this.remoteAddr = builder.remoteAddr;
//    this.remotePort = builder.remotePort;
//    this.serverName = builder.serverName;
//    this.serverPort = builder.serverPort;
//  }
//
//  public String protocol() {
//    return protocol;
//  }
//
//  public String localName() {
//    return localName;
//  }
//
//  public String localAddr() {
//    return localAddr;
//  }
//
//  public Integer localPort() {
//    return localPort;
//  }
//
//  public String remoteName() {
//    return remoteName;
//  }
//
//  public String remoteAddr() {
//    return remoteAddr;
//  }
//
//  public Integer remotePort() {
//    return remotePort;
//  }
//
//  public String serverName() {
//    return serverName;
//  }
//
//  public Integer serverPort() {
//    return serverPort;
//  }
//
//  public static class Builder {
//    private String protocol;
//
//    private String localName;
//    private String localAddr;
//    private Integer localPort;
//
//    private String remoteName;
//    private String remoteAddr;
//    private Integer remotePort;
//
//    private String serverName;
//    private Integer serverPort;
//
//
//    public Builder protocol(String protocol) {
//      this.protocol = protocol;
//      return this;
//    }
//
//    public Builder localName(String localName) {
//      this.localName = localName;
//      return this;
//    }
//
//    public Builder localAddr(String localAddr) {
//      this.localAddr = localAddr;
//      return this;
//    }
//
//    public Builder localPort(Integer localPort) {
//      this.localPort = localPort;
//      return this;
//    }
//
//    public Builder remoteName(String remoteName) {
//      this.remoteName = remoteName;
//      return this;
//    }
//
//    public Builder remoteAddr(String remoteAddr) {
//      this.remoteAddr = remoteAddr;
//      return this;
//    }
//
//    public Builder remotePort(Integer remotePort) {
//      this.remotePort = remotePort;
//      return this;
//    }
//
//    public Builder serverName(String serverName) {
//      this.serverName = serverName;
//      return this;
//    }
//
//    public Builder serverPort(Integer serverPort) {
//      this.serverPort = serverPort;
//      return this;
//    }
//  }
//
//}
