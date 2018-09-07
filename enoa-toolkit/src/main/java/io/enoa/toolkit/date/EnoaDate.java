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
package io.enoa.toolkit.date;

import io.enoa.toolkit.convert.IConverter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

public class EnoaDate implements Serializable {

  private Long ts;

  private EnoaDate(Long ts) {
    this.ts = ts;
  }

  private EnoaDate(Date date) {
    this.ts = date == null ? null : date.getTime();
  }

  public static EnoaDate with(Long ts) {
    return new EnoaDate(ts);
  }

  public static EnoaDate with(Date date) {
    return new EnoaDate(date);
  }

  public Date date(Date def) {
    return this.ts == null ?
      def :
      new Date(this.ts);
  }

  public Date date() {
    return this.date(null);
  }

  public Timestamp timestamp(Timestamp def) {
    return this.ts == null ?
      def :
      new Timestamp(this.ts);
  }

  public Timestamp timestamp() {
    return this.timestamp(null);
  }

  public LocalDateTime localdatetime(LocalDateTime def) {
    return this.ts == null ?
      def :
      LocalDateTime.ofInstant(Instant.ofEpochMilli(this.ts),
        TimeZone.getDefault().toZoneId());
  }

  public LocalDateTime localdatetime() {
    return this.localdatetime(null);
  }

  public Instant instant(Instant def) {
    return this.ts == null ?
      def :
      Instant.ofEpochMilli(this.ts);
  }

  public Instant instant() {
    return this.instant(null);
  }

  public LocalDate localdate(LocalDate def) {
    return this.ts == null ?
      def :
      this.localdatetime().toLocalDate();
  }

  public LocalDate localdate() {
    return this.localdate(null);
  }

  public <R> R to(IConverter<R, Long> converter) {
    return converter.convert(this.ts);
  }

}
