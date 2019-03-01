package com.cyrillrx.starwarsapi.film

import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.starwarsapi.common.BaseListActivity
import com.cyrillrx.swapi.model.Film
import com.cyrillrx.swapi.model.ResultList
import retrofit2.Call

/**
 * @author Cyril Leroux
 *         Created on 07/04/2018.
 */
class FilmListActivity : BaseListActivity<Film>() {

    override fun getApiCall(): Call<ResultList<Film>> =
            SwApp.swApi.getFilms()

    override fun getApiCall(url: String): Call<ResultList<Film>> =
            SwApp.swApi.getFilms(url)
}
