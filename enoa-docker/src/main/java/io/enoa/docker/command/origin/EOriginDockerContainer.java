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

import io.enoa.docker.dqp.container.DQPListContainer;
import io.enoa.docker.dqp.container.DQPLogs;

public interface EOriginDockerContainer {

  /**
   * @see #list(DQPListContainer)
   */
  default String list() {
    return this.list(null);
  }

  /**
   * List containers
   * <p>
   * Returns a list of containers. For details on the format, see the inspect endpoint.
   * <p>
   * Note that it uses a different, smaller representation of a container than inspecting a single container. For example, the list of linked containers is not propagated .
   *
   * @param dqp Query Parameter
   *            <p>
   *            query Parameters ?
   *            all
   *            boolean
   *            false
   *            <p>
   *            Return all containers. By default, only running containers are shown
   *            limit
   *            integer
   *            <p>
   *            Return this number of most recently created containers, including non-running ones.
   *            size
   *            boolean
   *            false
   *            <p>
   *            Return the size of container as fields SizeRw and SizeRootFs.
   *            filters
   *            string
   *            <p>
   *            Filters to process on the container list, encoded as JSON (a map[string][]string). For example, {"status": ["paused"]} will only return paused containers. Available filters:
   *            <p>
   *            ancestor=(<image-name>[:<tag>], <image id>, or <image@digest>)
   *            before=(<container id> or <container name>)
   *            expose=(<port>[/<proto>]|<startport-endport>/[<proto>])
   *            exited=<int> containers with exit code of <int>
   *            health=(starting|healthy|unhealthy|none)
   *            id=<ID> a container's ID
   *            isolation=(default|process|hyperv) (Windows daemon only)
   *            is-task=(true|false)
   *            label=key or label="key=value" of a container label
   *            name=<name> a container's name
   *            network=(<network id> or <network name>)
   *            publish=(<port>[/<proto>]|<startport-endport>/[<proto>])
   *            since=(<container id> or <container name>)
   *            status=(created|restarting|running|removing|paused|exited|dead)
   *            volume=(<volume name> or <mount point destination>)
   * @return String
   */
  String list(DQPListContainer dqp);

  /**
   * Create a container
   *
   * @param name Assign the specified name to the container. Must match /?[a-zA-Z0-9_-]+.
   * @param body Container body
   *             {
   *             "Hostname": "",
   *             "Domainname": "",
   *             "User": "",
   *             "AttachStdin": false,
   *             "AttachStdout": true,
   *             "AttachStderr": true,
   *             "Tty": false,
   *             "OpenStdin": false,
   *             "StdinOnce": false,
   *             "Env": [
   *             "FOO=bar",
   *             "BAZ=quux"
   *             ],
   *             "Cmd": [
   *             "date"
   *             ],
   *             "Entrypoint": "",
   *             "Image": "ubuntu",
   *             "Labels": {
   *             "com.example.vendor": "Acme",
   *             "com.example.license": "GPL",
   *             "com.example.version": "1.0"
   *             },
   *             "Volumes": {
   *             "/volumes/data": {}
   *             },
   *             "WorkingDir": "",
   *             "NetworkDisabled": false,
   *             "MacAddress": "12:34:56:78:9a:bc",
   *             "ExposedPorts": {
   *             "22/tcp": {}
   *             },
   *             "StopSignal": "SIGTERM",
   *             "StopTimeout": 10,
   *             "HostConfig": {
   *             "Binds": [
   *             "/tmp:/tmp"
   *             ],
   *             "Links": [
   *             "redis3:redis"
   *             ],
   *             "Memory": 0,
   *             "MemorySwap": 0,
   *             "MemoryReservation": 0,
   *             "KernelMemory": 0,
   *             "NanoCPUs": 500000,
   *             "CpuPercent": 80,
   *             "CpuShares": 512,
   *             "CpuPeriod": 100000,
   *             "CpuRealtimePeriod": 1000000,
   *             "CpuRealtimeRuntime": 10000,
   *             "CpuQuota": 50000,
   *             "CpusetCpus": "0,1",
   *             "CpusetMems": "0,1",
   *             "MaximumIOps": 0,
   *             "MaximumIOBps": 0,
   *             "BlkioWeight": 300,
   *             "BlkioWeightDevice": [
   *             {}
   *             ],
   *             "BlkioDeviceReadBps": [
   *             {}
   *             ],
   *             "BlkioDeviceReadIOps": [
   *             {}
   *             ],
   *             "BlkioDeviceWriteBps": [
   *             {}
   *             ],
   *             "BlkioDeviceWriteIOps": [
   *             {}
   *             ],
   *             "MemorySwappiness": 60,
   *             "OomKillDisable": false,
   *             "OomScoreAdj": 500,
   *             "PidMode": "",
   *             "PidsLimit": -1,
   *             "PortBindings": {
   *             "22/tcp": [
   *             {
   *             "HostPort": "11022"
   *             }
   *             ]
   *             },
   *             "PublishAllPorts": false,
   *             "Privileged": false,
   *             "ReadonlyRootfs": false,
   *             "Dns": [
   *             "8.8.8.8"
   *             ],
   *             "DnsOptions": [
   *             ""
   *             ],
   *             "DnsSearch": [
   *             ""
   *             ],
   *             "VolumesFrom": [
   *             "parent",
   *             "other:ro"
   *             ],
   *             "CapAdd": [
   *             "NET_ADMIN"
   *             ],
   *             "CapDrop": [
   *             "MKNOD"
   *             ],
   *             "GroupAdd": [
   *             "newgroup"
   *             ],
   *             "RestartPolicy": {
   *             "Name": "",
   *             "MaximumRetryCount": 0
   *             },
   *             "AutoRemove": true,
   *             "NetworkMode": "bridge",
   *             "Devices": [],
   *             "Ulimits": [
   *             {}
   *             ],
   *             "LogConfig": {
   *             "Type": "json-file",
   *             "Config": {}
   *             },
   *             "SecurityOpt": [],
   *             "StorageOpt": {},
   *             "CgroupParent": "",
   *             "VolumeDriver": "",
   *             "ShmSize": 67108864
   *             },
   *             "NetworkingConfig": {
   *             "EndpointsConfig": {
   *             "isolated_nw": {
   *             "IPAMConfig": {
   *             "IPv4Address": "172.20.30.33",
   *             "IPv6Address": "2001:db8:abcd::3033",
   *             "LinkLocalIPs": [
   *             "169.254.34.68",
   *             "fe80::3468"
   *             ]
   *             },
   *             "Links": [
   *             "container_1",
   *             "container_2"
   *             ],
   *             "Aliases": [
   *             "server_x",
   *             "server_y"
   *             ]
   *             }
   *             }
   *             }
   *             }
   * @return EContainerCreated
   */
  String create(String name, String body);

