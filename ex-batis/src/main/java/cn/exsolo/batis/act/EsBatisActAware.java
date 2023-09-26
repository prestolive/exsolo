package cn.exsolo.batis.act;

import cn.hutool.crypto.SecureUtil;
import cn.exsolo.batis.act.service.DdlServcie;
import cn.exsolo.comm.utils.ExAnnotationUtil;
import cn.exsolo.batis.act.bo.ActSuggestDatatypeBO;
import cn.exsolo.batis.act.dto.ActDdTableColumnDTO;
import cn.exsolo.batis.act.dto.ActDdTableIndexDTO;
import cn.exsolo.batis.act.utils.ActAnnotationUtil;
import cn.exsolo.batis.act.utils.DatabaseType;
import cn.exsolo.batis.act.bo.ActTableBO;
import cn.exsolo.batis.act.utils.DbFieldUtil;
import cn.exsolo.batis.core.stereotype.Table;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * ACT
 *
 * @author prestolive
 * @date 2021/3/8
 **/
@Transactional(rollbackOn = Exception.class)
@Component
public class EsBatisActAware implements ApplicationContextAware {

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Autowired
    private DdlServcie ddlServcie;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //从注解构建
        List<Class<?>> list = ExAnnotationUtil.getAnnotationFromContext(applicationContext, Table.class);
        List<ActTableBO> actTables = list.stream().map(clz -> {
            ActTableBO table = ActAnnotationUtil.getTableMeta(clz);
            table.getColumns().forEach(column->{
                ActSuggestDatatypeBO actSuggestDatatypeBO = DbFieldUtil.getMyUnderstanding(column.getName(),column.getDatatype(),column.getFieldClass(),getDatabaseType());
                column.setDatatypeUnderstandingBO(actSuggestDatatypeBO);
            });
            return table;
        }).collect(Collectors.toList());
        //从数据库读取实时数据结构
        List<ActDdTableColumnDTO> dbColumnAll = ddlServcie.selectTableColumnAll();
        List<ActDdTableIndexDTO> dbIndexAll = ddlServcie.selectTableIndexAll();
        for (ActTableBO table : actTables) {
            //当前对象生成的指纹
            String currStamp = getTableStamp(table);
            //获取数据实时结构的指纹
            List<ActDdTableColumnDTO> dbColumns = dbColumnAll.stream().filter(row -> row.getTableName().equalsIgnoreCase(table.getName())).collect(Collectors.toList());
            List<ActDdTableIndexDTO> dbIndexes = dbIndexAll.stream().filter(row -> row.getTableName().equalsIgnoreCase(table.getName())).collect(Collectors.toList());
            String dbStamp = getDatabaseTableStamp(table.getName(), dbColumns, dbIndexes);
            //如果相等就是没有变化
            if (currStamp.equals(dbStamp)) {
                continue;
            }
            if(dbColumns.size()==0){
                ddlServcie.createTable(table);
            }else{
                ddlServcie.modifyTable(table,dbColumns,dbIndexes);
            }

        }

    }


    public DatabaseType getDatabaseType() {
        String url = dataSourceUrl;
        if (url != null) {
            if (url.contains(":mysql:")) {
                return DatabaseType.MYSQL;
            } else if (url.contains(":postgresql:")) {
                return DatabaseType.POSTGRESQL;
            } else if (url.contains(":oracle:")) {
                return DatabaseType.ORACLE;
            } else if (url.contains(":sqlserver:")) {
                return DatabaseType.SQLSERVER;
            } else if (url.contains(":h2:")) {
                return DatabaseType.H2;
            }
        }
        return null;
    }


    private String getTableStamp(ActTableBO tableVO) {
        final List<String> list = new ArrayList<>();
        list.add(tableVO.getName());
        tableVO.getColumns().forEach(column -> {
            list.add(column.getName() + column.getActSuggestDatatypeBO().getTargetType());
        });
        tableVO.getIndexes().forEach(index -> {
            list.add(index.getName() + (index.isUnique() ? "1" : "0") + index.getFields());
        });
        List<String> results = list.stream().map(str -> str.toLowerCase(Locale.ROOT)).sorted().collect(Collectors.toList());
        return SecureUtil.md5(StringUtils.join(results, ""));
    }

    /**
     * 获取数据库实时结构的指纹
     *
     * @param table_name
     * @param dbColumns
     * @param dbIndexes
     * @return
     */
    private String getDatabaseTableStamp(String table_name,
                                         List<ActDdTableColumnDTO> dbColumns, List<ActDdTableIndexDTO> dbIndexes) {
        final List<String> list = new ArrayList<>();
        list.add(table_name);
        dbColumns.forEach(column -> {
            list.add(column.getColumnName() + column.getColumnType());
        });
        dbIndexes.forEach(index -> {
            list.add(index.getIndexName() + (index.getUnique() ? "1" : "") + index.getIndexFields());
        });
        List<String> results = list.stream().map(str -> str.toLowerCase(Locale.ROOT)).sorted().collect(Collectors.toList());
        return SecureUtil.md5(StringUtils.join(results, ""));
    }


}
