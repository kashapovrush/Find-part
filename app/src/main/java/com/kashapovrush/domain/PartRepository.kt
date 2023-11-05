package com.kashapovrush.domain

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

interface PartRepository {

    suspend fun addPart(part: Part)

    suspend fun deletePart(part: Part)

    suspend fun editPart(part: Part)

    suspend fun getPartItem(id: Int): Part

    fun getPartsList(): Flow<List<Part>>

    fun getPartByName(text: String): Flow<List<Part>>

}