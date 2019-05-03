package org.afeka.fi.tests.api;

import org.afeka.fi.backend.common.Constants;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.tests.common.ApiCommonTest;
import org.afeka.fi.tests.common.FiCommonTest;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
@WebAppConfiguration
public class UserApiTests extends ApiCommonTest {

    @Test
    public void admin_can_create_users_in_the_system() throws Exception {
        given("admin is login to the system");
        String uri="/api/user/login";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.
                post(uri)
                .header(HttpHeaders.AUTHORIZATION, Helpers.encodeBasicAuth(Constants.ADMIN_USER_NAME,Constants.ADMIN_PASSWORD))
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        mvcResultIsOk(mvcResult);
        String content = mvcResult.getResponse().getContentAsString();
        User user = mapFromJson(content, User.class);
        assertEquals("The role of the admin is not admin",user.role, Role.admin);
        when("The admin create new user");

        then("The user can login to the system");
        mvcResultIsOk(mvcResult);
    }




}
