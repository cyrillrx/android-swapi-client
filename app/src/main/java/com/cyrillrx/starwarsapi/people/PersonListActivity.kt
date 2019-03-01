package com.cyrillrx.starwarsapi.people

import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.starwarsapi.common.BaseListActivity
import com.cyrillrx.swapi.model.Person
import com.cyrillrx.swapi.model.ResultList
import retrofit2.Call

/**
 * @author Cyril Leroux
 *         Created on 07/04/2018.
 */
class PersonListActivity : BaseListActivity<Person>() {

    override fun getApiCall(): Call<ResultList<Person>> =
            SwApp.swApi.getPeople()

    override fun getApiCall(url: String): Call<ResultList<Person>> =
            SwApp.swApi.getPeople(url)
}
