package org.afeka.fi.backend.pojo.commonstructure;


import com.google.gson.annotations.Expose;
import j2html.tags.ContainerTag;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

////@XmlRootElement(name="FI")
public class FI
{
   @XmlAttribute(name="nPg")
    public String nPg;
   @XmlAttribute(name="pd")
   public String pd;
   @XmlAttribute(name="v")
   public String v;
    @Expose(serialize = true,deserialize = true)
    @XmlAttribute(name="lbl")
    public String lbl="lbl";
   @XmlAttribute(name="kd")
   public String kd;
   @XmlAttribute(name="newV")
   public String newV;
   @XmlAttribute(name="pdf")
   public String pdf;
   @XmlAttribute(name="ID")
   public String ID;
   @XmlAttribute(name="kIdDsp")
   public String kIdDsp;
   @XmlAttribute(name="doc")
   public String doc;
   @XmlAttribute(name="typ")
   public String typ;
    @Expose(serialize = true,deserialize = true)
    @XmlElement(name="PG")
    public List<PG> PG=new ArrayList<PG>();

    @XmlTransient
    @Expose(serialize = false, deserialize = false)
    public ContainerTag htmlObject;

}

