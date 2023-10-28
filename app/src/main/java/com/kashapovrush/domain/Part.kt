package com.kashapovrush.domain

data class Part(
    val number: String,
    val name: String,
    val count: Int,
    val date: String,
    val time: String,
    val location: String,
    var id: Int = UNDEFINDED_ID
) {
    companion object {
        const val UNDEFINDED_ID = 0
    }

}

