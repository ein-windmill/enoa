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
package io.enoa.toolkit.file;

import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.thr.EoException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileKit {

  private FileKit() {
  }

  /*
  http://tool.oschina.net/commons
   */
  private static final Map<String, String> MIMES = new HashMap<String, String>() {{

    put("3gp", "video/3gpp");
    put("asf", "video/x-ms-asf");
    put("avi", "video/x-msvideo");
    put("m4u", "video/vnd.mpegurl");
    put("m4v", "video/x-m4v");
    put("mov", "video/quicktime");

    put("mp4", "video/mp4");
    put("mpg4", "video/mp4");
    put("mpe", "video/x-mpeg");
    put("mpeg", "video/mpg");
    put("mpg", "video/mpg");
    put("m3u", "audio/x-mpegurl");
    put("m4a", "audio/mp4a-latm");
    put("m4b", "audio/mp4a-latm");
    put("m4p", "audio/mp4a-latm");

    put("mp2", "x-mpeg");
    put("mp3", "audio/x-mpeg");
    put("mpga", "audio/mpeg");
    put("ogg", "audio/ogg");
    put("rmvb", "audio/x-pn-realaudio");
    put("wav", "audio/x-wav");
    put("wma", "audio/x-ms-wma");
    put("wmv", "audio/x-ms-wmv");

    put("c", "text/plain");
    put("java", "text/plain");
    put("conf", "text/plain");
    put("cpp", "text/plain");
    put("h", "text/plain");
    put("prop", "text/plain");
    put("props", "text/plain");
    put("rc", "text/plain");
    put("sh", "text/plain");
    put("log", "text/plain");
    put("txt", "text/plain");
    put("xml", "text/plain");
    put("md", "text/plain");
    put("scss", "text/plain");
    put("jscsrc", "text/plain");
    put("jshintrc", "text/plain");
    put("gitignore", "text/plain");
    put("editorconfig", "text/plain");
    put("properties", "text/plain");

    put("html", "text/html");
    put("htm", "text/html");
    put("css", "text/css");

    put("jpg", "image/jpeg");
    put("jpeg", "image/jpeg");
    put("bmp", "image/bmp");
    put("gif", "image/gif");
    put("png", "image/png");
    put("pneg", "image/png");
    put("svg", "image/svg+xml");

    put("", "application/octet-stream");
    put("json", "application/json");
    put("yml", "application/x-yaml");
    put("yaml", "application/x-yaml");
    put("bin", "application/octet-stream");
    put("exe", "application/octet-stream");
    put("class", "application/octet-stream");
    put("apk", "application/vnd.android.package-archive");
    put("doc", "application/msword");
    put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    put("xls", "application/vnd.ms-excel");
    put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    put("gtar", "application/x-gtar");
    put("gz", "application/x-gzip");
    put("jar", "application/java-archive");
    put("js", "application/x-javascript");
    put("mpc", "application/vnd.mpohun.certificate");
    put("msg", "application/vnd.ms-outlook");
    put("pdf", "application/pdf");

    put("pps", "application/vnd.ms-powerpoint");
    put("ppt", "application/vnd.ms-powerpoint");
    put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
    put("rtf", "application/rtf");
    put("tar", "application/x-tar");
    put("tgz", "application/x-compressed");
    put("wps", "application/vnd.ms-works");
    put("z", "application/x-compress");
    put("zip", "application/x-zip-compressed");
  }};


  public static boolean delete(String file) {
    return delete(new File(file));
  }

  public static boolean delete(Path path) {
    return delete(path.toFile());
  }

  public static boolean delete(File file) {
    try {
      File[] fs = file.listFiles();
      if (fs != null) {
        for (File _f : fs) {
          delete(_f);
        }
      }
      return file.delete();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static Path mkdirs(String path) {
    return mkdirs(Paths.get(path));
  }

  public static Path mkdirs(Path path) {
    if (Files.exists(path))
      return path;
    try {
      return Files.createDirectories(path);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static String extension(String fileName) {
    int dotIdx = fileName.lastIndexOf('.');
    return (dotIdx == -1) ? "" : fileName.substring(dotIdx + 1);
  }

  public static String extension(Path path) {
    return extension(path.getFileName().toString());
  }

  public static boolean exists(String path) {
    return exists(Paths.get(path));
  }

  public static boolean exists(Path file) {
    return Files.exists(file);
  }

  public static void write(Path path, ByteBuffer bytes) {
    if (path == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.write_not_file", "path == null"));
    if (Files.exists(path) && Files.isDirectory(path))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.write_not_file", path.toString()));

    if (!Files.exists(path.getParent()))
      mkdirs(path.getParent());

    try (FileOutputStream out = new FileOutputStream(path.toFile());
         FileChannel channel = out.getChannel()) {
      channel.write(bytes);
    } catch (IOException e) {
      throw new EoException(e.getMessage(), e);
    }
  }

  public static EnoaBinary read(Path path) {
    return read(path, EoConst.CHARSET);
  }

  public static EnoaBinary read(Path path, Charset charset) {
    try (RandomAccessFile raf = new RandomAccessFile(path.toFile(), "r");
         FileChannel channel = raf.getChannel();
         ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

      ByteBuffer buffer = ByteBuffer.allocate(1024);
      while (channel.read(buffer) != -1) {
        baos.write(buffer.array(), 0, buffer.position());
        buffer.clear();
      }
//      ByteBuffer wrap = ByteBuffer.wrap(baos.toByteArray());
      return EnoaBinary.create(baos.toByteArray(), charset);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static Path move(Path from, Path to) {
    try {
      return Files.move(from, to);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String probeContentType(Path path) {
    if (!Files.isRegularFile(path))
      throw new RuntimeException(EnoaTipKit.message("eo.tip.toolkit.path_not_file", path.toString()));
    String contentType;
    try {
      contentType = Files.probeContentType(path);
    } catch (IOException e) {
      contentType = null;
    }
    if (contentType != null)
      return contentType;

    String suffix = extension(path.toString());
    contentType = MIMES.get(suffix);
    return contentType;
  }

}
