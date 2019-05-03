package org.afeka.fi.backend.pojo.commonstructure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PG
{
    @Expose(serialize = true,deserialize = true)
    @JsonIgnore
    @XmlAttribute(name="dwgID")
    public String dwgID="";
    @Expose(serialize = true,deserialize = true)
    @JsonIgnore
    @XmlAttribute(name="doc")
    public String doc;
    @JsonPropertyOrder("1")
    @SerializedName("n")
    @Expose(serialize = true,deserialize = true)
    @JsonProperty("n")
    @XmlAttribute(name="n")
    public String _n;
    @JsonPropertyOrder("4")

    @Expose(serialize = true,deserialize = true)
    @XmlElement(name = "N")
    public YN N=new YN();
    @JsonPropertyOrder("3")
    @Expose(serialize = true,deserialize = true)
    @XmlElement(name = "Y")
    public YN Y=new YN();
    @Expose(serialize = true,deserialize = true)
    @XmlTransient
    public HtmlObj htmlObj;
    @JsonPropertyOrder("2")
    @Expose(serialize = true,deserialize = true)
    @XmlTransient
    public String status;
    @Expose(serialize = true,deserialize = true)
    @XmlTransient
    public String type;


}