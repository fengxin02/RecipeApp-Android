package hu.bme.aut.android.recept.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.recept.data.model.Meal
import javax.inject.Singleton
import kotlin.jvm.java

//Hilt
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    //create a database
    @Provides
    @Singleton
    fun provideDatabaseInstance(@ApplicationContext context: Context): MealBase {
        return Room.databaseBuilder(
            context,
            MealBase::class.java,
            "recept_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    //give back Dao
    @Provides
    @Singleton
    fun provideMealDao(database: MealBase): MealDAO {
        return database.mealDao()
    }
}