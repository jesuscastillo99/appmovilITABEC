package com.example.myapplicationf.data.model
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


data class UserResponse(
    val idpersona: Int,
    val completo: String,
    val curp: String
)

interface ApiService {
    @GET("credito.php")
    fun login(
        @Query("usuario") usuario: String,
        @Query("pas") pas: String
    ): Call<UserResponse>
}