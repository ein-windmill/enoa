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
package io.enoa.trydb.convert;

import io.enoa.toolkit.convert.IConverter;
import io.enoa.toolkit.stream.StreamKit;
import io.enoa.trydb.thr.TrydbException;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

public class _ClobConverter implements IConverter<String, Clob> {

  private static class Holder {
    private static final _ClobConverter instance = new _ClobConverter();
  }

  private _ClobConverter() {
  }

  public static final _ClobConverter instance() {
    return _ClobConverter.Holder.instance;
  }

  @Override
  public String convert(Clob origin) {
    if (origin == null)
      return null;

    Reader reader = null;
    try {
      reader = origin.getCharacterStream();
      if (reader == null)
        return null;
      char[] buffer = new char[(int) origin.length()];
      if (buffer.length == 0)
        return null;
      reader.read(buffer);
      return new String(buffer);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    } catch (SQLException e) {
      throw new TrydbException(e.getMessage(), e);
    } finally {
      StreamKit.close(reader);
    }
  }
}
