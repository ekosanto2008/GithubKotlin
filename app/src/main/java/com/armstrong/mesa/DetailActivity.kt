package com.armstrong.mesa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    companion object {
        const val data = "DETAIL_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        getData()
    }

    private fun getData() {
        val tvName: TextView = findViewById(R.id.name)
        val tvBio: TextView = findViewById(R.id.bio)
        val tvCompany: TextView = findViewById(R.id.company)
        val img: ImageView = findViewById(R.id.img_detail)
        val name = intent.getStringExtra(data)
        if (name != null) {
            RetrofitConfig.apiService.getUsers(name)
                .enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            tvName.text = response.body()?.login
                            tvBio.text = response.body()?.bio
                            tvCompany.text = response.body()?.company
                            Glide.with(applicationContext)
                                .load(response.body()?.avatar_url)
                                .into(img)
                        }else{
                            tvName.text = ""
                            tvBio.text = ""
                            tvCompany.text = ""
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
        }
    }
}