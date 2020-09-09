package com.github.huifer.plugin.community.gorm.generator.utils;

import com.github.huifer.plugin.community.gorm.generator.model.db.Column;
import com.github.huifer.plugin.community.gorm.generator.model.db.Matcher;
import com.github.huifer.plugin.community.gorm.generator.model.db.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class SqlUtils {

  public static String cleanSql(String sql) {
    sql = sql.replaceAll("\\r", "");
    sql = sql.replaceAll("\\n\\n", "\n");
    sql = fixComment(sql);
    sql = fixColumnType(sql);
    sql = sql.replaceAll(",", "\n");
    System.out.println(sql);
    return sql;
  }

  private static String fixColumnType(String sql) {
    String regStr = "\\s+[a-z]*\\s*\\([^'\\)]*,[^'\\)]*\\)";
    java.util.regex.Matcher mr = Pattern.compile(regStr, Pattern.CASE_INSENSITIVE + Pattern.DOTALL)
        .matcher(sql);
    while (mr.find()) {
      String r = mr.group().replaceAll(",", "-");
      sql = sql.replace(mr.group(), r);
    }
    return sql;
  }

  private static String fixComment(String sql) {
    //对于
    String regStr = "comment\\s+'([^']*,[^']*)'";
    java.util.regex.Matcher mr = Pattern.compile(regStr, Pattern.CASE_INSENSITIVE + Pattern.DOTALL)
        .matcher(sql);
    while (mr.find()) {
      String r = mr.group().replaceAll(",", "-");
      sql = sql.replace(mr.group(), r);
    }
    regStr = "key\\s+[^(]*\\s+\\([^)]+,[^)]+\\)";
    java.util.regex.Matcher mr1 = Pattern.compile(regStr, Pattern.CASE_INSENSITIVE + Pattern.DOTALL)
        .matcher(sql);
    while (mr1.find()) {
      String r = mr1.group().replaceAll(",", "-");
      sql = sql.replace(mr1.group(), r);
    }
    return sql;
  }


  public static List<String> analyColumnLine(String sql) {
    String regStr = "create\\s*table\\s*(?:if\\s*not\\s*exists)*\\s*[^\\(]*\\s*\\((.*)\\)\\s*[^\\s\\)]*\\s*(?:(ENGINE|comment)*)";
    List<String> matches = Matcher.match(regStr, 1, sql);
    if (matches != null && matches.size() == 1) {
      String lines = matches.get(0);
      lines = lines.replaceAll("\\s+\\n\\s*", "\n");
      String[] la = lines.split("\n");
      return Arrays.asList(la);
    }
    return null;
  }

  public static String analyTableName(String sql) {
    String regStr = "create\\s+table\\s*(?:if\\s*not\\s*exists)*(([^\\(]+))\\s*\\(";
    List<String> matches = Matcher.match(regStr, sql);
    if (matches != null && matches.size() == 2) {
      return matches.get(1).replaceAll("`|'|\"|\\s", "");
    }
    return "table";
  }

  public static String analyPrimaryKey(String sql) {
    String regStr = "primary\\s+key\\s+\\(([^\\)]*)\\)";
    List<String> matches = Matcher.match(regStr, 1, sql);
    if (matches != null && matches.size() == 1) {
      return matches.get(0).replaceAll("`|'|\"|\\s", "");
    }
    return null;
  }


  public static Column getColumn(String line) {
    String regStrP = "[`]*([^`\\s]+)[`]*\\s+(\\S+)\\s*[unsigned\\s]*[not\\s]*[null\\s]*[default\\s]*.*[auto_increament\\s]*\\s*(?:comment\\s*'([^']*)')*";
    String regStr = "comment\\s+'(([^']*))'";
    List<String> l = Matcher.match(regStrP, line.trim());
    List<String> l2 = Matcher.match(regStr, line);
    if (l != null && l.size() == 3) {
      Column c = new Column(l.get(1), l.get(2));
      if (l2 != null && l2.size() == 2) {
        c.setComment(l2.get(1));
      }
      else {
        c.setComment(l.get(1));
      }
      return c;
    }
    else {
      return null;
    }
  }




  public static Table apply(String sql) {
    sql = cleanSql(sql);
    List<String> lines = analyColumnLine(sql);
    List<Column> colList = new ArrayList<Column>();
    if (lines != null && lines.size() > 0) {
      for (String line : lines) {
        if (StringUtils.isNotBlank(line)) {
          line = line.trim();
          if (!isKeyLine(line)) {
            try {
              Column c = getColumn(line);
              if (c != null) {
                colList.add(c);
              }
            } catch (Exception e) {
            }
          }
        }
      }
    }
    String k = analyPrimaryKey(sql);
    String n = analyTableName(sql);
    return new Table(n, k, colList);
  }

  public static boolean isKeyLine(String line) {
    String regStr = "\\s*((primary|unique)\\s+)*key\\s+";
    List<String> l = Matcher.match(regStr, line);
    return l != null && l.size() > 0 && !"`".equals(line.trim().substring(0, 1));
  }

}