package com.example.pokedexapp.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okio.Timeout

class ApiResponseCall<R>(private val delegate: Call<R>) : Call<ApiResponse<R>> {

    override fun enqueue(callback: Callback<ApiResponse<R>>) {
        delegate.enqueue(object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                val apiResponse = ApiResponse.create(response)
                callback.onResponse(this@ApiResponseCall, Response.success(apiResponse))
            }

            override fun onFailure(call: Call<R>, t: Throwable) {
                val apiResponse = ApiResponse.create<R>(t)
                callback.onResponse(this@ApiResponseCall, Response.success(apiResponse))
            }
        })
    }

    override fun clone(): Call<ApiResponse<R>> = ApiResponseCall(delegate.clone())

    override fun execute(): Response<ApiResponse<R>> {
        throw UnsupportedOperationException("ApiResponseCall doesn't support execute")
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request() = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}