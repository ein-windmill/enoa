package io.enoa.http.provider.httphelper;

import io.enoa.http.EoExecutor;
import io.enoa.http.EoUrl;
import io.enoa.http.HttpAuth;
import io.enoa.http.protocol.*;
import io.enoa.http.protocol.enoa.IHttpHandler;
import io.enoa.http.protocol.enoa.IHttpReporter;
import io.enoa.http.provider.httphelper.conn.ssl.TLSv;
import io.enoa.http.proxy.HttpProxy;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

public class EnoaRequestData implements HttpRequestData {

  private HttpMethod method;
  private Charset charset;
  private EoUrl url;
  private boolean traditional;
  private boolean encode;
  private String raw;
  private String contentType;
  private HttpProxy proxy;
  private HttpAuth auth;
  private byte[] binary;
  private Set<HttpCookie> cookies;
  private Set<HttpPara> paras;
  private Set<HttpHeader> headers;
  private List<HttpFormData> formDatas;
  private HttpHelperConfig config;
  private EoExecutor executor;

  private TLSv tlsv;

  private List<IHttpHandler> handlers;
  private List<IHttpReporter> reporters;

  public HttpMethod getMethod() {
    return method;
  }

  public void setMethod(HttpMethod method) {
    this.method = method;
  }

  public Charset getCharset() {
    return charset;
  }

  public void setCharset(Charset charset) {
    this.charset = charset;
  }

  public EoUrl getUrl() {
    return url;
  }

  public void setUrl(EoUrl url) {
    this.url = url;
  }

  public boolean isTraditional() {
    return traditional;
  }

  public void setTraditional(boolean traditional) {
    this.traditional = traditional;
  }

  public boolean isEncode() {
    return encode;
  }

  public void setEncode(boolean encode) {
    this.encode = encode;
  }

  public String getRaw() {
    return raw;
  }

  public void setRaw(String raw) {
    this.raw = raw;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public HttpProxy getProxy() {
    return proxy;
  }

  public void setProxy(HttpProxy proxy) {
    this.proxy = proxy;
  }

  public HttpAuth getAuth() {
    return auth;
  }

  public void setAuth(HttpAuth auth) {
    this.auth = auth;
  }

  public byte[] getBinary() {
    return binary;
  }

  public void setBinary(byte[] binary) {
    this.binary = binary;
  }

  public Set<HttpCookie> getCookies() {
    return cookies;
  }

  public void setCookies(Set<HttpCookie> cookies) {
    this.cookies = cookies;
  }

  public Set<HttpPara> getParas() {
    return paras;
  }

  public void setParas(Set<HttpPara> paras) {
    this.paras = paras;
  }

  public Set<HttpHeader> getHeaders() {
    return headers;
  }

  public void setHeaders(Set<HttpHeader> headers) {
    this.headers = headers;
  }

  public List<HttpFormData> getFormDatas() {
    return formDatas;
  }

  public void setFormDatas(List<HttpFormData> formDatas) {
    this.formDatas = formDatas;
  }

  public HttpHelperConfig getConfig() {
    return config;
  }

  public void setConfig(HttpHelperConfig config) {
    this.config = config;
  }

  public EoExecutor getExecutor() {
    return executor;
  }

  public void setExecutor(EoExecutor executor) {
    this.executor = executor;
  }

  public TLSv getTlsv() {
    return tlsv;
  }

  public void setTlsv(TLSv tlsv) {
    this.tlsv = tlsv;
  }

  public List<IHttpHandler> getHandlers() {
    return handlers;
  }

  public void setHandlers(List<IHttpHandler> handlers) {
    this.handlers = handlers;
  }

  public List<IHttpReporter> getReporters() {
    return reporters;
  }

  public void setReporters(List<IHttpReporter> reporters) {
    this.reporters = reporters;
  }
}
