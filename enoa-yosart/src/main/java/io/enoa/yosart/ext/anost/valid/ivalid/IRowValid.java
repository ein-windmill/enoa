package io.enoa.yosart.ext.anost.valid.ivalid;

/**
 * 数组每行验证接口
 */
public interface IRowValid {

  /**
   * 验证当前行是否通过
   *
   * @param row row
   * @return boolean
   */
  boolean pass(String row);

}
