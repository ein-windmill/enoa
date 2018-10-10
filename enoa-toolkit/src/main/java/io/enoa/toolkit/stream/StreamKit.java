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
package io.enoa.toolkit.stream;

import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.thr.EoCloseException;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public class StreamKit {

  private static final int DEFAULT_BUFFER_SIZE = 8129;
  private static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;

  private StreamKit() {
  }

  private static final byte[] empty = new byte[0];

  public static String string(byte[] bytes, Charset charset) {
    if (CollectionKit.isEmpty(bytes))
      return null;
    return EnoaBinary.create(bytes, charset).string();
  }

  public static String string(InputStream stream, Charset charset) throws IOException {
    if (stream == null)
      return null;

    return string(bytes(stream), charset);
  }

  public static String string(ByteBuffer binary, Charset charset) {
    return string(binary.array(), charset);
  }

  public static EnoaBinary binary(InputStream stream, Charset charset) throws IOException {
    return EnoaBinary.create(bytes(stream), charset);
  }

  public static EnoaBinary binary(InputStream stream) throws IOException {
    return EnoaBinary.create(bytes(stream));
  }

//  public static byte[] bytes(InputStream stream) throws IOException {
//    if (stream == null)
//      return empty;
//    try (ByteArrayOutputStream swapStream = new ByteArrayOutputStream()) {
//      byte[] buff = new byte[1024];
//      int rc;
//      while ((rc = stream.read(buff, 0, 100)) > 0) {
//        swapStream.write(buff, 0, rc);
//      }
//      close(stream);
//      return swapStream.toByteArray();
//    }
//  }

  public static byte[] bytes(InputStream stream) throws IOException {
    byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
    int capacity = buf.length;
    int nread = 0;
    int n;
    for (; ; ) {
      // read to EOF which may read more or less than initial buffer size
      while ((n = stream.read(buf, nread, capacity - nread)) > 0)
        nread += n;

      // if the last call to read returned -1, then we're done
      if (n < 0)
        break;

      // need to allocate a larger buffer
      if (capacity <= MAX_BUFFER_SIZE - capacity) {
        capacity = capacity << 1;
      } else {
        if (capacity == MAX_BUFFER_SIZE)
          throw new OutOfMemoryError("Required array size too large");
        capacity = MAX_BUFFER_SIZE;
      }
      buf = Arrays.copyOf(buf, capacity);
    }
    return (capacity == nread) ? buf : Arrays.copyOf(buf, nread);
  }

  public static void close(Closeable... closeables) {
    close(true, closeables);
  }

  public static void close(boolean skip, Closeable... closeables) {
    for (Closeable closeable : closeables) {
      if (closeable == null)
        continue;
      try {
        closeable.close();
      } catch (IOException e) {
        if (skip)
          return;
        throw new EoCloseException(e.getMessage(), e);
      }
    }
  }

  public static void close(AutoCloseable... closeables) {
    close(true, closeables);
  }

  public static void close(boolean skip, AutoCloseable... closeables) {
    for (AutoCloseable closeable : closeables) {
      if (closeable == null)
        continue;
      try {
        closeable.close();
      } catch (Exception e) {
        if (skip)
          return;
        throw new EoCloseException(e.getMessage(), e);
      }
    }
  }

}
