package org.afeka.fi.backend.pojo.ocr;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class OcrWebServiceResponse {

    public OcrWebServiceResponse(){

    }
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;
    @SerializedName("OutputInformation")
    @Expose
    private Object outputInformation;
    @SerializedName("AvailablePages")
    @Expose
    private Integer availablePages;
    @SerializedName("ProcessedPages")
    @Expose
    private Integer processedPages;
    @SerializedName("OCRText")
    @Expose
    private List<List<String>> oCRText = null;
    @SerializedName("OutputFileUrl")
    @Expose
    private String outputFileUrl;
    @SerializedName("OutputFileUrl2")
    @Expose
    private String outputFileUrl2;
    @SerializedName("OutputFileUrl3")
    @Expose
    private String outputFileUrl3;
    @SerializedName("Reserved")
    @Expose
    private List<Object> reserved = null;
    @SerializedName("OCRWords")
    @Expose
    private List<Object> oCRWords = null;
    @SerializedName("TaskDescription")
    @Expose
    private Object taskDescription;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getOutputInformation() {
        return outputInformation;
    }

    public void setOutputInformation(Object outputInformation) {
        this.outputInformation = outputInformation;
    }

    public Integer getAvailablePages() {
        return availablePages;
    }

    public void setAvailablePages(Integer availablePages) {
        this.availablePages = availablePages;
    }

    public Integer getProcessedPages() {
        return processedPages;
    }

    public void setProcessedPages(Integer processedPages) {
        this.processedPages = processedPages;
    }

    public List<List<String>> getOCRText() {
        return oCRText;
    }

    public void setOCRText(List<List<String>> oCRText) {
        this.oCRText = oCRText;
    }

    public String getOutputFileUrl() {
        return outputFileUrl;
    }

    public void setOutputFileUrl(String outputFileUrl) {
        this.outputFileUrl = outputFileUrl;
    }

    public String getOutputFileUrl2() {
        return outputFileUrl2;
    }

    public void setOutputFileUrl2(String outputFileUrl2) {
        this.outputFileUrl2 = outputFileUrl2;
    }

    public String getOutputFileUrl3() {
        return outputFileUrl3;
    }

    public void setOutputFileUrl3(String outputFileUrl3) {
        this.outputFileUrl3 = outputFileUrl3;
    }

    public List<Object> getReserved() {
        return reserved;
    }

    public void setReserved(List<Object> reserved) {
        this.reserved = reserved;
    }

    public List<Object> getOCRWords() {
        return oCRWords;
    }

    public void setOCRWords(List<Object> oCRWords) {
        this.oCRWords = oCRWords;
    }

    public Object getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(Object taskDescription) {
        this.taskDescription = taskDescription;
    }

}