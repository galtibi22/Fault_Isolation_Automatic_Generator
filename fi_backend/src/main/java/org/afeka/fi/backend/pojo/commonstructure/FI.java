package org.afeka.fi.backend.pojo.commonstructure;


import com.google.gson.annotations.Expose;
import j2html.tags.ContainerTag;
import org.afeka.fi.backend.pojo.report.ErrorReport;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

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
    public List<PG> PG=new ArrayList();

    @XmlTransient
    @Expose(serialize = false, deserialize = false)
    public ContainerTag htmlObject;

    @XmlTransient
    @Expose(serialize = false, deserialize = false)
    public ErrorReport errorReport;

    @XmlTransient
    @Expose(serialize = false, deserialize = false)
    public String version;

}

