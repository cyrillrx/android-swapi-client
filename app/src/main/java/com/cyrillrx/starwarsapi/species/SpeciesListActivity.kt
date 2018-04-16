package com.cyrillrx.starwarsapi.species

import com.cyrillrx.starwarsapi.ListActivity
import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.swapi.model.ResultList
import com.cyrillrx.swapi.model.Species
import retrofit2.Callback

/**
 * @author Cyril Leroux
 *         Created on 07/04/2018.
 */
class SpeciesListActivity : ListActivity<Species>() {

    override fun sendRequest(callback: Callback<ResultList<Species>>) =
            SwApp.swApi.getSpecies().enqueue(callback)

    override fun loadNextPage(url: String, callback: Callback<ResultList<Species>>) =
            SwApp.swApi.getSpecies(url).enqueue(callback)
}
