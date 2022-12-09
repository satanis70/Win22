package com.example.win22.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "betting_table")
data class BettingModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "position")
    val position: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "odd")
    val odd: String,
    @ColumnInfo(name = "amount")
    val amount: String,
    @ColumnInfo(name= "status")
    val status: String,
    @ColumnInfo(name = "capital")
    val capital: String
)
