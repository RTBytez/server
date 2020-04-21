package com.rtbytez.server.util;

public class LineChange {
    private final String change;
    private final int index;

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
