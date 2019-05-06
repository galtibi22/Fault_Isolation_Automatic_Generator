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
    @JsonIgnore
    @Expose
    @Transient
   @XmlAttribute(name="nPg")
    public String nPg;
 @JsonIgnore
 @Expose
    @Transient
    @XmlAttribute(name="pd")
   public String pd;
 @JsonIgnore

 @Expose
    @Transient
    @XmlAttribute(name="v")

    public String v;

    @Transient
    @Expose
    @XmlAttribute(name="lbl")
    public String lbl="lbl";
 @JsonIgnore
    @Expose
    @Transient
    @XmlAttribute(name="kd")

   public String kd;
 @JsonIgnore
 @Expose
    @Transient
    @XmlAttribute(name="newV")
   public String newV;
 @JsonIgnore
 @Expose
    @Transient
    @XmlAttribute(name="pdf")
   public String pdf;

    @Expose(serialize = true, deserialize = true)
    @Id
    @XmlAttribute(name="ID")
   public String ID;
 @JsonIgnore
    @Expose
    @Transient
    @XmlAttribute(name="kIdDsp")
   public String kIdDsp;

    @Expose
    @Transient
    @XmlAttribute(name="doc")

    public String doc;
 @JsonIgnore
    @Expose
    @Transient
    @XmlAttribute(name="typ")

    public String typ;
    @JsonIgnore
    @Transient
    @Expose
    @XmlElement(name="PG")
    public List<PG> PG;

    @XmlTransient
    @Expose
    public String ndId;
 @JsonIgnore
    @Transient
    @XmlTransient
    @Expose
    public HtmlObj htmlObject;
   @JsonIgnore
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
    @Transient
    @Expose
    @XmlTransient
    public List<PgBoundery> pgBounderies=new ArrayList<>();


}

