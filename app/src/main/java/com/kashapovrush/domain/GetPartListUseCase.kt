package com.kashapovrush.domain

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPartListUseCase @Inject constructor(private val repository: PartRepository) {

    fun getPartList(): Flow<List<Part>> = repository.getPartsList()
}