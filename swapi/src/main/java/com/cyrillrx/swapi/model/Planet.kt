package com.cyrillrx.swapi.model

import java.util.*

/**
 * @author Cyril Leroux
 *         Created on 03/04/2018.
 */
class Planet : Entity() {

    val name: String? = null
    val diameter: String? = null
    val rotation_period: String? = null
    val orbital_period: String? = null
    val gravity: String? = null
    val population: String? = null
    val climate: String? = null
    val terrain: String? = null
    val surface_water: String? = null

    val residents: List<String>? = ArrayList()
}