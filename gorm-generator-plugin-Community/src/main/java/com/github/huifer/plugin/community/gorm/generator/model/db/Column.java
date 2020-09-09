package com.github.huifer.plugin.community.gorm.generator.model.db;

import com.github.huifer.plugin.community.gorm.generator.utils.TypeChanger;

public class Column {

  private String name;
  private String type;
  private String typeStr;
  private String comment;
  private boolean isPrimaryKey;
  private String property;

  public Column(String name, String typeStr) {
    this.name = name;
    this.typeStr = TypeChanger.clean(typeStr);
    this.type = TypeChanger.getType(this.typeStr);
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTypeStr() {
    return typeStr;
  }

  public void setTypeStr(String typeStr) {
    this.typeStr = typeStr;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public boolean isPrimaryKey() {
    return isPrimaryKey;
  }

  public void setPrimaryKey(boolean primaryKey) {
    isPrimaryKey = primaryKey;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }
}
