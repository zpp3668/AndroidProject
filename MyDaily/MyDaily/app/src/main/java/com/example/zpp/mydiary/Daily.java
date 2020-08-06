package com.example.zpp.mydiary;

public class Daily {
    private Integer id;
    private String title;
    private String createtime;
    private String content;

    public Daily(){

    }

    public Daily(Integer id,String title,String createtime,String content){
        this.id=id;
        this.title=title;
        this.createtime=createtime;
        this.content=content;
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCreatetime() {
        return createtime;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
