package com.simple1c.impl;

import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStreamReader;

public class Transport {

    private HttpHost host = HttpHost.create("http://localhost:12345");

    public <TRequest, TResult> TResult invoke(String path, TRequest request, Class<TResult> resultClass) {

        Gson gson = new Gson();
        return gson.fromJson(GetStringResponse(path, gson.toJson(request)), resultClass);
    }

    private String GetStringResponse(String path, String content) {
        if (!path.startsWith("/"))
            path = "/" + path;
        HttpClient client = HttpClientBuilder.create().build();
        try {
            HttpPost postRequest = new HttpPost(path);
            postRequest.setEntity(new StringEntity(content, HTTP.UTF_8));
            HttpResponse response = client.execute(host, postRequest);
            int statusCode = response.getStatusLine().getStatusCode();

            try (InputStreamReader streamReader = new InputStreamReader(response.getEntity().getContent())) {
                StringBuilder sb = new StringBuilder();
                char[] result = new char[65 * 1024];
                int read;
                while (true) {
                    read = streamReader.read(result);
                    if (read > 0)
                        sb.append(result, 0, read);
                    else break;
                }
                if (statusCode != 200)
                    throw new RuntimeException(String.format("Invalid status code: %d. Body: %s", statusCode, sb.toString()));
                return sb.toString();
            }

        } catch (IOException e) {
            throw new RuntimeException("An exception occured " + e);
        }
    }
}
