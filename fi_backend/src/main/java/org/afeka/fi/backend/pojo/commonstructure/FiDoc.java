package org.afeka.fi.backend.pojo.commonstructure;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.IOException;

@Entity
public class FiDoc {
    @Column(columnDefinition="BINARY(100000)")
    public byte [] doc;
    @Id
    @GeneratedValue
    public Long ID;
    public String name;
    //public Long lenth;

    public FiDoc(MultipartFile fiDoc) throws IOException {
        this.doc=fiDoc.getBytes();
        this.name=fiDoc.getOriginalFilename();
    }

    public FiDoc(){

    }


}
