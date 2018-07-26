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
package io.enoa.repeater.provider.tomcat.server.plus;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

public abstract class _RepeaterTomcatRequest implements HttpServletRequest {

  private HttpServletRequest request;

  public _RepeaterTomcatRequest(HttpServletRequest request) {
    this.request = request;
  }

  @Override
  public String getAuthType() {
    return this.request.getAuthType();
  }

  @Override
  public Cookie[] getCookies() {
    return this.request.getCookies();
  }

  @Override
  public long getDateHeader(String name) {
    return this.request.getDateHeader(name);
  }

  @Override
  public String getHeader(String name) {
    return this.request.getHeader(name);
  }

  @Override
  public Enumeration<String> getHeaders(String name) {
    return this.request.getHeaders(name);
  }

  @Override
  public Enumeration<String> getHeaderNames() {
    return this.request.getHeaderNames();
  }

  @Override
  public int getIntHeader(String name) {
    return this.request.getIntHeader(name);
  }

  @Override
  public String getMethod() {
    return this.request.getMethod();
  }

  @Override
  public String getPathInfo() {
    return this.request.getPathInfo();
  }

  @Override
  public String getPathTranslated() {
    return this.request.getPathTranslated();
  }

  @Override
  public String getQueryString() {
    return this.request.getQueryString();
  }

  @Override
  public String getRemoteUser() {
    return this.request.getRemoteUser();
  }

  @Override
  public boolean isUserInRole(String role) {
    return this.request.isUserInRole(role);
  }

  @Override
  public Principal getUserPrincipal() {
    return this.request.getUserPrincipal();
  }

  @Override
  public String getRequestedSessionId() {
    return this.request.getRequestedSessionId();
  }

  @Override
  public String getRequestURI() {
    return this.request.getRequestURI();
  }

  @Override
  public StringBuffer getRequestURL() {
    return this.request.getRequestURL();
  }

  @Override
  public String getServletPath() {
    return this.request.getServletPath();
  }

  @Override
  public HttpSession getSession(boolean create) {
    return this.request.getSession(create);
  }

  @Override
  public HttpSession getSession() {
    return this.request.getSession();
  }

  @Override
  public String changeSessionId() {
    return this.request.changeSessionId();
  }

  @Override
  public boolean isRequestedSessionIdValid() {
    return this.request.isRequestedSessionIdValid();
  }

  @Override
  public boolean isRequestedSessionIdFromCookie() {
    return this.request.isRequestedSessionIdFromCookie();
  }

  @Override
  public boolean isRequestedSessionIdFromURL() {
    return this.request.isRequestedSessionIdFromURL();
  }

  @Deprecated
  @Override
  public boolean isRequestedSessionIdFromUrl() {
    return this.request.isRequestedSessionIdFromUrl();
  }

  @Override
  public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
    return this.request.authenticate(response);
  }

  @Override
  public void login(String username, String password) throws ServletException {
    this.request.login(username, password);
  }

  @Override
  public void logout() throws ServletException {
    this.request.logout();
  }

  @Override
  public Collection<Part> getParts() throws IOException, ServletException {
    return this.request.getParts();
  }

  @Override
  public Part getPart(String name) throws IOException, ServletException {
    return this.request.getPart(name);
  }

  @Override
  public <T extends HttpUpgradeHandler> T upgrade(Class<T> httpUpgradeHandlerClass) throws IOException, ServletException {
    return this.request.upgrade(httpUpgradeHandlerClass);
  }

  @Override
  public Object getAttribute(String name) {
    return this.request.getAttribute(name);
  }

  @Override
  public Enumeration<String> getAttributeNames() {
    return this.request.getAttributeNames();
  }

  @Override
  public String getCharacterEncoding() {
    return this.request.getCharacterEncoding();
  }

  @Override
  public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
    this.request.setCharacterEncoding(env);
  }

  @Override
  public int getContentLength() {
    return this.request.getContentLength();
  }

  @Override
  public long getContentLengthLong() {
    return this.request.getContentLengthLong();
  }

  @Override
  public String getContentType() {
    return this.request.getContentType();
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    return this.request.getInputStream();
  }

  @Override
  public String getParameter(String name) {
    return this.request.getParameter(name);
  }

  @Override
  public Enumeration<String> getParameterNames() {
    return this.request.getParameterNames();
  }

  @Override
  public String[] getParameterValues(String name) {
    return this.request.getParameterValues(name);
  }

  @Override
  public Map<String, String[]> getParameterMap() {
    return this.request.getParameterMap();
  }

  @Override
  public String getProtocol() {
    return this.request.getProtocol();
  }

  @Override
  public String getScheme() {
    return this.request.getScheme();
  }

  @Override
  public String getServerName() {
    return this.request.getServerName();
  }

  @Override
  public int getServerPort() {
    return this.request.getServerPort();
  }

  @Override
  public BufferedReader getReader() throws IOException {
    return this.request.getReader();
  }

  @Override
  public String getRemoteAddr() {
    return this.request.getRemoteAddr();
  }

  @Override
  public String getRemoteHost() {
    return this.request.getRemoteHost();
  }

  @Override
  public void setAttribute(String name, Object o) {
    this.request.setAttribute(name, o);
  }

  @Override
  public void removeAttribute(String name) {
    this.request.removeAttribute(name);
  }

  @Override
  public Locale getLocale() {
    return this.request.getLocale();
  }

  @Override
  public Enumeration<Locale> getLocales() {
    return this.request.getLocales();
  }

  @Override
  public boolean isSecure() {
    return this.request.isSecure();
  }

  @Override
  public RequestDispatcher getRequestDispatcher(String path) {
    return this.request.getRequestDispatcher(path);
  }

  @Deprecated
  @Override
  public String getRealPath(String path) {
    return this.request.getRealPath(path);
  }

  @Override
  public int getRemotePort() {
    return this.request.getRemotePort();
  }

  @Override
  public String getLocalName() {
    return this.request.getLocalName();
  }

  @Override
  public String getLocalAddr() {
    return this.request.getLocalAddr();
  }

  @Override
  public int getLocalPort() {
    return this.request.getLocalPort();
  }

  @Override
  public ServletContext getServletContext() {
    return this.request.getServletContext();
  }

  @Override
  public AsyncContext startAsync() throws IllegalStateException {
    return this.request.startAsync();
  }

  @Override
  public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
    return this.request.startAsync(servletRequest, servletResponse);
  }

  @Override
  public boolean isAsyncStarted() {
    return this.request.isAsyncStarted();
  }

  @Override
  public boolean isAsyncSupported() {
    return this.request.isAsyncSupported();
  }

  @Override
  public AsyncContext getAsyncContext() {
    return this.request.getAsyncContext();
  }

  @Override
  public DispatcherType getDispatcherType() {
    return this.request.getDispatcherType();
  }
}
