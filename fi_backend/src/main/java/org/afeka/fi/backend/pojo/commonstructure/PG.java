package org.afeka.fi.backend.pojo.commonstructure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class PG
{
    @XmlAttribute(name="dwgID")
    public String dwgID="";

    @XmlAttribute(name="doc")
    public String doc;

    @SerializedName("n")
    @Expose(serialize = true,deserialize = true)
    @XmlAttribute(name="n")
    public String _n;

    @Expose(serialize = true,deserialize = true)
    @XmlElement(name = "N")
    public YN N;
    @Expose(serialize = true,deserialize = true)
    @XmlElement(name = "Y")
    public YN Y;
    @Expose(serialize = true,deserialize = true)
    @XmlTransient
    public HtmlObj htmlObj;
}