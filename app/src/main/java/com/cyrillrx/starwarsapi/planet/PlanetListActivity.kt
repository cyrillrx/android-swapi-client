package com.cyrillrx.starwarsapi.planet

import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.starwarsapi.common.BaseListActivity
import com.cyrillrx.swapi.model.Planet
import com.cyrillrx.swapi.model.ResultList
import retrofit2.Call

/**
 * @author Cyril Leroux
 *         Created on 07/04/2018.
 */
class PlanetListActivity : BaseListActivity<Planet>() {

    override fun getApiCall(): Call<ResultList<Planet>> =
            SwApp.swApi.getPlanets()

    override fun getApiCall(url: String): Call<ResultList<Planet>> =
            SwApp.swApi.getPlanets(url)
}
