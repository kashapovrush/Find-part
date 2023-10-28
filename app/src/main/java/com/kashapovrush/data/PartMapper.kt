package com.kashapovrush.data

import com.kashapovrush.domain.Part
import javax.inject.Inject

class PartMapper @Inject constructor() {

    fun mapEntityTODbModel(part: Part): PartDb {
        return PartDb(
            id =part.id,
            number = part.number,
            name = part.name,
            count = part.count,
            date = part.date,
            time = part.time,
            location = part.location
        )
    }

    fun mapDbModelToEntity(partDb: PartDb): Part {
        return Part(
            id = partDb.id,
            number = partDb.number,
            name = partDb.name,
            count = partDb.count,
            date = partDb.date,
            time = partDb.time,
            location = partDb.location
        )
    }

    fun mapListDbModelToListEntity(list: List<PartDb>): List<Part> = list.map {
        mapDbModelToEntity(it)
    }
}