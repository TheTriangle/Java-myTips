package com.example.u.myapplicationtips;

public class Tip {
    String name;
    String tip;
    int id;
    boolean box;
    boolean clicked;

    Tip (int gid, String gname, String gtip, boolean gbox, boolean gclicked) {
        name = gname;
        id = gid;
        tip = gtip;
        box = gbox;
        clicked = gclicked;
    }
}
