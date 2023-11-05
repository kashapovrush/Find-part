package com.kashapovrush.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kashapovrush.domain.Part
import com.kashapovrush.domain.PartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun getPartsList(): Flow<List<Part>> {
        return partListDao.getPartList().map {
            partMapper.mapListDbModelToListEntity(it)
        }
    }

    override fun getPartByName(text: String): Flow<List<Part>> {
        return partListDao.getPartByName(text).map {
            partMapper.mapListDbModelToListEntity(it)
        }
    }
}