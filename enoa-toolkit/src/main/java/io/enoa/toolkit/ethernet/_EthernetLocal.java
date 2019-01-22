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

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

class _EthernetLocal {

  private static class Holder {
    private static final _EthernetLocal INSTANCE = new _EthernetLocal();
  }

  static _EthernetLocal instance() {
    return Holder.INSTANCE;
  }

  Ethernet local() {
    try {
      Ethernet ethernet = new Ethernet();
      Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
      while (interfaces.hasMoreElements()) {
        NetworkInterface nif = interfaces.nextElement();
        Enumeration<InetAddress> addresses = nif.getInetAddresses();
        while (addresses.hasMoreElements()) {
          InetAddress ip = addresses.nextElement();
          if (ip == null)
            continue;

          EthInfo.Builder builder = new EthInfo.Builder();
          builder.nif(nif)
            .name(nif.getName())
            .address(ip);

          byte[] mac = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
          builder.mac(new MAC(mac));

          if (ip instanceof Inet4Address) {
            builder.ip(IP.create(IP.Version.FOUR, ip.getHostAddress()));
          }
          if (ip instanceof Inet6Address) {
            builder.ip(IP.create(IP.Version.SIX, ip.getHostAddress()));
          }
          ethernet.add(builder.build());
        }
      }
      ethernet.unmodifiable();
      return ethernet;
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

}
