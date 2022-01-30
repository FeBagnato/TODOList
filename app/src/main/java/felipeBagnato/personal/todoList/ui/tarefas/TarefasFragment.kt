package felipeBagnato.personal.todoList.ui.tarefas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import felipeBagnato.personal.todoList.R
import felipeBagnato.personal.todoList.ui.tarefas.tarefasInfo.TarefaInfoActivity

class TarefasFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View{

        val root = inflater.inflate(R.layout.fragment_tarefas, container, false)
        val tarefaAdapter = TarefaAdapter()

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_view_tarefas)
        recycler.layoutManager = LinearLayoutManager(root.context)
        recycler.adapter = tarefaAdapter

        val mViewModel = TarefasViewModel(root.context)
        mViewModel.pegarTarefas().observe(viewLifecycleOwner){
            tarefaAdapter.atualizar(it)
        }

        val tarefaListener = object: TarefaListener{
            override fun onCheck(id: Int, state: Boolean) {
                mViewModel.mudarEstadoTarefa(id, state)
            }

            override fun onClick(id: Int) {
                val intent = Intent(root.context, TarefaInfoActivity::class.java)
                val bundle = Bundle()

                bundle.putInt("id", id)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
        tarefaAdapter.setTarefaListener(tarefaListener)

        return root
    }
}