package com.folioreader.model;

import java.util.Date;

public class HighLightInsert {
    private HighlightImpl note;
    private String rangy;

    public HighLightInsert(HighlightImpl note, String rangy) {
        this.note = note;
        this.rangy = rangy;
    }

    public String getRangy() {
        return rangy;
    }

    public HighlightImpl getNote() {
        return note;
    }
}
