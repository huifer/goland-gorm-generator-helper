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

package com.github.huifer.plugin.community.gorm.generator.ultimate.gorm.generator.model.enums;

public enum MySqlDataType {
  TINYINT("tinyint"),
  INT("int"),
  SMALLINT("smallint"),
  MEDIUMINT("mediumint"),
  BIGINT("bigint"),
  CHAR("char"),
  ENUM("enum"),
  VARCHAR("varchar"),
  LONGTEXT("longtext"),
  MEDIUMTEXT("mediumtext"),
  TEXT("text"),
  TINYTEXT("tinytext"),
  JSON("json"),
  DATE("date"),
  DATETIME("datetime"),
  TIME("time"),
  TIMESTAMP("timestamp"),
  DECIMAL("decimal"),
  DOUBLE("double"),
  FLAT("float"),
  BINARY("binary"),
  BLOB("blob"),
  LONGBLOB("longblob"),
  MEDIUMBLOB("mediumblob"),
  VARBINARY("varbinary"),

  ;
  private final String name;

  MySqlDataType(String name) {
    this.name = name;
  }

  public static String convertToGoType(String value) {
    MySqlDataType cv = cv(value);
    if (cv == MySqlDataType.TINYINT || cv == MySqlDataType.INT || cv == MySqlDataType.SMALLINT
        || cv == MySqlDataType.MEDIUMINT) {
      return GoDataType.INT.getName();
    }
    else if (cv == MySqlDataType.BIGINT) {
      return GoDataType.int64.getName();
    }
    else if (cv == MySqlDataType.CHAR || cv == MySqlDataType.ENUM || cv == MySqlDataType.VARCHAR
        || cv == MySqlDataType.LONGTEXT || cv == MySqlDataType.MEDIUMTEXT
        || cv == MySqlDataType.TEXT || cv == MySqlDataType.TINYTEXT || cv == MySqlDataType.JSON) {
      return GoDataType.string.getName();
    }
    else if (cv == MySqlDataType.DATE || cv == MySqlDataType.DATETIME || cv == MySqlDataType.TIME
        || cv == MySqlDataType.TIMESTAMP) {
      return GoDataType.time.getName();
    }
    else if (cv == MySqlDataType.DECIMAL || cv == MySqlDataType.DOUBLE) {
      return GoDataType.float64.getName();
    }
    else if (cv == MySqlDataType.FLAT) {
      return GoDataType.float32.getName();
    }
    else if (cv == MySqlDataType.BINARY || cv == MySqlDataType.BLOB || cv == MySqlDataType.LONGBLOB
        || cv == MySqlDataType.MEDIUMBLOB || cv == MySqlDataType.VARBINARY) {
      return GoDataType.byteArray.getName();
    }

    return null;
  }

  private static MySqlDataType cv(String value) {
    String s = value.toLowerCase();
    for (MySqlDataType mySqlDataType : MySqlDataType.values()) {
      if (mySqlDataType.getName().equals(s)) {
        return mySqlDataType;
      }
    }
    return null;
  }

  public String getName() {
    return name;
  }
}
