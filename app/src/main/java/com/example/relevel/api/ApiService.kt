package com.example.relevel.api

import com.example.relevel.model.users.CilentDataResponse
import com.example.relevel.utils.AllConstString
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(AllConstString.Get_All_Data)
    suspend fun getAllData(): Response<CilentDataResponse>


    /*@GET("uploads/dp/{user_profile_pic}.jpg")
    suspend fun getAllData(
        @Path("user_profile_pic") user_pic: String
    ): Response<CilentDataResponse>*/


}