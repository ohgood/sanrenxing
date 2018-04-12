package com.sanrenxing.shop.db.shop;

import com.sanrenxing.shop.db.annotation.Column;
import com.sanrenxing.shop.db.annotation.Ignore;
import com.sanrenxing.shop.db.annotation.Table;
import com.sanrenxing.shop.util.HumpUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created on 2017/7/28.
 *
 * @author tony
 */
public class SqlProvider {

    private static final Logger log = LoggerFactory.getLogger(SqlProvider.class);

    /**
     * 插入数据
     *
     * @param bean  bean对象或者bean的集合对象
     * @return      拼装后的sql
     */
    public String insert(Object bean) {
        Class<?> beanClass = bean.getClass();
        String tableName = getTableName(beanClass);
        List<Field> fields = getFields(beanClass);
        StringBuilder insertSql = new StringBuilder();
        List<String> insertParas = new ArrayList<>();
        List<String> insertParaNames = new ArrayList<>();
        insertSql.append("INSERT IGNORE INTO ").append(tableName).append("(");
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object object = field.get(bean);
                if (object != null) {
                    insertParaNames.add(getColumnName(field));

                    if (object instanceof Collection)
                        insertParas.add("#{" + field.getName() + ", typeHandler=com.sanrenxing.shop.util.SetTypeHandler}");
                    else
                        insertParas.add("#{" + field.getName() + "}");
                }
            }
        } catch (Exception e) {
            log.warn("get insert sql is exceptoin:" , e);
        }
        for (int i = 0; i < insertParaNames.size(); i++) {
            insertSql.append(insertParaNames.get(i));
            if (i != insertParaNames.size() - 1)
                insertSql.append(",");
        }
        insertSql.append(")").append(" VALUES(");
        for (int i = 0; i < insertParas.size(); i++) {
            insertSql.append(insertParas.get(i));
            if (i != insertParas.size() - 1)
                insertSql.append(",");
        }
        insertSql.append(")");
        return insertSql.toString();
    }

    /**
     * 根据id更新数据
     *
     * @param bean  bean对象分装的更新条件
     * @return      拼装后的sql
     */
    public String update(Object bean) {
        Class<?> beanClass = bean.getClass();
        String tableName = getTableName(beanClass);
        List<Field> fields = getFields(beanClass);
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("UPDATE ").append(tableName).append(" SET ");
        try {
            boolean firstColumn = true;
            for (Field field : fields) {
                String columnName = getColumnName(field);
                field.setAccessible(true);
                Object beanValue = field.get(bean);
                if (beanValue != null) {
                    if (firstColumn) {
                        updateSql.append(" ");
                        firstColumn = false;
                    } else {
                        updateSql.append(", ");
                    }
                    if (beanValue instanceof Collection)
                        updateSql.append(columnName).append("=#{").append(field.getName()).append(", typeHandler=com.sanrenxing.shop.util.SetTypeHandler}");
                    else
                        updateSql.append(columnName).append("=#{").append(field.getName()).append("}");
                }
            }
        } catch (Exception e) {
            log.warn("get update sql is exceptoin:" , e);
        }
        updateSql.append(" WHERE id=#{id}");
        return updateSql.toString();
    }

    /**
     * 删除数据
     *
     * @param bean  bean对象分装的删除条件
     * @return      拼装后的sql
     */
    public String delete(Object bean) {
        Class<?> beanClass = bean.getClass();
        String tableName = getTableName(beanClass);
        List<Field> fields = getFields(beanClass);
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("DELETE FROM ").append(tableName).append(" WHERE ");
        try {
            boolean firstField = true;
            for (Field field : fields) {
                String columnName = getColumnName(field);
                field.setAccessible(true);
                Object beanValue = field.get(bean);
                if (beanValue != null) {
                    if (!firstField) {
                        deleteSql.append(" AND ");
                    }
                    deleteSql.append(columnName).append("=#{").append(field.getName()).append("}");
                    if (firstField) {
                        firstField = false;
                    }
                }
            }
        } catch (Exception e) {
            log.warn("get delete sql is exceptoin:" , e);
        }
        return deleteSql.toString();
    }


    /**
     * 查询符合条件的数据量
     *
     * @param bean   bean对象分装的删除条件
     * @return       拼装后的sql
     */
    public String count(Object bean) {
        Class<?> beanClass = bean.getClass();
        String tableName = getTableName(beanClass);
        List<Field> fields = getFields(beanClass);
        StringBuilder selectSql = new StringBuilder();
        List<String> selectParaNames = new ArrayList<>();
        List<String> selectParas = new ArrayList<>();
        selectSql.append("SELECT COUNT(id)");
        try {
            for (Field field : fields) {
                String columnName = getColumnName(field);
                field.setAccessible(true);
                Object object = field.get(bean);
                if (object != null) {
                    selectParaNames.add(columnName);
                    selectParas.add("#{" + field.getName() + "}");
                }
            }
        } catch (Exception e) {
            log.warn("根据条件查询数据时报错:" , e);
        }
        selectSql.append(" FROM ").append(tableName);
        return appendWhere(selectSql, selectParaNames, selectParas).toString();
    }




    private StringBuilder appendWhere(StringBuilder sb, List<String> selectParaNames, List<String> selectParas) {
        if (!CollectionUtils.isEmpty(selectParaNames)) {
            sb.append(" WHERE ");
            for (int i = 0; i < selectParaNames.size(); i++) {
                sb.append(selectParaNames.get(i)).append("=").append(selectParas.get(i));
                if (i != selectParaNames.size() - 1)
                    sb.append(" AND ");
            }
        }
        return sb;
    }



    /**
     * 查询数据
     *
     * @param bean  bean对象分装的删除条件
     * @return      拼装后的sql
     */
    public String find(Object bean) {
        Class<?> beanClass = bean.getClass();
        String tableName = getTableName(beanClass);
        List<Field> fields = getFields(beanClass);
        StringBuilder selectSql = new StringBuilder();
        List<String> selectParaNames = new ArrayList<>();
        List<String> selectParas = new ArrayList<>();
        selectSql.append("SELECT ");
        try {
            boolean firstField = true;
            for (Field field : fields) {
                if (!firstField)
                    selectSql.append(", ");
                String columnName = getColumnName(field);
                selectSql.append(columnName);
                if (firstField)
                    firstField = false;
                field.setAccessible(true);
                Object object = field.get(bean);
                if (object != null) {
                    selectParaNames.add(columnName);
                    selectParas.add("#{" + field.getName() + "}");
                }
            }
        } catch (Exception e) {
            log.warn("根据条件查询数据时报错:" , e);
        }
        selectSql.append(" FROM ").append(tableName);
        return appendWhere(selectSql, selectParaNames, selectParas).toString();
    }

    /**
     * 根据bean对象获取表名  如果有Table注解，用注解value值；如果没有，对象名改下划线格式。
     *
     * @param beanClass  bean对象
     * @return    表名
     */
    private String getTableName(Class<?> beanClass) {
        String tableName;
        Table table = beanClass.getAnnotation(Table.class);
        if (table != null) {
            tableName = "`" + table.value() + "`";
        } else {
            tableName = "`" + HumpUtils.humpToUnderLine(beanClass.getSimpleName()) + "`";
        }
        return tableName;
    }

    /**
     * 根据Field对象，获取数据库列名
     *
     * @param field   Field对象
     * @return        数据库列名
     */
    private String getColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        String columnName;
        if (column != null) {
            columnName = "`" + column.value() + "`";
        } else {
            columnName = "`" + HumpUtils.humpToUnderLine(field.getName()) + "`";
        }
        return columnName;
    }

    /**
     * 递归获取一个类及其父类的所有值域
     *
     * @param classBean      Class对象
     * @return               对象所有的映射字段
     */
    private List<Field> getFields(Class<?> classBean) {
        List<Field> list = Stream.of(classBean.getDeclaredFields()).filter(a -> a.getAnnotation(Ignore.class) == null).collect(Collectors.toList());
        if(classBean.getSuperclass() != null) {
            list.addAll(getFields(classBean.getSuperclass()));
        }
        return list;
    }

}
