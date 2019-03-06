package com.cyrillrx.starwarsapi.vehicles

import com.cyrillrx.starwarsapi.SwApp
import com.cyrillrx.starwarsapi.common.BaseDetailActivity
import com.cyrillrx.swapi.model.Vehicle

/**
 * @author Cyril Leroux
 *         Created on 08/04/2018.
 */
class VehicleActivity : BaseDetailActivity<Vehicle>() {

    override fun getApiCall(url: String) = SwApp.swApi.getVehicle(url)

    override fun bind(body: Vehicle) { }
}
