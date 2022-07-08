package felipeBagnato.personal.todoList.repository

import android.content.Context
import felipeBagnato.personal.todoList.constants.SortingConstants

class Preferences(content: Context){
    private val pref = content.getSharedPreferences("sorting_list", Context.MODE_PRIVATE)

    fun saveSorting(valor: Int){
        pref.edit().putInt(SortingConstants.SORTING_MENU_KEY, valor).apply()
    }

    fun returnSort(): Int{
        return pref.getInt(SortingConstants.SORTING_MENU_KEY, SortingConstants.MAIS_ANTIGO)
    }
}