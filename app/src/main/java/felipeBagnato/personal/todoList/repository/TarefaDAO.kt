package felipeBagnato.personal.todoList.repository

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TarefaDAO{
    @Query("SELECT * FROM Tarefa")
    fun getAll(): LiveData<List<TarefaEntity>>

    @Query("SELECT * FROM Tarefa WHERE id LIKE :id")
    suspend fun getOne(id: Int): TarefaEntity

    @Query("UPDATE Tarefa SET feito=:estado WHERE id=:id")
    fun changeState(id: Int, estado: Boolean)

    @Insert
    suspend fun save(tarefa: TarefaEntity): Long

    @Delete
    suspend fun delete(tarefa: TarefaEntity): Int

    @Update
    suspend fun update(tarefa: TarefaEntity): Int
}