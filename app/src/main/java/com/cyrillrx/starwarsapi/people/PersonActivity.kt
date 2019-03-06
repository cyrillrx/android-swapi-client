package com.cyrillrx.starwarsapi.people

import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.starwarsapi.common.BaseDetailActivity
import com.cyrillrx.swapi.model.Person

/**
 * @author Cyril Leroux
 *         Created on 08/04/2018.
 */
class PersonActivity : BaseDetailActivity<Person>() {

    override fun getApiCall(url: String) = SwApp.swApi.getPerson(url)

    override fun bind(body: Person) { }
}
