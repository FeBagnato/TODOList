package felipeBagnato.personal.todoList.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import felipeBagnato.personal.todoList.constants.DatabaseConstants

@Dao
interface TarefaDAO{
    @Query("SELECT * FROM ${DatabaseConstants.TAREFA.NOME_TABELA}")
    fun getAll(): LiveData<List<TarefaEntity>>

    @Query("SELECT * FROM ${DatabaseConstants.TAREFA.NOME_TABELA} " +
            "WHERE ${DatabaseConstants.TAREFA.COLUNA.ID} LIKE :id")
    suspend fun getOne(id: Int): TarefaEntity

    @Query("UPDATE ${DatabaseConstants.TAREFA.NOME_TABELA} " +
            "SET ${DatabaseConstants.TAREFA.COLUNA.DONE} = :estado " +
            "WHERE ${DatabaseConstants.TAREFA.COLUNA.ID} = :id")
    fun changeState(id: Int, estado: Boolean)

    @Insert
    suspend fun save(tarefa: TarefaEntity): Long

    @Delete
    suspend fun delete(tarefa: TarefaEntity): Int

    @Update
    suspend fun update(tarefa: TarefaEntity): Int
}