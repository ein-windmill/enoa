/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
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
package io.enoa.example.tryjson;

import io.enoa.example.tryjson.entity.*;
import io.enoa.http.EoUrl;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.http.protocol.enoa.IHttpHandler;
import io.enoa.http.protocol.enoa.IHttpReporter;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.digest.UUIDKit;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.mark.IMarkIx;
import io.enoa.toolkit.mark.IMarkMsg;
import io.enoa.toolkit.mark.IMarkVal;
import io.enoa.toolkit.namecase.NamecaseKit;
import io.enoa.toolkit.namecase.NamecaseType;
import io.enoa.toolkit.path.PathKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.tryjson.Tryjson;
import io.enoa.tryjson.Tsonfig;
import io.enoa.tryjson.json.Ja;
import io.enoa.tryjson.json.Jo;
import io.enoa.tryjson.mark.DateFormatStrategy;
import io.enoa.typebuilder.TypeBuilder;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.*;

public class ToJsonExample {

  private Kv kv0() {
    return Kv.by("name", "jack")
      .set("age", 0)
      .set("ctime", new Date())
      .set("go", false)
      .set("pro0", null)
      .set("pro1", "null")
      .set("country", "UK");
  }

  private Kv kv1() {
    return Kv.by("key0", "string")
      .set("integer", 1)
      .set("longer", 9L)
      .set("shorter", (short) 0)
      .set("doubler", .3D)
      .set("floter", .4F)
      .set("bigdecimal", new BigDecimal(99999.99999))
      .set("byte", (byte) 4)
      .set("char", 'p')
      .set("number", new Number() {
        @Override
        public int intValue() {
          return 34567;
        }

        @Override
        public long longValue() {
          return 34567L;
        }

        @Override
        public float floatValue() {
          return 34567F;
        }

        @Override
        public double doubleValue() {
          return 34567D;
        }

//        @Override
//        public String toString() {
//          return String.valueOf(34567);
//        }
      })
      .set("bool", Boolean.TRUE)
      .set("bean90", this.bean90())
      .set("bean91", this.bean91())
      .set("bytes", new byte[]{1, 2, 3, 4})
      .set("arr0", new int[]{1, 2, 3, 4})
      .set("arr1", new char[]{'1', '2', '3', '4'})
      .set("arr2", new String[]{"1", "2", "3", "4"})
      .set("collection0", new ArrayList<String>() {{
        add("1");
        add("2");
        add("3");
        add("4");
      }})
      .set("collection1", new ArrayList<Bean90>() {{
        add(bean90());
      }})
      .set("map", this.kv0().set("kv0", this.kv0().set("kv0", this.kv0())));
  }

  private Bean90 bean90() {
    Bean90 bean90 = new Bean90();
    bean90.code = 0x83;
    bean90.country = "UK";
    bean90.name = "Jack";
    return bean90;
  }

  private Bean91 bean91() {
    Bean91 bean91 = new Bean91();
    bean91.country = "UK";
    bean91.name = "Jack";
    return bean91;
  }

  private Bean92 bean92() {
    Bean92 bean92 = new Bean92();
    bean92.ctime = new Timestamp(System.currentTimeMillis());
    bean92.appId("1")
      .expire_time("90999")
      .token(UUIDKit.next(false))
      .websiteHost("example.com");
    return bean92;
  }

  private Bean93 bean93() {
    Bean93 bean93 = new Bean93(this.bean92());
    bean93.address = "No 92.";
    bean93.city("Town")
      .province(1)
      .typeIx(BTypeIx.TYPE_A)
      .typeVal(BTypeVal.TYPE_B)
      .typeOrigin(BTypeOrigin.TYPE_A);
    return bean93;
  }

  private void testDateFormat() {
    System.out.println(Tryjson.json(this.kv0(), new Tsonfig.Builder().dateFormat("yyyy/MM/dd HH:mm:ss").build()));
  }

  private void testBean() {
    System.out.println(Tryjson.json(this.bean90()));
    System.out.println(Tryjson.json(this.bean91()));
    System.out.println(Tryjson.json(this.bean92()));
  }

  private void testMap() {
    System.out.println(Tryjson.json(this.kv1()));
  }

  private void testConf() {
    Tsonfig config = new Tsonfig.Builder()
      .namecase(NamecaseKit.namecase(NamecaseType.CASE_UNDERLINE))
      .dateFormatStrategy(DateFormatStrategy.STRING)
      .dateFormat(EoConst.DEF_FORMAT_DATE)
      .enumConverter(em -> {
        if (em instanceof IMarkIx)
          return ((IMarkIx) em).ix();
        if (em instanceof IMarkVal)
          return ((IMarkVal) em).val();
        if (em instanceof IMarkMsg)
          return ((IMarkMsg) em).code();
        return em.name();
      })
      .build();
    String json = Tryjson.json(this.bean93(), config);
    System.out.println(json);
  }

  private void testArr() {
    String[] arr = new String[]{
      "a\"",
      "'''\"\\\"",
      "\\\\",
      "\r\n\t\b\"",
      "\\r\\n\\t\\b\\\"",
      "\\\r\\\n\\\t\\\b\\\""
    };
    System.out.println(Tryjson.json(arr));
  }

  private Kv kv2() {
    return this.kv0()
      .set("spa ce", "\\sp\"ace\\")
      .set("esc", "\\/\\t\\a")
      .set("obj0", Kv.by("obk0", "obv0"))
      //
      ;
  }

