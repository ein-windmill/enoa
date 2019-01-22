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
package io.enoa.toolkit.ethernet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ethernet {


  public static Ethernet machine() {
    return _EthernetLocal.instance().local();
  }

  private List<EthInfo> infs;

  Ethernet() {
    this.infs = new ArrayList<>();
  }


  void add(EthInfo inf) {
    this.infs.add(inf);
  }

  void unmodifiable() {
    this.infs = Collections.unmodifiableList(this.infs);
  }

  public List<EthInfo> eths() {
    return this.infs;
  }

  public Stream<EthInfo> filter(Predicate<? super EthInfo> predicate) {
    return this.infs.stream()
      .filter(predicate);
  }

  public List<EthInfo> LANS() {
    return this.infs.stream()
      .filter(inf -> {
        IP ip = inf.ip();
        if (ip == null)
          return false;
        if (ip.version() == IP.Version.SIX)
          return false;
        return ip.string().startsWith("192.") ||
          ip.string().startsWith("10.") ||
          ip.string().startsWith("172.") ||
          ip.string().startsWith("169.");
      })
      .collect(Collectors.toList());
  }

  public EthInfo LAN() {
    return this.LANS().stream().findFirst().orElse(null);
  }


}
