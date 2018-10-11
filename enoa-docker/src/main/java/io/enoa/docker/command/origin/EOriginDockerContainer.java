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

import io.enoa.docker.dqp.container.*;
import io.enoa.docker.dret.DResp;
import io.enoa.docker.stream.DStream;
import io.enoa.docker.thr.DockerException;
import io.enoa.toolkit.eo.tip.EnoaTipKit;

public interface EOriginDockerContainer {

  /**
   * @see #list(DQPListContainer)
   */
  default DResp list() {
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
   * @return Dresp
   */
  DResp list(DQPListContainer dqp);

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
  DResp create(String name, String body);

  /**
   * @see #inspect(String, Boolean)
   */
  default DResp inspect(String id) {
    return this.inspect(id, Boolean.FALSE);
  }

  /**
   * Return low-level information about a container.
   *
   * @param id   ID or name of the container
   * @param size Return the size of container as fields SizeRw and SizeRootFs
   * @return Dresp
   */
  DResp inspect(String id, Boolean size);

  /**
   * @see #top(String, String)
   */
  default DResp top(String id) {
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
   * @return Dresp
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
  DResp top(String id, String para);

  /**
   * @see #logs(String, DQPLogs)
   */
  default DResp logs(String id) {
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
   * @return Dresp
   */
  DResp logs(String id, DQPLogs dqp);

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
   * @return Dresp
   */
  DResp changes(String id);

  /**
   * Export a container
   * Export the contents of a container as a tarball.
   *
   * @param id string Required
   *           <p>
   *           ID or name of the container
   * @return Dresp
   */
  DResp export(String id);

  DResp statistics(String id);

  /**
   * Get container stats based on resource usage
   * This endpoint returns a live stream of a container’s resource usage statistics.
   * <p>
   * The precpu_stats is the CPU statistic of last read, which is used for calculating the CPU usage percentage. It is not the same as the cpu_stats field.
   * <p>
   * If either precpu_stats.online_cpus or cpu_stats.online_cpus is nil then for compatibility with older daemons the length of the corresponding cpu_usage.percpu_usage array should be used.
   *
   * @param id      string Required
   *                <p>
   *                ID or name of the container
   * @param dstream DStream
   * @return Dresp
   */
  DResp statistics(String id, DStream<String> dstream);

  /**
   * @see #resize(String, DQPResize)
   */
  default DResp resize(String id) {
    return this.resize(id, null);
  }

  /**
   * Resize a container TTY
   * Resize the TTY for a container. You must restart the container for the resize to take effect.
   *
   * @param id  string Required
   *            <p>
   *            ID or name of the container
   * @param dqp dqp
   * @return Dresp
   */
  DResp resize(String id, DQPResize dqp);

  /**
   * @see #start(String, DQPStart)
   */
  default DResp start(String id) {
    return this.start(id, null);
  }

  /**
   * Start a container
   *
   * @param id  string Required
   *            <p>
   *            ID or name of the container
   * @param dqp dqp
   * @return Dresp
   */
  DResp start(String id, DQPStart dqp);

  /**
   * @see #stop(String, DQPTime)
   */
  default DResp stop(String id) {
    return this.stop(id, null);
  }

  /**
   * Stop a container
   *
   * @param id  string Required
   *            <p>
   *            ID or name of the container
   * @param dqp dqp
   * @return Dresp
   */
  DResp stop(String id, DQPTime dqp);

  /**
   * @see #restart(String, DQPTime)
   */
  default DResp restart(String id) {
    return this.restart(id, null);
  }

  /**
   * Restart a container
   *
   * @param id  id
   *            string Required
   *            <p>
   *            ID or name of the container
   * @param dqp dqp
   * @return Dresp
   */
  DResp restart(String id, DQPTime dqp);

  /**
   * @see #kill(String, DQPKill)
   */
  default DResp kill(String id) {
    return this.kill(id, null);
  }

  /**
   * Kill a container
   * Send a POSIX signal to a container, defaulting to killing to the container.
   *
   * @param id  string Required
   *            <p>
   *            ID or name of the container
   * @param dqp dqp
   * @return Dresp
   */
  DResp kill(String id, DQPKill dqp);

  /**
   * @see #update(String, String)
   */
  default DResp update(String id, DQPUpdate dqp) {
    return this.update(id, dqp.dqr().json());
  }

  /**
   * Update a container
   * Change various configuration options of a container without having to recreate it.
   *
   * @param id   string Required
   *             <p>
   *             ID or name of the container
   * @param body request body
   *             {
   *             "BlkioWeight": 300,
   *             "CpuShares": 512,
   *             "CpuPeriod": 100000,
   *             "CpuQuota": 50000,
   *             "CpuRealtimePeriod": 1000000,
   *             "CpuRealtimeRuntime": 10000,
   *             "CpusetCpus": "0,1",
   *             "CpusetMems": "0",
   *             "Memory": 314572800,
   *             "MemorySwap": 514288000,
   *             "MemoryReservation": 209715200,
   *             "KernelMemory": 52428800,
   *             "RestartPolicy": {
   *             "MaximumRetryCount": 4,
   *             "Name": "on-failure"
   *             }
   *             }
   * @return Dresp
   */
  DResp update(String id, String body);

  /**
   * Rename a container
   *
   * @param id   id
   *             string Required
   *             <p>
   *             ID or name of the container
   * @param name name
   *             string Required
   *             <p>
   *             New name for the container
   * @return Dresp
   */
  DResp rename(String id, String name);

  /**
   * Pause a container
   * Use the cgroups freezer to suspend all processes in a container.
   * <p>
   * Traditionally, when suspending a process the SIGSTOP signal is used,
   * which is observable by the process being suspended. With the cgroups freezer the process is unaware,
   * and unable to capture, that it is being suspended, and subsequently resumed.
   *
   * @param id string Required
   *           <p>
   *           ID or name of the container
   * @return Dresp
   */
  DResp pause(String id);

  /**
   * Unpause a container
   * Resume a container which has been paused.
   *
   * @param id string Required
   *           <p>
   *           ID or name of the container
   * @return Dresp
   */
  DResp unpause(String id);

  /**
   * @see #attach(String, DQPAttch)
   */
  default DResp attach(String id) {
    return this.attach(id, null);
  }

  /**
   * Attach to a container
   * Attach to a container to read its output or send it input. You can attach to the same container multiple times and you can reattach to containers that have been detached.
   * <p>
   * Either the stream or logs parameter must be true for this endpoint to do anything.
   * <p>
   * See the documentation for the docker attach command for more details.
   * Hijacking
   * <p>
   * This endpoint hijacks the HTTP connection to transport stdin, stdout, and stderr on the same socket.
   * <p>
   * This is the response from the daemon for an attach request:
   * <p>
   * HTTP/1.1 200 OK
   * Content-Type: application/vnd.docker.raw-stream
   * <p>
   * [STREAM]
   * <p>
   * After the headers and two new lines, the TCP connection can now be used for raw, bidirectional communication between the client and server.
   * <p>
   * To hint potential proxies about connection hijacking, the Docker client can also optionally send connection upgrade headers.
   * <p>
   * For example, the client sends this request to upgrade the connection:
   * <p>
   * POST /containers/16253994b7c4/attach?stream=1&stdout=1 HTTP/1.1
   * Upgrade: tcp
   * Connection: Upgrade
   * <p>
   * The Docker daemon will respond with a 101 UPGRADED response, and will similarly follow with the raw stream:
   * <p>
   * HTTP/1.1 101 UPGRADED
   * Content-Type: application/vnd.docker.raw-stream
   * Connection: Upgrade
   * Upgrade: tcp
   * <p>
   * [STREAM]
   * <p>
   * Stream format
   * <p>
   * When the TTY setting is disabled in POST /containers/create, the stream over the hijacked connected is multiplexed to separate out stdout and stderr. The stream consists of a series of frames, each containing a header and a payload.
   * <p>
   * The header contains the information which the stream writes (stdout or stderr). It also contains the size of the associated frame encoded in the last four bytes (uint32).
   * <p>
   * It is encoded on the first eight bytes like this:
   * <p>
   * header := [8]byte{STREAM_TYPE, 0, 0, 0, SIZE1, SIZE2, SIZE3, SIZE4}
   * <p>
   * STREAM_TYPE can be:
   * <p>
   * 0: stdin (is written on stdout)
   * 1: stdout
   * 2: stderr
   * <p>
   * SIZE1, SIZE2, SIZE3, SIZE4 are the four bytes of the uint32 size encoded as big endian.
   * <p>
   * Following the header is the payload, which is the specified number of bytes of STREAM_TYPE.
   * <p>
   * The simplest way to implement this protocol is the following:
   * <p>
   * 1. Read 8 bytes.
   * 2. Choose stdout or stderr depending on the first byte.
   * 3. Extract the frame size from the last four bytes.
   * 4. Read the extracted size and output it on the correct output.
   * 5. Goto 1.
   * <p>
   * Stream format when using a TTY
   * <p>
   * When the TTY setting is enabled in POST /containers/create, the stream is not multiplexed. The data exchanged over the hijacked connection is simply the raw data from the process PTY and client's stdin.
   *
   * @param id  string Required
   *            <p>
   *            ID or name of the container
   * @param dqp dqp
   * @return Dresp
   */
  DResp attach(String id, DQPAttch dqp);

  @Deprecated
  default Void ws(String id) {
    throw new DockerException(EnoaTipKit.message("eo.tip.docker.notsupport"));
  }

  /**
   * @see #wait(String, String)
   */
  default DResp wait(String id) {
    return this.wait(id, "not-running");
  }

  /**
   * Wait for a container
   * Block until a container stops, then returns the exit code.
   *
   * @param id        string Required
   *                  <p>
   *                  ID or name of the container
   * @param condition string
   *                  default "not-running"
   *                  <p>
   *                  Wait until a container state reaches the given condition, either 'not-running' (default), 'next-exit', or 'removed'.
   * @return Dresp
   */
  DResp wait(String id, String condition);


  default DResp remove(String id) {
    return this.remove(id, null);
  }

  /**
   * Remove a container
   *
   * @param id  string Required
   *            <p>
   *            ID or name of the container
   * @param dqp dqp
   * @return Dresp
   */
  DResp remove(String id, DQPRemove dqp);

  /**
   * Get an archive of a filesystem resource in a container
   * Get a tar archive of a resource in the filesystem of container id.
   *
   * @param id   string Required
   *             <p>
   *             ID or name of the container
   * @param path string Required
   *             <p>
   *             Resource in the container’s filesystem to archive.
   * @return Dresp
   */
  DResp archive(String id, String path);

  default DResp prune() {
    return this.prune(null);
  }

  /**
   * Delete stopped containers
   *
   * @param dqp dqp
   * @return Dresp
   */
  DResp prune(DQPPruneContainer dqp);


}
