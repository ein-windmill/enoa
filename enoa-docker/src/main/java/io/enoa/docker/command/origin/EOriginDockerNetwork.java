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
package io.enoa.docker.command.origin;

import io.enoa.docker.dqp.network.DQPNetworkInspect;
import io.enoa.docker.dqp.network.DQPNetworkList;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.ret.docker.DResp;

/**
 * Networks are user-defined networks that containers can be attached to. See the <a href="https://docs.docker.com/engine/userguide/networking/">networking documentation</a> for more information.
 */
public interface EOriginDockerNetwork {

  /**
   * @see #list(DQPNetworkList)
   */
  default DResp list() {
    return this.list(null);
  }

  /**
   * List networks
   * Returns a list of networks. For details on the format, see <a href="https://docs.docker.com/engine/api/v1.37/#operation/NetworkInspect">the network inspect endpoint.</a>
   * <p>
   * Note that it uses a different, smaller representation of a network than inspecting a single network. For example, the list of containers attached to the network is not propagated in API versions 1.28 and up.
   *
   * @param dqp dqp
   * @return DResp
   */
  DResp list(DQPNetworkList dqp);

  default DResp inspect(String id) {
    return this.inspect(id, null);
  }

  /**
   * Inspect a network
   *
   * @param id  string Required
   *            <p>
   *            Network ID or name
   * @param dqp dqp
   * @return DResp
   */
  DResp inspect(String id, DQPNetworkInspect dqp);

  /**
   * Remove a network
   *
   * @param id string Required
   *           <p>
   *           Network ID or name
   * @return DResp
   */
  DResp remove(String id);

  /**
   * Create a network
   *
   * @param body Network configuration
   *             {
   *             "Name": "isolated_nw",
   *             "CheckDuplicate": false,
   *             "Driver": "bridge",
   *             "EnableIPv6": true,
   *             "IPAM": {
   *             "Driver": "default",
   *             "Config": [
   *             {
   *             "Subnet": "172.20.0.0/16",
   *             "IPRange": "172.20.10.0/24",
   *             "Gateway": "172.20.10.11"
   *             },
   *             {
   *             "Subnet": "2001:db8:abcd::/64",
   *             "Gateway": "2001:db8:abcd::1011"
   *             }
   *             ],
   *             "Options": {
   *             "foo": "bar"
   *             }
   *             },
   *             "Internal": true,
   *             "Attachable": false,
   *             "Ingress": false,
   *             "Options": {
   *             "com.docker.network.bridge.default_bridge": "true",
   *             "com.docker.network.bridge.enable_icc": "true",
   *             "com.docker.network.bridge.enable_ip_masquerade": "true",
   *             "com.docker.network.bridge.host_binding_ipv4": "0.0.0.0",
   *             "com.docker.network.bridge.name": "docker0",
   *             "com.docker.network.driver.mtu": "1500"
   *             },
   *             "Labels": {
   *             "com.example.some-label": "some-value",
   *             "com.example.some-other-label": "some-other-value"
   *             }
   *             }
   * @return DResp
   */
  DResp create(String body);


  /**
   * Connect a container to a network
   *
   * @param id   string Required
   *             <p>
   *             Network ID or name
   * @param body Request Body
   *             {
   *             "Container": "3613f73ba0e4",
   *             "EndpointConfig": {
   *             "IPAMConfig": {
   *             "IPv4Address": "172.24.56.89",
   *             "IPv6Address": "2001:db8::5689"
   *             }
   *             }
   *             }
   * @return DResp
   */
  DResp connect(String id, String body);

  /**
   * Disconnect a container from a network
   *
   * @param id   string Required
   *             <p>
   *             Network ID or name
   * @param body Request Body
   *             {
   *             "Container": "string",
   *             "Force": true
   *             }
   * @return DResp
   */
  DResp disconnect(String id, String body);

  /**
   * @see #prune(DQPFilter)
   */
  default DResp prune() {
    return this.prune(null);
  }

  /**
   * Delete unused networks
   *
   * @return DResp
   */
  DResp prune(DQPFilter dqp);

}
