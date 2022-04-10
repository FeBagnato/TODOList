package felipeBagnato.personal.todoList.ui.tarefas.tarefasInfo

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import felipeBagnato.personal.todoList.R
import felipeBagnato.personal.todoList.constants.DatabaseConstants
import felipeBagnato.personal.todoList.repository.TarefaEntity
import felipeBagnato.personal.todoList.ui.tarefas.tarefasForm.TarefasFormActivity

class TarefaInfoActivity: AppCompatActivity(), View.OnClickListener{
    private val mViewModel = TarefaInfoViewModel(this)

    private lateinit var tarefa: TarefaEntity
    private lateinit var nomeTarefa: TextView
    private lateinit var descricaoTarefa: TextView
    private lateinit var dataTarefa: TextView
    private lateinit var excluirBtn: ImageView
    private lateinit var editarBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        nomeTarefa = findViewById(R.id.text_titulo)
        descricaoTarefa = findViewById(R.id.text_descricao)
        dataTarefa = findViewById(R.id.text_data)
        excluirBtn = findViewById(R.id.image_excluir)
        editarBtn = findViewById(R.id.image_edit)

        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(getColor(R.color.purple_500))
        )

        listener()
        observar()
        carregarDados()
    }

    override fun onClick(v: View) {

        if(v.id == R.id.image_excluir) {
            mViewModel.excluirTarefa(tarefa)
        }
        else if(v.id == R.id.image_edit){
            val intent = Intent(this, TarefasFormActivity::class.java)
            val bundle = Bundle()

            bundle.putInt(DatabaseConstants.TAREFA.COLUNA.ID, tarefa.id)
            bundle.putString(DatabaseConstants.TAREFA.COLUNA.NOME, tarefa.nome)
            bundle.putString(DatabaseConstants.TAREFA.COLUNA.DESC, tarefa.descricao)
            bundle.putString(DatabaseConstants.TAREFA.COLUNA.DATA, tarefa.data)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    override fun onResume(){
        super.onResume()
        carregarDados()
    }

    private fun observar(){
        val tarefaObserver = Observer<TarefaEntity>{
            tarefa = it
            nomeTarefa.text = it.nome
            descricaoTarefa.text = it.descricao
            dataTarefa.text = it.data
        }
        val excluirObserver = Observer<Int>{
            if(it == 1){
                Toast.makeText(this, R.string.sucesso_excluir_tarefa,
                    Toast.LENGTH_LONG).show()

                finish()
            }
            else{
                Toast.makeText(this, R.string.falha_excluir_tarefa,
                    Toast.LENGTH_LONG).show()
            }
        }

        mViewModel.carregar.observe(this, tarefaObserver)
        mViewModel.excluir.observe(this, excluirObserver)
    }

    private fun listener(){
        excluirBtn.setOnClickListener(this)
        editarBtn.setOnClickListener(this)
    }

    private fun carregarDados(){
        val bundle = intent.extras

        if(bundle != null){
            val tarefaID = bundle.getInt(DatabaseConstants.TAREFA.COLUNA.ID)
            mViewModel.carregarTarefa(tarefaID)
        }
    }
}