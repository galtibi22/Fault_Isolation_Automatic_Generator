package org.afeka.fi.backend.pojo.commonstructure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name="PG")
public class PG
{
    @XmlAttribute(name="dwgID")
    private String dwgID;

    @XmlAttribute(name="doc")
    private String doc;

    @XmlAttribute(name="n")
    private String _n;

    @XmlElement(name = "N")
    private YN N;

    @XmlElement(name = "Y")
    private YN Y;

   /* public String getDwgID ()
    {
        return dwgID;
    }

    public void setDwgID (String dwgID)
    {
        this.dwgID = dwgID;
    }

    public String getDoc ()
    {
        return doc;
    }

    public void setDoc (String doc)
    {
        this.doc = doc;
    }


    public YN getN ()
    {
        return N;
    }

    public void setN (YN N)
    {
        this.N = N;
    }

    public YN getY ()
    {
        return Y;
    }

    public void setY (YN Y)
    {
        this.Y = Y;
    }


    public String get_n() {
        return _n;
    }

    public void set_n(String _n) {
        this._n = _n;
    }
*/
    @Override
    public String toString() {
        return "PG{" +
                "dwgID='" + dwgID + '\'' +
                ", doc='" + doc + '\'' +
                ", _n='" + _n + '\'' +
                ", N=" + N +
                ", Y=" + Y +
                '}';
    }
}