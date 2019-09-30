package io.enoa.index.elasticsearch.types.error;

public class EErrorRootCause {

  private String type;
  private String reason;
  private EErrorResource resource;
  private String index_uuid;
  private String index;

  public String getType() {
    return type;
  }

  public EErrorRootCause setType(String type) {
    this.type = type;
    return this;
  }

  public String getReason() {
    return reason;
  }

  public EErrorRootCause setReason(String reason) {
    this.reason = reason;
    return this;
  }

  public EErrorResource getResource() {
    return resource;
  }

  public EErrorRootCause setResource(EErrorResource resource) {
    this.resource = resource;
    return this;
  }

  public String getIndex_uuid() {
    return index_uuid;
  }

  public EErrorRootCause setIndex_uuid(String index_uuid) {
    this.index_uuid = index_uuid;
    return this;
  }

  public String getIndex() {
    return index;
  }

  public EErrorRootCause setIndex(String index) {
    this.index = index;
    return this;
  }

  @Override
  public String toString() {
    return "EErrorRootCause{" +
      "type='" + type + '\'' +
      ", reason='" + reason + '\'' +
      ", resource=" + resource +
      ", index_uuid='" + index_uuid + '\'' +
      ", index='" + index + '\'' +
      '}';
  }
}
