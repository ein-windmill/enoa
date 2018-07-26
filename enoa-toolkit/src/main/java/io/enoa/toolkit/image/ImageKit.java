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
package io.enoa.toolkit.image;

import io.enoa.toolkit.digest.base.BaseKit;
import io.enoa.toolkit.file.FileKit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageKit {
  private ImageKit() {
  }

  /**
   * 生成形如data:image/jpeg;base64,iVBORw0KGgoA……的字符串，将图片文件Data URI化
   *
   * @param path 图片文件路径
   * @return 形如data:image/jpeg;base64,iVBORw0KGgoA……的字符串
   * @throws IOException
   */
  public static String datauri(String path) throws IOException {
    return datauri(Paths.get(path));
  }

  /**
   * 生成形如data:image/jpeg;base64,iVBORw0KGgoA……的字符串，将图片文件Data URI化
   *
   * @param path 图片文件对象
   * @return 形如data:image/jpeg;base64,iVBORw0KGgoA……的字符串
   * @throws IOException
   */
  public static String datauri(Path path) throws IOException {
    String type = FileKit.extension(path).toLowerCase();
    if ("jpg".equals(type)) {
      type = "jpeg";
    }
    return "data:image/" + type + ";base64," + base64(path);
  }

  /**
   * 将文件编码成base64格式
   *
   * @param path 图片文件路径
   * @return base64编码格式的字符串
   * @throws IOException
   */
  public static String base64(String path) throws IOException {
    return base64(Paths.get(path));
  }

  /**
   * 将文件编码成base64格式
   *
   * @param path 图片文件对象
   * @return base64编码格式的字符串
   * @throws IOException
   */
  public static String base64(Path path) throws IOException {
    BufferedImage bi = ImageIO.read(path.toFile());
    String type = FileKit.extension(path).toLowerCase();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bi, type, baos);
    return BaseKit.ebase64(baos.toByteArray());
  }

}
