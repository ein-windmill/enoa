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
package io.enoa.docker.tar;

import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.stream.StreamKit;
import io.enoa.toolkit.text.TextKit;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

public class DTar {


  public static EnoaBinary cvf(String filename, String text) {
    return cvf(filename, text, EoConst.CHARSET);
  }

  public static EnoaBinary cvf(String filename, String text, Charset charset) {
    return cvf(filename, text.getBytes(charset));
  }

  public static EnoaBinary cvf(String filename, EnoaBinary binary) {
    return cvf(filename, binary.bytes());
  }

  public static EnoaBinary cvf(String filename, byte[] binary) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      TarArchiveOutputStream stream = new TarArchiveOutputStream(baos);
      TarArchiveEntry entry = new TarArchiveEntry(TextKit.union("/", filename));
      entry.setSize(binary.length);
      stream.putArchiveEntry(entry);
      stream.write(binary, 0, binary.length);
      stream.closeArchiveEntry();
      stream.flush();
      EnoaBinary ret = EnoaBinary.create(baos.toByteArray());
      StreamKit.close(baos, stream);
      return ret;
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

}
