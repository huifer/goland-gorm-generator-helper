package com.github.huifer.plugin.community.gorm.generator.utils;


import com.github.huifer.plugin.community.gorm.generator.model.db.Column;
import com.github.huifer.plugin.community.gorm.generator.model.db.Table;
import com.github.huifer.plugin.community.gorm.generator.model.enums.MySqlDataType;
import com.google.common.base.CaseFormat;

public class SqlUtilsTest {

  public static void main(String[] args) {
    Table apply = SqlUtils.apply("CREATE TABLE `issue` (\n"
        + "  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',\n"
        + "  `new_title` varchar(250) NOT NULL DEFAULT 'title_value' COMMENT '标题',\n"
        + "  `context` varchar(255) DEFAULT NULL COMMENT '作业内容',\n"
        + "  `start_time` datetime DEFAULT NULL COMMENT '任务开始时间',\n"
        + "  `end_time` datetime DEFAULT NULL COMMENT '任务结束时间',\n"
        + "  `estimate_start_time` datetime DEFAULT NULL COMMENT '预计开始时间',\n"
        + "  `estimate_end_time` datetime DEFAULT NULL COMMENT '预计结束时间',\n"
        + "  `actual_work_time` bigint(32) DEFAULT NULL COMMENT '实际消费时间 毫秒',\n"
        + "  `state` varchar(30) DEFAULT NULL COMMENT '状态',\n"
        + "  `priority` varchar(30) DEFAULT NULL COMMENT '优先级 待办，进行中，已完成，已确认，处理中',\n"
        + "  `acc_user_id` bigint(32) DEFAULT NULL COMMENT '处理人id',\n"
        + "  `pid` bigint(32) DEFAULT NULL COMMENT '上级任务',\n"
        + "  `create_time` datetime DEFAULT NULL COMMENT '创建时间',\n"
        + "  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人',\n"
        + "  `update_time` datetime DEFAULT NULL COMMENT '修改时间',\n"
        + "  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人',\n"
        + "  `version` bigint(32) DEFAULT '0' COMMENT '版本',\n"
        + "  `is_delete` tinyint(4) DEFAULT '0' COMMENT '是否删除标记',\n"
        + "  `total_price` decimal(10,2) unsigned DEFAULT '1.00' COMMENT '价格',\n"
        + "  PRIMARY KEY (`id`),\n"
        + "  UNIQUE KEY `aa` (`new_title`)\n"
        + ") ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COMMENT='任务表';");

    StringBuilder s = new StringBuilder(32);

    String name = apply.getName();

    s.append("type  ").append(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name))
        .append("  struct  {\n");

    for (Column column : apply.getColumnList()) {
      String columnName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, column.getName());

      s.append("  ").append(columnName).append("  ")
          .append(MySqlDataType.convertToGoType(column.getTypeStr())).append("  ");

      if (column.getName().equals(apply.getPrimaryKey())) {
        s.append("`gorm:\"column:").append(column.getName()).append("  ").append("json:")
            .append("\"")

            .append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, columnName))
            .append("\"").append("`").append("\r\n");

      }
      else {
        s.append("`gorm:\"column:").append(column.getName()).append("\"").append("  ")
            .append("json:").append("\"")
            .append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, columnName)).append("\"")
            .append("`").append("\r\n");

      }
    }
    s.append("}");

    System.out.println(s.toString());
  }
}