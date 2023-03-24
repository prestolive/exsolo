package cn.exsolo.batis.core;

/**
 * @author prestolive
 * @date 2023/3/23
 **/
public class Pagination {

    public Pagination(int current, int pageSize) {
        this.current = current;
        this.pageSize = pageSize;
    }

    public Pagination(long total, int pages, int current, int pageSize) {
        this.total = total;
        this.pages = pages;
        this.current = current;
        this.pageSize = pageSize;
    }

    private long total = 0;

    private int pages = 0;

    private int current;

    private int pageSize;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
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
