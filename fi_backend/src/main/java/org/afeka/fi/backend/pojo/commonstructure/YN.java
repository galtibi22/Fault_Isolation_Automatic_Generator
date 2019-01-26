package org.afeka.fi.backend.pojo.commonstructure;


import com.google.gson.annotations.Expose;
import org.afeka.fi.backend.exception.DataNotValidException;

import javax.xml.bind.annotation.XmlAttribute;

public class YN
{

    @Expose(serialize = true,deserialize = true)
    private String to;
    @Expose(serialize = true,deserialize = true)
    private String rtN;
    @Expose(serialize = true,deserialize = true)
    private String msgRt;
    @Expose(serialize = true,deserialize = true)
    private String typ;
    private String msgIx;
    @Expose(serialize = true,deserialize = true)
    private String msgRtIx;
    @Expose(serialize = true,deserialize = true)
    private String tskNm;
    @Expose(serialize = true,deserialize = true)
    private String rtY;
    private String msg="1";

    @XmlAttribute(name="to")
    public String getTo ()
    {
        return to;
    }

    public void setTo (String to)
    {
        this.to = to;
    }

    @XmlAttribute(name="rtN")
    public String getRtN ()
    {
        return rtN;
    }

    public void setRtN (String rtN) throws DataNotValidException {
        try{
            Integer.parseInt(rtN);
            this.rtN = rtN;
        }catch (Exception e){
            throw new DataNotValidException("YN.rtN can be only Integer");
        }
    }
    @XmlAttribute(name="msgRt")
    public String getMsgRt ()
    {
        return msgRt;
    }

    //msgRt control if after finish the task you have 1 or 2 option to jump
    //if you have 1 option to next task msgRt=0 if you have 2 option return msgRt=1
    //if you have 0 option (final task) return msgRt=1 but set rtY=positive final PG and rtN= negative final PG
    public void setMsgRt (String msgRt) throws DataNotValidException {
        if (msgRt.equals("1") || msgRt.equals("0"))
            this.msgRt = msgRt;
        else
            throw new DataNotValidException("YN.setMsgRt can be 0 | 1");
    }

    @XmlAttribute(name="typ")
    public String getTyp ()
    {
        return typ;
    }

    //0 step
    //1 task
    //4 close
    public void setTyp (String typ) throws DataNotValidException {
        if (typ.equals("1") || typ.equals("0") || typ.equals("4"))
            this.typ = typ;
        else
            throw new DataNotValidException("YN.typ can be 0-step, 1-link,4-close");
    }
    @XmlAttribute(name="msgIx")
    public String getMsgIx ()
    {
        return msgIx;
    }

    public void setMsgIx (String msgIx)
    {
        this.msgIx = msgIx;
    }

    @XmlAttribute(name="msgRtIx")
    public String getMsgRtIx ()
    {
        return msgRtIx;
    }

    public void setMsgRtIx (String msgRtIx)
    {

        this.msgRtIx = msgRtIx;
    }

    @XmlAttribute(name="tskNm")
    public String getTskNm ()
    {
        return tskNm;
    }

    public void setTskNm (String tskNm)
    {
        this.tskNm = tskNm;
    }
    @XmlAttribute(name="rtY")
    public String getRtY ()
    {
        return rtY;
    }

    public void setRtY (String rtY) throws DataNotValidException {
        try{
            Integer.parseInt(rtY);
            this.rtY = rtY;
        }catch (Exception e){
            throw new DataNotValidException("YN.rtY can be only Integer");
        }
    }

    @XmlAttribute(name="msg")
    public String getMsg ()
    {
        return msg;
    }

    // 0 - noMessage
    // 1 - message
    public void setMsg (String msg) throws DataNotValidException {
        if (msg.equals("1")){
            setMsgIx("0");
            this.msg = msg;
        }
        else if (msg.equals("0"))
            this.msg = msg;
        else
            throw new DataNotValidException("YN.msg can be only 0 - noMessage or 1 - message");
    }
}