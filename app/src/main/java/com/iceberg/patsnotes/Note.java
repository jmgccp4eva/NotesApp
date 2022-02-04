package com.iceberg.patsnotes;

public class Note {
    private long id;
    private String title;
    private String content;
    private String type;
    private String date;
    private String parent;

    Note(){}
    Note(String title,String content,String type,String date, String parent){
        this.title=title;
        this.content=content;
        this.type=type;
        this.date=date;
        this.parent=parent;
    }
    Note(long id,String title,String content,String type,String date, String parent){
        this.id=id;
        this.title=title;
        this.content=content;
        this.type=type;
        this.date=date;
        this.parent=parent;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }
}
