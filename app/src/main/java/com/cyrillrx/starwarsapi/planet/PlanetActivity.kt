package com.cyrillrx.starwarsapi.planet

import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.starwarsapi.common.BaseDetailActivity
import com.cyrillrx.swapi.model.Planet

/**
 * @author Cyril Leroux
 *         Created on 08/04/2018.
 */
class PlanetActivity : BaseDetailActivity<Planet>() {

    override fun getApiCall(url: String) = SwApp.swApi.getPlanet(url)

    override fun bind(body: Planet) { }
}
