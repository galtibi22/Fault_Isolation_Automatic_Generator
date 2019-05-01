package org.afeka.fi.backend.pojo.ocr;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "response")
public class Response
{
    private Task task;

    public Task getTask ()
    {
        return task;
    }

    public void setTask (Task task)
    {
        this.task = task;
    }

}

