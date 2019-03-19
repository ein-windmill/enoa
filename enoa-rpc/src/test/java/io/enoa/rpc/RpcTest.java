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
package io.enoa.rpc;

import com.alibaba.fastjson.JSON;
import io.enoa.http.EoUrl;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.json.provider.fastjson.FastjsonProvider;
import io.enoa.log.provider.slf4j.Slf4JLogProvider;
import io.enoa.rpc.entity.Area;
import io.enoa.rpc.entity.Result;
import io.enoa.rpc.http.HttpRpcPromise;
import io.enoa.rpc.http.HttpRpcResult;
import io.enoa.toolkit.map.Kv;
import io.enoa.typebuilder.TypeBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RpcTest {

  @Before
  public void before() {
    Rpc.epm()
      .json(new FastjsonProvider())
      .log(new Slf4JLogProvider());

    Rpc.epm().register()
      .http("passport", EoUrl.with("http://localhost:20003/"))
      .http("publib", EoUrl.with("http://localhost:20005"));
  }

  @Test
  @Ignore
  public void testTcp() {

    HttpRpcResult<Kv> ret0 = Rpc.http("passport", "application")
      .method(HttpMethod.POST)
      .para("a", "b")
      .emit(Kv.class);
    System.out.println(ret0);

    HttpRpcResult<Kv> ret1 = Rpc.http("publib", "/area/lst")
      .emit(Kv.class);
    System.out.println(ret1.result());


    HttpRpcResult<Result<List<Area>>> ret2 = Rpc.http("publib", "/area/lst")
      .emit(TypeBuilder.with(Result.class).subType(List.class).type(Area.class).endSubType().build());
    System.out.println(ret2.result());

    HttpRpcResult<byte[]> ret3 = Rpc.http("passport", "/avatar/1").emit((body, type) -> body.bytes());
    System.out.println(Arrays.toString(ret3.result()));

  }

  @Test
  @Ignore
  public void testTcpEnqueue() throws InterruptedException {
    Rpc.http("passport", "application")
      .method(HttpMethod.POST)
      .para("a", "b")
      .enqueue(Kv.class)
      .done(System.out::println);

    Rpc.http("publib", "/area/lst")
      .enqueue(Kv.class)
      .done(System.out::println);

    HttpRpcPromise<Result<List<Area>>> ret2 = Rpc.http("publib", "/area/lst")
      .enqueue(TypeBuilder.with(Result.class).subType(List.class).type(Area.class).endSubType().build());
    ret2.done(System.out::println);

    Rpc.http("passport", "/avatar/1").enqueue((body, type) -> body.bytes())
      .done(ret -> System.out.println(Arrays.toString(ret.result())));

    TimeUnit.SECONDS.sleep(3L);
  }


  @Test
  public void testrjson() {

    List<String> a0 = new ArrayList<>();
    a0.add("1");
    a0.add("2");
    a0.add("3");
    a0.add("4");
    String s = JSON.toJSONString(a0);

    List<String> a1 = JSON.parseObject(s, TypeBuilder.with(List.class).type(String.class).build());
    System.out.println(a1);

  }


}
