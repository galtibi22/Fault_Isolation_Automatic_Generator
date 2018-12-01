package org.afeka.fi.backend.pojo.commonstructure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
public class ND
{
    @XmlAttribute(name="nPg")
    private String nPg;
    @XmlAttribute(name="pd")
    private String pd;
    @XmlAttribute(name="v")
    private String v;
    @XmlAttribute(name="lbl")
    private String lbl;
    @XmlAttribute(name="kd")
    private String kd;
    @XmlAttribute(name="newV")
    private String newV;
    @XmlAttribute(name="pdf")
    private String pdf;

    @XmlAttribute(name="ID")
    private String ID;
    @XmlAttribute(name="kIdDsp")
    private String kIdDsp;
    @XmlAttribute(name="doc")
    private String doc;
    @XmlAttribute(name="typ")
    private String typ;
    @XmlAttribute(name="pic")
    private String pic;
    @XmlElement(name="FI")
    private FI[] FI;
    @XmlElement(name="ND")
    private ND[] ND;

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
                ", FI=" + Arrays.toString(FI) +
                ", ND=" + Arrays.toString(ND) +
                '}';
    }
}

