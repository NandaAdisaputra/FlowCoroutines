package com.nandaadisaputra.retrofit.injection

import android.content.Context
import androidx.databinding.ktx.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nandaadisaputra.retrofit.BuildConfig.BASE_URL_POST
import com.nandaadisaputra.retrofit.network.ApiHelper
import com.nandaadisaputra.retrofit.network.ApiHelperImpl
import com.nandaadisaputra.retrofit.network.ApiService
import com.nandaadisaputra.retrofit.room.BodyDatabase
import com.nandaadisaputra.retrofit.room.DatabaseBuilder
import com.nandaadisaputra.retrofit.room.DatabaseHelper
import com.nandaadisaputra.retrofit.room.DatabaseHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    fun provideBodyDatabase(@ApplicationContext context: Context): BodyDatabase =
        DatabaseBuilder.getInstance(context)

    @Provides
    fun provideGson(): Gson =
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val interceptors = HttpLoggingInterceptor()
            interceptors.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(interceptors)
        }

        return okHttpClient.build()
    }

    @Provides
    fun provideApiHelper(apiHelperImpl: ApiHelperImpl): ApiHelper = apiHelperImpl

    @Provides
    fun provideDatabaseHelper(databaseHelperImpl: DatabaseHelperImpl): DatabaseHelper =
        databaseHelperImpl

    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL_POST)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build().create(ApiService::class.java)
    }
}
