package io.enoa.index.elasticsearch.types.error;

import java.util.List;

public class EError {

  private String type;
  private String reason;
  private EErrorResource resource;
  private String indexUuid;
  private String index;
  private Integer shard;

  private List<EErrorRootCause> rootCause;

  public Integer getShard() {
    return shard;
  }

  public EError setShard(Integer shard) {
    this.shard = shard;
    return this;
  }

  public String getType() {
    return type;
  }

  public EError setType(String type) {
    this.type = type;
    return this;
  }

  public String getReason() {
    return reason;
  }

  public EError setReason(String reason) {
    this.reason = reason;
    return this;
  }

  public EErrorResource getResource() {
    return resource;
  }

  public EError setResource(EErrorResource resource) {
    this.resource = resource;
    return this;
  }

  public String getIndexUuid() {
    return indexUuid;
  }

  public EError setIndexUuid(String indexUuid) {
    this.indexUuid = indexUuid;
    return this;
  }

  public String getIndex() {
    return index;
  }

  public EError setIndex(String index) {
    this.index = index;
    return this;
  }

  public List<EErrorRootCause> getRootCause() {
    return rootCause;
  }

  public EError setRootCause(List<EErrorRootCause> rootCause) {
    this.rootCause = rootCause;
    return this;
  }

  @Override
  public String toString() {
    return "EError{" +
      "type='" + type + '\'' +
      ", reason='" + reason + '\'' +
      ", resource=" + resource +
      ", index_uuid='" + indexUuid + '\'' +
      ", index='" + index + '\'' +
      ", rootCause=" + rootCause +
      '}';
  }
}
