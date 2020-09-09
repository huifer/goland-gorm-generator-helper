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

import com.github.huifer.plugin.community.gorm.generator.ultimate.gorm.generator.model.db.Table;
import com.github.huifer.plugin.community.gorm.generator.ultimate.gorm.generator.util.FileUtil;
import com.github.huifer.plugin.community.gorm.generator.ultimate.gorm.generator.util.ToGorm;
import com.google.common.base.CaseFormat;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Dialog extends DialogWrapper {
  TextFieldWithBrowseButton textFieldWithBrowseButton;
  List<Table> tb;
  private JPanel contentPane;
  private JPanel selector_path;
  private JTextField fileTextFiled;

  public Dialog(List<Table> tb) {
    super(true);
    this.tb = tb;
    init();
    setTitle("gorm gen");
  }

  @Override
  public JPanel getContentPane() {
    return contentPane;
  }

  @Override
  protected @Nullable JComponent createCenterPanel() {
    return this.contentPane;
  }

  @Override
  public void doCancelAction() {
    super.doCancelAction();
  }

  @Override
  protected void doOKAction() {
    StringBuilder sb = new StringBuilder(32);
    for (Table table : tb) {
      String string = ToGorm.getString(table);
      sb.append(string).append("\n\n");
    }

    String path = textFieldWithBrowseButton.getText();
    File f = new File(path);
    String packageName = f.getName();


    String s = path + File.separator + CaseFormat.UPPER_UNDERSCORE
        .to(CaseFormat.UPPER_CAMEL, "Model") + ".go";


    StringBuilder outPut = new StringBuilder(32);
    if (packageName.equals("src")) {
      outPut.append("package ").append("main");
    } else {
      outPut.append("package ").append(packageName);
    }
    outPut.append("\n\n");
    outPut.append("import \"time\"");
    outPut.append("\n\n");
    outPut.append(sb);


    try {

      File fa = new File(s);
      if (fa.exists()) {
        fa.delete();
      }
      FileUtil.WriteFile(s, outPut.toString());

    } catch (IOException e) {
      e.printStackTrace();
    }


    super.doOKAction();
  }

  private void createUIComponents() {
    selector_path = new JPanel();

    textFieldWithBrowseButton = new TextFieldWithBrowseButton();
    fileTextFiled = new JTextField();

    FileChooserDescriptor chooserDescriptor = new FileChooserDescriptor(true, true, true, true,
        true, true);
    TextBrowseFolderListener listener = new TextBrowseFolderListener(chooserDescriptor);
    textFieldWithBrowseButton.addBrowseFolderListener(listener);
    textFieldWithBrowseButton.setText("select out file path");
    selector_path.setLayout(new BorderLayout());
    selector_path.setPreferredSize(new Dimension(400, 40));
    selector_path.add(textFieldWithBrowseButton, BorderLayout.CENTER);
  }
}
