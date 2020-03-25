package com.rtbytez.server.file;

public class Line {

    private String id;
    private int lineNumber;
    private String text;

    public Line(String id, int lineNumber, String text) {
        this.id = id;
        this.lineNumber = lineNumber;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toString() {
        return this.text;
    }
}
