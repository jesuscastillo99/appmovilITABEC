package com.example.myapplicationf.data.model
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


data class UserResponse(
    val idpersona: Int,
    val completo: String,
    val curp: String
)

data class EstadoDeCuenta(
    val id: Int,
    val fechavencimiento: String,
    val monto: Double,
    val saldo: Double,
    val estatus: String
)

interface ApiService {
    @GET("credito.php")
    fun login(
        @Query("usuario") usuario: String,
        @Query("pas") pas: String
    ): Call<UserResponse>
}

interface ApiService2 {
    @GET("credito.php")
    fun getEstadosDeCuenta(@Query("edocta") idPersona: Int): Call<List<EstadoDeCuenta>>
}