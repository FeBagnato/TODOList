package felipeBagnato.personal.todoList.ui.tarefas


import android.content.Context
import felipeBagnato.personal.todoList.R
import felipeBagnato.personal.todoList.repository.TarefaEntity
import java.text.SimpleDateFormat

abstract class TarefaSorting{
    companion object{
        fun maisAntigo(list: List<TarefaEntity>): List<TarefaEntity>{
            return list;
        }

        fun maisNovo(list: List<TarefaEntity>): List<TarefaEntity>{
            return list.reversed();
        }

        fun dataLimite(list: MutableList<TarefaEntity>, context: Context): List<TarefaEntity>{
            val newList: MutableList<TarefaEntity> = ArrayList<TarefaEntity>();
            val nullDateList = mutableListOf<TarefaEntity>();
            while(list.isNotEmpty()){
                val dateFormat = SimpleDateFormat(context.getString(R.string.date_format));
                var menorValor = dateFormat.parse(list[0].data);
                var tarefa = TarefaEntity();

                for(i in list){
                    val date = dateFormat.parse(i.data);
                    if(menorValor != null){
                        if(menorValor.after(date) || menorValor == date){
                            menorValor = date;
                            tarefa = i;
                        }
                    }
                    else{
                        menorValor = dateFormat.parse(i.data)
                        tarefa = i;
                    }

                    if(date == null){
                        nullDateList.add(i);
                        list.remove(i);
                    }
                }
                newList.add(tarefa);
                list.remove(tarefa);
            }

            newList += newList + nullDateList;
            return newList.toList();
        }

        fun alfabetica(list: List<TarefaEntity>): List<TarefaEntity>{
            return list;
        }
    }
}