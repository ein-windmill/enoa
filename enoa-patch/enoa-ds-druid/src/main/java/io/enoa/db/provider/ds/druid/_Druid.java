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
package io.enoa.db.provider.ds.druid;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import io.enoa.db.EnoaDs;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class _Druid extends EnoaDs {

  private DruidConfig conf;
  private DruidDataSource ds;

  _Druid(DruidConfig conf) {
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

  private String validationQuery() {
    if (this.conf.url().startsWith("jdbc:oracle"))
      return "select 1 from dual";
    if (this.conf.url().startsWith("jdbc:db2"))
      return "select 1 from sysibm.sysdummy1";
    if (this.conf.url().startsWith("jdbc:hsqldb"))
      return "select 1 from INFORMATION_SCHEMA.SYSTEM_USERS";
    if (this.conf.url().startsWith("jdbc:derby"))
      return "select 1 from INFORMATION_SCHEMA.SYSTEM_USERS";
    return "select 1";
  }

  private void init() {
    this.ds = new DruidDataSource();

    if (this.conf.name() != null) {
      ds.setName(this.conf.name());
    }

    ds.setUrl(this.conf.url());
    ds.setUsername(this.conf.user());
    ds.setPassword(this.conf.passwd());
    if (this.conf.driver() != null)
      ds.setDriverClassName(this.conf.driver());
    ds.setInitialSize(this.conf.initSize());
    ds.setMinIdle(this.conf.minIdle());
    ds.setMaxActive(this.conf.maxActive());
    ds.setMaxWait(this.conf.maxWait());
    ds.setTimeBetweenConnectErrorMillis(this.conf.timeBetweenConnectErrorMillis());
    ds.setTimeBetweenEvictionRunsMillis(this.conf.timeBetweenEvictionRunsMillis());
    ds.setMinEvictableIdleTimeMillis(this.conf.minEvictableIdleTimeMillis());

    ds.setValidationQuery(this.validationQuery());
    if (this.conf.connectionInitSql() != null && !"".equals(this.conf.connectionInitSql())) {
      List<String> connectionInitSqls = new ArrayList<>();
      connectionInitSqls.add(this.conf.connectionInitSql());
      ds.setConnectionInitSqls(connectionInitSqls);
    }
    ds.setTestWhileIdle(this.conf.testWhileIdle());
    ds.setTestOnBorrow(this.conf.testOnBorrow());
    ds.setTestOnReturn(this.conf.testOnReturn());

    ds.setRemoveAbandoned(this.conf.removeAbandoned());
    ds.setRemoveAbandonedTimeoutMillis(this.conf.removeAbandonedTimeoutMillis());
    ds.setLogAbandoned(this.conf.logAbandoned());

    //只要maxPoolPreparedStatementPerConnectionSize>0,poolPreparedStatements就会被自动设定为true，参照druid的源码
    ds.setMaxPoolPreparedStatementPerConnectionSize(this.conf.maxPoolPreparedStatementPerConnectionSize());

    boolean hasSetConnectionProperties = false;
    if (this.conf.filters() != null && !"".equals(this.conf.filters())) {
      try {
        ds.setFilters(this.conf.filters());
        //支持加解密数据库
        if (this.conf.filters().contains("config")) {
          //判断是否设定了公钥
          if (this.conf.pubKey() == null || "".equals(this.conf.pubKey())) {
            throw new RuntimeException("Druid连接池的filter设定了config时，必须设定publicKey"); // todo
          }
          String decryptStr = "config.decrypt=true;config.decrypt.key=" + this.conf.pubKey();
          String cp = this.conf.connectionProperties();
          if (cp == null || "".equals(cp)) {
            cp = decryptStr;
          } else {
            cp = cp + ";" + decryptStr;
          }
          ds.setConnectionProperties(cp);
          hasSetConnectionProperties = true;
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
    //确保setConnectionProperties被调用过一次
    if (!hasSetConnectionProperties && (this.conf.connectionProperties() != null && !"".equals(this.conf.connectionProperties()))) {
      ds.setConnectionProperties(this.conf.connectionProperties());
    }
    addFilterList(ds);

  }

  private void addFilterList(DruidDataSource ds) {
    if (this.conf.filterList() != null) {
      List<Filter> targetList = ds.getProxyFilters();
      for (Filter add : this.conf.filterList()) {
        boolean found = false;
        for (Filter target : targetList) {
          if (add.getClass().equals(target.getClass())) {
            found = true;
            break;
          }
        }
        if (!found)
          targetList.add(add);
      }
    }
  }

}
