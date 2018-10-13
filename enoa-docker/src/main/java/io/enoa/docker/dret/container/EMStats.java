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
package io.enoa.docker.dret.container;

import io.enoa.docker.dret.AbstractDRet;

public class EMStats extends AbstractDRet {


  private final Integer totalpgmajfault;
  private final Integer cache;
  private final Integer mappedfile;
  private final Integer totalinactivefile;
  private final Integer pgpgout;
  private final Integer rss;
  private final Integer totalmappedfile;
  private final Integer writeback;
  private final Integer unevictable;
  private final Integer pgpgin;
  private final Integer totalunevictable;
  private final Integer pgmajfault;
  private final Integer totalrss;
  private final Integer totalrsshuge;
  private final Integer totalwriteback;
  private final Integer totalinactiveanon;
  private final Integer rsshuge;
  private final Double hierarchicalmemorylimit;
  private final Integer totalpgfault;
  private final Integer totalactivefile;
  private final Integer activeanon;
  private final Integer totalactiveanon;
  private final Integer totalpgpgout;
  private final Integer totalcache;
  private final Integer inactiveanon;
  private final Integer activefile;
  private final Integer pgfault;
  private final Integer inactivefile;
  private final Integer totalpgpgin;



  public EMStats(Builder builder) {
    this.totalpgmajfault = builder.totalpgmajfault;
    this.cache = builder.cache;
    this.mappedfile = builder.mappedfile;
    this.totalinactivefile = builder.totalinactivefile;
    this.pgpgout = builder.pgpgout;
    this.rss = builder.rss;
    this.totalmappedfile = builder.totalmappedfile;
    this.writeback = builder.writeback;
    this.unevictable = builder.unevictable;
    this.pgpgin = builder.pgpgin;
    this.totalunevictable = builder.totalunevictable;
    this.pgmajfault = builder.pgmajfault;
    this.totalrss = builder.totalrss;
    this.totalrsshuge = builder.totalrsshuge;
    this.totalwriteback = builder.totalwriteback;
    this.totalinactiveanon = builder.totalinactiveanon;
    this.rsshuge = builder.rsshuge;
    this.hierarchicalmemorylimit = builder.hierarchicalmemorylimit;
    this.totalpgfault = builder.totalpgfault;
    this.totalactivefile = builder.totalactivefile;
    this.activeanon = builder.activeanon;
    this.totalactiveanon = builder.totalactiveanon;
    this.totalpgpgout = builder.totalpgpgout;
    this.totalcache = builder.totalcache;
    this.inactiveanon = builder.inactiveanon;
    this.activefile = builder.activefile;
    this.pgfault = builder.pgfault;
    this.inactivefile = builder.inactivefile;
    this.totalpgpgin = builder.totalpgpgin;
  }

  public Integer totalpgmajfault() {
    return totalpgmajfault;
  }

  public Integer cache() {
    return cache;
  }

  public Integer mappedfile() {
    return mappedfile;
  }

  public Integer totalinactivefile() {
    return totalinactivefile;
  }

  public Integer pgpgout() {
    return pgpgout;
  }

  public Integer rss() {
    return rss;
  }

  public Integer totalmappedfile() {
    return totalmappedfile;
  }

  public Integer writeback() {
    return writeback;
  }

  public Integer unevictable() {
    return unevictable;
  }

  public Integer pgpgin() {
    return pgpgin;
  }

  public Integer totalunevictable() {
    return totalunevictable;
  }

  public Integer pgmajfault() {
    return pgmajfault;
  }

  public Integer totalrss() {
    return totalrss;
  }

  public Integer totalrsshuge() {
    return totalrsshuge;
  }

  public Integer totalwriteback() {
    return totalwriteback;
  }

  public Integer totalinactiveanon() {
    return totalinactiveanon;
  }

  public Integer rsshuge() {
    return rsshuge;
  }

  public Double hierarchicalmemorylimit() {
    return hierarchicalmemorylimit;
  }

  public Integer totalpgfault() {
    return totalpgfault;
  }

  public Integer totalactivefile() {
    return totalactivefile;
  }

  public Integer activeanon() {
    return activeanon;
  }

  public Integer totalactiveanon() {
    return totalactiveanon;
  }

  public Integer totalpgpgout() {
    return totalpgpgout;
  }

  public Integer totalcache() {
    return totalcache;
  }

  public Integer inactiveanon() {
    return inactiveanon;
  }

  public Integer activefile() {
    return activefile;
  }

  public Integer pgfault() {
    return pgfault;
  }

  public Integer inactivefile() {
    return inactivefile;
  }

