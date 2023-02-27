package uz.gita.waterappgoogleplaygb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_track")
data class WaterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val drink: Int,
    val date: String,
    val time: String
)
