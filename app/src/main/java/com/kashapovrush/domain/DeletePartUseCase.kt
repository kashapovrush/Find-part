package com.kashapovrush.domain

import javax.inject.Inject

class DeletePartUseCase @Inject constructor( private val repository: PartRepository) {

    suspend fun deletePart(part: Part) {
        repository.deletePart(part)
    }

}