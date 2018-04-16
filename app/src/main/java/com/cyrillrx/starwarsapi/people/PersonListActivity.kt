package com.cyrillrx.starwarsapi.people

import com.cyrillrx.starwarsapi.ListActivity
import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.swapi.model.Person
import com.cyrillrx.swapi.model.ResultList
import retrofit2.Callback

/**
 * @author Cyril Leroux
 *         Created on 07/04/2018.
 */
class PersonListActivity : ListActivity<Person>() {

    override fun sendRequest(callback: Callback<ResultList<Person>>) =
            SwApp.swApi.getPeople().enqueue(callback)

    override fun loadNextPage(url: String, callback: Callback<ResultList<Person>>) =
            SwApp.swApi.getPeople(url).enqueue(callback)
}
