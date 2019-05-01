package org.afeka.fi.backend.pojo.ocr;

import javax.xml.bind.annotation.XmlRootElement;

public class AbbyyOcrResponse
{
private Response response;

    public Response getResponse ()
    {
        return response;
    }

    public void setResponse (Response response)
    {
        this.response = response;
    }

}

