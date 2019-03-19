package org.afeka.fi.backend.pojo.commonstructure;

import com.google.gson.annotations.Expose;
import j2html.tags.ContainerTag;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement(name = "ND")

@Entity
public class NdParent
{
    public NdParent(){

    }

    public NdParent(String treId){
        this.treId=treId;
    }
    @XmlAttribute(name="nPg")
    public String nPg;
    @XmlAttribute(name="pd")
    public String pd;
    @XmlAttribute(name="v")
    public String v;
    @XmlAttribute(name="lbl")
    public String lbl;
    @XmlAttribute(name="kd")
    public String kd;
    @XmlAttribute(name="newV")
    public String newV;
    @XmlAttribute(name="pdf")
    public String pdf;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @XmlTransient
    public String treId;

    @Transient
    @XmlElement(name="ND")
    public List<ND> ND=new ArrayList<ND>();

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
                ", kd='" + kd + '\'' +
                ", newV='" + newV + '\'' +
                ", pdf='" + pdf + '\'' +
                ", ID='" + ID + '\'' +
                ", kIdDsp='" + kIdDsp + '\'' +
                ", doc='" + doc + '\'' +
                ", typ='" + typ + '\'' +
                ", pic='" + pic + '\'' +
                ", ND=" + Arrays.toString(ND.toArray()) +
                '}';
    }
}

