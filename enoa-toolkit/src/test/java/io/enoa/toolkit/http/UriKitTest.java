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
package io.enoa.toolkit.http;

import org.junit.Test;

public class UriKitTest {
  @Test
  public void correct() throws Exception {
    System.out.println(UriKit.correct(""));
    System.out.println(UriKit.correct("/"));
    System.out.println(UriKit.correct("test"));
    System.out.println(UriKit.correct("/test"));
    System.out.println(UriKit.correct("/test//a"));
    System.out.println(UriKit.correct("/test//a/////b"));
    System.out.println(UriKit.correct("/test//a/////b/"));
    System.out.println(UriKit.correct("/test//a/////b/c"));
  }

  @Test
  public void rmcontext() throws Exception {
    System.out.println(UriKit.rmcontext("/test", ""));
    System.out.println(UriKit.rmcontext("/test","/"));
    System.out.println(UriKit.rmcontext("/test","/test"));
    System.out.println(UriKit.rmcontext("/test","/test//a"));
    System.out.println(UriKit.rmcontext("/test","/test//a/////b"));
    System.out.println(UriKit.rmcontext("/test","/test//a/////b/"));
    System.out.println(UriKit.rmcontext("/test","/test//a/////b/c"));
    System.out.println("=====");
    System.out.println(UriKit.rmcontext("/test/", ""));
    System.out.println(UriKit.rmcontext("/test/","/"));
    System.out.println(UriKit.rmcontext("/test/","/test"));
    System.out.println(UriKit.rmcontext("/test/","/test//a"));
    System.out.println(UriKit.rmcontext("/test/","/test//a/////b"));
    System.out.println(UriKit.rmcontext("/test/","/test//a/////b/"));
    System.out.println(UriKit.rmcontext("/test/","/test//a/////b/c"));
  }

}
