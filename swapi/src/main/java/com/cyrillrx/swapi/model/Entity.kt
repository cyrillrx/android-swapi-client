package com.cyrillrx.swapi.model

/**
 * @author Cyril Leroux
 *         Created on 04/04/2018.
 */
open class Entity {

    val url: String? = null
    val created: String? = null
    val edited: String? = null

    val films: List<String>? = ArrayList()
    val species: List<String>? = ArrayList()
    val starships: List<String>? = ArrayList()
    val vehicles: List<String>? = ArrayList()
    val planets: List<String> = ArrayList()
}