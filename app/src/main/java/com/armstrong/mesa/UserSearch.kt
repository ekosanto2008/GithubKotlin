package com.armstrong.mesa

data class UserSearch(val items: ArrayList<Result>) {
    data class Result (val login: String, val avatar_url: String, val url: String)
}
