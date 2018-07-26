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
package io.enoa.toolkit.file;

import io.enoa.toolkit.digest.DigestKit;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

public class ChecksumKit {

  private ChecksumKit() {

  }

  private static String checksum(MessageDigest digest, Path path) {
    try (FileInputStream fis = new FileInputStream(path.toString());
         FileChannel channel = fis.getChannel()) {
      int capacity = 2048;
      long filesize = Files.size(path);
      if (filesize > 1024 * 1024 * 5)
        capacity = 5120;
      if (filesize > 1024 * 1024 * 99)
        capacity = 1024 * 1024;
      if (filesize > 1024 * 1024 * 999)
        capacity = 1024 * 1024 * 4;

      ByteBuffer buffer = ByteBuffer.allocate(capacity);
      while (channel.read(buffer) != -1) {
        buffer.flip();
        digest.update(buffer.array(), 0, buffer.limit());
        buffer.clear();
      }
      return DigestKit.hex(digest.digest());
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static String md5(Path path) {
    return checksum(DigestKit.digest("MD5"), path);
  }

  public static String sha1(Path path) {
    return checksum(DigestKit.digest("SHA-1"), path);
  }

  public static String sha256(Path path) {
    return checksum(DigestKit.digest("SHA-256"), path);
  }

  public static String sha384(Path path) {
    return checksum(DigestKit.digest("SHA-384"), path);
  }

  public static String sha512(Path path) {
    return checksum(DigestKit.digest("SHA-512"), path);
  }

}
