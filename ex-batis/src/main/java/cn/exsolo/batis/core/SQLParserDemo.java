package cn.exsolo.batis.core;

import cn.exsolo.comm.ex.ExDevException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.schema.Column;

public class SQLParserDemo {
    public static void main(String[] args) {
        TinyCache<String,String> cache = new TinyCache<String,String>(100);
        long t= System.currentTimeMillis();
//        for(int i=0;i<1000;i++){
            try {
                String sql = "select * from user a where age>10 order by a.id desc,a.code";
                String sqlTemplate = cache.get(sql);
                if(sqlTemplate==null){
                    Statement statement =  CCJSqlParserUtil.parse(sql);
                    if(statement instanceof Select){
                        Select select = (Select) statement;
                        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
                        Expression where = plainSelect.getWhere();
                        Expression newCondition = new AndExpression(where, new Column("_#wmj#wq#wby#2025_"));
                        plainSelect.setWhere(newCondition);
                        sqlTemplate= select.toString();
                    }else{
                        throw new ExDevException("非Select语句暂时不支持");
                    }

                    cache.put(sql,sqlTemplate);
                }
                sqlTemplate = sqlTemplate.replace("_#wmj#wq#wby_","xx.bb = 'abcd'");
                System.out.printf(sqlTemplate);
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }

        long e = System.currentTimeMillis();
        System.out.println(String.format("\nuse:%d",e-t));
    }
}