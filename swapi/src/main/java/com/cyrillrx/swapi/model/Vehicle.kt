package com.cyrillrx.swapi.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author Cyril Leroux
 *         Created on 03/04/2018.
 */
open class Vehicle : Entity() {

    val name: String? = null
    val model: String? = null
    @SerializedName(value = "vehicle_class", alternate = ["starship_class"])
    val vehicleClass: String? = null
    val manufacturer: String? = null
    val cost_in_credits: String? = null
    val length: String? = null
    val crew: String? = null
    val passengers: String? = null
    val max_atmosphering_speed: String? = null
    val cargo_capacity: String? = null
    val consumables: String? = null

    val pilots: List<String>? = ArrayList()
}