  private void testParseObject(int ix) {
//    String json = FileKit.read(PathKit.debugPath().resolve(TextKit.union("enoa-example/example-tryjson/src/main/resources/", ix, ".json"))).string();
    String json = FileKit.read(PathKit.debugPath().resolve(TextKit.union("src/main/resources/", ix, ".json"))).string();
    Tsonfig config = Tryjson.epm()
      .config()
      .builder()
      .skipNull(false)
      .namecase(NamecaseKit.namecase(NamecaseType.CASE_UNDERLINE))
      .build();
    Jo jo = Tryjson.object(json, config);
    String _j1 = Tryjson.json(jo, config);
    System.out.println(_j1);
  }

  private void testParseArray(int ix) {
//    String json = FileKit.read(PathKit.debugPath().resolve(TextKit.union("enoa-example/example-tryjson/src/main/resources/", ix, ".json"))).string();
    String json = FileKit.read(PathKit.debugPath().resolve(TextKit.union("src/main/resources/", ix, ".json"))).string();
    Tsonfig config = Tryjson.epm()
      .config()
      .builder()
      .skipNull(false)
      .build();
    Ja ja = Tryjson.array(json, config);
    String _j1 = Tryjson.json(ja, config);
    System.out.println(_j1);
  }

  private void parseJson0() {
    Path path = PathKit.debugPath().resolve("enoa-example/example-tryjson/_tmp/enoa.json");
    String json = null;
    if (!FileKit.exists(path)) {
      HttpResponse response = Http.request(EoUrl.with("https://api.github.com/repos/ein-windmill/enoa/commits?per_page=5"))
        .reporter(IHttpReporter.def())
        .handler(IHttpHandler.def())
        .emit();
      String body = response.body().string();
      json = body;
      FileKit.write(path, ByteBuffer.wrap(body.getBytes(EoConst.CHARSET)));
    }
    if (json == null)
      json = FileKit.read(path).string();
    Ja ja = Tryjson.array(json);
    Jo j1 = ja.value(1).as();

    String tj = Tryjson.json(ja);
    System.out.println(tj);
  }

  private void parseBean93() {
    List<Bean93> bl = new ArrayList<>();
//    Bean93 bean93 = this.bean93();
    bl.add(this.bean93());
    bl.add(this.bean93());
    String json = Tryjson.json(bl);
    List<Bean93> _b0 = Tryjson.object(json, TypeBuilder.with(List.class).type(Bean93.class).build());
    System.out.println(_b0);
  }

  private void parseBean0() {
    Bean90 bean90_0 = new Bean90();
    bean90_0.code = 1;
    bean90_0.name = "UK";
    bean90_0.country = "UK";
    Bean90 bean90_1 = new Bean90();
    bean90_1.code = 1;
    bean90_1.name = "US";
    bean90_1.country = "US";


    List<Kv> kvs = new ArrayList<Kv>() {{
      add(Kv.by("item", Kv.by("name", "jack"))
        .set("com", " com0")
        .set("stat", 1)
        .set("type", 2)
        .set("open", true)
        .set("marks", new ArrayList<Bean96>() {{
          add(new Bean96().id(UUIDKit.next()).mark("mark0").country(bean90_0));
        }})
        .set("unknown", new Bean97().text("text0"))
      );
      add(Kv.by("item", Kv.by("name", "tom"))
        .set("com", "com1")
        .set("stat", 1)
        .set("type", 3)
        .set("marks", new ArrayList<Bean96>() {{
          add(new Bean96().id(UUIDKit.next()).mark("mark1").country(bean90_1));
        }})
        .set("unknown", new Bean97().text("text1"))
      );
    }};
    String json = Tryjson.json(kvs);
    List<Bean94<Bean97>> ret0 = Tryjson.object(json,
      TypeBuilder.with(List.class)
        .beginSubType(Bean94.class)
        .type(Bean97.class)
        .endSubType()
        .build(),
      Tryjson.epm().config()
        .builder()
        .namecase(NamecaseKit.namecase(NamecaseType.CASE_UNDERLINE))
        .build()
    );
    System.out.println(Tryjson.json(ret0));
  }

  private void parseBean1() {
    List<Kv> data = new ArrayList<>();
    Kv kv0 = Kv.create()
      .set("k0", new HashSet<Integer>() {{
        add(1);
        add(2);
        add(3);
      }});
    Kv kv1 = Kv.create()
      .set("k1", new HashSet<Integer>() {{
        add(4);
        add(5);
        add(6);
      }});
    data.add(kv0);
    data.add(kv1);
    String json = Tryjson.json(data);

    Type type = TypeBuilder.with(List.class)
      .beginSubType(Map.class)
      .type(String.class)
      .beginSubType(Set.class)
      .type(Integer.class)
      .endSubType()
      .endSubType()
      .build();
    List<Map<String, Set<Integer>>> ret = Tryjson.object(json, type);
    System.out.println(ret);
  }

  public static void main(String[] args) {

    // default tryjson config
    Tryjson.epm().install(new Tsonfig.Builder().debug().dateFormatStrategy(DateFormatStrategy.TIMESTAMP).build());

    ToJsonExample example = new ToJsonExample();
    example.testDateFormat();
    example.testBean();
    example.testMap();
    example.testConf();
    example.testArr();

//    example.testParseObject(0);
//    example.testParseObject(1);
//    example.testParseObject(2);
//    example.testParseObject(3);
//    example.testParseObject(6);

//    example.testParseArray(4);
//    example.testParseArray(5);
//    example.parseJson0();

//    example.parseBean93();

//    example.parseBean0();
//    example.parseBean1();
  }


}
