package org.afeka.fi.backend.pojo.commonstructure;

import com.google.gson.annotations.Expose;

public class HtmlData {
    @Override
    public String toString() {
        return "HtmlData{" +
                "htmlType=" + htmlType +
                ", txt='" + txt + '\'' +
                '}';
    }

    @Expose(serialize = true, deserialize = true)
    public HtmlType htmlType;
    @Expose(serialize = true, deserialize = true)
    public String txt;
}
