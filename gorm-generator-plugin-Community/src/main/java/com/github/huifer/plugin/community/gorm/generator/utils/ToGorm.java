package com.github.huifer.plugin.community.gorm.generator.utils;

import com.github.huifer.plugin.community.gorm.generator.model.db.Column;
import com.github.huifer.plugin.community.gorm.generator.model.db.Table;
import com.github.huifer.plugin.community.gorm.generator.model.enums.MySqlDataType;
import com.google.common.base.CaseFormat;
import org.jetbrains.annotations.NotNull;

public class ToGorm {

  public static String go(String sql) {
    Table apply = SqlUtils.apply(sql);
    return getString(apply);
  }

  @NotNull
  public static String getString(Table apply) {
    String name = apply.getName();
    StringBuilder s = new StringBuilder(32);

    s.append("type  ").append(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name))
        .append("  struct  {\n");

    for (Column column : apply.getColumnList()) {
      String columnName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, column.getName());

      s.append("  ").append(columnName).append("  ")
          .append(MySqlDataType.convertToGoType(column.getTypeStr())).append("  ");

      if (column.getName().equals(apply.getPrimaryKey())) {
        s.append("`gorm:\"column:").append(column.getName()).append("  ").append("json:")
            .append("\"")

            .append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, columnName))
            .append("\"").append("`").append("\r\n");

      }
      else {
        s.append("`gorm:\"column:").append(column.getName()).append("\"").append("  ")
            .append("json:").append("\"")
            .append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, columnName)).append("\"")
            .append("`").append("\r\n");

      }
    }
    s.append("}");
    return s.toString();
  }
}
