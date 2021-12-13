package com.example.demo.grpc

import io.grpc.Status
import org.lognet.springboot.grpc.recovery.GRpcExceptionHandler
import org.lognet.springboot.grpc.recovery.GRpcExceptionScope
import org.lognet.springboot.grpc.recovery.GRpcServiceAdvice


@GRpcServiceAdvice
class GRpcErrorInterceptor {

    @GRpcExceptionHandler
    fun globalHandler(e: Exception, scope: GRpcExceptionScope): Status {
        return Status.INTERNAL.withDescription("Global exception handler works!")
    }
}
