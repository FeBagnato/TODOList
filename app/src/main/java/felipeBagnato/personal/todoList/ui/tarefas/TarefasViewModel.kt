package felipeBagnato.personal.todoList.ui.tarefas

import android.content.Context
import androidx.lifecycle.*
import felipeBagnato.personal.todoList.repository.TarefaDatabase
import felipeBagnato.personal.todoList.repository.TarefaEntity

class TarefasViewModel(context: Context) : ViewModel(){
    private val tarefaDAO = TarefaDatabase.getDatabase(context)

    fun pegarTarefas(): LiveData<List<TarefaEntity>>{
        return tarefaDAO.getAll()
    }

    fun mudarEstadoTarefa(id: Int, estado: Boolean){
        tarefaDAO.changeState(id, estado)
    }
}