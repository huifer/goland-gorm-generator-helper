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
