package felipeBagnato.personal.todoList.ui.tarefas

import android.content.Context
import felipeBagnato.personal.todoList.R
import felipeBagnato.personal.todoList.repository.TarefaEntity
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

abstract class TarefaSorting{
    companion object{
        fun maisAntigo(list: List<TarefaEntity>): List<TarefaEntity>{
            return list
        }

        fun maisNovo(list: List<TarefaEntity>): List<TarefaEntity>{
            return list.reversed()
        }

        fun dataLimite(list: MutableList<TarefaEntity>, context: Context): List<TarefaEntity>{
            val newList: MutableList<TarefaEntity> = ArrayList()
            val nullDateList = mutableListOf<TarefaEntity>()
            while(list.isNotEmpty()){
                val dateFormat = SimpleDateFormat(context.getString(R.string.date_format))
                var tarefa = TarefaEntity()

                var menorValor = try{
                    dateFormat.parse(list[0].data)
                }
                catch(e: Exception){
                    null
                }

                for(i in list){
                    val date = try{
                        dateFormat.parse(i.data)
                    }
                    catch(e: Exception){
                        null
                    }

                    if(menorValor != null){
                        try{
                            if (menorValor.after(date) || menorValor == date) {
                                menorValor = date
                                tarefa = i
                            }
                        }
                        catch(e: Exception){}
                    }
                    else{
                        menorValor = try {
                            dateFormat.parse(i.data)
                        }
                        catch(e: Exception){
                            null
                        }
                        tarefa = i
                    }

                    if(date == null){
                        nullDateList.add(i)
                    }
                }
                newList.add(tarefa)
                list.remove(tarefa)

                for(i in nullDateList) list.remove(i)
            }

            return (newList + nullDateList).toList()
        }

        fun alfabetica(list: MutableList<TarefaEntity>): List<TarefaEntity>{
            val newList: MutableList<TarefaEntity> = arrayListOf()
            while(list.isNotEmpty()){
                var menorNome = list[0].nome
                var tarefa = TarefaEntity()
                for(i in list){
                    if(i.nome.lowercase().compareTo(menorNome.lowercase()) <= 0){
                        menorNome = i.nome
                        tarefa = i
                    }
                }
                newList.add(tarefa)
                list.remove(tarefa)
            }

            return newList.toList()
        }
    }
}