package com.kashapovrush.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetPartListUseCase @Inject constructor(private val repository: PartRepository) {

    fun getPartList(): LiveData<List<Part>> = repository.getPartsList()
}