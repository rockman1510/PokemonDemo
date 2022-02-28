package com.pokemondemo.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pokemondemo.BuildConfig
import com.pokemondemo.roomdatabase.converter.PokemonConverter
import com.pokemondemo.roomdatabase.dao.PokemonDAO
import com.pokemondemo.roomdatabase.dao.PokemonStatDAO
import com.pokemondemo.roomdatabase.entity.PokemonEntity
import com.pokemondemo.roomdatabase.entity.PokemonStatEntity

@Database(entities = [PokemonEntity::class, PokemonStatEntity::class], version = 1)
@TypeConverters(PokemonConverter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getPokemonDAO(): PokemonDAO
    abstract fun getPokemonStatDAO(): PokemonStatDAO

    companion object {
        private var INSTANCE: AppDataBase? = null
        fun getRoomDatabase(context: Context): AppDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDataBase::class.java,
                    BuildConfig.APPLICATION_ID + AppDataBase::class.java.simpleName
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}