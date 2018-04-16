package com.cyrillrx.starwarsapi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.cyrillrx.logger.Logger
import com.cyrillrx.starwarsapi.adapter.BaseAdapter
import com.cyrillrx.starwarsapi.film.FilmListActivity
import com.cyrillrx.starwarsapi.people.PersonListActivity
import com.cyrillrx.starwarsapi.planet.PlanetListActivity
import com.cyrillrx.starwarsapi.species.SpeciesListActivity
import com.cyrillrx.starwarsapi.starship.StarshipListActivity
import com.cyrillrx.starwarsapi.vehicles.VehicleListActivity
import com.cyrillrx.swapi.model.Root
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * @author Cyril Leroux
 *         Created on 08/04/2018.
 */
class RootActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private val adapter = BaseAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        recyclerView = findViewById(R.id.recycler)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        recyclerView.adapter = adapter

        adapter.add(title)

        sendRequest()
    }

    private fun sendRequest() {
        SwApp.swApi
                .getRoot()
                .enqueue(object : Callback<Root> {

                    override fun onResponse(call: Call<Root>?, response: Response<Root>?) {
                        adapter.addAll(getRootItems(response?.body()))
                    }

                    override fun onFailure(call: Call<Root>?, t: Throwable?) {
                        Logger.error(TAG, "", t)
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

    private fun getClassForKey(key: String) = when (key) {
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
