package org.afeka.fi.backend.pojo.commonstructure;

import com.google.gson.annotations.Expose;
import j2html.tags.ContainerTag;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@XmlRootElement(name = "ND")

public class ND
{
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
    @XmlElement(name="FI")
    public List<FI> FI=new ArrayList<FI>();
    @XmlElement(name="ND")
    public List<ND> ND=new ArrayList<ND>();

    @XmlTransient
    @Expose(serialize = false, deserialize = false)
    public ContainerTag htmlObject;
   /* public String getNPg ()
    {
        return nPg;
    }

    public void setNPg (String nPg)
    {
        this.nPg = nPg;
    }

    public String getPd ()
    {
        return pd;
    }

    public void setPd (String pd)
    {
        this.pd = pd;
    }

    public String getV ()
    {
        return v;
    }

    public void setV (String v)
    {
        this.v = v;
    }

    public String getLbl ()
    {
        return lbl;
    }

    public void setLbl (String lbl)
    {
        this.lbl = lbl;
    }

    public String getKd ()
    {
        return kd;
    }

    public void setKd (String kd)
    {
        this.kd = kd;
    }

    public String getNewV ()
    {
        return newV;
    }

    public void setNewV (String newV)
    {
        this.newV = newV;
    }

    public String getPdf ()
    {
        return pdf;
    }

    public void setPdf (String pdf)
    {
        this.pdf = pdf;
    }

    public String getID ()
    {
        return ID;
    }

    public void setID (String ID)
    {
        this.ID = ID;
    }

    public String getKIdDsp ()
    {
        return kIdDsp;
    }

    public void setKIdDsp (String kIdDsp)
    {
        this.kIdDsp = kIdDsp;
    }

    public String getDoc ()
    {
        return doc;
    }

    public void setDoc (String doc)
    {
        this.doc = doc;
    }

    public String getTyp ()
    {
        return typ;
    }

    public void setTyp (String typ)
    {
        this.typ = typ;
    }

    public String getPic ()
    {
        return pic;
    }

    public void setPic (String pic)
    {
        this.pic = pic;
    }

    public FI[] getFI ()
    {
        return FI;
    }

    public void setFI (FI[] FI)
    {
        this.FI = FI;
    }


    public org.afeka.fi.backend.pojo.commonstructure.ND[] getND() {
        return ND;
    }

    public void setND(org.afeka.fi.backend.pojo.commonstructure.ND[] ND) {
        this.ND = ND;
    }*/

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
                ", FI=" + Arrays.toString(FI.toArray()) +
                ", ND=" + Arrays.toString(ND.toArray()) +
                '}';
    }
}

