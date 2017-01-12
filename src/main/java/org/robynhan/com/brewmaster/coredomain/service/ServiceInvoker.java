package org.robynhan.com.brewmaster.coredomain.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.robynhan.com.brewmaster.resource.exception.RestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

public class ServiceInvoker {

    private static CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceInvoker.class);

    public String get(final String url) {
        URI uri = formatURI(url);
        try {
            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            String message = String.format("Error when get url : %s", uri);
            processError(message);
            return "";
        }
    }

    private URI formatURI(final String url) {
        try {
            return new URIBuilder(url).setCharset(Charset.forName("UTF-8")).build();
        } catch (URISyntaxException e) {
            processError(String.format("Bad url : %s", url));
            return null;
        }
    }

    private void processError(final String message) {
        LOGGER.error(message);
        throw new RestException(message);
    }

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("DM_DEFAULT_ENCODING")
    public String post(final String url, final String filePath) {
        URI uri = formatURI(url);
        try {
            JSONReader reader = new JSONReader(new FileReader(filePath));
            Object jsonString = JSON.toJSON(reader.readObject());
            HttpPost httpPost = new HttpPost(uri);
            httpPost.addHeader("content-type", "application/json");
            httpPost.setEntity(new StringEntity(jsonString.toString()));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (FileNotFoundException e) {
            processError(String.format("file : %s not found, when post to url : %s", filePath, uri));
            return "";
        } catch (UnsupportedEncodingException e) {
            processError(String.format("bad content in file : %s", filePath));
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            processError(String.format("Error when post to url : %s", uri));
            return "";
        }
    }

    public String delete(final String url) {
        URI uri = formatURI(url);
        try {
            HttpDelete httpGet = new HttpDelete(uri);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            processError(String.format("Error when delete with url : %s", uri));
            return "";
        }
    }

}
