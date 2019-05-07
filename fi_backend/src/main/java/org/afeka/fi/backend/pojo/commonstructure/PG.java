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
    @Expose
    @JsonIgnore
    @XmlAttribute(name="dwgID")
    public String dwgID="";
    @Expose
    @JsonIgnore
    @XmlAttribute(name="doc")
    public String doc;
    @Expose
    @JsonProperty("n")
    @XmlAttribute(name="n")
    @SerializedName("n")
    public String _n;
    @Expose
    @XmlElement(name = "N")
    public YN N=new YN();
    @Expose
    @XmlElement(name = "Y")
    public YN Y=new YN();
    @Expose
    @XmlTransient
    public HtmlObj htmlObj;
    @Expose
    @XmlTransient
    public String status;
    @Expose
    @XmlTransient
    public String type;

    @Override
    public String toString() {
        return "PG{" + "dwgID='" + dwgID + '\'' + ", doc='" + doc + '\'' + ", _n='" + _n + '\'' + ", N=" + N + ", Y=" + Y + ", htmlObj=" + htmlObj + ", status='" + status + '\'' + ", type='" + type + '\'' + '}';
    }
}