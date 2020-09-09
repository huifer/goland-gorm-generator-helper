package com.github.huifer.plugin.community.gorm.generator.model.db;

import java.util.List;

public class Table {

  private String name;
  private String primaryKey;

  public Table(String name, String primaryKey,
      List<Column> columnList) {
    this.name = name;
    this.primaryKey = primaryKey;
    this.columnList = columnList;
  }

  private List<Column> columnList;

  public Table() {
  }

  public Table(List<Column> columnList, String name, String primaryKey) {
    this.columnList = columnList;
    this.name = name;
    this.primaryKey = primaryKey;
  }

  public List<Column> getColumnList() {
    return columnList;
  }

  public void setColumnList(List<Column> columnList) {
    this.columnList = columnList;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(String primaryKey) {
    this.primaryKey = primaryKey;
  }
}
