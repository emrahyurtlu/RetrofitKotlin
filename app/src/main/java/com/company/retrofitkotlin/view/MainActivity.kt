package com.company.retrofitkotlin.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.retrofitkotlin.R
import com.company.retrofitkotlin.adapter.RecyclerViewAdapter
import com.company.retrofitkotlin.model.CryptoModel
import com.company.retrofitkotlin.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {
    private val BASE_URL = "https://api.nomics.com/v1/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var recAdapter: RecyclerViewAdapter? = null

    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //4f45a3a309631e9d8c21450c1d8172d6
        // SERVER_URL https://api.nomics.com/v1

        // https://api.nomics.com/v1/prices?key=4f45a3a309631e9d8c21450c1d8172d6

        compositeDisposable = CompositeDisposable()

        val layoutManaget: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManaget


        loadData()


    }

    private fun loadData() {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        compositeDisposable?.add(
            retrofit.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )


        /*
        Burada Retrofit Kullandık. Bellek kullanımı konusunda bir kısım handikaplar olabileceği için sonrasında RxJava kullanımı uyguladık.
        val service = retrofit.create(CryptoAPI::class.java)

        val call = service.getData()
        call.enqueue(object: Callback<List<CryptoModel>> {
            override fun onResponse(call: Call<List<CryptoModel>>, response: Response<List<CryptoModel>>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        cryptoModels = ArrayList(it)

                        recAdapter = RecyclerViewAdapter(cryptoModels!!, this@MainActivity)
                        recyclerView.adapter = recAdapter
                    }

                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })

        */
    }

    private fun handleResponse(list: List<CryptoModel>){
        cryptoModels = ArrayList(list)

        cryptoModels?.let {
            recAdapter = RecyclerViewAdapter(it, this@MainActivity)
            recyclerView.adapter = recAdapter
        }


    }

    override fun onItemClick(model: CryptoModel) {
        println(model)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}