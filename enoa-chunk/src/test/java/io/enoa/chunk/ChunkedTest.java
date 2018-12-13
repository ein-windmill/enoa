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
package io.enoa.chunk;

import org.junit.Ignore;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

@Ignore
public class ChunkedTest {

  @Test
  public void testChunked() {
    String text = "* Rebuilt URL to: cip.cc/\r\n" +
      "  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current\r\n" +
      "                                 Dload  Upload   Total   Spent    Left  Speed\r\n" +
      "  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0*   Trying 106.12.206.251...\r\n" +
      "* TCP_NODELAY set\r\n" +
      "* Connected to cip.cc (106.12.206.251) port 80 (#0)\r\n" +
      "> GET / HTTP/1.1\r\n" +
      "> Host: cip.cc\r\n" +
      "> User-Agent: curl/7.61.1\r\n" +
      "> Accept: */*\r\n" +
      "> \r\n" +
      "< HTTP/1.1 200 OK\r\n" +
      "< Server: openresty\r\n" +
      "< Date: Sun, 09 Dec 2018 04:43:07 GMT\r\n" +
      "< Content-Type: text/html; charset=UTF-8\r\n" +
      "< Transfer-Encoding: chunked\r\n" +
      "< Connection: keep-alive\r\n" +
      "< Vary: Accept-Encoding\r\n" +
      "< X-cip-c: H\r\n" +
      "< \r\n" +
      "{ [190 bytes data]\r\n" +
      "100   179    0   179    0     0   1904      0 --:--:-- --:--:-- --:--:--  1904IP\t: 164.95.119.201\r\n" +
      "餈\r\n" +
      "\r\n" +
      "\r\n" +
      "URL\t: http://www.cip.cc/114.95.110.201\r\n" +
      "\r" +
      "* Connection #0 to host cip.cc left intact\n" +
      "Disconnected from the target VM, address: '127.0.0.1:5275', transport: 'socket'\n" +
      "\r" +
      "Process finished with exit code 0\r" +
      "\n"
      ;

    Chunk chunk = Chunk.string(System.out::println);
    ChunkCaller caller = chunk.caller();

    caller.call(text.getBytes(Charset.forName("utf-8")));

  }

}
