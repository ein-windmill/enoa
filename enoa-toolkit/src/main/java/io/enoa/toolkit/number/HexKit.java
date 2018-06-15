package io.enoa.toolkit.number;

public class HexKit {

  private HexKit() {

  }


  /**
   * Signopt 0x HexDigits
   * Signopt 0X HexDigits
   * Signopt # HexDigits
   * 是否为十六进制
   *
   * @param text value
   * @return boolean
   */
  public static boolean isHexNumber(String text) {
    int index = (text.startsWith("-") ? 1 : 0);
    return (text.startsWith("0x", index) || text.startsWith("0X", index) || text.startsWith("#", index));
  }

  /**
   * 二进制转字符串
   *
   * @param bytes
   * @return
   */
  public static String hex(byte[] bytes) {
    StringBuilder hs = new StringBuilder();
    String _tmp;
    for (byte b : bytes) {
      _tmp = (Integer.toHexString(b & 0XFF));
      if (_tmp.length() == 1)
        hs.append('0');
      hs.append(_tmp);
    }
    return hs.toString();
  }

  /**
   * 字符串转二进制
   *
   * @param str
   * @return
   */
  public static byte[] bytes(String str) {
    if (str == null)
      return null;
    str = str.trim();
    int len = str.length();
    if (len == 0 || len % 2 == 1)
      return null;

    byte[] b = new byte[len / 2];
    try {
      for (int i = 0; i < str.length(); i += 2) {
        b[i / 2] = (byte) Integer.decode("0x" + str.substring(i, i + 2)).intValue();
      }
      return b;
    } catch (Exception e) {
      return null;
    }
  }
}
