package com.cyrillrx.starwarsapi.species

import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.starwarsapi.common.BaseListActivity
import com.cyrillrx.swapi.model.ResultList
import com.cyrillrx.swapi.model.Species
import retrofit2.Call

/**
 * @author Cyril Leroux
 *         Created on 07/04/2018.
 */
class SpeciesListActivity : BaseListActivity<Species>() {

    override fun getApiCall(): Call<ResultList<Species>> =
            SwApp.swApi.getSpecies()

    override fun getApiCall(url: String): Call<ResultList<Species>> =
            SwApp.swApi.getSpecies(url)
}
