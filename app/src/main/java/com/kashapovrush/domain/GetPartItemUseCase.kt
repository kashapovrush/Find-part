package com.kashapovrush.domain

import javax.inject.Inject

class GetPartItemUseCase @Inject constructor(private val repository: PartRepository) {

    suspend fun getPartItem(id: Int): Part = repository.getPartItem(id)
}