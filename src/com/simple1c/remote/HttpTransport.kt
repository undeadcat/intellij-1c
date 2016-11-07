package com.simple1c.remote

import com.google.gson.Gson
import org.apache.http.HttpHost
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder

class HttpTransport(port: Int) {
    private val host = HttpHost("localhost", port, "http")
    var requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000).build()!!

    private val httpClient = HttpClientBuilder.create()
            .setDefaultRequestConfig(requestConfig)
            .build()

    private val gson = Gson()

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
            if (statusCode != 200)
                throw RemoteException("Invalid status code: $statusCode. Body: $body")
            return body
        }
    }
}

class RemoteException(message: String) : Exception(message) {

}