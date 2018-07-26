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
package io.enoa.db.provider.ds.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import io.enoa.db.EnoaDs;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

class _C3p0 extends EnoaDs {

  private C3p0Config conf;
  private ComboPooledDataSource ds;

  _C3p0(C3p0Config conf) {
    this.conf = conf;
  }

  @Override
  public DataSource open() {
    this.init();
    return this.ds;
  }

  @Override
  public void close() {
    this.ds.close();
  }

  private void init() {
    this.ds = new ComboPooledDataSource();

    try {
      this.ds.setDriverClass(this.conf.driver());
    } catch (PropertyVetoException e) {
      this.ds = null;
      System.err.println("C3p0Plugin start error");
      throw new RuntimeException(e.getMessage(), e);
    }

    this.ds.setJdbcUrl(this.conf.url());
    this.ds.setUser(this.conf.user());
    this.ds.setPassword(this.conf.passwd());
    this.ds.setMaxPoolSize(this.conf.maxSize());
    this.ds.setMinPoolSize(this.conf.minSize());
    this.ds.setInitialPoolSize(this.conf.initSize());
    this.ds.setMaxIdleTime(this.conf.maxIdle());
    this.ds.setAcquireIncrement(this.conf.acquireIncrement());

  }
}
