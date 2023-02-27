package uz.gita.waterappgoogleplaygb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.waterappgoogleplaygb.data.local.dao.ReminderDao
import uz.gita.waterappgoogleplaygb.data.local.dao.WaterTrackerDao
import uz.gita.waterappgoogleplaygb.data.local.entity.ReminderEntity
import uz.gita.waterappgoogleplaygb.data.local.entity.WaterEntity

@Database(entities = [WaterEntity::class, ReminderEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun waterDao(): WaterTrackerDao

    abstract fun reminderDao(): ReminderDao

}