package io.enoa.index.elasticsearch.types.bulk;

import io.enoa.index.elasticsearch.types.error.IEError;

import java.util.List;

public class EBulk<T> implements IEError<T> {

  private Integer took;
  private Boolean errors;
  private List<T> items;

  @Override
  public Integer getTook() {
    return took;
  }

  public EBulk<T> setTook(Integer took) {
    this.took = took;
    return this;
  }

  public Boolean getErrors() {
    return errors;
  }

  public EBulk<T> setErrors(Boolean errors) {
    this.errors = errors;
    return this;
  }

  @Override
  public List<T> getItems() {
    return items;
  }

  public EBulk<T> setItems(List<T> items) {
    this.items = items;
    return this;
  }
}
