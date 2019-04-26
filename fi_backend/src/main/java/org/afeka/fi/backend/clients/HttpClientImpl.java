package org.afeka.fi.backend.clients;

import org.afeka.fi.backend.common.FiCommon;
import org.afeka.fi.backend.common.FiCommonTest;
import org.afeka.fi.backend.common.Helpers;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.swing.text.html.parser.Entity;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HttpClientImpl extends FiCommon {
    static HttpClient client = new DefaultHttpClient();


    public <E> E post(String url, Header[] headers,Object entity,E e) throws IOException {
        HttpPost request=new HttpPost(url);
        request.setHeaders(headers);
        HttpEntity entity1=new StringEntity(Helpers.initGson().toJson(entity));
        request.setEntity(entity1);
        HttpResponse response=client.execute(request);
        Assert.assertThat("response code is not 200 for request "+request.toString(),response.getStatusLine().getStatusCode(),equalTo(200));
        return (E) Helpers.initGson().fromJson(response.getEntity().toString(),e.getClass());
    }

    public <E> E post(String url, Header[] headers, byte[] stream, E e) throws IOException {
        HttpPost request=new HttpPost(url);
        request.setHeaders(headers);
        HttpEntity entity=new ByteArrayEntity(stream);
        request.setEntity(entity);
        logger.info("PostRequest url "+request.getURI()+" headers "+ Arrays.toString(request.getAllHeaders())+" entity "+EntityUtils.toString(request.getEntity()));

        HttpResponse response=client.execute(request);
        String entityResponse=EntityUtils.toString(response.getEntity());
        Assert.assertThat("response code is not 200 for request "+request.toString(),response.getStatusLine().getStatusCode(),equalTo(200));
        logger.info("PostResponse for request url "+request.getURI()+" response entity "+entityResponse);
        return (E) Helpers.initGson().fromJson(entityResponse,e.getClass());
    }

    public <E>E get(String url,Header[] headers,E e) throws IOException {
        HttpGet request=new HttpGet(url);
        request.setHeaders(headers);
        logger.info("GetRequest url "+request.getURI()+" headers "+ Arrays.toString(request.getAllHeaders()));
        HttpResponse response=client.execute(request);
        String entityResponse=EntityUtils.toString(response.getEntity());
        Assert.assertThat("response code is not 200 for request "+request.toString(),response.getStatusLine().getStatusCode(),equalTo(200));
        logger.info("GetResponse for request url "+request.getURI()+" response entity "+entityResponse);
        return (E) Helpers.initGson().fromJson(entityResponse,e.getClass());
    }

    public MultipartFile getFile(String url, Header[] headers) throws IOException {
        HttpGet request=new HttpGet(url);
        request.setHeaders(headers);
        logger.info("GetRequest url "+request.getURI()+" headers "+ Arrays.toString(request.getAllHeaders()));
        HttpResponse response=client.execute(request);
        Assert.assertThat("response code is not 200 for request "+request.toString(),response.getStatusLine().getStatusCode(),equalTo(200));
        logger.info("GetResponse for request url "+request.getURI());
        InputStream initialStream = new ByteArrayInputStream(EntityUtils.toByteArray(response.getEntity()));
       /* byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        File targetFile = new File("targetFile.doc");
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);*/
        MultipartFile fiDoc = new MockMultipartFile("fiDoc.doc",initialStream);

        return fiDoc;
    }


}


