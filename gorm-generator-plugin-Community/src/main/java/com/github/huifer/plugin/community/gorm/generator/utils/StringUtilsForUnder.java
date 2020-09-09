package com.github.huifer.plugin.community.gorm.generator.utils;

public class StringUtilsForUnder {

  public static String UnderlineToHump(String para) {
    StringBuilder result = new StringBuilder();
    String[] a = para.split("_");
    for (String s : a) {
      if (!para.contains("_")) {
        result.append(s);
        continue;
      }
      if (result.length() == 0) {
        result.append(s.toLowerCase());
      }
      else {
        result.append(s.substring(0, 1).toUpperCase());
        result.append(s.substring(1).toLowerCase());
      }
    }
    return result.toString();
  }


  public static String HumpToUnderline(String para) {
    StringBuilder sb = new StringBuilder(para);
    int temp = 0;
    if (!para.contains("_")) {
      for (int i = 0; i < para.length(); i++) {
        if (Character.isUpperCase(para.charAt(i))) {
          sb.insert(i + temp, "_");
          temp += 1;
        }
      }
    }
    return sb.toString().toUpperCase();
  }

}
