package com.github.huifer.plugin.community.gorm.generator.model.enums;

public enum GoDataType {
  INT("int", true),
  int64("int64", true),
  string("string", true),
  time("time.Time", true),
  float64("float64", true),
  float32("float32", true),
  byteArray("[]byte", true),
  NullInt64("sql.NullInt64", false),
  NullString("sql.NullString", false),
  NullTime("null.Time", false),
  NullFloat64("sql.NullFloat64", false),
  ;
  private final String name;
  private final boolean nullable;

  GoDataType(String name, boolean nullable) {
    this.name = name;
    this.nullable = nullable;
  }

  public boolean isNullable() {
    return nullable;
  }

  public String getName() {
    return name;
  }
}
