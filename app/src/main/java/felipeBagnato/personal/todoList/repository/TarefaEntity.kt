package felipeBagnato.personal.todoList.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import felipeBagnato.personal.todoList.constants.DatabaseConstants

@Entity(tableName = DatabaseConstants.TAREFA.NOME_TABELA)
class TarefaEntity{
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    @ColumnInfo(name = DatabaseConstants.TAREFA.COLUNA.NOME) var nome: String = ""
    @ColumnInfo(name = DatabaseConstants.TAREFA.COLUNA.DESC) var descricao: String = ""
    @ColumnInfo(name = DatabaseConstants.TAREFA.COLUNA.DATA) var data: String = ""
    @ColumnInfo(name = DatabaseConstants.TAREFA.COLUNA.DONE) var feito: Boolean = false
}