package com.example.sr_kodtest.data.connection

import retrofit2.Response
import retrofit2.http.GET

interface SRApi {
    @GET("v2/programs/popular?format=json&pagination=false")
    suspend fun getPrograms(): Response<ProgramsResponse>
}
