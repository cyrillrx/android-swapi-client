package com.cyrillrx.starwarsapi.film

import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.starwarsapi.common.BaseDetailActivity
import com.cyrillrx.swapi.model.Film

/**
 * @author Cyril Leroux
 *         Created on 08/04/2018.
 */
class FilmActivity : BaseDetailActivity<Film>() {

    override fun getApiCall(url: String) = SwApp.swApi.getFilm(url)

    override fun bind(body: Film) { }
}
