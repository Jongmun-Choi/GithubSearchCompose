package com.dave.githubsearchcompose.network

import androidx.lifecycle.asLiveData
import com.dave.githubsearchcompose.BuildConfig
import com.dave.githubsearchcompose.repository.ApiRepository
import com.dave.githubsearchcompose.repository.AuthRepository
import com.dave.githubsearchcompose.repository.TokenRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Auth

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Api

    @Provides
    fun provideGitHubAuthUrl(): String = BuildConfig.AUTH_URL

    @Provides
    fun provideGitHubApiUrl(): String = BuildConfig.API_URL

    private val resultFactory = ResultFactory()

    @Provides
    @Singleton
    @Auth
    fun provideAuthInterceptor(tokenRepository: TokenRepository) : Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()

            requestBuilder.addHeader("Accept", "application/json")
            tokenRepository.getToken().let { accessToken ->
                requestBuilder.addHeader("Authorization", "Bearer $accessToken")
            }

            chain.proceed(requestBuilder.build())
        }
    }


    @Provides
    @Singleton
    @Auth
    fun provideAuthOkHttpClient(@Auth interceptor: Interceptor) =
        if(BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .connectTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS).writeTimeout(150, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).addInterceptor(interceptor).build()
        } else {
            OkHttpClient.Builder().connectTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS).writeTimeout(150, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build()
        }

    @Singleton
    @Provides
    @Auth
    fun provideAccessRetrofit(@Auth okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder().build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(resultFactory)
            .baseUrl(provideGitHubAuthUrl())
            .client(okHttpClient)
            .build()
    }
    @Singleton
    @Provides
    @Auth
    fun provideAccessService(@Auth retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)

    @Singleton
    @Provides
    @Auth
    fun provideAccessRepository(@Auth accessService: AuthService)= AuthRepository(accessService)


    @Provides
    @Singleton
    @Api
    fun provideApiInterceptor(tokenRepository: TokenRepository) : Interceptor {
        return Interceptor { chain ->

            val requestBuilder = chain.request().newBuilder()

            requestBuilder.addHeader("Accept", "application/json")
            val token = runBlocking {
                tokenRepository.getToken().first()
            }
            requestBuilder.addHeader("Authorization", "Bearer $token")
            chain.proceed(requestBuilder.build())

        }
    }

    @Provides
    @Singleton
    @Api
    fun provideApiOkHttpClient(@Api interceptor: Interceptor) =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .connectTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS).writeTimeout(150, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).addInterceptor(interceptor).build()
        } else {
            OkHttpClient.Builder().connectTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS).writeTimeout(150, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build()
        }

    @Singleton
    @Provides
    @Api
    fun provideApiRetrofit(@Api okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder().build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(resultFactory)
            .baseUrl(provideGitHubApiUrl())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @Api
    fun provideApiService(@Api retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    @Api
    fun provideApiRepository(@Api apiService: ApiService)= ApiRepository(apiService)
}