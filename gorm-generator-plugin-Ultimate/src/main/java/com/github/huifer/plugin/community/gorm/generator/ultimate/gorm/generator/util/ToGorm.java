/*
 *
 * Copyright 2020-2020 HuiFer All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.github.huifer.plugin.community.gorm.generator.ultimate.gorm.generator.util;

import com.github.huifer.plugin.community.gorm.generator.ultimate.gorm.generator.model.db.Column;
import com.github.huifer.plugin.community.gorm.generator.ultimate.gorm.generator.model.db.Table;
import com.github.huifer.plugin.community.gorm.generator.ultimate.gorm.generator.model.enums.MySqlDataType;
import com.google.common.base.CaseFormat;
import org.jetbrains.annotations.NotNull;

public class ToGorm {


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

      if (column.isPrimaryKey()) {
        s.append("`gorm:\"column:").append(column.getName()).append("  ").append("json:")
            .append("\"")

            .append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, columnName))
            .append("\"").append("`").append("\r\n");

      } else {
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
