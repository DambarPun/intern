package com.example.android.pullnewsapi;

public class News {
    private String id;
    private String blogtitle;
    private String blog;
    private String publishedby;
    private String date;
    private String imglink;
    private String office;

    public String getId() {
        return id;
    }

    public String getBlogtitle() {
        return blogtitle;
    }

    public String getPublisdedby() {
        return publishedby;
    }

    public String getDate() {
        return date;
    }

    public String getImglink() {
        return imglink;
    }

    public String getBlog() {
        return blog;
    }

    public String getOffice() {
        return office;

    }

    public News(String id, String blogtitle, String blog, String publisdedby, String date, String imglink, String office){
        this.id = id;
        this.blogtitle = blogtitle;
        this.blog = blog;
        this.publishedby = publisdedby;
        this.date = date;
        this.imglink = imglink;
        this.office = office;
    }

}
