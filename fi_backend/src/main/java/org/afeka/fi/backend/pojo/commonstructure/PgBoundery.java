package org.afeka.fi.backend.pojo.commonstructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.afeka.fi.backend.common.FiLogger;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PgBoundery {
    Logger logger= FiLogger.getLogger(getClass().getName());
    @JsonPropertyOrder("1")
    @JsonProperty("Number")
    private String Number="";
    @JsonPropertyOrder("2")
    @JsonProperty("Type")
    private String Type="";
    @JsonPropertyOrder("3")
    @JsonProperty("Status")
    private String status="";
    @JsonPropertyOrder("4")
    @JsonProperty("Description")
    private String Description="";
    @JsonPropertyOrder("5")
    @JsonProperty("To yes")
    private String toYes="";
    @JsonPropertyOrder("6")
    @JsonProperty("To no")
    private String toNo="";

    public PgBoundery(){

    }

    public PgBoundery(PG pg){
      try {
          Number = pg._n;
          Type =createType(pg);
          status = pg.status;
          if (pg.N.getTo()!=null)
            toNo = pg.N.getTo();
          if (pg.Y.getTo()!=null)
            toYes = pg.Y.getTo();
          Description = createDescription(pg);
      }catch (Exception e){
          logger.error("Cannot convert pg to PgBoundery",e);
      }
    }

    private String createType(PG pg){
        String type;
        if (pg.type!=null)
            type=pg.type;
        else if(pg._n.equals("0"))
            type="opening";
        else if (pg.htmlObj.getHtmlData()[0].htmlType.equals(HtmlType.fiPosEnd))
            type="finish";
        else if (pg.htmlObj.getHtmlData()[0].htmlType.equals(HtmlType.fiNegEnd))
            type="finish";
        else
            type="null";
        return type;


    }
    private String createDescription(PG pg) {
        String description="";
        if(pg.htmlObj.getHtmlData()[0].htmlType.equals(HtmlType.fiNegEnd)){
            description="Negative end for the flow";
        }else if(pg.htmlObj.getHtmlData()[0].htmlType.equals(HtmlType.fiPosEnd)){
            description="Positive end for the flow";
        }
        else if(status.equals(Status.success.name()))
            description= Arrays.stream(pg.htmlObj.getHtmlData()).map(htmlData -> htmlData.txt).collect(Collectors.joining());
        else{
            if(pg.status.startsWith("missing")){
                String[] errors=pg.status.split(",");
                for (String error:errors) {
                    error.replace("missing","");
                    String name=error.substring(0,1).toUpperCase()+error.substring(1);
                    description += name +" is missing, ";
                }
                description.substring(0,description.length()-3);
            }
            else{
                description=pg.status+" please implement this error in PgBoundery.createDescription";
            }
        }
        return description;
    }

  /*  public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getToYes() {
        return toYes;
    }

    public void setToYes(String toYes) {
        this.toYes = toYes;
    }

    public String getToNo() {
        return toNo;
    }

    public void setToNo(String toNo) {
        this.toNo = toNo;
    }*/
}
