package com.example.newsapp.di

import com.example.newsapp.BuildConfig.BASE_URL
import com.example.newsapp.data.remote.ApiInterface
import com.example.newsapp.data.repositoryimpl.MainRepositoryImpl
import com.example.newsapp.domain.repository.MainRepository
import com.example.newsapp.presentation.article_list.ArticleViewModel
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.newsapp.utils.addLoggingInterceptor
import java.util.concurrent.TimeUnit

private const val timeOut = 50L

val networkModule = module {
    single {
        GsonBuilder().setLenient().create()
    }

    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addLoggingInterceptor(androidApplication().applicationContext)
            .connectTimeout(timeout = timeOut, TimeUnit.SECONDS)
            .readTimeout(timeout = timeOut, TimeUnit.SECONDS)
            .writeTimeout(timeout = timeOut, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(ApiInterface::class.java)
    }
}

val repositoryModule = module {
    single<MainRepository> { MainRepositoryImpl(api = get()) }
}

val viewModelModule = module {
    viewModel { ArticleViewModel(mainRepository = get()) }
}
