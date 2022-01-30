package felipeBagnato.personal.todoList.ui.tarefas.tarefasInfo

import androidx.appcompat.app.AlertDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import felipeBagnato.personal.todoList.R
import felipeBagnato.personal.todoList.repository.TarefaDatabase
import felipeBagnato.personal.todoList.repository.TarefaEntity
import kotlinx.coroutines.launch

class TarefaInfoViewModel(val context: Context): ViewModel(){
    val tarefaDAO = TarefaDatabase.getDatabase(context)
    val carregar = MutableLiveData<TarefaEntity>()
    val excluir = MutableLiveData<Int>()

    fun carregarTarefa(id: Int){
        viewModelScope.launch{
            carregar.value = tarefaDAO.getOne(id)
        }
    }

    fun excluirTarefa(tarefa: TarefaEntity){
        AlertDialog.Builder(context, R.style.alertDialogStyle)
            .setTitle("Remover Tarefa")
            .setMessage("Você deseja remover essa tarefa?")
            .setPositiveButton("Sim"){dialog, which ->
                viewModelScope.launch{
                    excluir.value = tarefaDAO.delete(tarefa)
                }
            }
            .setNeutralButton("Não", null).show()
    }
}