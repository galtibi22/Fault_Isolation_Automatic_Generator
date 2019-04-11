package org.afeka.fi.backend.pojo.commonstructure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.Expose;
import j2html.tags.ContainerTag;

import java.util.Arrays;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HtmlObj {

    @Expose(serialize = true, deserialize = true)
    private HtmlData[] htmlData;

    public HtmlData[] getHtmlData() {
        return htmlData;
    }

    public void setHtmlData(HtmlData[] htmlData) {
        this.htmlData = htmlData;
    }

    @Override
    public String toString() {
        return "HtmlObj{" +
                "htmlData=" + Arrays.toString(htmlData) +
                '}';
    }
}