package cn.exsolo.kit.render;


import cn.exsolo.kit.ex.EsBuilderException;

/**
 * Created by prestolive on 2017/7/28.
 * @Author prestolive
 */
public interface IDataRender {

    /**
     * 获取该数据渲染器的说明
     * @return
     */
    public String getDesc();

    /**
     * 获取目标深度路径 支持类似格式：xxx.page.values
     * @return
     */
    public String getTargetPath();

    /**
     * 渲染方法
     * @param target
     * @throws EsBuilderException 理论上render只会查询，不会有业务逻辑，所以这里抛出的是runtime级别错误
     */
    public void pack(Object target) throws EsBuilderException;

}
