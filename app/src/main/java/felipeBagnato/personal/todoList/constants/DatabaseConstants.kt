package felipeBagnato.personal.todoList.constants

abstract class DatabaseConstants{
    object TAREFA{
        const val NOME_TABELA = "Tarefa"

        object COLUNA{
            const val ID = "id"
            const val NOME = "nome"
            const val DESC = "descricao"
            const val DATA = "data"
            const val DONE = "feito"
        }
    }
}