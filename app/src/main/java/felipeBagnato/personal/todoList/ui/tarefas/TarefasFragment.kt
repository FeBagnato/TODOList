package felipeBagnato.personal.todoList.ui.tarefas

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import felipeBagnato.personal.todoList.R
import felipeBagnato.personal.todoList.constants.DatabaseConstants
import felipeBagnato.personal.todoList.constants.SortingConstants
import felipeBagnato.personal.todoList.repository.Preferences
import felipeBagnato.personal.todoList.repository.TarefaEntity
import felipeBagnato.personal.todoList.ui.tarefas.tarefasForm.TarefasFormActivity
import felipeBagnato.personal.todoList.ui.tarefas.tarefasInfo.TarefaInfoActivity

class TarefasFragment : Fragment(){
    private lateinit var sortingConfig: Preferences
    private lateinit var mViewModel: TarefasViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View{

        val root = inflater.inflate(R.layout.fragment_tarefas, container, false)
        val tarefaAdapter = TarefaAdapter()

        val fab: FloatingActionButton = root.findViewById(R.id.fab)
        fab.setOnClickListener{
            startActivity(Intent(root.context, TarefasFormActivity::class.java))
        }
        setHasOptionsMenu(true)
        sortingConfig = Preferences(root.context)

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_view_tarefas)
        recycler.layoutManager = LinearLayoutManager(root.context)
        recycler.adapter = tarefaAdapter

        mViewModel = TarefasViewModel(root.context)
        mViewModel.pegarTarefas().observe(viewLifecycleOwner){
            var list: List<TarefaEntity> = arrayListOf()
            when(sortingConfig.returnSort()){
                SortingConstants.MAIS_ANTIGO -> {
                    list = TarefaSorting.maisAntigo(it)
                }
                SortingConstants.MAIS_NOVO -> {
                    list = TarefaSorting.maisNovo(it)
                }
                SortingConstants.DATA_LIMITE -> {
                    list = TarefaSorting.dataLimite(it.toMutableList(), root.context)
                }
                SortingConstants.ALFABETICA -> {
                    list = TarefaSorting.alfabetica(it.toMutableList())
                }
            }
            tarefaAdapter.atualizar(list)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.toolbar_sorting_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_mais_antigo -> {
                sortingConfig.saveSorting(SortingConstants.MAIS_ANTIGO)
                mViewModel.notifyObserver()
            }
            R.id.menu_mais_novo -> {
                sortingConfig.saveSorting(SortingConstants.MAIS_NOVO)
                mViewModel.notifyObserver()
            }
            R.id.menu_data_limite -> {
                sortingConfig.saveSorting(SortingConstants.DATA_LIMITE)
                mViewModel.notifyObserver()
            }
            R.id.menu_alfabetico -> {
                sortingConfig.saveSorting(SortingConstants.ALFABETICA)
                mViewModel.notifyObserver()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}