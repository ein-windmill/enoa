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
package io.enoa.docker.command.docker.origin;

import io.enoa.chunk.stream.ChunkStream;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.image.*;
//import io.enoa.docker.stream.DStream;

public interface EOriginDockerImage {

  default DResp list() {
    return this.list(null);
  }

  /**
   * List Images
   * Returns a list of images on the server. Note that it uses a different,
   * smaller representation of an image than inspecting a single image.
   *
   * @param dqp dqp
   * @return DResp
   */
  DResp list(DQPImageList dqp);

  /**
   * @see #build(String, DQPImageBuild, ChunkStream)
   */
  default DResp build(String dockerfile, DQPImageBuild dqp) {
    return this.build(dockerfile, dqp, null);
  }

  /**
   * Build an image
   * Build an image from a tar archive with a Dockerfile in it.
   * <p>
   * The Dockerfile specifies how the image is built from the tar archive.
   * It is typically in the archive's root, but can be at a different path or have a different name
   * by specifying the dockerfile parameter. See the Dockerfile reference for more information.
   * <p>
   * The Docker daemon performs a preliminary validation of the Dockerfile before starting the build,
   * and returns an error if the syntax is incorrect. After that, each instruction is run one-by-one
   * until the ID of the new image is output.
   * <p>
   * The build is canceled if the client drops the connection by quitting or being killed.
   *
   * @param dqp        dqp
   * @param dockerfile dockerfile content
   * @param stream     stream
   * @return DResp
   */
  DResp build(String dockerfile, DQPImageBuild dqp, ChunkStream stream);

  /**
   * Delete builder cache
   *
   * @return DResp
   */
  DResp prunebuild();

  default DResp create(DQPImageCreate dqp) {
    return this.create(dqp, null, null);
  }

  default DResp create(DQPImageCreate dqp, String body) {
    return this.create(dqp, body, null);
  }

  default DResp create(DQPImageCreate dqp, ChunkStream stream) {
    return this.create(dqp, null, stream);
  }

  /**
   * Create an image
   * Create an image by either pulling it from a registry or importing it.
   *
   * @param dqp  dqp
   * @param body request body
   * @return DResp
   */
  DResp create(DQPImageCreate dqp, String body, ChunkStream stream);


  /**
   * Inspect an image
   * Return low-level information about an image.
   *
   * @param id string Required
   *           <p>
   *           Image name or id
   * @return DResp
   */
  DResp inspect(String id);

  /**
   * Get the history of an image
   * Return parent layers of an image.
   *
   * @param id string Required
   *           <p>
   *           Image name or ID
   * @return Stirng
   */
  DResp history(String id);

  /**
   * @see #push(String, DQPImagePush)
   */
  default DResp push(String id) {
    return this.push(id, null, null);
  }

  default DResp push(String id, ChunkStream stream) {
    return this.push(id, null, stream);
  }

  default DResp push(String id, DQPImagePush dqp) {
    return this.push(id, dqp, null);
  }

  /**
   * Push an image
   * Push an image to a registry.
   * <p>
   * If you wish to push an image on to a private registry, that image must already have a tag which references the registry.
   * For example, registry.example.com/myimage:latest.
   * <p>
   * The push is cancelled if the HTTP connection is closed.
   *
   * @param id  string Required
   *            <p>
   *            Image name or ID.
   * @param dqp dqp
   * @return DResp
   */
  DResp push(String id, DQPImagePush dqp, ChunkStream stream);

  /**
   * Tag an image
   * Tag an image so that it becomes part of a repository.
   *
   * @param id  string Required
   *            <p>
   *            Image name or ID to tag.
   * @param dqp dqp
   * @return DResp
   */
  DResp tag(String id, DQPImageTag dqp);

  /**
   * @see #remove(String, DQPImageRmi)
   */
  default DResp remove(String id) {
    return this.remove(id, null);
  }

  /**
   * Remove an image
   * Remove an image, along with any untagged parent images that were referenced by that image.
   * <p>
   * Images can't be removed if they have descendant images, are being used by a running container or are being used by a build.
   *
   * @param id string Required
   *           <p>
   *           Image name or ID
   * @return DResp
   */
  DResp remove(String id, DQPImageRmi dqp);

