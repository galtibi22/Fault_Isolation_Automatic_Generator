package org.afeka.fi.backend.pojo.commonstructure;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.Expose;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@XmlRootElement(name = "TRE")
public class TRE
{
    public TRE(){
    }
    public TRE(String userName){
        this.userName=userName;
    }
    @JsonIgnore
    @XmlAttribute(name="lnkCol0")
    public String lnkCol0;
    @JsonIgnore
    @XmlAttribute(name="prnt")
    public String prnt;
    @JsonIgnore
    @XmlAttribute(name="lnkCol0tl")
    public String lnkCol0tl;
    @JsonIgnore
    @XmlAttribute(name="lnkCol2")
    public String lnkCol2;
    @JsonIgnore
    @XmlAttribute(name="lnkCol1")
    public String lnkCol1;
    @JsonIgnore
    @XmlAttribute(name="fiRigid")
    public String fiRigid;
    @JsonIgnore
    @XmlAttribute(name="srch")
    public String srch;
    @JsonIgnore
    @XmlAttribute(name="ful")
    public String ful;
    @JsonIgnore
    @XmlAttribute(name="lnkCol2w")
    public String lnkCol2w;
    @JsonIgnore
    @XmlAttribute(name="nLnkCols")
    public String nLnkCols;
    @JsonIgnore
    @XmlAttribute(name="lnkCol1w")
    public String lnkCol1w;
    @JsonIgnore
    @XmlAttribute(name="lnkCol0w")
    public String lnkCol0w;
    @JsonIgnore
    @XmlAttribute(name="mxPgs")
    public String mxPgs;
    @JsonIgnore
    @XmlAttribute(name="v")
    public String v;
    @JsonIgnore
    @XmlAttribute(name="lnkCol1tl")
    public String lnkCol1tl;
    @JsonIgnore
    @XmlAttribute(name="lnkCol2tl")
    public String lnkCol2tl;
    @XmlTransient
    public String des;
    @XmlTransient
    public String lbl;
    @Transient
    @XmlElement(name="ND")
    public List<NdParent> ndParents=new ArrayList<NdParent>();

    @XmlTransient
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String ID;

    @XmlTransient
    public String userName;

    /*  public String getLnkCol0 ()
    {
        return lnkCol0;
    }

    public void setLnkCol0 (String lnkCol0)
    {
        this.lnkCol0 = lnkCol0;
    }

    public String getPrnt ()
    {
        return prnt;
    }

    public void setPrnt (String prnt)
    {
        this.prnt = prnt;
    }

    public String getLnkCol0tl ()
    {
        return lnkCol0tl;
    }

    public void setLnkCol0tl (String lnkCol0tl)
    {
        this.lnkCol0tl = lnkCol0tl;
    }

    public String getLnkCol2 ()
    {
        return lnkCol2;
    }

    public void setLnkCol2 (String lnkCol2)
    {
        this.lnkCol2 = lnkCol2;
    }

    public String getLnkCol1 ()
    {
        return lnkCol1;
    }

    public void setLnkCol1 (String lnkCol1)
    {
        this.lnkCol1 = lnkCol1;
    }

    public String getFiRigid ()
    {
        return fiRigid;
    }

    public void setFiRigid (String fiRigid)
    {
        this.fiRigid = fiRigid;
    }

    public String getSrch ()
    {
        return srch;
    }

    public void setSrch (String srch)
    {
        this.srch = srch;
    }

    public String getFul ()
    {
        return ful;
    }

    public void setFul (String ful)
    {
        this.ful = ful;
    }

    public String getLnkCol2w ()
    {
        return lnkCol2w;
    }

    public void setLnkCol2w (String lnkCol2w)
    {
        this.lnkCol2w = lnkCol2w;
    }

    public String getNLnkCols ()
    {
        return nLnkCols;
    }

    public void setNLnkCols (String nLnkCols)
    {
        this.nLnkCols = nLnkCols;
    }

    public String getLnkCol1w ()
    {
        return lnkCol1w;
    }

    public void setLnkCol1w (String lnkCol1w)
    {
        this.lnkCol1w = lnkCol1w;
    }

    public String getLnkCol0w ()
    {
        return lnkCol0w;
    }

    public void setLnkCol0w (String lnkCol0w)
    {
        this.lnkCol0w = lnkCol0w;
    }

    public String getMxPgs ()
    {
        return mxPgs;
    }

    public void setMxPgs (String mxPgs)
    {
        this.mxPgs = mxPgs;
    }

    public String getV ()
    {
        return v;
    }

    public void setV (String v)
    {
        this.v = v;
    }

    public String getLnkCol1tl ()
    {
        return lnkCol1tl;
    }

    public void setLnkCol1tl (String lnkCol1tl)
    {
        this.lnkCol1tl = lnkCol1tl;
    }

    public String getLnkCol2tl ()
    {
        return lnkCol2tl;
    }

    public void setLnkCol2tl (String lnkCol2tl)
    {
        this.lnkCol2tl = lnkCol2tl;
    }

    public ND getND ()
    {
        return ND;
    }

    public void setND (ND ND)
    {
        this.ND = ND;
    }*/

    @Override
    public String toString() {
        return "TRE{" + "lnkCol0='" + lnkCol0 + '\'' + ", prnt='" + prnt + '\'' + ", lnkCol0tl='" + lnkCol0tl + '\'' + ", lnkCol2='" + lnkCol2 + '\'' + ", lnkCol1='" + lnkCol1 + '\'' + ", fiRigid='" + fiRigid + '\'' + ", srch='" + srch + '\'' + ", ful='" + ful + '\'' + ", lnkCol2w='" + lnkCol2w + '\'' + ", nLnkCols='" + nLnkCols + '\'' + ", lnkCol1w='" + lnkCol1w + '\'' + ", lnkCol0w='" + lnkCol0w + '\'' + ", mxPgs='" + mxPgs + '\'' + ", v='" + v + '\'' + ", lnkCol1tl='" + lnkCol1tl + '\'' + ", lnkCol2tl='" + lnkCol2tl + '\'' + ", des='" + des + '\'' + ", lbl='" + lbl + '\'' + ", ndParents=" + ndParents + ", ID='" + ID + '\'' + ", userName='" + userName + '\'' + '}';
    }
}

