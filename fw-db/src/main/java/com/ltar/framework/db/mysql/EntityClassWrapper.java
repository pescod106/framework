package com.ltar.framework.db.mysql;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import com.ltar.framework.base.util.ReflectUtils;
import com.ltar.framework.db.mysql.annotation.*;
import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: changzhigao
 * @date: 2018-12-31
 * @version: 1.0.0
 */
public class EntityClassWrapper {
    private static ConcurrentHashMap<Class<?>, EntityClassWrapper> wrapperCache = new ConcurrentHashMap<>();
    private boolean includeAllField;
    private final Class<?> kclass;
    private final List<Field> fields;
    private final Table table;
    private final String tableName;
    private final LinkedHashMap<String, ColumnField> columnFields;
    private final LinkedHashMap<String, ColumnField> dbColumnFields;
    //    private final ColumnField idColumnField;
//    private final ColumnField VersionField;
//    private final LinkedHashMap<String, ReferenceField> referenceFields;
//    private final LinkedHashMap<String, ReferencesField> referencesField;
    private final List<EntityField> entityFields = null;

    public EntityClassWrapper(Class<?> kclass) {
        if (!ReflectUtils.isAnnotationPresent(kclass, Table.class)) {
            throw new IllegalArgumentException("no Table annotation");
        } else {
            this.includeAllField = ReflectUtils.isAnnotationPresent(kclass, IncludeAllField.class);
            this.kclass = kclass;
            ReflectUtils.ClassAnnotation<Table> classAnnotation = ReflectUtils.getAnnotation(kclass, Table.class);
            this.table = classAnnotation.getAnnotation();
            String name = this.table.name();
            if (Strings.isNullOrEmpty(name)) {
                name = classAnnotation.getClass().getSimpleName();
                this.tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
            } else {
                this.tableName = name;
            }
            this.fields = ReflectUtils.getFields(kclass, true);
            this.columnFields = new LinkedHashMap<>();
            this.dbColumnFields = new LinkedHashMap<>();
            // TODO
        }
    }

    // TODO check is correct
    private void scanEntiryFields() {
        for (Field field : fields) {
            Class type = field.getType();
            boolean isEntityField = (!ReflectUtils.isAnnotationPresent(type, Table.class)
                    && null == field.getAnnotation(EntityReference.class)
                    && null == field.getAnnotation(EntityReferences.class));
            if (isEntityField) {
                this.entityFields.add(new EntityField(field));
            }
        }
    }

    private void scanCoulumnFields() {
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            Column column = field.getAnnotation(Column.class);
            ExcludeField excludeField = field.getAnnotation(ExcludeField.class);
            if (null != excludeField) {

            }
        }
    }

    public static class EntityField extends BaseField {
        public EntityField(Field field) {
            super(field);
        }
    }

    public static class ReferencesField extends BaseField {
        private final EntityReferences references;

        public ReferencesField(Field field) {
            super(field);
            this.references = field.getAnnotation(EntityReferences.class);
        }

        public EntityReferences getReferences() {
            return references;
        }

        public Class<?> getReferenceENtityClass() {
            return this.references.referenceClass();
        }

        public String getProperty() {
            return this.references.property();
        }

        public String getOrderBy() {
            return this.references.orderBy();
        }
    }

    public static class ReferenceField extends BaseField {
        private final EntityReference reference;

        public ReferenceField(Field field) {
            super(field);
            this.reference = field.getAnnotation(EntityReference.class);
        }

        public EntityReference getReference() {
            return reference;
        }

        public String getReferenceProperty() {
            String referenceProperty = this.reference.referenceProperty();
            if (Strings.isNullOrEmpty(referenceProperty)) {
                referenceProperty = this.field.getName() + "Id";
            }
            return referenceProperty;
        }

        public boolean isInverse() {
            return this.reference.inverse();
        }

        public Class<?> getReferenceEntityClass() {
            return this.field.getType();
        }

    }

    public static class ColumnField extends BaseField {
        private Id id;
        private Column column;
        private String columnName;
        private boolean isUpdatedAt;
        private boolean isUpdatedBy;
        private boolean isCreatedAt;
        private boolean isCreatedBy;
        private boolean isVersion;

        public ColumnField(Field field) {
            super(field);
            this.id = field.getAnnotation(Id.class);
            this.column = field.getAnnotation(Column.class);

            if (null != column && !Strings.isNullOrEmpty(this.column.name())) {
                this.columnName = this.column.name();
            }
            this.isUpdatedAt = "updated_at".equalsIgnoreCase(this.columnName);
            this.isUpdatedAt = "updated_by".equalsIgnoreCase(this.columnName);
            this.isUpdatedAt = "created_at".equalsIgnoreCase(this.columnName);
            this.isUpdatedAt = "created_by".equalsIgnoreCase(this.columnName);
            this.isUpdatedAt = "version".equalsIgnoreCase(this.columnName);
        }

        public boolean isId() {
            return null != this.id;
        }

        public String getColumnName() {
            return columnName;
        }

        public boolean isUpdatedAt() {
            return isUpdatedAt;
        }

        public boolean isUpdatedBy() {
            return isUpdatedBy;
        }

        public boolean isCreatedAt() {
            return isCreatedAt;
        }

        public boolean isCreatedBy() {
            return isCreatedBy;
        }

        public boolean isVersion() {
            return isVersion;
        }
    }

    public static class BaseField {
        protected final Field field;

        public BaseField(Field field) {
            this.field = field;
        }

        public Field getField() {
            return field;
        }

        public String getName() {
            return this.field.getName();
        }

        public Class<?> getType() {
            return this.field.getType();
        }

        public Object get(Object entity) {
            try {
                return this.field.get(entity);
            } catch (Exception e) {
                throw new BadFieldException(e.getMessage(), e);
            }
        }

        public void set(Object entity, Object value) {
            try {
                this.field.set(entity, value);
            } catch (Exception e) {
                throw new BadFieldException(e.getMessage(), e);
            }
        }

        public Object getJdbcValue(Object entity) {
            Object value = this.get(entity);
            if (value instanceof Enum) {
                value = value.toString();
            } else if (value instanceof DateTime) {
                value = ((DateTime) value).toDate();
            } else if (this.asJson(entity)) {
                value = JSONObject.toJSONString(entity);
            }
            return value;
        }

        private boolean asJson(Object value) {
            if (null == value) {
                return false;
            } else {
                return value.getClass().isArray()
                        || value instanceof List
                        || value instanceof Map
                        || value.getClass().getName().startsWith("com.");

            }
        }
    }

    static class BadFieldException extends DataAccessException {

        public BadFieldException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }
}
