/*
 * Copyright (C) 2012 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.ros.internal.message.field;

import org.ros.exception.RosMessageRuntimeException;
import org.ros.internal.message.context.MessageContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author damonkohler@google.com (Damon Kohler)
 */
public class MessageFields {

  private final MessageContext messageContext;
  private final List<Field> fields;

  public MessageFields(MessageContext messageContext) {
    this.messageContext = messageContext;
    List<String> fieldNames = messageContext.getFieldNames();
    Field[] fieldsArr = new Field[fieldNames.size()];
    for (int i = 0; i < fieldsArr.length; i++) {
      String fieldName = fieldNames.get(i);
      fieldsArr[i] = messageContext.getFieldFactory(fieldName).create();
    }
    fields = Collections.unmodifiableList(Arrays.asList(fieldsArr));
  }

  public Field getField(String name) {
    return getField(messageContext.getFieldIndexByName(name));
  }

  public Field getSetterField(String name) {
    return getField(messageContext.getFieldIndexBySetterName(name));
  }

  public Field getGetterField(String name) {
    return getField(messageContext.getFieldIndexByGetterName(name));
  }

  private Field getField(Integer index) {
    return index != null ? fields.get(index) : null;
  }

  public List<Field> getFields() {
    return fields;
  }

  public Object getFieldValue(String name) {
    Field field = getField(name);
    if (field != null) {
      return field.getValue();
    }
    throw new RosMessageRuntimeException("Uknown field: " + name);
  }

  public void setFieldValue(String name, Object value) {
    Field field = getField(name);
    if (field != null) {
      field.setValue(value);
    } else {
      throw new RosMessageRuntimeException("Uknown field: " + name);
    }
  }

  @Override
  public int hashCode() {
    int result = messageContext.hashCode();
    result = 31 * result + fields.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MessageFields)) {
      return false;
    }

    MessageFields that = (MessageFields) o;

    if (!messageContext.equals(that.messageContext)) {
      return false;
    }
    return fields.equals(that.fields);

  }

}
