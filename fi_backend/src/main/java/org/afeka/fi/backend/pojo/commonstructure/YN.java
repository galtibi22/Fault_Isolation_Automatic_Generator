package org.afeka.fi.backend.pojo.commonstructure;


import javax.xml.bind.annotation.XmlAttribute;

public class YN
{
    @XmlAttribute(name="to")
    private String to;
    @XmlAttribute(name="rtN")
    private String rtN;
    @XmlAttribute(name="msgRt")
    private String msgRt;
    @XmlAttribute(name="typ")
    private String typ;
    @XmlAttribute(name="msgIx")
    private String msgIx;
    @XmlAttribute(name="msgRtIx")
    private String msgRtIx;
    @XmlAttribute(name="tskNm")
    private String tskNm;
    @XmlAttribute(name="rtY")
    private String rtY;
    @XmlAttribute(name="msg")
    private String msg;

   /* public String getTo ()
    {
        return to;
    }

    public void setTo (String to)
    {
        this.to = to;
    }

    public String getRtN ()
    {
        return rtN;
    }

    public void setRtN (String rtN)
    {
        this.rtN = rtN;
    }

    public String getMsgRt ()
    {
        return msgRt;
    }

    public void setMsgRt (String msgRt)
    {
        this.msgRt = msgRt;
    }

    public String getTyp ()
    {
        return typ;
    }

    public void setTyp (String typ)
    {
        this.typ = typ;
    }

    public String getMsgIx ()
    {
        return msgIx;
    }

    public void setMsgIx (String msgIx)
    {
        this.msgIx = msgIx;
    }

    public String getMsgRtIx ()
    {
        return msgRtIx;
    }

    public void setMsgRtIx (String msgRtIx)
    {
        this.msgRtIx = msgRtIx;
    }

    public String getTskNm ()
    {
        return tskNm;
    }

    public void setTskNm (String tskNm)
    {
        this.tskNm = tskNm;
    }

    public String getRtY ()
    {
        return rtY;
    }

    public void setRtY (String rtY)
    {
        this.rtY = rtY;
    }

    public String getMsg ()
    {
        return msg;
    }

    public void setMsg (String msg)
    {
        this.msg = msg;
    }*/

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"{" +
                "to='" + to + '\'' +
                ", rtN='" + rtN + '\'' +
                ", msgRt='" + msgRt + '\'' +
                ", typ='" + typ + '\'' +
                ", msgIx='" + msgIx + '\'' +
                ", msgRtIx='" + msgRtIx + '\'' +
                ", tskNm='" + tskNm + '\'' +
                ", rtY='" + rtY + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}