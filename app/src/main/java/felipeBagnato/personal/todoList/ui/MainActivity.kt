package felipeBagnato.personal.todoList.ui

import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.google.android.material.floatingactionbutton.FloatingActionButton
import felipeBagnato.personal.todoList.R
import felipeBagnato.personal.todoList.ui.gallery.GalleryFragment
import felipeBagnato.personal.todoList.ui.sobre.SobreActivity
import felipeBagnato.personal.todoList.ui.tarefas.TarefasFragment
import felipeBagnato.personal.todoList.ui.tarefas.tarefasForm.TarefasFormActivity

class MainActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener{view ->
            startActivity(Intent(this, TarefasFormActivity::class.java))
        }

        setupNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupNavigation(){
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_sobre
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener{
            if(it.itemId == R.id.nav_sobre){
                startActivity(Intent(this, SobreActivity::class.java))
            }
            else if(it.itemId == R.id.nav_tarefas){
                supportFragmentManager.commit{
                    setReorderingAllowed(true)
                    add(R.id.nav_host_fragment_content_main, TarefasFragment())
                }
            }
            false
        }
    }
}