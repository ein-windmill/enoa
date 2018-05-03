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
package io.enoa.trydb;

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.stream.StreamKit;
import io.enoa.trydb.build.TrydbBuilder;
import io.enoa.trydb.dialect.IDialect;
import io.enoa.trydb.thr.NestedTransactionHelpException;
import io.enoa.trydb.thr.TrydbException;
import io.enoa.trydb.tsql.Trysql;
import io.enoa.trydb.tx.IAtom;
import io.enoa.trydb.tx.TxLevel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EnoaTrydb implements TrydbCommandBase<EnoaTrydb>, TrydbCommandTSql<EnoaTrydb> {


  private final ThreadLocal<Connection> LOCAL_CONN;

  private TrydbConfig config;
  private Connection xconn;
  private IDialect xdialect;

  EnoaTrydb(TrydbConfig config) {
    LOCAL_CONN = new ThreadLocal<>();
    this.config = config;
  }

  private Connection conn() throws SQLException {
    if (this.xconn != null)
      return this.xconn;

    Connection connection = this.LOCAL_CONN.get();
    if (connection != null)
      return connection;
    return this.config.ds().getConnection();
  }

  private IDialect dialect() {
    return this.xdialect == null ? this.config.dialect() : this.xdialect;
  }

  private void close(Connection conn) {
    // 用戶傳遞 connection 不主動釋放
//    if (this.xconn != null) {
//      StreamKit.close(this.xconn);
//      return;
//    }
    if (this.LOCAL_CONN.get() != null)
      return;
    if (conn == null)
      return;
    StreamKit.close(conn);
  }

  @Override
  public EnoaTrydb conn(Connection conn) {
    EnoaTrydb trydb = new EnoaTrydb(this.config);
    trydb.xconn = conn;
    trydb.xdialect = this.xdialect;
    return trydb;
  }

  @Override
  public EnoaTrydb dialect(IDialect dialect) {
    EnoaTrydb trydb = new EnoaTrydb(this.config);
    trydb.xconn = this.xconn;
    trydb.xdialect = dialect;
    return trydb;
  }

  @Override
  public List<Kv> find(String sql) {
    return this.find(sql, CollectionKit.emptyArray(Object.class));
  }

  @Override
  public List<Kv> find(String sql, Object... paras) {
    return this.beans(sql, Kv.class, paras);
  }

  @Override
  public List<Kv> find(Trysql sql) {
    return this.find(sql.dialect(this.config.dialect()).sql());
  }

  @Override
  public List<Kv> find(Trysql sql, Object... paras) {
    return this.find(sql.dialect(this.config.dialect()).sql(), paras);
  }

  @Override
  public Kv first(String sql) {
    return this.first(sql, CollectionKit.emptyArray(Object.class));
  }

  @Override
  public Kv first(String sql, Object... paras) {
    return this.bean(sql, Kv.class, paras);
  }

  @Override
  public Kv first(Trysql sql) {
    return this.first(sql.dialect(this.config.dialect()).sql());
  }

  @Override
  public Kv first(Trysql sql, Object... paras) {
    return this.first(sql.dialect(this.config.dialect()).sql(), paras);
  }

  @Override
  public <T> List<T> beans(String sql, Class<T> clazz) {
    return this.beans(sql, clazz, CollectionKit.emptyArray(Object.class));
  }

  @Override
  public <T> List<T> beans(String sql, Class<T> clazz, Object... paras) {
    Connection conn = null;
    try {
      conn = this.conn();
      PreparedStatement pst = conn.prepareStatement(sql);
      this.dialect().fillParas(pst, paras);
      ResultSet rs = pst.executeQuery();
      List<T> rets = TrydbBuilder.build(rs, clazz);
      StreamKit.close(false, pst, rs);
      return rets;
    } catch (SQLException e) {
      throw new TrydbException(e.getMessage(), e);
    } finally {
      this.close(conn);
    }
  }

  @Override
  public <T> List<T> beans(Trysql sql, Class<T> clazz) {
    return this.beans(sql.dialect(this.config.dialect()).sql(), clazz);
  }

  @Override
  public <T> List<T> beans(Trysql sql, Class<T> clazz, Object... paras) {
    return this.beans(sql.dialect(this.config.dialect()).sql(), clazz, paras);
  }

  @Override
  public <T> T bean(String sql, Class<T> clazz) {
    return this.bean(sql, clazz, CollectionKit.emptyArray(Object.class));
  }

  @Override
  public <T> T bean(String sql, Class<T> clazz, Object... paras) {
    List<T> beans = this.beans(sql, clazz, paras);
    if (beans.isEmpty())
      return null;
    if (beans.size() > 1) {
      // fix log warn
//      LogKit.warn(EnoaTipKit.message("eo.tip.trydb.single_query_has_multi_value", sql, beans.size()));
    }
    return beans.get(0);
  }

  @Override
  public <T> T bean(Trysql sql, Class<T> clazz) {
    return this.bean(sql.dialect(this.config.dialect()).sql(), clazz);
  }

  @Override
  public <T> T bean(Trysql sql, Class<T> clazz, Object... paras) {
    return this.bean(sql.dialect(this.config.dialect()).sql(), clazz, paras);
  }

  @Override
  public boolean tx(IAtom atom) {
    return this.tx(this.config.txlevel(), atom);
  }

  @Override
  public boolean tx(TxLevel level, IAtom atom) {
    Connection conn = this.LOCAL_CONN.get();
    if (conn != null) {
      try {
        if (conn.getTransactionIsolation() < level.ix())
          conn.setTransactionIsolation(level.ix());
        boolean result = atom.execute();
        if (result)
          return true;
        // 嵌套事物返回最外层 false, 由外层捕获
        throw new NestedTransactionHelpException();
      } catch (SQLException e) {
        throw new TrydbException(e.getMessage(), e);
      }
    }


    Boolean autoCommit = null;
    try {
      conn = this.conn();
      autoCommit = conn.getAutoCommit();
      this.LOCAL_CONN.set(conn);
      conn.setTransactionIsolation(level.ix());
      conn.setAutoCommit(false);
      boolean result = atom.execute();
      if (result)
        conn.commit();
      else
        conn.rollback();
      return result;
    } catch (NestedTransactionHelpException e) {
      if (conn != null) {
        try {
          conn.rollback();
        } catch (Exception e1) {
          throw new TrydbException(e1.getMessage(), e1);
        }
      }
      return false;
    } catch (Throwable t) {
      if (conn != null) {
        try {
          conn.rollback();
        } catch (Exception e1) {
          throw new TrydbException(e1.getMessage(), e1);
        }
      }
      throw t instanceof RuntimeException ? (RuntimeException) t : new TrydbException(t.getMessage(), t);
    } finally {
      try {
        if (conn != null) {
          if (autoCommit != null)
            conn.setAutoCommit(autoCommit);
          StreamKit.close(conn);
        }
      } catch (Throwable t) {
        // can not throw exception here, otherwise the more important exception in previous catch block can not be thrown
//        LogKit.error(t.getMessage(), t);
        t.printStackTrace();
      } finally {
        this.LOCAL_CONN.remove();
      }
    }
  }

  @Override
  public int update(String sql) {
    return this.update(sql, CollectionKit.emptyArray(Object.class));
  }

  @Override
  public int update(String sql, Object... paras) {
    Connection conn = null;
    try {
      conn = this.conn();
      PreparedStatement pst = conn.prepareStatement(sql);
      this.dialect().fillParas(pst, paras);
      int ret = pst.executeUpdate();
      StreamKit.close(pst);
      return ret;
    } catch (SQLException e) {
      throw new TrydbException(e.getMessage(), e);
    } finally {
      this.close(conn);
    }
  }

  @Override
  public int update(Trysql sql) {
    return this.update(sql.dialect(this.config.dialect()).sql());
  }

  @Override
  public int update(Trysql sql, Object... paras) {
    return this.update(sql.dialect(this.config.dialect()).sql(), paras);
  }

}
