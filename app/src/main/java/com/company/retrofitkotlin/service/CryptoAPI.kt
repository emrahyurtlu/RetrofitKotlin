package com.company.retrofitkotlin.service

import com.company.retrofitkotlin.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {

    @GET("prices?key=4f45a3a309631e9d8c21450c1d8172d6")
    //fun getData(): Call<List<CryptoModel>>
    fun getData(): Observable<List<CryptoModel>>

}