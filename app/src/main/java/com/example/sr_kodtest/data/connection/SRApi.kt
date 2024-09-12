package com.example.sr_kodtest.data.connection

import retrofit2.http.GET

interface SRApi {
    @GET("v2/programs/popular?format=json&pagination=true")
    suspend fun getPrograms(): ProgramsResponse
}