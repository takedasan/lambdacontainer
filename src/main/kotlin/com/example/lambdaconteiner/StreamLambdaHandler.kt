    package com.example.lambdaconteiner

import com.amazonaws.serverless.exceptions.ContainerInitializationException
import com.amazonaws.serverless.proxy.model.AwsProxyRequest
import com.amazonaws.serverless.proxy.model.AwsProxyResponse
import com.amazonaws.serverless.proxy.spring.SpringLambdaContainerHandler
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


class StreamLambdaHandler : RequestStreamHandler {
    private var handler: SpringLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse>? = null

    init {
        try {
            handler = SpringLambdaContainerHandler.getAwsProxyHandler(LambdaconteinerApplication::class.java)

        } catch (e: ContainerInitializationException) {
            // if we fail here. We re-throw the exception to force another cold start
            e.printStackTrace()
            throw RuntimeException("Could not initialize Spring framework", e)
        }
    }

    @Throws(IOException::class)
    override fun handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context) {
        handler!!.proxyStream(inputStream, outputStream, context)

        // just in case it wasn't closed by the mapper
        outputStream.close()
    }
}
