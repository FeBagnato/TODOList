package felipeBagnato.personal.todoList.ui.tarefas.tarefasForm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import felipeBagnato.personal.todoList.repository.TarefaDatabase
import felipeBagnato.personal.todoList.repository.TarefaEntity
import kotlinx.coroutines.launch

class TarefaFormViewModel(context: Context): ViewModel(){
    private val tarefaDAO = TarefaDatabase.getDatabase(context)
    val isNomeVazio = MutableLiveData<Boolean>()
    val salvarTarefa = MutableLiveData<Long>()
    val atualizarTarefa = MutableLiveData<Int>()

    fun salvar(id: Int, nomeTarefa: String, descTarefa: String, data: String){
        val tarefa = TarefaEntity().apply{
            this.id = id
            this.nome = nomeTarefa
            this.descricao = descTarefa
            this.data = data
        }

        if(tarefa.data.length >= 13) tarefa.data = ""

        if(tarefa.nome.isBlank()){
            isNomeVazio.value = true
        }
        else{
            viewModelScope.launch{
                if (id == 0) salvarTarefa.value = tarefaDAO.save(tarefa)
                else atualizarTarefa.value = tarefaDAO.update(tarefa)
            }
        }
    }
}