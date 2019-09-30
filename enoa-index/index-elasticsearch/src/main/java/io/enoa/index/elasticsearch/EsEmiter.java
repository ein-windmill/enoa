package io.enoa.index.elasticsearch;

import io.enoa.http.EoUrl;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.index.elasticsearch.eql.Eql;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.value.EnoaValue;

import java.util.Collection;
import java.util.stream.Stream;

public class EsEmiter implements IEs {

  private Http http;
  private ElasticsearchConfig config;
  private Integer size;
  private Long from;

  public EsEmiter(ElasticsearchConfig config) {
    this.config = config;
    this.http = config.http();
    this.http.contentType("application/json");
  }

  EsEmiter url(EoUrl url) {
    this.http.url(url);
    return this;
  }

  public EsEmiter eql(Eql eql) {
    if (Is.not().nullx(eql))
      this.http.raw(eql.eql());
    return this;
  }

  public EsEmiter from(Long from) {
    this.from = from;
    return this;
  }

  public EsEmiter size(Integer size) {
    this.size = size;
    return this;
  }

  public EsEmiter method(HttpMethod method) {
    this.http.method(method);
    return this;
  }

  public EsEmiter para(String name, Object value) {
    if (Is.truthy(name) && name.equalsIgnoreCase("size")) {
      this.size = EnoaValue.with(value).integer(10);
      return this;
    }
    if (Is.truthy(name) && name.equalsIgnoreCase("from")) {
      this.from = EnoaValue.with(value).longer(0L);
    }
    this.http.para(name, value);
    return this;
  }

  public EsEmiter para(String name, Object... values) {
    if (Is.truthy(name) && name.equalsIgnoreCase("size")) {
      this.size = Stream.of(values).findFirst()
        .filter(item -> Is.not().nullx(item))
        .map(item -> EnoaValue.with(item).integer(10))
        .orElse(10);
      return this;
    }
    if (Is.truthy(name) && name.equalsIgnoreCase("from")) {
      this.from = Stream.of(values).findFirst()
        .filter(item -> Is.not().nullx(item))
        .map(item -> EnoaValue.with(item).longer(0L))
        .orElse(0L);
      return this;
    }
    this.http.para(name, values);
    return this;
  }

  public EsEmiter para(String name, Collection values) {
    if (Is.truthy(name) && name.equalsIgnoreCase("size")) {
      this.size = (Integer) values.stream().findFirst()
        .filter(item -> Is.not().nullx(item))
        .map(item -> EnoaValue.with(item).integer(10))
        .orElse(10);
      return this;
    }
    if (Is.truthy(name) && name.equalsIgnoreCase("from")) {
      this.from = (Long) values.stream().findFirst()
        .filter(item -> Is.not().nullx(item))
        .map(item -> EnoaValue.with(item).longer(0L))
        .orElse(0L);
      return this;
    }
    this.http.para(name, values);
    return this;
  }

  @Override
  public <T> T emit(EParser<T> parser) {
    if (Is.not().nullx(this.size))
      this.http.para("size", this.size);
    if (Is.not().nullx(this.from))
      this.http.para("from", this.from);
    HttpResponse response = this.http.emit();
    return parser.parse(this.config.json(), response, this.size, this.from);
  }
}
