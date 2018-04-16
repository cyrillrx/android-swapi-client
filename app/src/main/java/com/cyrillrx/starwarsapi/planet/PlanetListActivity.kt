package com.cyrillrx.starwarsapi.planet

import com.cyrillrx.starwarsapi.ListActivity
import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.swapi.model.Planet
import com.cyrillrx.swapi.model.ResultList
import retrofit2.Callback

/**
 * @author Cyril Leroux
 *         Created on 07/04/2018.
 */
class PlanetListActivity : ListActivity<Planet>() {

    override fun sendRequest(callback: Callback<ResultList<Planet>>) =
            SwApp.swApi.getPlanets().enqueue(callback)

    override fun loadNextPage(url: String, callback: Callback<ResultList<Planet>>) =
            SwApp.swApi.getPlanets(url).enqueue(callback)
}
