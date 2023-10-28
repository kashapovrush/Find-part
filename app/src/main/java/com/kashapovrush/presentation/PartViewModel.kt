package com.kashapovrush.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kashapovrush.domain.AddPartUseCase
import com.kashapovrush.domain.EditPartUseCase
import com.kashapovrush.domain.GetPartItemUseCase
import com.kashapovrush.domain.Part
import kotlinx.coroutines.launch
import javax.inject.Inject

class PartViewModel @Inject constructor(
    private val getPartItemUseCase: GetPartItemUseCase,
    private val editPartItemUseCase: EditPartUseCase,
    private val addPartItemUseCase: AddPartUseCase
) : ViewModel() {

    private val _errorInputNumber = MutableLiveData<Boolean>()
    val errorInputNumber: LiveData<Boolean>
        get() = _errorInputNumber

    private val _errorInputLocation = MutableLiveData<Boolean>()
    val errorInputLocation: LiveData<Boolean>
        get() = _errorInputLocation

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _closeActivity = MutableLiveData<Unit>()
    val closeActivity: LiveData<Unit>
        get() = _closeActivity

    private val _showItem = MutableLiveData<Part>()
    val showItem: LiveData<Part>
        get() = _showItem


    fun getPartItem(id: Int) {
        viewModelScope.launch {
            _showItem.postValue(getPartItemUseCase.getPartItem(id))
        }
    }

    fun addPartItem(
        inputNumber: String?,
        inputName: String?,
        inputCount: String?,
        date: String,
        time: String,
        inputLocation: String?
    ) {
        val number = parseNumber(inputNumber)
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val location = parseLocation(inputLocation)
        val validate = validate(number, name, count, location)
        if (validate) {
            viewModelScope.launch {
                addPartItemUseCase.addPart(Part(number, name, count, date, time, location))
                finishWork()
            }
        }
    }

    fun editPartItem(
        inputNumber: String?,
        inputName: String?,
        inputCount: String?,
        date: String,
        time: String,
        inputLocation: String?
    ) {
        val number = parseNumber(inputNumber)
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val location = parseLocation(inputLocation)
        val validate = validate(number, name, count, location)
        if (validate) {
            viewModelScope.launch {
                _showItem.value?.let {
                    editPartItemUseCase.editPart(
                        it.copy(
                            number = number,
                            name = name,
                            count = count,
                            date = date,
                            time = time,
                            location = location
                        )
                    )
                    finishWork()
                }
            }

        }
    }

    private fun finishWork() {
        _closeActivity.postValue(Unit)
    }

    private fun validate(number: String, name: String, count: Int, location: String): Boolean {
        var result = true
        if (number.isBlank()) {
            _errorInputNumber.value = true
            result = false
        }

        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }

        if (location.isBlank()) {
            _errorInputLocation.value = true
            result = false
        }

        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }

        return result
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseLocation(inputLocation: String?): String {
        return inputLocation?.trim() ?: ""
    }

    private fun parseNumber(inputNumber: String?): String {
        return inputNumber?.trim() ?: ""

    }

    fun resetValueInputName() {
        _errorInputName.value = false
    }

    fun resetValueInputCount() {
        _errorInputCount.value = false
    }

    fun resetValueInputNumber() {
        _errorInputNumber.value = false
    }
}