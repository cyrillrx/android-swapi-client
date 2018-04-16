package com.cyrillrx.starwarsapi.film

import com.cyrillrx.starwarsapi.ListActivity
import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.swapi.model.Film
import com.cyrillrx.swapi.model.ResultList
import retrofit2.Callback

/**
 * @author Cyril Leroux
 *         Created on 07/04/2018.
 */
class FilmListActivity : ListActivity<Film>() {

    override fun sendRequest(callback: Callback<ResultList<Film>>) =
            SwApp.swApi.getFilms().enqueue(callback)

    override fun loadNextPage(url: String, callback: Callback<ResultList<Film>>) =
            SwApp.swApi.getFilms(url).enqueue(callback)
}
