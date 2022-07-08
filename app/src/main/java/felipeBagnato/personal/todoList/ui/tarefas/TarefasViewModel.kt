package felipeBagnato.personal.todoList.ui.tarefas

import android.content.Context
import androidx.lifecycle.*
import felipeBagnato.personal.todoList.repository.TarefaDatabase
import felipeBagnato.personal.todoList.repository.TarefaEntity

class TarefasViewModel(context: Context) : ViewModel(){
    private val tarefaDAO = TarefaDatabase.getDatabase(context)
    private lateinit var tarefaLiveData: LiveData<List<TarefaEntity>>

    fun pegarTarefas(): LiveData<List<TarefaEntity>>{
        tarefaLiveData = tarefaDAO.getAll()
        return tarefaLiveData
    }

    fun mudarEstadoTarefa(id: Int, estado: Boolean){
        tarefaDAO.changeState(id, estado)
    }

    fun notifyObserver(){
        val tarefa = tarefaLiveData.value?.get(0)
        tarefaDAO.changeState(tarefa!!.id, !tarefa.feito)
        tarefaDAO.changeState(tarefa.id, tarefa.feito)
    }
}