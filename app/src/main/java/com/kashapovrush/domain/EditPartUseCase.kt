package com.kashapovrush.domain

import javax.inject.Inject

class EditPartUseCase @Inject constructor(private val repository: PartRepository) {

    suspend fun editPart(part: Part) {
        repository.editPart(part)
    }
}