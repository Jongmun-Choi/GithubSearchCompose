package com.dave.githubsearchcompose.network

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit

// 에러 처리 관련 로직 수정 요함
class ResultCall<T>(private val call: Call<T>, private val retrofit: Retrofit) : Call<Result<T>> {
    override fun enqueue(callback: Callback<Result<T>>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    callback.onResponse(
                        this@ResultCall,
                        // 성공 시 response.body가 null일 경우, type을 Unit으로 적용해야함.
                        Response.success(response.code(), if (response.body() != null) Result.success(response.body()!!) else Result.success(Unit as T))
                    )
                } else {
                    if(response.code() == 500) {
                        // 서버 에러 로깅처리
                    }
                    val errorBody = response.errorBody()

                    if (errorBody == null) {
                        callback.onResponse(this@ResultCall, Response.success(Result.failure(Throwable(response.code().toString(), HttpException(response)))))
                    } else {
                        callback.onResponse(this@ResultCall, Response.error(response.code(), errorBody))
                    }
                }
            }
            override fun onFailure(call: Call<T>, t: Throwable) {
                val message = t.localizedMessage
                callback.onResponse(
                    this@ResultCall,
                    Response.success(Result.failure(Throwable(message, t)))
                )
            }
        })
    }

    override fun isExecuted(): Boolean {
        return call.isExecuted
    }

    override fun execute(): Response<Result<T>> {
        return Response.success(Result.success(call.execute().body()!!))
    }

    override fun cancel() {
        call.cancel()
    }

    override fun isCanceled(): Boolean {
        return call.isCanceled
    }

    override fun clone(): Call<Result<T>> {
        return ResultCall(call.clone(), retrofit)
    }

    override fun request(): Request {
        return call.request()
    }

    override fun timeout(): Timeout {
        return call.timeout()
    }
}

class NetworkException(
    val code: String,
    override  val message: String
) : Exception(message)