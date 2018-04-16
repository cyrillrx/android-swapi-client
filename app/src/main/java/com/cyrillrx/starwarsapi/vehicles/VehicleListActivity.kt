package com.cyrillrx.starwarsapi.vehicles

import com.cyrillrx.starwarsapi.ListActivity
import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.swapi.model.ResultList
import com.cyrillrx.swapi.model.Vehicle
import retrofit2.Callback

/**
 * @author Cyril Leroux
 *         Created on 07/04/2018.
 */
class VehicleListActivity : ListActivity<Vehicle>() {

    override fun sendRequest(callback: Callback<ResultList<Vehicle>>) =
            SwApp.swApi.getVehicles().enqueue(callback)

    override fun loadNextPage(url: String, callback: Callback<ResultList<Vehicle>>) =
            SwApp.swApi.getVehicles(url).enqueue(callback)
}
