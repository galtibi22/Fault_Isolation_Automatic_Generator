package org.afeka.fi.backend.pojo.commonstructure;



import javax.xml.bind.annotation.*;

@XmlRootElement(name = "TRE")
public class TRE
{
    @XmlAttribute(name="lnkCol0")
    private String lnkCol0;
    @XmlAttribute(name="prnt")
    private String prnt;
    @XmlAttribute(name="lnkCol0tl")
    private String lnkCol0tl;
    @XmlAttribute(name="lnkCol2")
    private String lnkCol2;
    @XmlAttribute(name="lnkCol1")
    private String lnkCol1;
    @XmlAttribute(name="fiRigid")
    private String fiRigid;
    @XmlAttribute(name="srch")
    private String srch;
    @XmlAttribute(name="ful")
    private String ful;
    @XmlAttribute(name="lnkCol2w")
    private String lnkCol2w;
    @XmlAttribute(name="nLnkCols")
    private String nLnkCols;
    @XmlAttribute(name="lnkCol1w")
    private String lnkCol1w;
    @XmlAttribute(name="lnkCol0w")
    private String lnkCol0w;
    @XmlAttribute(name="mxPgs")
    private String mxPgs;
    @XmlAttribute(name="v")
    private String v;
    @XmlAttribute(name="lnkCol1tl")
    private String lnkCol1tl;
    @XmlAttribute(name="lnkCol2tl")
    private String lnkCol2tl;
    @XmlElement(name="ND")
    private ND ND;

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
        return "TRE{" +
                "lnkCol0='" + lnkCol0 + '\'' +
                ", prnt='" + prnt + '\'' +
                ", lnkCol0tl='" + lnkCol0tl + '\'' +
                ", lnkCol2='" + lnkCol2 + '\'' +
                ", lnkCol1='" + lnkCol1 + '\'' +
                ", fiRigid='" + fiRigid + '\'' +
                ", srch='" + srch + '\'' +
                ", ful='" + ful + '\'' +
                ", lnkCol2w='" + lnkCol2w + '\'' +
                ", nLnkCols='" + nLnkCols + '\'' +
                ", lnkCol1w='" + lnkCol1w + '\'' +
                ", lnkCol0w='" + lnkCol0w + '\'' +
                ", mxPgs='" + mxPgs + '\'' +
                ", v='" + v + '\'' +
                ", lnkCol1tl='" + lnkCol1tl + '\'' +
                ", lnkCol2tl='" + lnkCol2tl + '\'' +
                ", ND=" + ND +
                '}';
    }
}

