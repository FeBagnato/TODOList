package felipeBagnato.personal.todoList.ui.tarefas

import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import felipeBagnato.personal.todoList.R
import felipeBagnato.personal.todoList.repository.TarefaEntity

class TarefaViewHolder(itemView: View, private val listener: TarefaListener):
    RecyclerView.ViewHolder(itemView){

    fun bind(tarefa: TarefaEntity){
        val checkbox: CheckBox = itemView.findViewById(R.id.checkbox_tarefa)
        val nomeTarefa: TextView = itemView.findViewById(R.id.text_tarefa)
        val data: TextView = itemView.findViewById(R.id.text_data)

        nomeTarefa.text = tarefa.nome
        data.text = tarefa.data
        checkbox.isChecked = tarefa.feito


        with(nomeTarefa){
            if(checkbox.isChecked){
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            else{
                paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        nomeTarefa.setOnClickListener{
            listener.onClick(tarefa.id)
        }
        data.setOnClickListener{
            listener.onClick(tarefa.id)
        }

        checkbox.setOnClickListener{
            listener.onCheck(tarefa.id, checkbox.isChecked)
        }
    }
}