package com.cyrillrx.starwarsapi.starship

import com.cyrillrx.starwarsapi.ListActivity
import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.swapi.model.ResultList
import com.cyrillrx.swapi.model.Starship
import retrofit2.Callback

/**
 * @author Cyril Leroux
 *         Created on 07/04/2018.
 */
class StarshipListActivity : ListActivity<Starship>() {

    override fun sendRequest(callback: Callback<ResultList<Starship>>) =
            SwApp.swApi.getStarships().enqueue(callback)

    override fun loadNextPage(url: String, callback: Callback<ResultList<Starship>>) =
            SwApp.swApi.getStarships(url).enqueue(callback)
}
