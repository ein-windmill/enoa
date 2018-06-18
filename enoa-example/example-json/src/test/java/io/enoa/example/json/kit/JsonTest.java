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
package io.enoa.example.json.kit;

import io.enoa.example.json.User;
import io.enoa.json.Json;
import io.enoa.json.provider.enoa.EoJsonProvider;
import io.enoa.json.provider.fastjson.FastjsonProvider;
import io.enoa.json.provider.gson.GsonProvider;
import io.enoa.json.provider.jackson.JacksonProvider;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ein on 2017/8/20.
 */
public class JsonTest {


  private Map<String, Object> dataMap() {
    Map<String, Object> data = new HashMap<>();
    data.put("id", 345678L);
    data.put("name", "Joy");
    data.put("age", 30);
    data.put("gender", 1);
    data.put("score", 80.3D);
    data.put("now", new Date());
    data.put("other", null);
    return data;
  }

  private User dataUser() {
    User user = new User();
    user.setId(5678L);
    user.setName("Jen");
    user.setAge(23);
    user.setGender(1);
    user.setScore(5678.23D);
    user.setNow(new Timestamp(System.currentTimeMillis()));
    user.setOther(null);
    return user;
  }

  @Test
  public void testToJson() throws Exception {
//    EMgrJson.defDatePattern("yyyy-MM-dd HH:mm:ss.SSS");

    Map<String, Object> dmp = this.dataMap();
    Json.epm().install(new EoJsonProvider());
    System.out.println(Json.toJson(dmp));
    Json.epm().install(new JacksonProvider());
    System.out.println(Json.toJson(dmp));
    Json.epm().install(new GsonProvider());
    System.out.println(Json.toJson(dmp));
    Json.epm().install(new FastjsonProvider());
    System.out.println(Json.toJson(dmp));

    User dmu = this.dataUser();
    Json.epm().install(new EoJsonProvider());
    System.out.println(Json.toJson(dmu));
    Json.epm().install(new JacksonProvider());
    System.out.println(Json.toJson(dmu));
    Json.epm().install(new GsonProvider());
    System.out.println(Json.toJson(dmu));
    Json.epm().install(new FastjsonProvider());
    System.out.println(Json.toJson(dmu));

    List<User> us = new ArrayList<>();
    us.add(this.dataUser());
    us.add(this.dataUser());
    Json.epm().install(new EoJsonProvider());
    System.out.println(Json.toJson(us));
    Json.epm().install(new JacksonProvider());
    System.out.println(Json.toJson(us));
    Json.epm().install(new GsonProvider());
    System.out.println(Json.toJson(us));
    Json.epm().install(new FastjsonProvider());
    System.out.println(Json.toJson(us));
  }

  @Test
  public void testParse() throws Exception {
    Map dmp = this.dataMap();
    String dmps = Json.toJson(dmp);
    User dmu = this.dataUser();
    String dmus = Json.toJson(dmu);

    Json.epm().install(new FastjsonProvider());
    dmp = Json.parse(dmps, Map.class);
    System.out.println(Json.toJson(dmp));
    Json.epm().install(new JacksonProvider());
    dmp = Json.parse(dmps, Map.class);
    System.out.println(Json.toJson(dmp));
    Json.epm().install(new GsonProvider());
    dmp = Json.parse(dmps, Map.class);
    System.out.println(Json.toJson(dmp));

    Json.epm().install(new FastjsonProvider());
    dmu = Json.parse(dmps, User.class);
    System.out.println(Json.toJson(dmu));
    Json.epm().install(new JacksonProvider());
    dmu = Json.parse(dmps, User.class);
    System.out.println(Json.toJson(dmu));
    Json.epm().install(new GsonProvider());
    dmu = Json.parse(dmps, User.class);
    System.out.println(Json.toJson(dmu));

    List<Map<String, Object>> mps = new ArrayList<>();
    mps.add(this.dataMap());
    mps.add(this.dataMap());
    mps.add(this.dataMap());
    String mpsr = Json.toJson(mps);


    List<Map> pu = Json.parseArray(mpsr, Map.class);
    System.out.println(Json.toJson(pu));

  }

  @Test
  public void testFormat() {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    System.out.println(format.toString());
  }


}
