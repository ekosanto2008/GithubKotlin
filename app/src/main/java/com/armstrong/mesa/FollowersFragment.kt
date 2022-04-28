package com.armstrong.mesa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowersFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var mAdapter: UserSearchAdapter
    private val TAG: String = "Followers"

    companion object {
        const val data = "DETAIL_USER"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_followers, container, false)
        getData(view)
        return view
    }

    private fun getData(view: View?) {
        if (view != null) {
            recyclerView = view.findViewById(R.id.rv_user)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.setHasFixedSize(true)
            RetrofitConfig.apiService.getFollowers(data)
                .enqueue(object : Callback<List<UserSearch.Result>> {
                    override fun onResponse(call: Call<List<UserSearch.Result>>, response: Response<List<UserSearch.Result>>) {
                        if (response.isSuccessful) {
                           val result = response.body()
                            Log.i(TAG, result.toString())
                        }
                    }

                    override fun onFailure(call: Call<List<UserSearch.Result>>, t: Throwable) {

                    }

                })
        }

    }
}