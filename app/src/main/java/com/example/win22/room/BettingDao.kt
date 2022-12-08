package com.example.win22.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.win22.model.BettingModel

@Dao
interface BettingDao {
    @Query("SELECT * FROM betting_table")
    fun getAllData():List<BettingModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(bettingModel: BettingModel)

    @Delete()
    suspend fun deleteData(bettingModel: BettingModel)

    @Query("DELETE FROM betting_table")
    suspend fun deleteDatabase()
}