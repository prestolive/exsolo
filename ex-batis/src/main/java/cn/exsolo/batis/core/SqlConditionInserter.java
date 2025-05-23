package cn.exsolo.batis.core;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlConditionInserter {

    private static final Pattern WHERE_PATTERN = Pattern.compile("\\bWHERE\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern FROM_PATTERN = Pattern.compile("\\bFROM\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern SUBSEQUENT_CLAUSE_PATTERN = Pattern.compile(
            "\\b(GROUP BY|HAVING|ORDER BY|LIMIT)\\b", Pattern.CASE_INSENSITIVE);

    public static String addCondition(String sql,Condition cond, Map<String,Object> values){
        StringBuilder sb = new StringBuilder();
        CommonOrmUtils.generateConditionSql(sb,null,cond,values);
        String condSql = sb.toString();
        if(condSql.startsWith(" and")){
            condSql = condSql.substring(4);
        }
        return addCondition(sql,condSql);
    }
    public static String addCondition(String sql, String newCondition) {
        // 处理存在WHERE子句的情况
        Matcher whereMatcher = WHERE_PATTERN.matcher(sql);
        if (whereMatcher.find()) {
            int whereEnd = whereMatcher.end();
            String afterWhere = sql.substring(whereEnd).trim();
            StringBuilder sb = new StringBuilder(sql);

            // 根据WHERE后是否有内容决定是否添加AND
            if (afterWhere.isEmpty()) {
                sb.insert(whereEnd, " " + newCondition);
            } else {
                sb.insert(whereEnd, " AND " + newCondition);
            }
            return sb.toString();
        } else {
            // 处理不存在WHERE子句的情况
            Matcher fromMatcher = FROM_PATTERN.matcher(sql);
            if (fromMatcher.find()) {
                int fromEnd = fromMatcher.end();
                Matcher subsequentMatcher = SUBSEQUENT_CLAUSE_PATTERN.matcher(sql);
                int insertPos = findInsertPosition(sql, fromEnd, subsequentMatcher);

                StringBuilder sb = new StringBuilder(sql);
                sb.insert(insertPos, " WHERE " + newCondition + " ");
                return sb.toString();
            } else {
                throw new IllegalArgumentException("SQL does not contain a FROM clause");
            }
        }
    }

    private static int findInsertPosition(String sql, int fromEnd, Matcher subsequentMatcher) {
        if (subsequentMatcher.find(fromEnd)) {
            return subsequentMatcher.start();
        } else {
            // 检查是否存在已有的WHERE（例如在子查询中）
            Matcher whereCheck = WHERE_PATTERN.matcher(sql);
            if (whereCheck.find(fromEnd)) {
                return whereCheck.start();
            }
            return sql.length();
        }
    }

    public static void main(String[] args) {
        String sql1 = "SELECT * FROM users WHERE age > 18";
        String sql2 = "SELECT * FROM products ORDER BY price";
        Condition cond = new Condition();
        cond.eq("name","John");
        System.out.println(addCondition(sql1, cond,new HashMap<>()));
        // 输出: SELECT * FROM users WHERE age > 18 AND name = 'John'
        
        System.out.println(addCondition(sql2, "stock > 0")); 
        // 输出: SELECT * FROM products WHERE stock > 0 ORDER BY price
    }
}