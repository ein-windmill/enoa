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
package io.enoa.yosart.ext.assets;

import io.enoa.log.kit.LogKit;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.Response;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.mark.IMarkVal;
import io.enoa.toolkit.text.TextKit;
import io.enoa.yosart.Oysart;
import io.enoa.yosart.kernel.data.YdAssets;
import io.enoa.yosart.kernel.ext.YmAssetsExt;
import io.enoa.yosart.resp.Renderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EyAssetsExt implements YmAssetsExt {


  private static final Map<String, ICON> ICON_CACHE;

  static {
    ICON_CACHE = new HashMap<>();
    String[] files = {"txt", "html", "css", "js", "java", "cs", "json", "yml", "yaml", "xml", "pdf", "doc",
      "docx", "xls", "xlsx", "ppt", "pptx", "md", "jscsrc", "scss", "gitignore", "gitignore", "editorconfig",
      "jshintrc", "log", "properties", "ini"};
    for (String file : files) {
      ICON_CACHE.put(file, ICON.FILE);
    }
    String[] pics = {"jpg", "jpeg", "png", "pneg", "svg", "bmp", "gif"};
    for (String pic : pics) {
      ICON_CACHE.put(pic, ICON.IMAGE);
    }

  }

  private String basePath;


  @Override
  public String name() {
    return "EyAssetsExt";
  }

  @Override
  public String version() {
    return "1";
  }

  @Override
  public double weight() {
    return 0;
  }

  @Override
  public Response handle(String action, Request request) {
    Renderer renderer = Renderer.with(request);
    try {
      YdAssets assets = Oysart.assets();
      Path path = "/".equals(action) ? assets.path() : this.path(action.substring(1, action.length()));

      boolean isDir = Files.isDirectory(path);
      if (isDir && assets.showTree())
        return this.renderDir(action, request, path);
      boolean isFile = Files.isRegularFile(path);
      if (isFile)
        return this.renderFile(action, request, path);

      if (Oysart.config().debug()) {
        String accessPath = Paths.get(assets.path().toString().concat(action)).normalize().toString();
        renderer.attr("msg", EnoaTipKit.message("eo.tip.yosart.ext_assets_file_404", accessPath));
      }

      return renderer.renderError(HttpStatus.NOT_FOUND).end();
    } catch (Exception e) {
      LogKit.error(e.getMessage(), e);
      return renderer.renderError(HttpStatus.INTERNAL_ERROR, e).end();
    }
  }

  private Path path(String subpath) {
    this.basePath = this.basePath == null ? Oysart.assets().path().toString() : this.basePath;
    return Paths.get(this.basePath, subpath);
  }

  private Response renderDir(String action, Request request, Path path) throws IOException {
    String tpl = this.dirTpl();
    StringBuilder body = new StringBuilder();

    if (!Oysart.assets().uri().concat("/".equals(action) ? "" : action).equals(Oysart.assets().uri()))
      body.append(String.format("<li><a href=\"%s\"><span>%s</span><span>../</span></a>",
        this.uri(request.context(), action, "../"), ICON.BACK.val()));

    Files.list(path).forEach(p -> body.append(String.format("<li><span>%s</span><span><a href=\"%s\">%s</a></span>",
      this.chooseIcon(p).val(), this.uri(request.context(), action, p.getFileName().toString()), p.getFileName().toString())));
    return Renderer.with(request).renderHtml(String.format(tpl, request.uri(), body.toString())).end();
  }

  private Response renderFile(String action, Request request, Path path) throws IOException {
    LogKit.debug(this.chooseContentType(path));
    return Renderer.with(request).renderFile(path, path.getFileName().toString(), this.chooseContentType(path)).end();
  }

  private String dirTpl() {
    return "<!doctype html>\n" +
      "<html>\n" +
      "<head>\n" +
      "  <meta charset=\"UTF-8\">\n" +
      "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
      "  <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
      "  <title>%s</title>\n" +
      "</head>\n" +
      "<body>\n" +
      "<ul style=\"list-style: none; padding: 0;\">%s</ul>\n" +
      "</body>\n" +
      "</html>";
  }

  private enum ICON implements IMarkVal {
    UNKNOWN("<svg fill=\"#000000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
      "    <circle cx=\"12\" cy=\"19\" r=\"2\"/>\n" +
      "    <path d=\"M10 3h4v12h-4z\"/>\n" +
      "    <path d=\"M0 0h24v24H0z\" fill=\"none\"/>\n" +
      "</svg>"),
    BACK("<svg fill=\"#000000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
      "    <path d=\"M0 0h24v24H0z\" fill=\"none\"/>\n" +
      "    <path d=\"M21 11H6.83l3.58-3.59L9 6l-6 6 6 6 1.41-1.41L6.83 13H21z\"/>\n" +
      "</svg>"),
    FOLDER("<svg fill=\"#000000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
      "    <path d=\"M10 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2h-8l-2-2z\"/>\n" +
      "    <path d=\"M0 0h24v24H0z\" fill=\"none\"/>\n" +
      "</svg>"),
    FILE("<svg fill=\"#000000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
      "    <path d=\"M6 2c-1.1 0-1.99.9-1.99 2L4 20c0 1.1.89 2 1.99 2H18c1.1 0 2-.9 2-2V8l-6-6H6zm7 7V3.5L18.5 9H13z\"/>\n" +
      "    <path d=\"M0 0h24v24H0z\" fill=\"none\"/>\n" +
      "</svg>"),
    IMAGE("<svg fill=\"#000000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
      "    <path d=\"M21 19V5c0-1.1-.9-2-2-2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2zM8.5 13.5l2.5 3.01L14.5 12l4.5 6H5l3.5-4.5z\"/>\n" +
      "    <path d=\"M0 0h24v24H0z\" fill=\"none\"/>\n" +
      "</svg>"),
    //
    ;
    private final String val;

    ICON(String val) {
      this.val = val;
    }

    public String val() {
      return val;
    }
  }


  private ICON chooseIcon(Path path) {
    if (Files.isDirectory(path))
      return ICON.FOLDER;
    if (!Files.isRegularFile(path))
      return ICON.UNKNOWN;

    String filename = path.toString();
    int dotIdx = filename.lastIndexOf('.');
    String suffix = (dotIdx == -1) ? "" : filename.substring(dotIdx + 1);
    ICON icon = ICON_CACHE.get(suffix.toLowerCase());
    return icon == null ? ICON.UNKNOWN : icon;
  }

  private String chooseContentType(Path path) {
    String contentType = FileKit.probeContentType(path);
    return TextKit.isBlank(contentType) ? "application/octet-stream" : contentType;
  }

  private String uri(String context, String action, String filename) {
    return context.concat(Oysart.assets().uri()).concat(action).concat(filename);
  }
}
