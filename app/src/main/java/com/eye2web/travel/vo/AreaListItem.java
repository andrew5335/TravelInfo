package com.eye2web.travel.vo;

public class AreaListItem implements Comparable<AreaListItem> {

    private String code;
    private String name;
    private int rnum;

    public AreaListItem(String code, String name, int rnum) {
        this.code = code;
        this.name = name;
        this.rnum = rnum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRnum() {
        return rnum;
    }

    public void setRnum(int rnum) {
        this.rnum = rnum;
    }

    @Override
    public int compareTo(AreaListItem item) {
        int compareCode = Integer.parseInt(((AreaListItem) item).getCode());
        return Integer.parseInt(this.code) - compareCode;
    }
}
