package com.example.win22.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.win22.model.BettingModel

@Database(entities = [BettingModel::class], version = 1)
abstract class BetDatabase: RoomDatabase() {
    abstract fun bettingDao():BettingDao

    companion object{
        @Volatile
        private var INSTANSE: BetDatabase? = null
        fun getDatabase(context: Context): BetDatabase{
            val tempInstance = INSTANSE
            if (tempInstance!=null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BetDatabase::class.java,
                    "bet_database"
                ).build()
                INSTANSE = instance
                return instance
            }
        }
    }
}