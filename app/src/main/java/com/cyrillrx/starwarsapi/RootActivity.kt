package com.cyrillrx.starwarsapi

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cyrillrx.logger.Logger
import com.cyrillrx.starwarsapi.common.SwItemFactory
import com.cyrillrx.starwarsapi.film.FilmListActivity
import com.cyrillrx.starwarsapi.people.PersonListActivity
import com.cyrillrx.starwarsapi.planet.PlanetListActivity
import com.cyrillrx.starwarsapi.species.SpeciesListActivity
import com.cyrillrx.starwarsapi.starship.StarshipListActivity
import com.cyrillrx.starwarsapi.vehicles.VehicleListActivity
import com.cyrillrx.swapi.model.Root
import com.cyrillrx.templates.BaseAdapter
import com.cyrillrx.templates.ListActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Cyril Leroux
 *         Created on 08/04/2018.
 */
class RootActivity : ListActivity() {

    override val header: String? by lazy { title?.toString() }

    override val adapter: BaseAdapter = BaseAdapter(SwItemFactory())

    override fun addItemDecoration(recyclerView: RecyclerView, layoutManager: LinearLayoutManager) {
        recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
    }

    override fun sendRequest() {
        SwApp.swApi
                .getRoot()
                .enqueue(object : Callback<Root> {

                    override fun onResponse(call: Call<Root>?, response: Response<Root>?) {
                        if (response?.isSuccessful == true) {
                            response.body()?.let { body ->
                                adapter.addAll(getRootItems(body))
                            }
                        } else {
                            Logger.error(TAG, call?.request()?.url()?.toString())
                        }
                    }

                    override fun onFailure(call: Call<Root>?, t: Throwable?) {
                        Logger.error(TAG, call?.request()?.url()?.toString() + " - ${t?.message}", t)
                    }
                })
    }

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

    companion object {
        private val TAG = RootActivity::class.java.simpleName
    }
}
