package io.enoa.index.elasticsearch.types.bulk;

import io.enoa.index.elasticsearch.types.error.EError;

public class BulkCreate implements IBulk {

  private String _index;
  private String _type;
  private String _id;
  private Integer status;
  private EError error;

  public String get_index() {
    return _index;
  }

  public BulkCreate set_index(String _index) {
    this._index = _index;
    return this;
  }

  public String get_type() {
    return _type;
  }

  public BulkCreate set_type(String _type) {
    this._type = _type;
    return this;
  }

  public String get_id() {
    return _id;
  }

  public BulkCreate set_id(String _id) {
    this._id = _id;
    return this;
  }

  public Integer getStatus() {
    return status;
  }

  public BulkCreate setStatus(Integer status) {
    this.status = status;
    return this;
  }

  public EError getError() {
    return error;
  }

  public BulkCreate setError(EError error) {
    this.error = error;
    return this;
  }
}
