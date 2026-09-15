package com.isuzuki.examples.oss.api;

import java.io.Serializable;


public class Paging implements Serializable {
    /**
     * 当前页码
     */
    private int page;
    /**
     * 页大小
     */
    private int size;
    /**
     * 总条数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;

    public Paging() {
    }

    public Paging(int page, int size) {
        this(page, size, 0L);
    }

    public Paging(int page, int size, long total) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.pages = (size > 0 && total > 0) ? ((int) Math.ceil((double) total / size)) : 0;
    }

    public static Paging empty() {
        return of(1, 1);
    }

    public static Paging of(int current, int size) {
        return new Paging(current, size, 0L);
    }

    public static Paging of(int current, int size, long total) {
        return new Paging(current, size, total);
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotal() {
        return total;
    }

    public int getPages() {
        return pages;
    }
}
