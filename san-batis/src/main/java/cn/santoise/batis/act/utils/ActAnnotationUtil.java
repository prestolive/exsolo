package cn.santoise.batis.act.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.santoise.batis.act.bo.ActTableColumnBO;
import cn.santoise.batis.act.bo.ActTableIndexBO;
import cn.santoise.batis.act.bo.ActTableBO;
import cn.santoise.batis.core.stereotype.Column;
import cn.santoise.batis.core.stereotype.Index;
import cn.santoise.batis.core.stereotype.Indexes;
import cn.santoise.batis.core.stereotype.Table;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wbingy
 * @date 2023/3/8
 **/
public class ActAnnotationUtil {

    public static ActTableBO getTableMeta(Class clz) {
        Table table = AnnotationUtil.getAnnotation(clz, Table.class);
        if (table == null) {
            throw new RuntimeException("target doesn't have Table annotation");
        }
        ActTableBO meta = new ActTableBO();
        meta.setName(table.value());
        //索引
        List<ActTableIndexBO> indexes = new ArrayList<>();
        meta.setIndexes(indexes);
        Annotation[] annas = AnnotationUtil.getAnnotations(clz, false);
        for (Annotation anna : annas) {
            if (anna instanceof Indexes) {
                for (Index indexAnno : ((Indexes) anna).value()) {
                    ActTableIndexBO index = new ActTableIndexBO();
                    index.setTableName(table.value());
                    index.setName(indexAnno.name());
                    index.setUnique(indexAnno.unique());
                    index.setFields(indexAnno.fields());
                    indexes.add(index);
                }
            }
        }
        //字段
        List<ActTableColumnBO> columns = new ArrayList<>();
        meta.setColumns(columns);
        Field[] fields = clz.getDeclaredFields();
        for (Field f : fields) {
            Column col = f.getAnnotation(Column.class);
            if (col == null) {
                continue;
            }
            ActTableColumnBO column = new ActTableColumnBO();
            column.setName(col.name());
            column.setDatatype(col.datatype());
            column.setPrimary(col.primary());
            column.setNullable(col.nullable());
            columns.add(column);
        }
        ActTableColumnBO primaryColumn = columns.stream().filter(row -> row.getPrimary()).findFirst().orElse(null);
        if (primaryColumn != null) {
            meta.setPrimaryKey(primaryColumn.getName());
        }
        return meta;
    }

    public static void main(String[] args) {
    }
}
