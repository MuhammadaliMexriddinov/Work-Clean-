package uz.gita.waterappgoogleplaygb.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.waterappgoogleplaygb.data.local.AppDatabase
import uz.gita.waterappgoogleplaygb.data.local.dao.ReminderDao
import uz.gita.waterappgoogleplaygb.data.local.dao.WaterTrackerDao
import uz.gita.waterappgoogleplaygb.data.local.entity.ReminderEntity
import uz.gita.waterappgoogleplaygb.data.prefs.MySharedPref
import uz.gita.waterappgoogleplaygb.utils.extensions.getInitialDelayTime
import uz.gita.waterappgoogleplaygb.workmanager.Work
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "water_tracker.db")
            .addCallback(
                object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        val dao = provideReminderDao(provideDatabase(context))
                        CoroutineScope(Dispatchers.IO).launch {
                            for (i in 6..24) {
                                Log.d("TTT", "onCreate:$i")
                                val data = if(i<10) "0$i" else "$i"
                                val build = "${data}:00"
                                dao.insertReminder(ReminderEntity(0, build))
                                 addWorkManager(build, context)
                            }
                        }
                    }
                }
            ).build()
    }

    private fun addWorkManager(time: String, ctx: Context) {
        val request = PeriodicWorkRequest.Builder(Work::class.java, 24L, TimeUnit.HOURS)
            .setInitialDelay(getInitialDelayTime(time).toLong(), TimeUnit.SECONDS)
            .addTag(time)
            .build()

        WorkManager.getInstance(ctx).enqueue(request)
    }

    @[Provides Singleton]
    fun provideWaterTrackerDao(appDatabase: AppDatabase): WaterTrackerDao {
        return appDatabase.waterDao()
    }

    @[Provides Singleton]
    fun provideReminderDao(appDatabase: AppDatabase): ReminderDao = appDatabase.reminderDao()

    @[Provides Singleton]
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("app_data", Context.MODE_PRIVATE)

    @[Provides Singleton]
    fun provideSharedPrefs(
        @ApplicationContext context: Context,
        sharedPreferences: SharedPreferences
    ): MySharedPref =
        MySharedPref(context, sharedPreferences)
}