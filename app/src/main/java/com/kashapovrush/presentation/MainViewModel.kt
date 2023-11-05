package com.kashapovrush.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kashapovrush.domain.DeletePartUseCase
import com.kashapovrush.domain.GetPartByNameUseCase
import com.kashapovrush.domain.GetPartListUseCase
import com.kashapovrush.domain.Part
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getPartListUseCase: GetPartListUseCase,
    private val deletePartUseCase: DeletePartUseCase,
    private val getPartByNameUseCase: GetPartByNameUseCase
) : ViewModel() {

    val partList = getPartListUseCase.getPartList().asLiveData()

    fun deletePartItem(part: Part) {
        viewModelScope.launch {
            deletePartUseCase.deletePart(part)
        }

    }

    fun getPartByName(text: String): LiveData<List<Part>> {
        return getPartByNameUseCase.getPartByName(text).asLiveData()
    }
}