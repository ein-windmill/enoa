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
package io.enoa.yosart.ext.router;

import io.enoa.repeater.http.Method;
import io.enoa.yosart.kernel.resources.OyResource;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OyResourcedTest {

  private int cardinal = 10000;
  private String[] uris = new String[cardinal];
  private int[] testPoints = new int[10];


  @Before
  public void init() {
    int i = 0;
    while (i < this.cardinal) {
      this.uris[i] = String.format("/api/%d", i);
      i += 1;
    }
    for (int z = 0; z < this.testPoints.length; z++) {
      int ix = (int) (Math.random() * (this.cardinal + 1));
      this.testPoints[z] = ix;
    }
  }

  @Test
  public void testEfficiency() {
    this.testMap();
    this.testArray();
    this.testStream();
  }

  private List<YdRouterMapping> arrayMapping() {
    int i = 0;
    List<YdRouterMapping> mappings = new ArrayList<>();
    while (i < this.cardinal) {
      mappings.add(new YdRouterMapping.Builder().uri(this.uris[i]).resource(new OyResource() {
        @Override
        public Type type() {
          return null;
        }

        @Override
        public String route() {
          return null;
        }

        @Override
        public String className() {
          return null;
        }

        @Override
        public java.lang.reflect.Method func() {
          return null;
        }

        @Override
        public String funcName() {
          return null;
        }

        @Override
        public String identityFuncName() {
          return null;
        }

        @Override
        public String hashName() {
          return null;
        }

        @Override
        public String[] vars() {
          return new String[0];
        }

        @Override
        public Method[] methods() {
          return new Method[0];
        }
      }).build());
      i += 1;
    }
    return mappings;
  }

  private void testArray() {
    System.out.println("ARRAY =============");
    long start = System.currentTimeMillis();
    List<YdRouterMapping> mappings = this.arrayMapping();
    long rec1 = System.currentTimeMillis();
    System.out.println(String.format("load data time %d", rec1 - start));

    for (int point : this.testPoints) {
      for (YdRouterMapping m : mappings) {
        if (!m.uri().equals(String.format("/api/%d", point)))
          continue;
        long rec2 = System.currentTimeMillis();
        System.out.println(String.format("hint %s time %d", m.uri(), rec2 - start));
      }
    }
    long rec3 = System.currentTimeMillis();
    System.out.println(String.format("final time %d", rec3 - start));
    System.out.println("\n\n");
  }

  private void testStream() {
    System.out.println("STREAM =============");
    long start = System.currentTimeMillis();
    List<YdRouterMapping> mappings = this.arrayMapping();
    long rec1 = System.currentTimeMillis();
    System.out.println(String.format("load data time %d", rec1 - start));

    for (int point : this.testPoints) {
      mappings.stream()
        .filter(m -> m.uri().equals(String.format("/api/%d", point)))
        .forEach(m -> {
          if (!m.uri().equals(String.format("/api/%d", point)))
            return;
          long rec2 = System.currentTimeMillis();
          System.out.println(String.format("hint %s time %d", m.uri(), rec2 - start));
        });
    }
    long rec3 = System.currentTimeMillis();
    System.out.println(String.format("final time %d", rec3 - start));
    System.out.println("\n\n");
  }

  private void testMap() {
    System.out.println("MAP =============");
    long start = System.currentTimeMillis();
    int i = 0;
    Map<String, OyResource> mappings = new HashMap<>();
    while (i < this.cardinal) {
      mappings.put(this.uris[i], new OyResource() {
        @Override
        public Type type() {
          return null;
        }

        @Override
        public String route() {
          return null;
        }

        @Override
        public String className() {
          return null;
        }

        @Override
        public java.lang.reflect.Method func() {
          return null;
        }

        @Override
        public String funcName() {
          return null;
        }

        @Override
        public String identityFuncName() {
          return null;
        }

        @Override
        public String hashName() {
          return null;
        }

        @Override
        public String[] vars() {
          return new String[0];
        }

        @Override
        public Method[] methods() {
          return new Method[0];
        }
      });
      i += 1;
    }
    long rec1 = System.currentTimeMillis();
    System.out.println(String.format("load data time %d", rec1 - start));

    for (int point : this.testPoints) {
      String uri = String.format("/api/%d", point);
      mappings.get(uri);
      long rec2 = System.currentTimeMillis();
      System.out.println(String.format("hint %s time %d", uri, rec2 - start));
    }
    long rec3 = System.currentTimeMillis();
    System.out.println(String.format("final time %d", rec3 - start));
    System.out.println("\n\n");
  }

}
