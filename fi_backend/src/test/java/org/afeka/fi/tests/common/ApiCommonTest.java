package org.afeka.fi.tests.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.afeka.fi.backend.pojo.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public abstract class ApiCommonTest extends FiCommonTest{

    protected WebApplicationContext webApplicationContext;
    protected MockMvc mvc;


    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext){
        this.webApplicationContext = webApplicationContext;
    }

    @Autowired
    public void setMvc() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    protected void mvcResultIsOk(MvcResult mvcResult){
        int status = mvcResult.getResponse().getStatus();
        assertEquals("The code response should be 200",200, status);
    }

    protected User login (String userName,String password){
        return null;
    }
}
