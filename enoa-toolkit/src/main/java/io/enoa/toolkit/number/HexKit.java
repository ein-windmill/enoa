package io.enoa.toolkit.number;

import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.collection.CollectionKit;

import java.util.ArrayList;
import java.util.List;

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
    return hex(bytes, null);
  }

  public static String hex(byte[] bytes, HexToStringOptions options) {
    StringBuilder hs = new StringBuilder();
    String _tmp;
    for (byte b : bytes) {
      _tmp = (Integer.toHexString(b & 0XFF));
      if (_tmp.length() == 1)
        hs.append('0');
      hs.append(_tmp);
    }
    String hex = hs.toString();
    if (options == null)
      return hex;
    List<String> rets = new ArrayList<>();
    String[] cells = new String[2];
    int celIx = 0;
    for (int i = 0; i<hex.length(); i++) {
      char ch = hex.charAt(i);
      cells[celIx] = String.valueOf(ch);
      celIx += 1;
      if ((i + 1) % 2 == 0) {
        celIx = 0;
        rets.add(String.join("", cells));
        if (options.spaceBetween) {
          rets.add(" ");
        }
      }
      if ((i + 1) % options.maxLineLength == 0) {
        rets.add("\n");
      }
    }
    String format = String.join("", rets);
    CollectionKit.clear(rets);
    return format;
  }

  public static class HexToStringOptions {
    private boolean spaceBetween = true;
    private int maxLineLength = 16;

    public boolean isSpaceBetween() {
      return spaceBetween;
    }

    public void setSpaceBetween(boolean spaceBetween) {
      this.spaceBetween = spaceBetween;
    }

    public int getMaxLineLength() {
      return maxLineLength;
    }

    public void setMaxLineLength(int maxLineLength) {
      this.maxLineLength = maxLineLength;
    }
  }

  /**
   * 字符串转二进制
   *
   * @param text hex string
   * @return EnoaBinary
   */
  public static EnoaBinary binary(String text) {
    if (text == null)
      return null;
    text = text.trim();
    int len = text.length();
    if (len == 0 || len % 2 == 1)
      return null;

    byte[] b = new byte[len / 2];
    try {
      for (int i = 0; i < text.length(); i += 2) {
        b[i / 2] = (byte) Integer.decode("0x" + text.substring(i, i + 2)).intValue();
      }
      return EnoaBinary.create(b);
    } catch (Exception e) {
      return null;
    }
  }
}
