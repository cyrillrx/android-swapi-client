package com.cyrillrx.starwarsapi.vehicles

import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.starwarsapi.common.BaseListActivity
import com.cyrillrx.swapi.model.ResultList
import com.cyrillrx.swapi.model.Vehicle
import retrofit2.Call

/**
 * @author Cyril Leroux
 *         Created on 07/04/2018.
 */
class VehicleListActivity : BaseListActivity<Vehicle>() {

    override fun getApiCall(): Call<ResultList<Vehicle>> =
            SwApp.swApi.getVehicles()

    override fun getApiCall(url: String): Call<ResultList<Vehicle>> =
            SwApp.swApi.getVehicles(url)
}
