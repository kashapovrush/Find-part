package com.kashapovrush.domain

import androidx.lifecycle.LiveData

interface PartRepository {

    suspend fun addPart(part: Part)

    suspend fun deletePart(part: Part)

    suspend fun editPart(part: Part)

    suspend fun getPartItem(id: Int): Part

    fun getPartsList(): LiveData<List<Part>>

}