  public Integer totalpgpgin() {
    return totalpgpgin;
  }

  public static class Builder {

    private Integer totalpgmajfault;
    private Integer cache;
    private Integer mappedfile;
    private Integer totalinactivefile;
    private Integer pgpgout;
    private Integer rss;
    private Integer totalmappedfile;
    private Integer writeback;
    private Integer unevictable;
    private Integer pgpgin;
    private Integer totalunevictable;
    private Integer pgmajfault;
    private Integer totalrss;
    private Integer totalrsshuge;
    private Integer totalwriteback;
    private Integer totalinactiveanon;
    private Integer rsshuge;
    private Double hierarchicalmemorylimit;
    private Integer totalpgfault;
    private Integer totalactivefile;
    private Integer activeanon;
    private Integer totalactiveanon;
    private Integer totalpgpgout;
    private Integer totalcache;
    private Integer inactiveanon;
    private Integer activefile;
    private Integer pgfault;
    private Integer inactivefile;
    private Integer totalpgpgin;

    public EMStats build() {
      return new EMStats(this);
    }

    public Builder totalpgmajfault(Integer totalpgmajfault) {
      this.totalpgmajfault = totalpgmajfault;
      return this;
    }

    public Builder cache(Integer cache) {
      this.cache = cache;
      return this;
    }

    public Builder mappedfile(Integer mappedfile) {
      this.mappedfile = mappedfile;
      return this;
    }

    public Builder totalinactivefile(Integer totalinactivefile) {
      this.totalinactivefile = totalinactivefile;
      return this;
    }

    public Builder pgpgout(Integer pgpgout) {
      this.pgpgout = pgpgout;
      return this;
    }

    public Builder rss(Integer rss) {
      this.rss = rss;
      return this;
    }

    public Builder totalmappedfile(Integer totalmappedfile) {
      this.totalmappedfile = totalmappedfile;
      return this;
    }

    public Builder writeback(Integer writeback) {
      this.writeback = writeback;
      return this;
    }

    public Builder unevictable(Integer unevictable) {
      this.unevictable = unevictable;
      return this;
    }

    public Builder pgpgin(Integer pgpgin) {
      this.pgpgin = pgpgin;
      return this;
    }

    public Builder totalunevictable(Integer totalunevictable) {
      this.totalunevictable = totalunevictable;
      return this;
    }

    public Builder pgmajfault(Integer pgmajfault) {
      this.pgmajfault = pgmajfault;
      return this;
    }

    public Builder totalrss(Integer totalrss) {
      this.totalrss = totalrss;
      return this;
    }

    public Builder totalrsshuge(Integer totalrsshuge) {
      this.totalrsshuge = totalrsshuge;
      return this;
    }

    public Builder totalwriteback(Integer totalwriteback) {
      this.totalwriteback = totalwriteback;
      return this;
    }

    public Builder totalinactiveanon(Integer totalinactiveanon) {
      this.totalinactiveanon = totalinactiveanon;
      return this;
    }

    public Builder rsshuge(Integer rsshuge) {
      this.rsshuge = rsshuge;
      return this;
    }

    public Builder hierarchicalmemorylimit(Double hierarchicalmemorylimit) {
      this.hierarchicalmemorylimit = hierarchicalmemorylimit;
      return this;
    }

    public Builder totalpgfault(Integer totalpgfault) {
      this.totalpgfault = totalpgfault;
      return this;
    }

    public Builder totalactivefile(Integer totalactivefile) {
      this.totalactivefile = totalactivefile;
      return this;
    }

    public Builder activeanon(Integer activeanon) {
      this.activeanon = activeanon;
      return this;
    }

    public Builder totalactiveanon(Integer totalactiveanon) {
      this.totalactiveanon = totalactiveanon;
      return this;
    }

    public Builder totalpgpgout(Integer totalpgpgout) {
      this.totalpgpgout = totalpgpgout;
      return this;
    }

    public Builder totalcache(Integer totalcache) {
      this.totalcache = totalcache;
      return this;
    }

    public Builder inactiveanon(Integer inactiveanon) {
      this.inactiveanon = inactiveanon;
      return this;
    }

    public Builder activefile(Integer activefile) {
      this.activefile = activefile;
      return this;
    }

    public Builder pgfault(Integer pgfault) {
      this.pgfault = pgfault;
      return this;
    }

    public Builder inactivefile(Integer inactivefile) {
      this.inactivefile = inactivefile;
      return this;
    }

    public Builder totalpgpgin(Integer totalpgpgin) {
      this.totalpgpgin = totalpgpgin;
      return this;
    }
  }

}
