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
package io.enoa.nosql.redis;

import redis.clients.jedis.*;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;
import redis.clients.util.Slowlog;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Redis {

  private Redis() {
  }

  public static EnoaRedis use(String name) {
    if (EMgrRedis.POOL == null || EMgrRedis.POOL.isEmpty())
      throw new RuntimeException("Please do start redis. `EMgrRedis.start()`");
    EnoaRedis redis = EMgrRedis.POOL.get(name);
    if (redis == null)
      throw new NullPointerException("This redis client name is not exists. => " + name);
    return redis;
  }

  public static EnoaRedis use() {
    return use("main");
  }

  public static Jedis jedis() {
    return use().jedis();
  }

  public static <T> T run(EoRedisRunner caller) {
    return use().run(caller);
  }

  public static String auth(String password) {
    return use().auth(password);
  }

  public static String echo(Object object) {
    return use().echo(object);
  }

  public static String ping() {
    return use().ping();
  }

  public static String quit() {
    return use().quit();
  }

  public static String select(int index) {
    return use().select(index);
  }

  public static Long geoadd(String key, double longitude, double latitude, String member) {
    return use().geoadd(key, longitude, latitude, member);
  }

  public static Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
    return use().geoadd(key, memberCoordinateMap);
  }

  public static List<GeoCoordinate> geopos(String key, String... members) {
    return use().geopos(key, members);
  }

  public static Double geodist(String key, String member1, String member2) {
    return use().geodist(key, member1, member2);
  }

  public static Double geodist(String key, String member1, String member2, GeoUnit unit) {
    return use().geodist(key, member1, member2, unit);
  }

  public static List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit) {
    return use().georadius(key, longitude, latitude, radius, unit);
  }

  public static List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
    return use().georadius(key, longitude, latitude, radius, unit, param);
  }

  public static List<GeoRadiusResponse> georadiusbymember(String key, String member, double radius, GeoUnit unit) {
    return use().georadiusbymember(key, member, radius, unit);
  }

  public static List<GeoRadiusResponse> georadiusbymember(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
    return use().georadiusbymember(key, member, radius, unit, param);
  }

  public static List<String> geohash(String key, String... members) {
    return use().geohash(key, members);
  }

  public static Long hdel(String key, String... field) {
    return use().hdel(key, field);
  }

  public static Boolean hexists(String key, String field) {
    return use().hexists(key, field);
  }

  public static <T> T hget(String key, String field) {
    return use().hget(key, field);
  }

  public static <T> Map<String, T> hgetall(String key) {
    return use().hgetall(key);
  }

  public static Long hincrby(String key, String field, long value) {
    return use().hincrby(key, field, value);
  }

  public static Double hincrbyfloat(String key, String field, double value) {
    return use().hincrbyfloat(key, field, value);
  }

  public static Set<String> hkeys(String key) {
    return use().hkeys(key);
  }

  public static Long hlen(String key) {
    return use().hlen(key);
  }

  public static <T> List<T> hmget(String key, String... fields) {
    return use().hmget(key, fields);
  }

  public static String hmset(String key, Map<String, Object> hash) {
    return use().hmset(key, hash);
  }

  public static Long hset(String key, String field, Object value) {
    return use().hset(key, field, value);
  }

  public static Long hsetnx(String key, String field, Object value) {
    return use().hsetnx(key, field, value);
  }

  public static <T> List<T> hvals(String key) {
    return use().hvals(key);
  }

  public static <T> ScanResult<Map.Entry<String, T>> hscan(String key, String cursor) {
    return use().hscan(key, cursor);
  }

  public static <T> ScanResult<Map.Entry<String, T>> hscan(String key, String cursor, ScanParams params) {
    return use().hscan(key, cursor, params);
  }

  public static Long pfadd(String key, Object... elements) {
    return use().pfadd(key, elements);
  }

  public static long pfcount(String... key) {
    return use().pfcount(key);
  }

  public static long pfcount(String key) {
    return use().pfcount(key);
  }

  public static String pfmerge(String destkey, String... sourcekeys) {
    return use().pfmerge(destkey, sourcekeys);
  }

  public static Long del(String key) {
    return use().del(key);
  }

  public static Long del(String... keys) {
    return use().del(keys);
  }

  public static byte[] dump(String key) {
    return use().dump(key);
  }

  public static Boolean exists(String key) {
    return use().exists(key);
  }

  public static Long exists(String... keys) {
    return use().exists(keys);
  }

  public static Long expire(String key, int seconds) {
    return use().expire(key, seconds);
  }

  public static Long expireat(String key, long unixTime) {
    return use().expireat(key, unixTime);
  }

  public static Set<String> keys(String pattern) {
    return use().keys(pattern);
  }

  public static String migrate(String host, int port, String key, int destinationDb, int timeout) {
    return use().migrate(host, port, key, destinationDb, timeout);
  }

  public static Long move(String key, int dbIndex) {
    return use().move(key, dbIndex);
  }

  public static String objectencoding(String string) {
    return use().objectencoding(string);
  }

  public static Long objectidletime(String string) {
    return use().objectidletime(string);
  }

  public static Long objectrefcount(String string) {
    return use().objectrefcount(string);
  }

  public static Long persist(String key) {
    return use().persist(key);
  }

  public static Long pexpire(String key, long milliseconds) {
    return use().pexpire(key, milliseconds);
  }

  public static Long pexpireat(String key, long millisecondsTimestamp) {
    return use().pexpireat(key, millisecondsTimestamp);
  }

  public static Long pttl(String key) {
    return use().pttl(key);
  }

  public static String randomkey() {
    return use().randomkey();
  }

  public static String rename(String oldkey, String newkey) {
    return use().rename(oldkey, newkey);
  }

  public static Long renamenx(String oldkey, String newkey) {
    return use().renamenx(oldkey, newkey);
  }

  public static String restore(String key, int ttl, byte[] serializedValue) {
    return use().restore(key, ttl, serializedValue);
  }

  public static <T> List<T> sort(String key) {
    return use().sort(key);
  }

  public static <T> List<T> sort(String key, SortingParams sortingParameters) {
    return use().sort(key, sortingParameters);
  }

  public static Long sort(String key, SortingParams sortingParameters, String dstkey) {
    return use().sort(key, sortingParameters, dstkey);
  }

  public static Long sort(String key, String dstkey) {
    return use().sort(key, dstkey);
  }

  public static Long ttl(String key) {
    return use().ttl(key);
  }

  public static String type(String key) {
    return use().type(key);
  }

  public static <T> ScanResult<T> scan(String cursor) {
    return use().scan(cursor);
  }

  public static <T> ScanResult<T> scan(String cursor, ScanParams params) {
    return use().scan(cursor, params);
  }

  public static <T> List<T> blpop(int timeout, String... keys) {
    return use().blpop(timeout, keys);
  }

  public static <T> List<T> blpop(int timeout, String key) {
    return use().blpop(timeout, key);
  }

  public static <T> List<T> blpop(String... args) {
    return use().blpop(args);
  }

  public static <T> List<T> brpop(int timeout, String... keys) {
    return use().brpop(timeout, keys);
  }

  public static <T> List<T> brpop(int timeout, String key) {
    return use().brpop(timeout, key);
  }

  public static <T> List<T> brpop(String... args) {
    return use().brpop(args);
  }

  public static <T> T brpoplpush(String source, String destination, int timeout) {
    return use().brpoplpush(source, destination, timeout);
  }

  public static String lindex(String key, long index) {
    return use().lindex(key, index);
  }

  public static Long linsert(String key, BinaryClient.LIST_POSITION where, Object pivot, Object value) {
    return use().linsert(key, where, pivot, value);
  }

  public static Long llen(String key) {
    return use().llen(key);
  }

  public static <T> T lpop(String key) {
    return use().lpop(key);
  }

  public static Long lpush(String key, Object... values) {
    return use().lpush(key, values);
  }

  public static Long lpushx(String key, Object... values) {
    return use().lpushx(key, values);
  }

  public static <T> List<T> lrange(String key, long start, long end) {
    return use().lrange(key, start, end);
  }

  public static Long lrem(String key, long count, Object value) {
    return use().lrem(key, count, value);
  }

  public static String lset(String key, long index, Object value) {
    return use().lset(key, index, value);
  }

  public static String ltrim(String key, long start, long end) {
    return use().ltrim(key, start, end);
  }

  public static <T> T rpop(String key) {
    return use().rpop(key);
  }

  public static <T> T rpoplpush(String srckey, String dstkey) {
    return use().rpoplpush(srckey, dstkey);
  }

  public static Long rpush(String key, Object... values) {
    return use().rpush(key, values);
  }

  public static Long rpushx(String key, Object... values) {
    return use().rpushx(key, values);
  }

  public static List<String> pubsubchannels(String pattern) {
    return use().pubsubchannels(pattern);
  }

  public static Long pubsubnumpat() {
    return use().pubsubnumpat();
  }

  public static Map<String, String> pubsubnumsub(String... channels) {
    return use().pubsubnumsub(channels);
  }

  public static Long publish(String channel, Object message) {
    return use().publish(channel, message);
  }

  public static void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
    use().psubscribe(jedisPubSub, patterns);
  }

  public static void subscribe(JedisPubSub jedisPubSub, String... channels) {
    use().subscribe(jedisPubSub, channels);
  }

  public static Object eval(String script, int keyCount, String... params) {
    return use().eval(script, keyCount, params);
  }

  public static Object eval(String script, List<String> keys, List<String> args) {
    return use().eval(script, keys, args);
  }

  public static Object eval(String script) {
    return use().eval(script);
  }

  public static Object evalsha(String script) {
    return use().evalsha(script);
  }

  public static Object evalsha(String sha1, List<String> keys, List<String> args) {
    return use().evalsha(sha1, keys, args);
  }

  public static Object evalsha(String sha1, int keyCount, String... params) {
    return use().evalsha(sha1, keyCount, params);
  }

  public static Boolean scriptexists(String sha1) {
    return use().scriptexists(sha1);
  }

  public static List<Boolean> scriptexists(String... sha1) {
    return use().scriptexists(sha1);
  }

  public static String scriptflush() {
    return use().scriptflush();
  }

  public static String scriptkill() {
    return use().scriptkill();
  }

  public static String scriptload(String script) {
    return use().scriptload(script);
  }

  public static String bgrewriteaof() {
    return use().bgrewriteaof();
  }

  public static String bgsave() {
    return use().bgsave();
  }

  public static String clientgetname() {
    return use().clientgetname();
  }

  public static String clientkill(String client) {
    return use().clientkill(client);
  }

  public static String clientlist() {
    return use().clientlist();
  }

  public static String clientsetname(String name) {
    return use().clientsetname(name);
  }

  public static List<String> configget(String pattern) {
    return use().configget(pattern);
  }

  public static String configresetstat() {
    return use().configresetstat();
  }

  public static String configset(String parameter, String value) {
    return use().configset(parameter, value);
  }

  public static Long dbsize() {
    return use().dbsize();
  }

  public static String debug(DebugParams params) {
    return use().debug(params);
  }

  public static String flushall() {
    return use().flushall();
  }

  public static String flushdb() {
    return use().flushdb();
  }

  public static String info() {
    return use().info();
  }

  public static Long lastsave() {
    return use().lastsave();
  }

  public static void monitor(JedisMonitor jedisMonitor) {
    use().monitor(jedisMonitor);
  }

  public static String save() {
    return use().save();
  }

  public static String shutdown() {
    return use().shutdown();
  }

  public static String slaveof(String host, int port) {
    return use().slaveof(host, port);
  }

  public static List<Slowlog> slowlogget() {
    return use().slowlogget();
  }

  public static Long slowloglen() {
    return use().slowloglen();
  }

  public static String slowlogreset() {
    return use().slowlogreset();
  }

  public static void sync() {
    use().sync();
  }

  public static List<String> time() {
    return use().time();
  }

  public static Long sadd(String key, Object... member) {
    return use().sadd(key, member);
  }

  public static Long scard(String key) {
    return use().scard(key);
  }

  public static <T> Set<T> sdiff(String... keys) {
    return use().sdiff(keys);
  }

  public static Long sdiffstore(String dstkey, String... keys) {
    return use().sdiffstore(dstkey, keys);
  }

  public static Boolean sismember(String key, Object member) {
    return use().sismember(key, member);
  }

  public static <T> Set<T> smembers(String key) {
    return use().smembers(key);
  }

  public static Long smove(String srckey, String dstkey, Object member) {
    return use().smove(srckey, dstkey, member);
  }

  public static <T> T spop(String key) {
    return use().spop(key);
  }

  public static <T> Set<T> spop(String key, long count) {
    return use().spop(key, count);
  }

  public static <T> T srandmember(String key) {
    return use().srandmember(key);
  }

  public static <T> List<T> srandmember(String key, int count) {
    return use().srandmember(key, count);
  }

  public static Long srem(String key, Object... member) {
    return use().srem(key, member);
  }

  public static <T> Set<T> sunion(String... keys) {
    return use().sunion(keys);
  }

  public static Long sunionstore(String dstkey, String... keys) {
    return use().sunionstore(dstkey, keys);
  }

  public static <T> ScanResult<T> sscan(String key, String cursor) {
    return use().sscan(key, cursor);
  }

  public static <T> ScanResult<T> sscan(String key, String cursor, ScanParams params) {
    return use().sscan(key, cursor, params);
  }

  public static Long zadd(String key, double score, Object member) {
    return use().zadd(key, score, member);
  }

  public static Long zadd(String key, double score, Object member, ZAddParams params) {
    return use().zadd(key, score, member, params);
  }

  public static Long zadd(String key, Map<String, Double> scoreMembers) {
    return use().zadd(key, scoreMembers);
  }

  public static Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
    return use().zadd(key, scoreMembers, params);
  }

  public static Long zcard(String key) {
    return use().zcard(key);
  }

  public static Long zcount(String key, double min, double max) {
    return use().zcount(key, min, max);
  }

  public static Long zcount(String key, Object min, Object max) {
    return use().zcount(key, min, max);
  }

  public static Double zincrby(String key, double score, Object member) {
    return use().zincrby(key, score, member);
  }

  public static Double zincrby(String key, double score, Object member, ZIncrByParams params) {
    return use().zincrby(key, score, member, params);
  }

  public static <T> Set<T> zrange(String key, long start, long end) {
    return use().zrange(key, start, end);
  }

  public static <T> Set<T> zrangebyscore(String key, double min, double max) {
    return use().zrangebyscore(key, min, max);
  }

  public static <T> Set<T> zrangebyscore(String key, T min, T max) {
    return use().zrangebyscore(key, min, max);
  }

  public static <T> Set<T> zrangebyscore(String key, double min, double max, int offset, int count) {
    return use().zrangebyscore(key, min, max, offset, count);
  }

  public static <T> Set<T> zrangebyscore(String key, T min, T max, int offset, int count) {
    return use().zrangebyscore(key, min, max, offset, count);
  }

  public static Long zrank(String key, Object member) {
    return use().zrank(key, member);
  }

  public static Long zrem(String key, Object... member) {
    return use().zrem(key, member);
  }

  public static Long zremrangebyrank(String key, long start, long end) {
    return use().zremrangebyrank(key, start, end);
  }

  public static Long zremrangebyscore(String key, double start, double end) {
    return use().zremrangebyscore(key, start, end);
  }

  public static Long zremrangebyscore(String key, Object start, Object end) {
    return use().zremrangebyscore(key, start, end);
  }

  public static <T> Set<T> zrevrange(String key, long start, long end) {
    return use().zrevrange(key, start, end);
  }

  public static <T> Set<T> zrevrangebylex(String key, T max, T min) {
    return use().zrevrangebylex(key, max, min);
  }

  public static <T> Set<T> zrevrangebylex(String key, T max, T min, int offset, int count) {
    return use().zrevrangebylex(key, max, min, offset, count);
  }

  public static <T> Set<T> zrevrangebyscore(String key, double max, double min) {
    return use().zrevrangebyscore(key, max, min);
  }

  public static <T> Set<T> zrevrangebyscore(String key, T max, T min) {
    return use().zrevrangebyscore(key, max, min);
  }

  public static <T> Set<T> zrevrangebyscore(String key, double max, double min, int offset, int count) {
    return use().zrevrangebyscore(key, max, min, offset, count);
  }

  public static <T> Set<T> zrevrangebyscore(String key, T max, T min, int offset, int count) {
    return use().zrevrangebyscore(key, max, min, offset, count);
  }

  public static Long zrevrank(String key, Object member) {
    return use().zrevrank(key, member);
  }

  public static Double zscore(String key, Object member) {
    return use().zscore(key, member);
  }

  public static Long zunionstore(String dstkey, String... sets) {
    return use().zunionstore(dstkey, sets);
  }

  public static Long zunionstore(String dstkey, ZParams params, String... sets) {
    return use().zunionstore(dstkey, params, sets);
  }

  public static Long zinterstore(String dstkey, String... sets) {
    return use().zinterstore(dstkey, sets);
  }

  public static Long zinterstore(String dstkey, ZParams params, String... sets) {
    return use().zinterstore(dstkey, params, sets);
  }

  public static ScanResult<Tuple> zscan(String key, String cursor) {
    return use().zscan(key, cursor);
  }

  public static ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
    return use().zscan(key, cursor, params);
  }

  public static <T> Set<T> zrangebylex(String key, T min, T max) {
    return use().zrangebylex(key, min, max);
  }

  public static <T> Set<T> zrangebylex(String key, T min, T max, int offset, int count) {
    return use().zrangebylex(key, min, max, offset, count);
  }

  public static Long zlexcount(String key, Object min, Object max) {
    return use().zlexcount(key, min, max);
  }

  public static Long zremrangebylex(String key, Object min, Object max) {
    return use().zremrangebylex(key, min, max);
  }

  public static Long append(String key, String value) {
    return use().append(key, value);
  }

  public static Long bitcount(String key) {
    return use().bitcount(key);
  }

  public static Long bitcount(String key, long start, long end) {
    return use().bitcount(key, start, end);
  }

  public static Long bitop(BitOP op, String destKey, String... srcKeys) {
    return use().bitop(op, destKey, srcKeys);
  }

  public static List<Long> bitfield(String key, String... arguments) {
    return use().bitfield(key, arguments);
  }

  public static Long decr(String key) {
    return use().decr(key);
  }

  public static Long decrby(String key, long integer) {
    return use().decrby(key, integer);
  }

  public static String get(String key) {
    return use().get(key);
  }

  public static Boolean getbit(String key, long offset) {
    return use().getbit(key, offset);
  }

  public static String getrange(String key, long startOffset, long endOffset) {
    return use().getrange(key, startOffset, endOffset);
  }

  public static String getset(String key, String value) {
    return use().getset(key, value);
  }

  public static Long incr(String key) {
    return use().incr(key);
  }

  public static Long incrby(String key, long integer) {
    return use().incrby(key, integer);
  }

  public static Double incrbyfloat(String key, double value) {
    return use().incrbyfloat(key, value);
  }

  public static List<String> mget(String... keys) {
    return use().mget(keys);
  }

  public static String mset(String... keysvalues) {
    return use().mset(keysvalues);
  }

  public static Long msetnx(String... keysvalues) {
    return use().msetnx(keysvalues);
  }

  public static String psetex(String key, long milliseconds, String value) {
    return use().psetex(key, milliseconds, value);
  }

  public static String set(String key, String value) {
    return use().set(key, value);
  }

  public static String set(String key, String value, String nxxx) {
    return use().set(key, value, nxxx);
  }

  public static String set(String key, String value, String nxxx, String expx, int time) {
    return use().set(key, value, nxxx, expx, time);
  }

  public static Boolean setbit(String key, long offset, String value) {
    return use().setbit(key, offset, value);
  }

  public static String setex(String key, int seconds, String value) {
    return use().setex(key, seconds, value);
  }

  public static Long setnx(String key, String value) {
    return use().setnx(key, value);
  }

  public static Long setrange(String key, long offset, String value) {
    return use().setrange(key, offset, value);
  }

  public static Long strlen(String key) {
    return use().strlen(key);
  }

  public static Transaction multi() {
    return use().multi();
  }

  public static String unwatch() {
    return use().unwatch();
  }

  public static String watch(String... keys) {
    return use().watch(keys);
  }
}
