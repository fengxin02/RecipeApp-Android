package hu.bme.aut.android.recept.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.recept.data.api.MealAPI
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object Network {

    //Moshi json
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    //set website
    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/") //website
            .addConverterFactory(MoshiConverterFactory.create(moshi)) //converter
            .build()
    }

    //create the api
    @Provides
    @Singleton
    fun provideMealAPI(retrofit: Retrofit): MealAPI {
        return retrofit.create(MealAPI::class.java)
    }
}