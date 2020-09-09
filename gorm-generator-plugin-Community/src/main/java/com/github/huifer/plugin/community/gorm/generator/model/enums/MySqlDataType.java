package com.github.huifer.plugin.community.gorm.generator.model.enums;

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
