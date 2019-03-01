package com.cyrillrx.starwarsapi.starship

import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.starwarsapi.common.BaseListActivity
import com.cyrillrx.swapi.model.ResultList
import com.cyrillrx.swapi.model.Starship
import retrofit2.Call

/**
 * @author Cyril Leroux
 *         Created on 07/04/2018.
 */
class StarshipListActivity : BaseListActivity<Starship>() {

    override fun getApiCall(): Call<ResultList<Starship>> =
            SwApp.swApi.getStarships()

    override fun getApiCall(url: String): Call<ResultList<Starship>> =
            SwApp.swApi.getStarships(url)
}