  /**
   * Search images
   * Search for an image on Docker Hub.
   *
   * @param dqp dqp
   * @return DResp
   */
  DResp search(DQPImageSearch dqp);

  default DResp pruneimage() {
    return this.pruneimage(null);
  }

  /**
   * Delete unused images
   *
   * @return Stirng
   */
  DResp pruneimage(DQPFilter dqp);

  /**
   * @see #commit(String, DQPImageCommit)
   */
  default DResp commit(String body) {
    return this.commit(body, null);
  }

  /**
   * Create a new image from a container
   *
   * @param body The container configuration
   *             {
   *             "Hostname": "string",
   *             "Domainname": "string",
   *             "User": "string",
   *             "AttachStdin": false,
   *             "AttachStdout": true,
   *             "AttachStderr": true,
   *             "ExposedPorts": {
   *             "property1": {},
   *             "property2": {}
   *             },
   *             "Tty": false,
   *             "OpenStdin": false,
   *             "StdinOnce": false,
   *             "Env": [
   *             "string"
   *             ],
   *             "Cmd": [
   *             "string"
   *             ],
   *             "Healthcheck": {
   *             "Test": [
   *             "string"
   *             ],
   *             "Interval": 0,
   *             "Timeout": 0,
   *             "Retries": 0,
   *             "StartPeriod": 0
   *             },
   *             "ArgsEscaped": true,
   *             "Image": "string",
   *             "Volumes": {
   *             "property1": {},
   *             "property2": {}
   *             },
   *             "WorkingDir": "string",
   *             "Entrypoint": [
   *             "string"
   *             ],
   *             "NetworkDisabled": true,
   *             "MacAddress": "string",
   *             "OnBuild": [
   *             "string"
   *             ],
   *             "Labels": {
   *             "property1": "string",
   *             "property2": "string"
   *             },
   *             "StopSignal": "SIGTERM",
   *             "StopTimeout": 10,
   *             "Shell": [
   *             "string"
   *             ]
   *             }
   * @param dqp  dqp
   * @return DResp
   */
  DResp commit(String body, DQPImageCommit dqp);

  /**
   * Export an image
   * Get a tarball containing all images and metadata for a repository.
   * <p>
   * If name is a specific name and tag (e.g. ubuntu:latest), then only that image (and its parents) are returned. If name is an image ID, similarly only that image (and its parents) are returned, but with the exclusion of the repositories file in the tarball, as there were no image names referenced.
   * Image tarball format
   * <p>
   * An image tarball contains one directory per image layer (named using its long ID), each containing these files:
   * <p>
   * VERSION: currently 1.0 - the file format version
   * json: detailed layer information, similar to docker inspect layer_id
   * layer.tar: A tarfile containing the filesystem changes in this layer
   * <p>
   * The layer.tar file contains aufs style .wh..wh.aufs files and directories for storing attribute changes and deletions.
   * <p>
   * If the tarball defines a repository, the tarball should also include a repositories file at the root that contains a list of repository and tag names mapped to layer IDs.
   *
   * @return DResp
   */
  DResp export(String id);

  /**
   * Export several images
   * Get a tarball containing all images and metadata for several image repositories.
   * <p>
   * For each value of the names parameter: if it is a specific name and tag (e.g. ubuntu:latest), then only that image (and its parents) are returned; if it is an image ID, similarly only that image (and its parents) are returned and there would be no names referenced in the 'repositories' file for this image ID.
   * <p>
   * For details on the format, see <a href="https://docs.docker.com/engine/api/v1.37/#operation/ImageGet">the export image endpoint.</a>
   *
   * @param dqp dqp
   * @return DResp
   */
  DResp export(DQPImageExport dqp);

  /**
   * @see #load(byte[], DQPImageLoad)
   */
  default DResp load(byte[] binary) {
    return this.load(binary, null);
  }

  /**
   * Import images
   * Load a set of images and tags into a repository.
   * <p>
   * For details on the format, see <a href="https://docs.docker.com/engine/api/v1.37/#operation/ImageGet">the export image endpoint.</a>
   *
   * @param dqp dqp
   * @return Dresp
   */
  DResp load(byte[] binary, DQPImageLoad dqp);

}
