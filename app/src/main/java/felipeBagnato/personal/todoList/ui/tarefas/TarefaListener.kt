package felipeBagnato.personal.todoList.ui.tarefas

interface TarefaListener{
    fun onCheck(id: Int, state: Boolean)
    fun onClick(id: Int)
}