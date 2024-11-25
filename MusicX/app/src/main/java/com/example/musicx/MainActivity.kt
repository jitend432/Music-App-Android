package com.example.musicx

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var myRecyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        myRecyclerView= findViewById(R.id.recyclerView)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData(query = "eminem")

        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                //If the API call is success then this method will executed

                val dataList = response.body()?.data!!
             //   val textView = findViewById<TextView>(R.id.hello)
             //   textView.text = dataList.toString()

                myAdapter= MyAdapter(this@MainActivity, dataList)
                myRecyclerView.adapter= myAdapter
                myRecyclerView.layoutManager= LinearLayoutManager(this@MainActivity)

                Log.d("TAG: onResponse", "onResponse: " + response.body())
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                //On failure this method will executed
                Log.d("TAG: onFailure", "onFailure: " + t.message)
            }
        })
    }
}