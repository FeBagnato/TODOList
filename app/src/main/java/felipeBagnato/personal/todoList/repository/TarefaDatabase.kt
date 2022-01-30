package felipeBagnato.personal.todoList.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TarefaEntity::class], version = 1)
abstract class TarefaDatabase: RoomDatabase(){
    abstract fun tarefaDao(): TarefaDAO

    companion object{
        private lateinit var INSTANCIA: TarefaDatabase

        fun getDatabase(context: Context): TarefaDAO{
            synchronized(TarefaDatabase::class.java){
                if(!::INSTANCIA.isInitialized){
                    INSTANCIA = Room.databaseBuilder(
                        context, TarefaDatabase::class.java,
                        "todoDB"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCIA.tarefaDao()
        }
    }
}