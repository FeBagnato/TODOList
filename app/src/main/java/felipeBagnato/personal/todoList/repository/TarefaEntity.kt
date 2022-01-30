package felipeBagnato.personal.todoList.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tarefa")
class TarefaEntity{
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    @ColumnInfo(name = "nome") var nome: String = ""
    @ColumnInfo(name = "descricao") var descricao: String = ""
    @ColumnInfo(name = "data") var data: String = ""
    @ColumnInfo(name = "feito") var feito: Boolean = false
}