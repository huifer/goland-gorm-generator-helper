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

package com.github.huifer.plugin.community.gorm.generator.ultimate.gorm.generator.gui;

import com.github.huifer.plugin.community.gorm.generator.ultimate.gorm.generator.model.db.Column;
import com.github.huifer.plugin.community.gorm.generator.ultimate.gorm.generator.model.db.Table;
import com.intellij.database.model.DasColumn;
import com.intellij.database.model.DataType;
import com.intellij.database.psi.DbTable;
import com.intellij.database.util.DasUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiElement;
import com.intellij.util.containers.JBIterable;

import java.util.ArrayList;
import java.util.List;

public class Ademo extends AnAction {

  List<Table> tb = new ArrayList<>();
  /**
   * 当前选中的表
   */
  private DbTable selectDbTable;
  /**
   * 所有选中的表
   */
  private List<DbTable> dbTableList;

  @Override
  public void actionPerformed(AnActionEvent e) {
    DbTable selectDbTable = null;
    PsiElement psiElement = e.getData(LangDataKeys.PSI_ELEMENT);
    if (psiElement instanceof DbTable) {
      selectDbTable = (DbTable) psiElement;
    }
    if (selectDbTable == null) {
      return;
    }
    // get select all table
    PsiElement[] psiElements = e.getData(LangDataKeys.PSI_ELEMENT_ARRAY);
    if (psiElements == null || psiElements.length == 0) {
      return;
    }

    List<DbTable> dbTableList = new ArrayList<>();
    for (PsiElement element : psiElements) {
      if (!(element instanceof DbTable)) {
        continue;
      }
      DbTable dbTable = (DbTable) element;
      dbTableList.add(dbTable);
      List<Column> tableInfo = getTableInfo(dbTable);
      Table table = new Table();

      table.setName(dbTable.getName());
      table.setColumnList(tableInfo);
      tb.add(table);
    }
    if (dbTableList.isEmpty()) {
      return;
    } else {
      if (new Dialog(tb).showAndGet()) {

      }
    }

  }

  private List<Column> getTableInfo(DbTable dbTable) {
    // table name
    String name = dbTable.getName();
    // table comment
    String comment = dbTable.getComment();
    // all columns
    JBIterable<? extends DasColumn> columns = DasUtil.getColumns(dbTable);
    // convert plugin's Column
    List<Column> columnsList = new ArrayList<>();

    for (DasColumn column : columns) {

      DataType dataType = column.getDataType();
      String columnName = column.getName();
      String columnComment = column.getComment();
      String typeName = dataType.typeName;
      Column column1 = new Column();
      column1.setComment(columnComment);
      column1.setName(columnName);
      column1.setType(typeName);
      column1.setTypeStr(typeName);
      column1.setPrimaryKey(DasUtil.isPrimary(column));

      columnsList.add(column1);
    }
    return columnsList;

  }

}
