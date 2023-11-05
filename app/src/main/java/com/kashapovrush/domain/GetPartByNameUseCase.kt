package com.kashapovrush.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPartByNameUseCase @Inject constructor(private val repository: PartRepository) {

    fun getPartByName(text: String): Flow<List<Part>> {
        return repository.getPartByName(text)
    }
}