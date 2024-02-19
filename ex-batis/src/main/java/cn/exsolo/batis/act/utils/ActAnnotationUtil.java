package cn.exsolo.batis.act.utils;

import cn.exsolo.batis.act.bo.ActTableBO;
import cn.exsolo.batis.act.bo.ActTableColumnBO;
import cn.exsolo.batis.act.bo.ActTableIndexBO;
import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.crypto.SecureUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author prestolive
 * @date 2021/3/8
 **/
public class ActAnnotationUtil {

    public static ActTableBO getTableMeta(Class clz) {
        Table table = AnnotationUtil.getAnnotation(clz, Table.class);
        if (table == null) {
            throw new RuntimeException("target doesn't have Table annotation");
        }
        ActTableBO meta = new ActTableBO();
        meta.setName(table.name());
        //索引
        List<ActTableIndexBO> indexes = new ArrayList<>();
        meta.setIndexes(indexes);
        //FIXME 目前都是都通过唯一索引实现， 待修复
        UniqueConstraint[] uqIndexes = table.uniqueConstraints();
        if(uqIndexes!=null){
            for(UniqueConstraint uqIndex:uqIndexes){
                String columns = StringUtils.join(uqIndex.columnNames(),",");
                ActTableIndexBO index = new ActTableIndexBO();
                index.setTableName(table.name());
                index.setName(getIndexName("UQ",table.name(),columns));
                index.setUnique(true);
                index.setFields(columns);
                indexes.add(index);
            }
        }
        Index[] cindexes = table.indexes();
        if(cindexes!=null){
            for(Index cindex:cindexes){
                ActTableIndexBO index = new ActTableIndexBO();
                index.setTableName(table.name());
                index.setName(getIndexName("IDX",table.name(),cindex.columnList()));
                index.setUnique(index.isUnique());
                index.setFields(cindex.columnList());
                indexes.add(index);
            }
        }
        //字段
        List<ActTableColumnBO> columns = new ArrayList<>();
        meta.setColumns(columns);
//        Field[] fields = clz.getDeclaredFields();
        Field[] fields = ReflectUtil.getFields(clz);
        for (Field f : fields) {
            Column col = f.getAnnotation(Column.class);
            if (col == null) {
                continue;
            }
            ActTableColumnBO column = new ActTableColumnBO();
            column.setName(col.name());
            column.setTableName(table.name());
            //Fixme 自动形成数据类型
            column.setDatatype(col.columnDefinition());
            column.setNullable(col.nullable());
            column.setFieldClass(f.getDeclaringClass());
            if(f.getAnnotation(Id.class)!=null){
                column.setPrimary(true);
            }else{
                column.setPrimary(false);
            }
            columns.add(column);
        }
        ActTableColumnBO primaryColumn = columns.stream().filter(row -> row.getPrimary()).findFirst().orElse(null);
        if (primaryColumn != null) {
            meta.setPrimaryKey(primaryColumn.getName());
        }
        return meta;
    }

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};


    private static String getIndexName(String tag,String tableName,String fields){
        String str = String.format("%s:%s",tableName,fields);
        String md5=  SecureUtil.md5(str);
        StringBuffer shortBuffer = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            String block = md5.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(block, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return tag+"_"+shortBuffer.toString();
    }

    public static void main(String[] args) {
    }
}
