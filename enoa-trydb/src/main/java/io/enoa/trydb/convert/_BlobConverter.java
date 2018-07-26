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
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

public class _BlobConverter implements IConverter<byte[], Blob> {

  private static class Holder {
    private static final _BlobConverter instance = new _BlobConverter();
  }

  private _BlobConverter() {
  }

  public static final _BlobConverter instance() {
    return Holder.instance;
  }

  @Override
  public byte[] convert(Blob origin) {
    if (origin == null)
      return null;

    InputStream is = null;
    try {
      is = origin.getBinaryStream();
      if (is == null)
        return null;
      // byte[] data = new byte[is.available()];
      byte[] data = new byte[(int) origin.length()];
      if (data.length == 0)
        return null;
      is.read(data);
      return data;
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    } catch (SQLException e) {
      throw new TrydbException(e.getMessage(), e);
    } finally {
      StreamKit.close(is);
    }
  }
}
