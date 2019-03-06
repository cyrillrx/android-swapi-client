package com.cyrillrx.starwarsapi.species

import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.starwarsapi.common.BaseDetailActivity
import com.cyrillrx.swapi.model.Species

/**
 * @author Cyril Leroux
 *         Created on 08/04/2018.
 */
class SpeciesActivity : BaseDetailActivity<Species>() {

    override fun getApiCall(url: String) = SwApp.swApi.getASpecies(url)

    override fun bind(body: Species) { }
}
