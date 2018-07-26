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
package io.enoa.serialization;

import io.enoa.serialization.provider.jdk.JdkSerializeProvider;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.sys.EnvKit;
import org.junit.Test;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EPMSerializationTest {

  private Map<String, Object> data() {
    Map<String, Object> ret = new HashMap<>();
    Map<String, Object> a0 = new HashMap<>();

    a0.put("a0", "b0");
    a0.put("a1", "b1");
    a0.put("a2", "b2");

    ret.put("a", a0);
    ret.put("b", 1);
    ret.put("c", 8L);
    return ret;
  }

  @Test
  public void testSerialize() throws Exception {
//    EMgrSerialization.defSerializationFactory(new JdkSerializeProvider());
//    EoSerializer serializer = EMgrSerialization.serialization().serializer();

    Serializer.epm().install(new JdkSerializeProvider());
    EoSerializer serializer = Serializer.epm().serializer();

    byte[] serialize = serializer.serialize(this.data());

    Path sfile = Paths.get(EnvKit.string("java.io.tmpdir"), "serializefile");
    System.out.println(sfile.toString());
    FileKit.write(sfile, ByteBuffer.wrap(serialize));


    FileChannel channel;
    FileInputStream fis = new FileInputStream(sfile.toFile());
    channel = fis.getChannel();
    ByteBuffer bbuf = ByteBuffer.allocate(1024);
    while (channel.read(bbuf) != -1) {
      bbuf.clear();
    }
    byte[] radarr = bbuf.array();
    Map reduction = serializer.reduction(radarr);
    System.out.println(reduction);


  }

}
