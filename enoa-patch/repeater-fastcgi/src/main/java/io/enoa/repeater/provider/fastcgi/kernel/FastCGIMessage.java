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
package io.enoa.repeater.provider.fastcgi.kernel;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

final class FastCGIMessage {
  private static final byte VERSION = 1;
  private static final int HEADER_LENGTH = 8;
  private static final int END_REQUEST_BODY_LENGTH = 8;

  byte type;
  int requestId;
  int contentLength;
  byte[] content;

  FastCGIMessage(InputStream input) throws IOException {
    int version = input.read() & 0xff;
    if (version != VERSION) {
      throw new IOException("Invalid version");
    }
    type = (byte) (input.read() & 0xff);
    requestId = ((input.read() & 0xff) << 8) | (input.read() & 0xff);
    contentLength = ((input.read() & 0xff) << 8) | (input.read() & 0xff);
    int paddingLength = input.read() & 0xff;
    skip(1, input);
    if (contentLength > 0) {
      content = new byte[contentLength];
      readFully(content, input);
    }
    skip(paddingLength, input);
  }

  FastCGIMessage(byte type, int requestId, byte content) {
    this(type, requestId);
    byte[] contentBytes = new byte[END_REQUEST_BODY_LENGTH];
    contentBytes[4] = content;
    setContent(contentBytes);
  }

  FastCGIMessage(byte type, int requestId, byte[] content) {
    this(type, requestId);
    setContent(content);
  }

  FastCGIMessage(byte type, int requestId) {
    this.type = type;
    this.requestId = requestId;
    setContent(null);
  }

  static void readFully(byte[] content, InputStream input) throws IOException {
    int byteCount = content.length;
    int offset = 0;
    while (byteCount > 0) {
      int result = input.read(content, offset, byteCount);
      if (result < 0) {
        throw new EOFException();
      }
      offset += result;
      byteCount -= result;
    }
  }

  static void skip(int len, InputStream input) throws IOException {
    int byteCount = len;
    while (byteCount > 0) {
      long result = input.skip(byteCount);
      if (result < 0) {
        throw new EOFException();
      }
      byteCount -= result;
    }
  }

  private static void writeToOutput(byte[] buffer, int offset, int length, int requestId, OutputStream outputStream) throws IOException {
    for (int offs = offset, len = Math.min(length, 0xffff);
         len != 0 && offs + len <= offset + length;
         offs += len, len = Math.min(offset + length - offs, 0xffff)) {
      byte[] content;
      if (offs == 0 && len == buffer.length) {
        content = buffer;
      } else {
        content = new byte[len];
        System.arraycopy(buffer, offs, content, 0, len);
      }
      FastCGIMessage message = new FastCGIMessage(FastCGIServer.STDOUT, requestId);
      message.setContent(content);
      message.write(outputStream);
    }
  }

  void setContent(byte[] newContent) {
    contentLength = newContent == null ? 0 : newContent.length;
    content = contentLength == 0 ? null : newContent;
  }

  void write(OutputStream output) throws IOException {
    byte[] header = new byte[HEADER_LENGTH];
    header[0] = VERSION;
    header[1] = type;
    header[2] = (byte) ((requestId >> 8) & 0xff);
    header[3] = (byte) (requestId & 0xff);
    header[4] = (byte) ((contentLength >> 8) & 0xff);
    header[5] = (byte) (contentLength & 0xff);
//    header[6] = 0;
//    header[7] = 0;
    output.write(header);
    if (content != null) {
      output.write(content);
    }
  }
}
