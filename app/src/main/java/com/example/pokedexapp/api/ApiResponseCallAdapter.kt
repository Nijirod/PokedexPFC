package com.example.pokedexapp.api

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseCallAdapter<R>(private val responseType: Type) : CallAdapter<R, Call<ApiResponse<R>>> {
    override fun responseType() = responseType

    override fun adapt(call: Call<R>): Call<ApiResponse<R>> {
        return ApiResponseCall(call)
    }
}

class ApiResponseCallAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }
        val callType = getParameterUpperBound(0, returnType as ParameterizedType)
        if (getRawType(callType) != ApiResponse::class.java) {
            return null
        }
        val responseType = getParameterUpperBound(0, callType as ParameterizedType)
        return ApiResponseCallAdapter<Any>(responseType)
    }
}