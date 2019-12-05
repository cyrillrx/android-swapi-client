package com.cyrillrx.starwarsapi

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cyrillrx.logger.Logger
import com.cyrillrx.starwarsapi.common.ItemConverter
import com.cyrillrx.starwarsapi.coroutine.UIScope
import com.cyrillrx.starwarsapi.film.FilmListActivity
import com.cyrillrx.starwarsapi.people.PersonListActivity
import com.cyrillrx.starwarsapi.planet.PlanetListActivity
import com.cyrillrx.starwarsapi.species.SpeciesListActivity
import com.cyrillrx.starwarsapi.starship.StarshipListActivity
import com.cyrillrx.starwarsapi.vehicles.VehicleListActivity
import com.cyrillrx.swapi.model.Root
import com.cyrillrx.templates.BaseAdapter
import com.cyrillrx.templates.ListActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Cyril Leroux
 *         Created on 08/04/2018.
 */
class RootActivity : ListActivity() {

    override val adapter: BaseAdapter = BaseAdapter(ItemConverter())

    private val uiScope = UIScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(uiScope)
    }

    override fun addItemDecoration(recyclerView: RecyclerView, layoutManager: LinearLayoutManager) {
        recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
    }

    override fun sendRequest() {

        uiScope.launch {
            adapter.add(title)

            startLoading()

            try {
                val items = withContext(Dispatchers.IO) {
                    val root = SwApp.swApi.getRoot()
                    getRootItems(root)
                }
                adapter.addAll(items)

            } catch (e: Exception) {
                Logger.error(TAG, "sendRequest() - swApi.getRoot()", e)

            } finally {
                stopLoading()
            }
        }
    }

    companion object {
        private val TAG = RootActivity::class.java.simpleName

        private fun getRootItems(root: Root?): List<RootItem> {

            if (root == null) return emptyList()

            val rootItems: ArrayList<RootItem> = ArrayList()
            for ((key, value) in root) {
                rootItems.add(RootItem(key, value, getClassForKey(key)))
            }

            return rootItems
        }

        private fun getClassForKey(key: String) =
            when (key) {
                "films" -> FilmListActivity::class.java
                "people" -> PersonListActivity::class.java
                "planets" -> PlanetListActivity::class.java
                "species" -> SpeciesListActivity::class.java
                "starships" -> StarshipListActivity::class.java
                "vehicles" -> VehicleListActivity::class.java
                else -> null
            }
    }
}
