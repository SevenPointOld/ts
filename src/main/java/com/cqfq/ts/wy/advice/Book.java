/**
 * Copyright (C) 2017 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.advice;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2017年12月19日 下午3:09:57
 */
public class Book {

    private String name;
    private String url;
    private String pages;
    
    public void printName() {
        System.out.println(this.name);
    }
    public void printUrl() {
        System.out.println(this.url);
    }
    public void printPages() {
        System.out.println(this.pages);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getPages() {
        return pages;
    }
    public void setPages(String pages) {
        this.pages = pages;
    }
    
    
}
