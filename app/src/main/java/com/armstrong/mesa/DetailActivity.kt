package com.armstrong.mesa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_FILES = intArrayOf(
            R.string.followers,
            R.string.following
        )
        const val data = "DETAIL_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        getData()
        showNav()
    }

    private fun showNav() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(DetailActivity.TAB_FILES[position])
        }.attach()

        supportActionBar?.elevation = 0f
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