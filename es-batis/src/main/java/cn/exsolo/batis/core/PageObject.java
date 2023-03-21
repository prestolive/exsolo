package cn.exsolo.batis.core;

import java.util.List;

/**
 * Created by prestolive on 2017/6/20.
 */
public class PageObject<T>  {

    public PageObject() {

    }

    public PageObject(int pageSize, int current) {
        this.pageSize = pageSize;
        this.current = current;
    }

    private List<T> values;

    private long total = 0;

    private long pages = 0;

    private int current;

    private int pageSize;

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
