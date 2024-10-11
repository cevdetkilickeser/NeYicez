package com.cevdetkilickeser.neyicez.retrofit

class ApiUtils {
    companion object{
        val BASE_URL = "http://kasimadalan.pe.hu/"

        fun apiService() : ApiService {
            return RetrofitClient.getClient(BASE_URL).create(ApiService::class.java)
        }
    }
}