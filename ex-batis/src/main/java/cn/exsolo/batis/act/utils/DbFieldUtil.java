package cn.exsolo.batis.act.utils;

import cn.exsolo.batis.act.bo.ActSuggestDatatypeBO;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.exsolo.batis.act.utils.StandType.*;

/**
 * @author prestolive
 * @date 2023/3/8
 **/
public class DbFieldUtil {

    public static ActSuggestDatatypeBO getMyUnderstanding(String name, String sourceType,Class fieldClass, DatabaseType targetDatabase) {
        sourceType = sourceType.toLowerCase(Locale.ROOT);
        ActSuggestDatatypeBO vo = new ActSuggestDatatypeBO();
        vo.setName(name);
        vo.setSourceType(sourceType);
        StandType standType = null;
        if (sourceType.contains("char")) {
            if (sourceType.contains("varchar") || sourceType.contains("varying")) {
                standType = VARCHAR;
            } else {
                standType = CHAR;
            }
        }else if(sourceType.contains("text")){
            standType = TEXT;
        }else if (sourceType.contains("decimal") || sourceType.contains("numeric")) {
            standType = StandType.NUMERIC;
        } else if (sourceType.contains("int")) {
            if(fieldClass.getName().toLowerCase(Locale.ROOT).contains("boolean")){
                standType = BOOLEAN;
            }else{
                if (sourceType.contains("small") || sourceType.contains("int2")) {
                    standType = StandType.SMALLINT;
                } else {
                    standType = StandType.BIGINT;
                }
            }

        }else if(sourceType.contains("boolean")){
            standType = BOOLEAN;
        }
        richInfo(vo, sourceType, standType, targetDatabase);
        return vo;
    }

    private static void richInfo(ActSuggestDatatypeBO vo, String sourceType, StandType standType, DatabaseType targetDatabase) {
        vo.setStandType(standType);
        String attr = getAttr(sourceType);
        switch (standType) {
            case CHAR:
            case VARCHAR: {
                if (StringUtils.isNotEmpty(attr)) {
                    vo.setMaxLength(Integer.valueOf(attr));
                }
                vo.setClassType(String.class);
                break;
            }
            case NUMERIC: {
                if (StringUtils.isNotEmpty(attr)) {
                    String[] args = attr.split(",");
                    vo.setMaxLength(Integer.valueOf(args[0]));
                    vo.setScale(Integer.valueOf(args[1]));
                }
                vo.setClassType(BigDecimal.class);
                break;
            }
            case TEXT: {
                vo.setClassType(String.class);
                break;
            }
            case SMALLINT:
            case BIGINT: {
                vo.setClassType(Integer.class);
                break;
            }
            case BOOLEAN: {
                vo.setClassType(Boolean.class);
                break;
            }
            default:
        }
        String targetDatatypeFormat = getDatatypeFormat(targetDatabase, vo.getStandType());
        targetDatatypeFormat = targetDatatypeFormat.replaceAll("#maxLength", String.valueOf(vo.getMaxLength()));
        targetDatatypeFormat = targetDatatypeFormat.replaceAll("#scale", String.valueOf(vo.getScale()));
        vo.setTargetType(targetDatatypeFormat);
    }

    private static String getDatatypeFormat(DatabaseType databaseType, StandType standType) {
        switch (standType) {
            case CHAR: {
                switch (databaseType) {
                    case POSTGRESQL:
                        return "character(#maxLength)";
                }
            }
            case VARCHAR: {
                switch (databaseType) {
                    case POSTGRESQL:
                        return "character varying(#maxLength)";
                }
            }
            case NUMERIC: {
                switch (databaseType) {
                    case POSTGRESQL:
                        return "numeric(#maxLength,#scale)";
                }
            }
            case SMALLINT: {
                switch (databaseType) {
                    case POSTGRESQL:
                        return "smallint";
                }
            }
            case BIGINT: {
                switch (databaseType) {
                    case POSTGRESQL:
                        return "bigint";
                }
            }
            case BOOLEAN: {
                switch (databaseType) {
                    case POSTGRESQL:
                        return "boolean";
                }
            }
            case TEXT: {
                switch (databaseType) {
                    case POSTGRESQL:
                        return "text";
                }
            }
            default:
                throw new RuntimeException(String.format("未能翻译成目标数据格式，数据库：%s，标准类型%s。", databaseType, standType));
        }
    }

    private static Pattern pattern = Pattern.compile("\\((.*?)\\)");

    private static String getAttr(String sourceType) {
        Matcher matcher = pattern.matcher(sourceType);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }


}
