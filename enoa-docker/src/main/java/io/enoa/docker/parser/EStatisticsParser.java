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
package io.enoa.docker.parser;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.ret.docker.DResp;
import io.enoa.docker.ret.docker.container.*;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.date.DateKit;
import io.enoa.toolkit.map.Kv;

import java.util.HashMap;
import java.util.Map;

class EStatisticsParser extends AbstractParser<EStatistics> {

  private static class Holder {
    private static final EStatisticsParser INSTANCE = new EStatisticsParser();
  }

  static EStatisticsParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EStatistics ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    EStatistics.Builder builder = new EStatistics.Builder();
    builder.id(kv.string("id"))
      .name(kv.string("name"))
      .read(DateKit.parse(kv.string("read"), "yyyy-MM-dd'T'HH:mm:ss"))
      .preread(DateKit.parse(kv.string("preread"), "yyyy-MM-dd'T'HH:mm:ss"))
      .numprocs(kv.integer("num_procs"))
      .storagestats(kv.get("storage_stats"))
      .cpustats(this.cpustats(kv, "cpu_stats"))
      .precpustats(this.cpustats(kv, "precpu_stats"))
      .blkiostats(this.blkiostats(kv))
      .memorystats(this.memorystats(kv))
      .networks(this.network(kv));
    return builder.build();
  }


  private ECpuStats cpustats(Kv kv, String key) {
    Object cpuo = kv.get(key);
    if (!(cpuo instanceof Map))
      return null;
    ECpuStats.Builder builder = new ECpuStats.Builder();
    Map cum = (Map) cpuo;
    Object cuso0 = cum.get("cpu_usage");
    if (cuso0 instanceof Map) {
      Kv cpuusage = Kv.by((Map) cuso0);
      ECpuUseage.Builder cub = new ECpuUseage.Builder()
        .totalusage(cpuusage.integer("total_usage"))
        .usageinkernelmode(cpuusage.integer("usage_in_kernelmode"))
        .usageinusermode(cpuusage.integer("usage_in_usermode"));
      builder.cpuusage(cub.build());
    }
    Object tgd0 = cum.get("throttling_data");
    if (tgd0 instanceof Map) {
      Kv throd = Kv.by((Map) tgd0);
      EThrottlingData.Builder tdb = new EThrottlingData.Builder()
        .periods(throd.integer("periods"))
        .throttledperiods(throd.integer("throttled_periods"))
        .throttledtime(throd.integer("throttled_time"));
      builder.throttlingdata(tdb.build());
    }
    return builder.build();
  }


  private EBlkioStats blkiostats(Kv kv) {
    Object bstats = kv.get("blkio_stats");
    if (!(bstats instanceof Map))
      return null;
    Map bsm = (Map) bstats;
    EBlkioStats.Builder builder = new EBlkioStats.Builder()
      .ioservicebytesrecursive(bsm.get("io_service_bytes_recursive"))
      .ioservicedrecursive(bsm.get("io_serviced_recursive"))
      .ioqueuerecursive(bsm.get("io_queue_recursive"))
      .ioservicetimerecursive(bsm.get("io_service_time_recursive"))
      .iowaittimerecursive(bsm.get("io_wait_time_recursive"))
      .iomergedrecursive(bsm.get("io_merged_recursive"))
      .iotimerecursive(bsm.get("io_time_recursive"))
      .sectorsrecursive(bsm.get("sectors_recursive"));
    return builder.build();
  }

  private EMemoryStats memorystats(Kv kv) {
    Object msts = kv.get("memory_stats");
    if (!(msts instanceof Map))
      return null;
    Kv msk = Kv.by((Map) msts);
    EMemoryStats.Builder builder = new EMemoryStats.Builder()
      .maxusage(msk.integer("max_usage"))
      .usage(msk.integer("usage"))
      .failcnt(msk.integer("failcnt"))
      .limit(msk.doubler("limit"));

    Object xsta = msk.get("stats");
    if (xsta instanceof Map) {
      Kv stats = Kv.by((Map) xsta);
      EMStats.Builder msb = new EMStats.Builder()
        .totalpgmajfault(stats.integer("total_pgmajfault"))
        .cache(stats.integer("cache"))
        .mappedfile(stats.integer("mapped_file"))
        .totalinactivefile(stats.integer("total_inactive_file"))
        .pgpgout(stats.integer("pgpgout"))
        .rss(stats.integer("rss"))
        .totalmappedfile(stats.integer("total_mapped_file"))
        .writeback(stats.integer("writeback"))
        .unevictable(stats.integer("unevictable"))
        .pgpgin(stats.integer("pgpgin"))
        .totalunevictable(stats.integer("total_unevictable"))
        .pgmajfault(stats.integer("pgmajfault"))
        .totalrss(stats.integer("total_rss"))
        .totalrsshuge(stats.integer("total_rss_huge"))
        .totalwriteback(stats.integer("total_writeback"))
        .totalinactiveanon(stats.integer("total_inactive_anon"))
        .rsshuge(stats.integer("rss_huge"))
        .hierarchicalmemorylimit(stats.doubler("hierarchical_memory_limit"))
        .totalpgfault(stats.integer("total_pgfault"))
        .totalactivefile(stats.integer("total_active_file"))
        .activeanon(stats.integer("active_anon"))
        .totalactiveanon(stats.integer("total_active_anon"))
        .totalpgpgout(stats.integer("total_pgpgout"))
        .totalcache(stats.integer("total_cache"))
        .inactiveanon(stats.integer("inactive_anon"))
        .activefile(stats.integer("active_file"))
        .pgfault(stats.integer("pgfault"))
        .inactivefile(stats.integer("inactive_file"))
        .totalpgpgin(stats.integer("total_pgpgin"));
      builder.stats(msb.build());
    }
    return builder.build();
  }

  private Map<String, EEth> network(Kv kv) {
    Object networks = kv.get("networks");
    if (!(networks instanceof Map))
      return null;
    Map nwm = (Map) networks;
    Map<String, EEth> ret = new HashMap<>(nwm.size());
    nwm.forEach((key, val) -> {
      if (!(val instanceof Map))
        return;
      Kv eth = Kv.by((Map) val);
      EEth.Builder builder = new EEth.Builder()
        .rxbytes(eth.integer("rx_bytes"))
        .rxdropped(eth.integer("rx_dropped"))
        .rxerrors(eth.integer("rx_errors"))
        .rxpackets(eth.integer("rx_packets"))
        .txbytes(eth.integer("tx_bytes"))
        .txdropped(eth.integer("tx_dropped"))
        .txerrors(eth.integer("tx_errors"))
        .txpackets(eth.integer("tx_packets"));
      ret.put(ConvertKit.string(key), builder.build());
    });
    return ret;
  }

}
