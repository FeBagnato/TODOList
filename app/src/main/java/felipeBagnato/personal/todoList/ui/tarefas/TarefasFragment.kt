package felipeBagnato.personal.todoList.ui.tarefas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import felipeBagnato.personal.todoList.R
import felipeBagnato.personal.todoList.constants.DatabaseConstants
import felipeBagnato.personal.todoList.ui.tarefas.tarefasForm.TarefasFormActivity
import felipeBagnato.personal.todoList.ui.tarefas.tarefasInfo.TarefaInfoActivity

class TarefasFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View{

        val root = inflater.inflate(R.layout.fragment_tarefas, container, false)
        val tarefaAdapter = TarefaAdapter()

        val fab: FloatingActionButton = root.findViewById(R.id.fab)
        fab.setOnClickListener{
            startActivity(Intent(root.context, TarefasFormActivity::class.java))
        }

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_view_tarefas)
        recycler.layoutManager = LinearLayoutManager(root.context)
        recycler.adapter = tarefaAdapter

        val mViewModel = TarefasViewModel(root.context)
        mViewModel.pegarTarefas().observe(viewLifecycleOwner){
            //TODO: Add ordenação aqui
            tarefaAdapter.atualizar(it)
        }

        val tarefaListener = object: TarefaListener{
            override fun onCheck(id: Int, state: Boolean) {
                mViewModel.mudarEstadoTarefa(id, state)
            }

            override fun onClick(id: Int) {
                val intent = Intent(root.context, TarefaInfoActivity::class.java)
                val bundle = Bundle()

                bundle.putInt(DatabaseConstants.TAREFA.COLUNA.ID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
        tarefaAdapter.setTarefaListener(tarefaListener)

        return root
    }
}