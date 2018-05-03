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
package io.enoa.nosql.redis.command;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import java.util.List;
import java.util.Map;

interface GeoCommand extends _Runner {

  default Long geoadd(String key, double longitude, double latitude, String member) {
    return this.run((jedis, serializer) -> jedis.geoadd(key, longitude, latitude, member));
  }

  default Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
    return this.run((jedis, serializer) -> jedis.geoadd(key, memberCoordinateMap));
  }

  default List<GeoCoordinate> geopos(String key, String... members) {
    return this.run((jedis, serializer) -> jedis.geopos(key, members));
  }

  default Double geodist(String key, String member1, String member2) {
    return this.run((jedis, serializer) -> jedis.geodist(key, member1, member2));
  }

  default Double geodist(String key, String member1, String member2, GeoUnit unit) {
    return this.run((jedis, serializer) -> jedis.geodist(key, member1, member2, unit));
  }

  default List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit) {
    return this.run((jedis, serializer) -> jedis.georadius(key, longitude, latitude, radius, unit));
  }

  default List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
    return this.run((jedis, serializer) -> jedis.georadius(key, longitude, latitude, radius, unit, param));
  }

  default List<GeoRadiusResponse> georadiusbymember(String key, String member, double radius, GeoUnit unit) {
    return this.run((jedis, serializer) -> jedis.georadiusByMember(key, member, radius, unit));
  }

  default List<GeoRadiusResponse> georadiusbymember(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
    return this.run((jedis, serializer) -> jedis.georadiusByMember(key, member, radius, unit, param));
  }

  default List<String> geohash(String key, String... members) {
    return this.run((jedis, serializer) -> jedis.geohash(key, members));
  }

}
