package cn.exsolo.batis.act.bo;

import cn.exsolo.batis.act.utils.StandType;

/**
 * 转换工具理解并提取的字段信息
 * @author prestolive
 * @date 2023/3/8
 **/
public class ActSuggestDatatypeBO {

    private String name;

    private String sourceType;

    private int maxLength;

    private int scale;

    private StandType StandType;

    private String targetType;

    private Class classType;

    public cn.exsolo.batis.act.utils.StandType getStandType() {
        return StandType;
    }

    public void setStandType(cn.exsolo.batis.act.utils.StandType standType) {
        StandType = standType;
    }

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
}
