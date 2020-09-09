package com.github.huifer.plugin.community.gorm.generator.gui;

import com.github.huifer.plugin.community.gorm.generator.model.db.Table;
import com.github.huifer.plugin.community.gorm.generator.utils.FileUtil;
import com.github.huifer.plugin.community.gorm.generator.utils.SqlUtils;
import com.github.huifer.plugin.community.gorm.generator.utils.ToGorm;
import com.google.common.base.CaseFormat;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

public class Dialog extends DialogWrapper {

  private static String SQL;
  private static String PATH;
  TextFieldWithBrowseButton textFieldWithBrowseButton;
  private JTextField fileTextFiled;
  private JPanel contentPane;
  private JTextArea sqlText;
  private JPanel selector_path;

  protected Dialog() {
    super(true);
    init();
    setTitle("gorm_gen");
  }

  protected void doOKAction() {
    super.doOKAction();

    if (!StringUtils.isEmpty(sqlText.getText()) && !sqlText.getText().equals("请输入sql")) {
      String sql = sqlText.getText();
      Table apply = SqlUtils.apply(sql);

      String path = textFieldWithBrowseButton.getText();
      SQL = sql;

      File f = new File(path);
      String packageName = f.getName();
      String s = path + File.separator + CaseFormat.UPPER_UNDERSCORE
          .to(CaseFormat.UPPER_CAMEL, apply.getName()) + ".go";

      String string = ToGorm.getString(apply);

      StringBuilder sb = new StringBuilder(32);
      sb.append("package ").append(packageName);
      sb.append("\n\n");
      sb.append("import \"time\"");
      sb.append("\n\n");
      sb.append(string);
      try {

        File fa = new File(s);
        if (fa.exists()) {
          fa.delete();
        }
        FileUtil.WriteFile(s, sb.toString());


      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void doCancelAction() {

    super.doCancelAction();

  }

  @Override
  public boolean shouldCloseOnCross() {
    return super.shouldCloseOnCross();
  }

  @Override
  protected @Nullable JComponent createCenterPanel() {
    return this.contentPane;
  }

  private void createUIComponents() {
    sqlText = new JTextArea();
    if (StringUtils.isNotBlank(SQL)) {
      sqlText.setText(SQL);

    }
    else {
      sqlText.setText("请输入sql");
    }

    selector_path = new JPanel();

    textFieldWithBrowseButton = new TextFieldWithBrowseButton();
    fileTextFiled = new JTextField();

    FileChooserDescriptor chooserDescriptor = new FileChooserDescriptor(true, true, true, true,
        true, true);
    TextBrowseFolderListener listener = new TextBrowseFolderListener(chooserDescriptor);
    textFieldWithBrowseButton.addBrowseFolderListener(listener);
    textFieldWithBrowseButton.setText("输入输出文件位置");
    selector_path.setLayout(new BorderLayout());
    selector_path.setPreferredSize(new Dimension(400, 40));
    selector_path.add(textFieldWithBrowseButton, BorderLayout.CENTER);
  }
}
