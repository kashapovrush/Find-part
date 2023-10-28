package com.kashapovrush.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kashapovrush.domain.DeletePartUseCase
import com.kashapovrush.domain.GetPartListUseCase
import com.kashapovrush.domain.Part
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getPartListUseCase: GetPartListUseCase,
    private val deletePartUseCase: DeletePartUseCase
) : ViewModel() {

    val partList = getPartListUseCase.getPartList()

    fun deletePartItem(part: Part) {
        viewModelScope.launch {
            deletePartUseCase.deletePart(part)
        }

    }
}