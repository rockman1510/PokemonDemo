package com.pokemondemo.constant

object Constants {
    const val CACHE_SIZE = (10 * 5 * 1024 * 1024).toLong()
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 45L
    const val CONNECT_TIMEOUT = 30L
    const val CACHE_CONTROL = "Cache-Control"
    const val TIME_CACHE_ONLINE = "public, max-age = 60" // 1 minute
    const val TIME_CACHE_OFFLINE = "public, only-if-cached, max-stale = 604800"

    const val QUERY_OFFSET = "offset"
    const val QUERY_LIMIT = "limit"
}