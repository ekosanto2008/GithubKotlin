package com.armstrong.mesa

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView
    lateinit var mAdapter: UserSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getData()
        showRv()
    }

    private fun showRv() {
        mAdapter = UserSearchAdapter(arrayListOf())
        recyclerView = findViewById(R.id.rv_user)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter.setOnItemClickCallback(object : UserSearchAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserSearch.Result) {
                showSelectedUser(data)
            }

        })
    }

    private fun getData() {
        progressBar = findViewById(R.id.progressbar)
        imageView = findViewById(R.id.octopus)
        progressBar.visibility = View.GONE
        searchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                progressBar.visibility = View.VISIBLE
                imageView.visibility = View.GONE
                if (p0 != null) {
                    RetrofitConfig.apiService.getData(p0)
                        .enqueue(object : Callback<UserSearch> {
                            override fun onResponse(call: Call<UserSearch>, response: Response<UserSearch>) {
                                if (response.isSuccessful) {
                                    progressBar.visibility = View.GONE
                                    showData(response.body()!!)
                                    recyclerView.adapter = mAdapter
                                }
                            }

                            override fun onFailure(call: Call<UserSearch>, t: Throwable) {
                               Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                            }
                        })
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun showData(data: UserSearch) {
        val results = data.items
        mAdapter.setData(results)
    }

    private fun showSelectedUser(user: UserSearch.Result){
        Toast.makeText(this, "You choose " + user.login, Toast.LENGTH_SHORT).show()
    }
}