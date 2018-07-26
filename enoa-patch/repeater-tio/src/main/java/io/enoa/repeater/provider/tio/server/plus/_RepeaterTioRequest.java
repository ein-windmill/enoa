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
package io.enoa.repeater.provider.tio.server.plus;

import io.enoa.repeater.provider._RepeaterPlusRequest;
import org.tio.core.ChannelContext;
import org.tio.core.Node;
import org.tio.core.intf.Packet;
import org.tio.core.intf.PacketListener;
import org.tio.http.common.*;
import org.tio.http.common.session.HttpSession;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class _RepeaterTioRequest extends HttpRequest implements _RepeaterPlusRequest<_RepeaterTioRequest> {

  private HttpRequest request;
  private Map<String, Object> attribute = new ConcurrentHashMap<>();

  protected _RepeaterTioRequest(HttpRequest request) {
    this(request.getRemote());
    this.request = request;
  }

  /**
   * @param remote
   * @author tanyaowu
   * 2017年2月22日 下午4:14:40
   */
  private _RepeaterTioRequest(Node remote) {
    super(remote);
  }

  @Override
  public Object originRequest() {
    return this.request;
  }

  @Override
  public <T> T attr(String name) {
    return (T) this.attribute.get(name);
  }

  @Override
  public <T> _RepeaterTioRequest attr(String name, T data) {
    this.attribute.put(name, data);
    return this;
  }

  @Override
  public _RepeaterTioRequest rmAttr(String name) {
    this.attribute.remove(name);
    return this;
  }

  @Override
  public String[] attrNames() {
    Set<String> attrs = this.attribute.keySet();
    return attrs.toArray(new String[attrs.size()]);
  }


  @Override
  public void addParam(String key, Object value) {
    this.request.addParam(key, value);
  }

  @Override
  public HttpConst.RequestBodyFormat getBodyFormat() {
    return this.request.getBodyFormat();
  }

  @Override
  public String getUserAgent() {
    return this.request.getUserAgent();
  }

  @Override
  public String getHost() {
    return this.request.getHost();
  }

  @Override
  public String getClientIp() {
    return this.request.getClientIp();
  }

  @Override
  public String getDomain() {
    return this.request.getDomain();
  }

  @Override
  public String getBodyString() {
    return this.request.getBodyString();
  }

  @Override
  public ChannelContext getChannelContext() {
    return this.request.getChannelContext();
  }

  @Override
  public String getCharset() {
    return this.request.getCharset();
  }

  @Override
  public int getContentLength() {
    return this.request.getContentLength();
  }

  @Override
  public Cookie getCookie(String cooiename) {
    return this.request.getCookie(cooiename);
  }

  @Override
  public Map<String, Cookie> getCookieMap() {
    return this.request.getCookieMap();
  }

  @Override
  public List<Cookie> getCookies() {
    return this.request.getCookies();
  }

  @Override
  public HttpConfig getHttpConfig() {
    return this.request.getHttpConfig();
  }

  @Override
  public HttpSession getHttpSession() {
    return this.request.getHttpSession();
  }

  @Override
  public Boolean getIsAjax() {
    return this.request.getIsAjax();
  }

  @Override
  public Boolean getIsSupportGzip() {
    return this.request.getIsSupportGzip();
  }

  @Override
  public Map<String, Object[]> getParams() {
    return this.request.getParams();
  }

  @Override
  public String getParam(String name) {
    return this.request.getParam(name);
  }

  @Override
  public Node getRemote() {
    return this.request.getRemote();
  }

  @Override
  public RequestLine getRequestLine() {
    return this.request.getRequestLine();
  }

  @Override
  public String logstr() {
    return this.request.logstr();
  }

  @Override
  public void parseCookie() {
    this.request.parseCookie();
  }

  @Override
  public void setBody(byte[] body) {
    this.request.setBody(body);
  }

  @Override
  public void setBodyFormat(HttpConst.RequestBodyFormat bodyFormat) {
    this.request.setBodyFormat(bodyFormat);
  }

  @Override
  public void setBodyString(String bodyString) {
    this.request.setBodyString(bodyString);
  }

  @Override
  public void setChannelContext(ChannelContext channelContext) {
    this.request.setChannelContext(channelContext);
  }

  @Override
  public void setCharset(String charset) {
    this.request.setCharset(charset);
  }

  @Override
  public void setContentLength(int contentLength) {
    this.request.setContentLength(contentLength);
  }

  @Override
  public void setCookieMap(Map<String, Cookie> cookieMap) {
    this.request.setCookieMap(cookieMap);
  }

  @Override
  public void setCookies(List<Cookie> cookies) {
    this.request.setCookies(cookies);
  }

  @Override
  public void setHeaders(Map<String, String> headers) {
    this.request.setHeaders(headers);
  }

  @Override
  public void setHttpConfig(HttpConfig httpConfig) {
    this.request.setHttpConfig(httpConfig);
  }

  @Override
  public void setHttpSession(HttpSession httpSession) {
    this.request.setHttpSession(httpSession);
  }

  @Override
  public void setIsAjax(Boolean isAjax) {
    this.request.setIsAjax(isAjax);
  }

  @Override
  public void setIsSupportGzip(Boolean isSupportGzip) {
    this.request.setIsSupportGzip(isSupportGzip);
  }

  @Override
  public void setParams(Map<String, Object[]> params) {
    this.request.setParams(params);
  }

  @Override
  public void setRemote(Node remote) {
    this.request.setRemote(remote);
  }

  @Override
  public void setRequestLine(RequestLine requestLine) {
    this.request.setRequestLine(requestLine);
  }

  @Deprecated
  @Override
  public Object getAttribute(String key) {
    return this.request.getAttribute(key);
  }

  @Deprecated
  @Override
  public Object getAttribute(String key, Serializable defaultValue) {
    return this.request.getAttribute(key, defaultValue);
  }

  @Deprecated
  @Override
  public void removeAttribute(String key) {
    this.request.removeAttribute(key);
  }

  @Deprecated
  @Override
  public void setAttribute(String key, Serializable value) {
    this.request.setAttribute(key, value);
  }

  @Override
  public void addHeader(String key, String value) {
    this.request.addHeader(key, value);
  }

  @Override
  public void addHeaders(Map<String, String> headers) {
    this.request.addHeaders(headers);
  }

  @Override
  public byte[] getBody() {
    return this.request.getBody();
  }

  @Override
  public String getHeader(String key) {
    return this.request.getHeader(key);
  }

  @Override
  public Map<String, String> getHeaders() {
    return this.request.getHeaders();
  }

  @Override
  public String getHeaderString() {
    return this.request.getHeaderString();
  }

  @Override
  public void removeHeader(String key, String value) {
    this.request.removeHeader(key, value);
  }

  @Override
  public void setHeaderString(String headerString) {
    this.request.setHeaderString(headerString);
  }

  @Override
  public Packet clone() {
    return this.request.clone();
  }

  @Override
  public int getByteCount() {
    return this.request.getByteCount();
  }

  @Override
  public Long getId() {
    return this.request.getId();
  }

  @Override
  public PacketListener getPacketListener() {
    return this.request.getPacketListener();
  }

  @Override
  public ByteBuffer getPreEncodedByteBuffer() {
    return this.request.getPreEncodedByteBuffer();
  }

  @Override
  public Long getRespId() {
    return this.request.getRespId();
  }

  @Override
  public Integer getSynSeq() {
    return this.request.getSynSeq();
  }

  @Override
  public boolean isBlockSend() {
    return this.request.isBlockSend();
  }

  @Override
  public void setBlockSend(boolean isBlockSend) {
    this.request.setBlockSend(isBlockSend);
  }

  @Override
  public void setByteCount(int byteCount) {
    this.request.setByteCount(byteCount);
  }

  @Override
  public void setId(Long id) {
    this.request.setId(id);
  }

  @Override
  public void setPacketListener(PacketListener packetListener) {
    this.request.setPacketListener(packetListener);
  }

  @Override
  public void setPreEncodedByteBuffer(ByteBuffer preEncodedByteBuffer) {
    this.request.setPreEncodedByteBuffer(preEncodedByteBuffer);
  }

  @Override
  public void setRespId(Long respId) {
    this.request.setRespId(respId);
  }

  @Override
  public void setSynSeq(Integer synSeq) {
    this.request.setSynSeq(synSeq);
  }

  @Override
  public boolean isFromCluster() {
    return this.request.isFromCluster();
  }

  @Override
  public void setFromCluster(boolean isFromCluster) {
    this.request.setFromCluster(isFromCluster);
  }

  @Override
  public boolean isSslEncrypted() {
    return this.request.isSslEncrypted();
  }

  @Override
  public void setSslEncrypted(boolean isSslEncrypted) {
    this.request.setSslEncrypted(isSslEncrypted);
  }

  @Override
  public Meta getMeta() {
    return this.request.getMeta();
  }

  @Override
  public void setMeta(Meta meta) {
    this.request.setMeta(meta);
  }

  @Override
  public void clear() {
    this.attribute.clear();
  }
}
