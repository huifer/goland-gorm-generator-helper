package com.github.huifer.plugin.community.gorm.generator.gui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

public class GormGen extends AnAction {

  @Override
  public void actionPerformed(AnActionEvent e) {
    // TODO: insert action logic here
    Project project = e.getProject();
    if (new Dialog().showAndGet()) {

    }
  }
}
