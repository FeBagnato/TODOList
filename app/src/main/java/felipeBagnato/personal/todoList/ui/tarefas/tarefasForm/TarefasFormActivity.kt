package felipeBagnato.personal.todoList.ui.tarefas.tarefasForm

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import felipeBagnato.personal.todoList.R
import java.text.SimpleDateFormat
import java.util.*

class TarefasFormActivity: AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener{
    private lateinit var mViewModel: TarefaFormViewModel
    lateinit var nome: EditText
    lateinit var descricao: EditText
    lateinit var textData: TextView
    lateinit var salvarBtn: Button
    private val dataFormat = SimpleDateFormat("dd/MM/yyyy")
    private var idPadrao = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mViewModel = TarefaFormViewModel(this)

        nome = findViewById(R.id.edit_nome)
        descricao = findViewById(R.id.edit_descricao)
        textData = findViewById(R.id.text_data)
        salvarBtn = findViewById(R.id.button_salvar)

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
            Toast.makeText(this, "Insira o nome da tarefa!", Toast.LENGTH_LONG).show()
        }
        val salvarObserver = Observer<Long>{
            if(it >= 0) {
                Toast.makeText(this, "Tarefa salva!", Toast.LENGTH_LONG).show()
                finish()
            }
            else{
                Toast.makeText(this, "Falha ao salvar a tarefa!",
                    Toast.LENGTH_LONG).show()
            }
        }
        val atualizarObserver = Observer<Int>{
            if(it == 1) {
                Toast.makeText(this, "Tarefa editada!", Toast.LENGTH_LONG).show()
                finish()
            }
            else{
                Toast.makeText(this, "Nao foi possivel editar essa tarefa!",
                    Toast.LENGTH_LONG).show()
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
            idPadrao = bundle.getInt("id")
            nome.setText(bundle.getString("nome"))
            descricao.setText(bundle.getString("descricao"))
            textData.text = bundle.getString("data")
        }
    }
}