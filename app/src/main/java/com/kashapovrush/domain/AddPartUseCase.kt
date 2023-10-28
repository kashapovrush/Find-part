package com.kashapovrush.domain

import javax.inject.Inject

class AddPartUseCase @Inject constructor(private val repository: PartRepository) {

    suspend fun addPart(part: Part) {
        repository.addPart(part)
    }
}