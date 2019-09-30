package io.enoa.index.elasticsearch.types.bulk;

import io.enoa.index.elasticsearch.types.EShards;

public class BulkIndex implements IBulk {

  private String _index;
  private String _type;
  private String _id;
  private Integer _version;
  private String result;
  private EShards _shards;
  private Integer _seq_no;
  private Integer _primaryTerm;
  private Integer status;


  public String get_index() {
    return _index;
  }

  public BulkIndex set_index(String _index) {
    this._index = _index;
    return this;
  }

  public String get_type() {
    return _type;
  }

  public BulkIndex set_type(String _type) {
    this._type = _type;
    return this;
  }

  public String get_id() {
    return _id;
  }

  public BulkIndex set_id(String _id) {
    this._id = _id;
    return this;
  }

  public Integer get_version() {
    return _version;
  }

  public BulkIndex set_version(Integer _version) {
    this._version = _version;
    return this;
  }

  public String getResult() {
    return result;
  }

  public BulkIndex setResult(String result) {
    this.result = result;
    return this;
  }

  public EShards get_shards() {
    return _shards;
  }

  public BulkIndex set_shards(EShards _shards) {
    this._shards = _shards;
    return this;
  }

  public Integer get_seq_no() {
    return _seq_no;
  }

  public BulkIndex set_seq_no(Integer _seq_no) {
    this._seq_no = _seq_no;
    return this;
  }

  public Integer get_primaryTerm() {
    return _primaryTerm;
  }

  public BulkIndex set_primaryTerm(Integer _primaryTerm) {
    this._primaryTerm = _primaryTerm;
    return this;
  }

  public Integer getStatus() {
    return status;
  }

  public BulkIndex setStatus(Integer status) {
    this.status = status;
    return this;
  }
}
