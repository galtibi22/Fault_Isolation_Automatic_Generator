package org.afeka.fi.backend.pojo.commonstructure;


import com.google.gson.annotations.Expose;
import j2html.tags.ContainerTag;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.pojo.report.ErrorReport;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name="FI")

public class FI
{
    public FI(){

    }

    public FI(String ndId){
        this.ndId=ndId;
    }

    @Expose(serialize = true, deserialize = true)
    @Transient
   @XmlAttribute(name="nPg")
    public String nPg;

    @Transient
    @XmlAttribute(name="pd")
   public String pd;
    @Expose(serialize = true, deserialize = true)
    @Transient
    @XmlAttribute(name="v")

    public String v;

    @Transient
    @Expose(serialize = true,deserialize = true)
    @XmlAttribute(name="lbl")
    public String lbl="lbl";
    @Expose(serialize = true, deserialize = true)
    @Transient
    @XmlAttribute(name="kd")

   public String kd;
    @Expose(serialize = true, deserialize = true)
    @Transient
    @XmlAttribute(name="newV")
   public String newV;
    @Expose(serialize = true, deserialize = true)
    @Transient
    @XmlAttribute(name="pdf")
   public String pdf;

    @Expose(serialize = true, deserialize = true)
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
   @XmlAttribute(name="ID")
   public String ID;
    @Expose(serialize = true, deserialize = true)
    @Transient
    @XmlAttribute(name="kIdDsp")
   public String kIdDsp;

    @Expose(serialize = true, deserialize = true)
    @Transient
    @XmlAttribute(name="doc")
   public String doc;
    @Expose(serialize = true, deserialize = true)
    @Transient
    @XmlAttribute(name="typ")
   public String typ;

    @Transient
    @Expose(serialize = true,deserialize = true)
    @XmlElement(name="PG")
    public List<PG> PG;//=new ArrayList();
    @Transient
    @XmlTransient
    @Expose(serialize = true, deserialize = true)
    public String ndId;
    @Transient
    @XmlTransient

    @Expose(serialize = true, deserialize = true)
    public ContainerTag htmlObject;
    @Transient
    @XmlTransient
    //@Expose(serialize = false, deserialize = false)

    @Expose(serialize = true, deserialize = true)
    public ErrorReport errorReport;

    @Transient
    @XmlTransient
   // @Expose(serialize = true, deserialize = true)
    public String version;

    @Column(columnDefinition="varchar(20000)")
    @Expose(serialize = false, deserialize = false)
    @XmlTransient
    public String fiJson;

    @Override
    public String toString() {
        return "FI{" + "nPg='" + nPg + '\'' + ", pd='" + pd + '\'' + ", v='" + v + '\'' + ", lbl='" + lbl + '\'' + ", kd='" + kd + '\'' + ", newV='" + newV + '\'' + ", pdf='" + pdf + '\'' + ", ID='" + ID + '\'' + ", kIdDsp='" + kIdDsp + '\'' + ", doc='" + doc + '\'' + ", typ='" + typ + '\'' + ", PG=" + PG + ", ndId='" + ndId + '\'' + ", htmlObject=" + htmlObject + ", errorReport=" + errorReport + ", version='" + version + '\'' + '}';
    }
}

