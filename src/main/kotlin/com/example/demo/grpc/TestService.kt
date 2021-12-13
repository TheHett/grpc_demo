package com.example.demo.grpc

import demo.api.DemoServiceGrpcKt
import demo.api.Test
import io.grpc.Status
import org.lognet.springboot.grpc.GRpcService
import org.lognet.springboot.grpc.recovery.GRpcExceptionHandler
import org.lognet.springboot.grpc.recovery.GRpcExceptionScope

@GRpcService
class TestService : DemoServiceGrpcKt.DemoServiceCoroutineImplBase() {

    @GRpcExceptionHandler
    fun privateHandler(e: NullPointerException, scope: GRpcExceptionScope): Status {
        return Status.INTERNAL.withDescription("Private exception handler works!")
    }

    override suspend fun test(request: Test.Request): Test.Response {
        throw NullPointerException("Hello, it's me :)")
    }
}
