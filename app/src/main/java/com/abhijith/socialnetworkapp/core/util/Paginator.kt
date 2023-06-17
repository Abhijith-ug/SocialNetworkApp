package com.abhijith.socialnetworkapp.core.util

interface Paginator<T> {
    suspend fun loadNextItems()
}