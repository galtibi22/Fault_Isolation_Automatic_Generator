package org.afeka.fi.backend.pojo.commonstructure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.Expose;
import j2html.tags.ContainerTag;
import org.hibernate.annotations.Parent;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@XmlRootElement(name = "ND")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class ND
{
    public ND(){

    }
    public ND(String parentNdId){
        this.ndParentId=parentNdId;
    }
    @XmlAttribute(name="nPg")
    public String nPg;
    @XmlAttribute(name="pd")
    public String pd;
    @XmlAttribute(name="v")
    public String v;
    @XmlAttribute(name="lbl")
    public String lbl;
    @XmlTransient
    public String des;
    @XmlAttribute(name="kd")
    public String kd;
    @XmlAttribute(name="newV")
    public String newV;
    @XmlAttribute(name="pdf")
    public String pdf;

    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlAttribute(name="ID")
    public String ID;
    @XmlAttribute(name="kIdDsp")
    public String kIdDsp;
    @XmlAttribute(name="doc")
    public String doc;
    @XmlAttribute(name="typ")
    public String typ;
    @XmlAttribute(name="pic")
    public String pic;

    @Transient
    @XmlElement(name="FI")
    public List<FI> FI=new ArrayList<FI>();

 /*   @Transient
    @XmlElement(name="ND")
    public List<ND> ND=new ArrayList<ND>();*/
     @XmlTransient
    public String ndParentId;
    @Transient
    @XmlTransient
    @Expose(serialize = false, deserialize = false)
    public ContainerTag htmlObject;

    @Override
    public String toString() {
        return "ND{" +
                "nPg='" + nPg + '\'' +
                ", pd='" + pd + '\'' +
                ", v='" + v + '\'' +
                ", lbl='" + lbl + '\'' +
                ", des='" + des + '\'' +
                ", kd='" + kd + '\'' +
                ", newV='" + newV + '\'' +
                ", pdf='" + pdf + '\'' +
                ", ID='" + ID + '\'' +
                ", kIdDsp='" + kIdDsp + '\'' +
                ", doc='" + doc + '\'' +
                ", typ='" + typ + '\'' +
                ", pic='" + pic + '\'' +
                ", FI=" + Arrays.toString(FI.toArray()) +
                //", ND=" + Arrays.toString(ND.toArray()) +
                '}';
    }
}

