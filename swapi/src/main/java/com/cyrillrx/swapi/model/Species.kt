package com.cyrillrx.swapi.model

import java.util.*

/**
 * @author Cyril Leroux
 *         Created on 03/04/2018.
 */
class Species : Entity() {

    val name: String? = null
    val classification: String? = null
    val designation: String? = null
    val average_height: String? = null
    val average_lifespan: String? = null
    val eye_colors: String? = null
    val hair_colors: String? = null
    val skin_colors: String? = null
    val language: String? = null
    val homeworld: String? = null

    val people: List<String>? = ArrayList()
}