  /**
   * @see #inspect(String, Boolean)
   */
  default String inspect(String id) {
    return this.inspect(id, Boolean.FALSE);
  }

  /**
   * Return low-level information about a container.
   *
   * @param id   ID or name of the container
   * @param size Return the size of container as fields SizeRw and SizeRootFs
   * @return String
   */
  String inspect(String id, Boolean size);

  /**
   * @see #top(String, String)
   */
  default String top(String id) {
    return this.top(id, null);
  }

  /**
   * List processes running inside a container
   * On Unix systems, this is done by running the ps command. This endpoint is not supported on Windows.
   *
   * @param id   id
   * @param para string
   *             default "-ef"
   *             <p>
   *             The arguments to pass to ps. For example, aux
   * @return String
   * {
   * "Titles": [
   * "UID",
   * "PID",
   * "PPID",
   * "C",
   * "STIME",
   * "TTY",
   * "TIME",
   * "CMD"
   * ],
   * "Processes": [
   * [
   * "root",
   * "13642",
   * "882",
   * "0",
   * "17:03",
   * "pts/0",
   * "00:00:00",
   * "/bin/bash"
   * ],
   * [
   * "root",
   * "13735",
   * "13642",
   * "0",
   * "17:06",
   * "pts/0",
   * "00:00:00",
   * "sleep 10"
   * ]
   * ]
   * }
   */
  String top(String id, String para);

  /**
   * @see #logs(String, DQPLogs)
   */
  default String logs(String id) {
    return this.logs(id, DQPLogs.create().stdout());
  }

  /**
   * Get container logs
   * Get stdout and stderr logs from a container.
   * <p>
   * Note: This endpoint works only for containers with the json-file or journald logging driver.
   *
   * @param id string Required
   *           <p>
   *           ID or name of the container
   * @return String
   */
  String logs(String id, DQPLogs dqp);

  /**
   * Get changes on a container’s filesystem
   * Returns which files in a container's filesystem have been added, deleted, or modified. The Kind of modification can be one of:
   * <p>
   * 0: Modified
   * 1: Added
   * 2: Deleted
   *
   * @param id string Required
   *           <p>
   *           ID or name of the container
   * @return String
   */
  String changes(String id);

  /**
   * Export a container
   * Export the contents of a container as a tarball.
   *
   * @param id string Required
   *           <p>
   *           ID or name of the container
   * @return String
   */
  String export(String id);

}
