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
package io.enoa.example.trydb;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

public class DruidDs {

  private DruidDataSource ds;

  private void init() {
    this.ds = new DruidDataSource();
    ds.setName("test");

    ds.setUrl("jdbc:postgresql://localhost:5432/trydb");
    ds.setUsername("postgres");
    ds.setPassword("passwd");

//    ds.setUrl("jdbc:mysql://localhost:3306/trydb");
//    ds.setUsername("root");
//    ds.setPassword("passwd");

    ds.setInitialSize(10);
    ds.setMaxActive(20);

  }

  public DataSource ds() {
    this.init();
    return this.ds;
  }

}
