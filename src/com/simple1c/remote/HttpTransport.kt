package com.simple1c.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import coreUtils.RetryStrategy
import org.apache.http.HttpHost
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import java.time.Duration

class HttpTransport(port: Int) {
    private val host = HttpHost("localhost", port, "http")
    private var requestConfig = RequestConfig
            .custom()
            .setConnectTimeout(30 * 1000)
            .build()!!

    private val httpClient = HttpClientBuilder.create()
            .setDefaultRequestConfig(requestConfig)
            .build()

    private val retry = RetryStrategy.Retry()
            .withTimeout(Duration.ofSeconds(1))
            .byCount(20)
            .withDelay(Duration.ofMillis(30))

    private val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .disableHtmlEscaping()
            .create()

    @Throws(RemoteException::class)
    fun invoke(path: String) {
        getStringResponse(path, "")
    }

    @Throws(RemoteException::class)
    fun <TRequest> invoke(path: String, request: TRequest) {
        getStringResponse(path, gson.toJson(request))
    }

    @Throws(RemoteException::class)
    fun <TRequest, TResult> invoke(path: String, request: TRequest, resultClass: Class<TResult>): TResult {
        val stringResponse = getStringResponse(path, gson.toJson(request))
        return gson.fromJson<TResult>(stringResponse, resultClass)
    }

    private fun getStringResponse(path: String, content: String): String {
        val thePath = if (!path.startsWith("/")) "/" + path else path
        val postRequest = HttpPost(thePath)
        postRequest.entity = StringEntity(content, "UTF-8")
        httpClient.execute(host, postRequest).use { response ->
            val statusCode = response.statusLine.statusCode
            val body = coreUtils.readString(response.entity.content)
            if (statusCode == 500)
                throw RemoteException(body)
            else if (statusCode != 200)
                throw Exception("Invalid status code: $statusCode. Body: $body")
            return body
        }
    }
}

class RemoteException(message: String) : Exception(message) {

}