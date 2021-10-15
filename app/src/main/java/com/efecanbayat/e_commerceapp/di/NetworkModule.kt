package com.efecanbayat.e_commerceapp.di

import com.efecanbayat.e_commerceapp.data.remote.APIService
import com.efecanbayat.e_commerceapp.data.remote.RemoteDataSource
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
class NetworkModule {

    @Provides
    fun provideApiService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
        endPoint: EndPoint
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endPoint.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideOkHttpClient(
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .build()
    }


    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideRemoteDataSource(
        apiService: APIService,
    ): RemoteDataSource {
        return RemoteDataSource(apiService)
    }


    @Provides
    fun provideEndPoint(): EndPoint {
        return EndPoint("https://us-central1-mobiroller-8200b.cloudfunctions.net/widgets/")
    }


}

data class EndPoint(val url: String)