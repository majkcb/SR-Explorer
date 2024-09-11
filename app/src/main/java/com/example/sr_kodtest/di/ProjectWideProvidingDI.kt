package com.example.sr_kodtest.di

import android.content.Context
import androidx.room.Room
import com.example.sr_kodtest.connection.SRApi
import com.example.sr_kodtest.roomDB.FavoriteProgramDB
import com.example.sr_kodtest.roomDB.FavoriteProgramDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProjectWideProvidingDI {

    @Provides
    fun provideRetrofit(): Retrofit {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()


        return Retrofit.Builder().baseUrl("https://api.sr.se/api/").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun provideSRApi(retrofit: Retrofit): SRApi {
        return retrofit.create(SRApi::class.java)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object DatabaseModule {

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): FavoriteProgramDB {
            return Room.databaseBuilder(
                context.applicationContext,
                FavoriteProgramDB::class.java,
                "favoriteProgram_database"
            ).build()
        }

        @Provides
        @Singleton
        fun provideFavoriteProgramDao(database: FavoriteProgramDB): FavoriteProgramDAO {
            return database.favoriteProgramDao()
        }
    }


}