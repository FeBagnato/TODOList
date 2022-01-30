package felipeBagnato.personal.todoList.ui.tarefas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import felipeBagnato.personal.todoList.R
import felipeBagnato.personal.todoList.repository.TarefaEntity

class TarefaAdapter: RecyclerView.Adapter<TarefaViewHolder>(){
    var listTarefa: List<TarefaEntity> = arrayListOf()
    lateinit var listener: TarefaListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.tarefa_item,
            parent, false)

        return TarefaViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        holder.bind(listTarefa[position])
    }

    override fun getItemCount(): Int {
        return listTarefa.count()
    }

    fun atualizar(novaLista: List<TarefaEntity>){
        this.listTarefa = novaLista
        notifyDataSetChanged()
    }

    fun setTarefaListener(novoListener: TarefaListener){
        listener = novoListener
    }
}