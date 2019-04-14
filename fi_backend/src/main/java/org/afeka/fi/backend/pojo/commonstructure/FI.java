package org.afeka.fi.backend.pojo.commonstructure;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.Expose;
import j2html.tags.ContainerTag;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.pojo.report.ErrorReport;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FI
{
    public FI(){

    }

    public FI(String ndId){
        this.ndId=ndId;
    }
   public FI(String ndId,Long fiDocId){
      this.ndId=ndId;
      this.fiDocId=fiDocId;
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
    public List<PG> PG;

    @XmlTransient
    @Expose(serialize = true, deserialize = true)
    public String ndId;
    @Transient
    @XmlTransient
    @Expose(serialize = true, deserialize = true)

    public HtmlObj htmlObject;
    @Transient
    @XmlTransient
    @Expose
    public ErrorReport errorReport;

    @Transient
    @XmlTransient
    @Expose
    public String version;

    @Column(columnDefinition="varchar(20000)")
    @Expose(serialize = false, deserialize = false)
    @XmlTransient
    public String fiJson;

    @Transient
    @XmlTransient
    @Expose
    public Status status;

    @Expose
    @XmlTransient
    public Long fiDocId;


}

