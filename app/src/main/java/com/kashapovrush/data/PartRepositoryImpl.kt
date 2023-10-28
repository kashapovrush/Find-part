package com.kashapovrush.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kashapovrush.domain.Part
import com.kashapovrush.domain.PartRepository
import javax.inject.Inject

class PartRepositoryImpl @Inject constructor(
    private val partMapper: PartMapper,
    private val partListDao: PartListDao
) : PartRepository {

    override suspend fun addPart(part: Part) {
        partListDao.addPart(partMapper.mapEntityTODbModel(part))
    }

    override suspend fun deletePart(part: Part) {
        partListDao.deletePart(part.id)
    }

    override suspend fun editPart(part: Part) {
        partListDao.addPart(partMapper.mapEntityTODbModel(part))
    }

    override suspend fun getPartItem(id: Int): Part {
        return partMapper.mapDbModelToEntity(partListDao.getPartItem(id))
    }

    override fun getPartsList(): LiveData<List<Part>> {
        return MediatorLiveData<List<Part>>().apply {
            addSource(partListDao.getPartList()) {
                value = partMapper.mapListDbModelToListEntity(it)
            }
        }
    }
}