package com.cyrillrx.starwarsapi.starship

import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.starwarsapi.common.BaseDetailActivity
import com.cyrillrx.swapi.model.Starship

/**
 * @author Cyril Leroux
 *         Created on 08/04/2018.
 */
class StarshipActivity : BaseDetailActivity<Starship>() {

    override fun getApiCall(url: String) = SwApp.swApi.getStarship(url)

    override fun bind(body: Starship) { }
}
