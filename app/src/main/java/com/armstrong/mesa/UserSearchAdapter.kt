package com.armstrong.mesa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class UserSearchAdapter(private val listUser: ArrayList<UserSearch.Result>): RecyclerView.Adapter<UserSearchAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]

        holder.tvName.text = user.login
        holder.tvUrl.text = user.url
        Glide.with(holder.itemView.context)
            .load(user.avatar_url)
            .apply(RequestOptions().override(55, 55))
            .into(holder.tvImg)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.bindingAdapterPosition]) }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.name)
        var tvUrl: TextView = itemView.findViewById(R.id.url)
        var tvImg: ImageView = itemView.findViewById(R.id.img_item_photo)
    }

    fun setData(data: List<UserSearch.Result>) {
        this.listUser.clear()
        this.listUser.addAll(data)
        notifyItemChanged(data.size)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserSearch.Result)
    }
}