package com.rtbytez.server.util;

public class LineChange {
    private String change;
    private int index;

    public LineChange(String change, int index) {
        this.change = change;
        this.index = index;
    }

    public String getChange() {
        return change;
    }

    public int getIndex() {
        return index;
    }
}
