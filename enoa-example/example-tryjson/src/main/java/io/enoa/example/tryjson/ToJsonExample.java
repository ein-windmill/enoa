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
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.digest.UUIDKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.mark.IMarkIx;
import io.enoa.toolkit.mark.IMarkMsg;
import io.enoa.toolkit.mark.IMarkVal;
import io.enoa.toolkit.namecase.NamecaseKit;
import io.enoa.toolkit.namecase.NamecaseType;
import io.enoa.tryjson.Esonfig;
import io.enoa.tryjson.Tryjson;
import io.enoa.tryjson.mark.DateFormatStrategy;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToJsonExample {

  private Kv kv0() {
    return Kv.by("name", "jack")
      .set("age", 0)
      .set("ctime", new Date())
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
      .set("bean0", this.bean0())
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
      .set("map", this.kv0().set("kv0", this.kv0().set("kv0", this.kv0())))
      .set("bean", this.bean0());
  }

  private Esonfig bean0() {
    return new Esonfig.Builder()
      .debug(false)
      .dateFormat(EoConst.DEF_FORMAT_DATE)
      .dateFormatStrategy(DateFormatStrategy.STRING)
      .build();
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
    System.out.println(Tryjson.json(this.kv0(), new Esonfig.Builder().dateFormat("yyyy/MM/dd HH:mm:ss").build()));
  }

  private void testBean() {
    System.out.println(Tryjson.json(this.bean0()));
    System.out.println(Tryjson.json(this.bean90()));
    System.out.println(Tryjson.json(this.bean91()));
    System.out.println(Tryjson.json(this.bean92()));
  }

  private void testMap() {
    System.out.println(Tryjson.json(this.kv1()));
  }

  private void testConf() {
    System.out.println(Tryjson.json(this.bean93(), new Esonfig.Builder()
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
      .build()));
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

  public static void main(String[] args) {

    // default tryjson config
    Tryjson.epm().install(new Esonfig.Builder().dateFormatStrategy(DateFormatStrategy.TIMESTAMP).build());

    ToJsonExample example = new ToJsonExample();
    example.testDateFormat();
    example.testBean();
    example.testMap();
    example.testConf();
    example.testArr();

  }


}
