package com.kashapovrush.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kashapovrush.domain.Part

@Dao
interface PartListDao {

    @Query("SELECT * FROM parts3")
    fun getPartList(): LiveData<List<PartDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPart(partDb: PartDb)

    @Query("DELETE FROM parts3 WHERE id=:id")
    suspend fun deletePart(id: Int)

    @Query("SELECT * FROM parts3 WHERE id=:id LIMIT 1")
    suspend fun getPartItem(id: Int): PartDb
}