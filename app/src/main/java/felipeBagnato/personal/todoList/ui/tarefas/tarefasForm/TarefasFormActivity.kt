package felipeBagnato.personal.todoList.ui.tarefas.tarefasForm

import android.app.DatePickerDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import felipeBagnato.personal.todoList.R
import felipeBagnato.personal.todoList.constants.DatabaseConstants
import java.text.DateFormat.MEDIUM
import java.text.DateFormat.getDateInstance
import java.util.*

class TarefasFormActivity: AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener{
    private lateinit var mViewModel: TarefaFormViewModel
    lateinit var nome: EditText
    lateinit var descricao: EditText
    lateinit var textData: TextView
    lateinit var salvarBtn: Button
    private val dataFormat = getDateInstance(MEDIUM)
    private var idPadrao = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mViewModel = TarefaFormViewModel(this)

        nome = findViewById(R.id.edit_nome)
        descricao = findViewById(R.id.edit_descricao)
        textData = findViewById(R.id.text_data)
        salvarBtn = findViewById(R.id.button_salvar)

        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(getColor(R.color.purple_500))
        )
        supportActionBar?.title = getString(R.string.label_form_criar)

        listener()
        carregarDados()
        observar()
    }

    override fun onClick(v: View) {
        if(v.id == R.id.button_salvar){
            mViewModel.salvar(idPadrao,
                nome.text.toString(),
                descricao.text.toString(),
                textData.text.toString())
        }
        else if(v.id == R.id.text_data){
            mostrarDatePicker()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendario = Calendar.getInstance()
        calendario.set(year, month, dayOfMonth)

        val dataString = dataFormat.format(calendario.time)
        textData.text = dataString
    }

    fun mostrarDatePicker(){
        val dataAtual = Calendar.getInstance()
        val ano = dataAtual.get(Calendar.YEAR)
        val mes = dataAtual.get(Calendar.MONTH)
        val dia = dataAtual.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, this, ano, mes, dia).show()
    }

    fun observar(){
        val nomeObserver = Observer<Boolean>{
            Toast.makeText(this, R.string.nome_vazio_tarefa, Toast.LENGTH_LONG).show()
        }
        val salvarObserver = Observer<Long>{
            if(it >= 0) {
                Toast.makeText(this, R.string.sucesso_salvar_tarefa,
                    Toast.LENGTH_LONG).show()

                finish()
            }
            else{
                Toast.makeText(this, R.string.falha_salvar_tarefa, Toast.LENGTH_LONG).show()
            }
        }
        val atualizarObserver = Observer<Int>{
            if(it == 1) {
                Toast.makeText(this, R.string.sucesso_editar_tarefa,
                    Toast.LENGTH_LONG).show()

                finish()
            }
            else{
                Toast.makeText(this, R.string.falha_editar_tarefa, Toast.LENGTH_LONG).show()
            }
        }

        mViewModel.isNomeVazio.observe(this, nomeObserver)
        mViewModel.salvarTarefa.observe(this, salvarObserver)
        mViewModel.atualizarTarefa.observe(this, atualizarObserver)
    }

    fun listener(){
        salvarBtn.setOnClickListener(this)
        textData.setOnClickListener(this)
    }

    fun carregarDados(){
        val bundle = intent.extras

        if(bundle != null){
            idPadrao = bundle.getInt(DatabaseConstants.TAREFA.COLUNA.ID)
            nome.setText(bundle.getString(DatabaseConstants.TAREFA.COLUNA.NOME))
            descricao.setText(bundle.getString(DatabaseConstants.TAREFA.COLUNA.DESC))
            textData.text = bundle.getString(DatabaseConstants.TAREFA.COLUNA.DATA)

            if(textData.text == "") textData.text = getString(R.string.task_date)

            supportActionBar?.title = getString(R.string.label_form_editar)
            salvarBtn.text = getString(R.string.botao_editar)
        }
    }
}