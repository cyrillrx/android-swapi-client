package com.cyrillrx.swapi.model

/**
 * @author Cyril Leroux
 *         Created on 06/04/2018.
 */
class ResultList<T> {

    var count: Int = 0
    var next: String? = null
    var previous: String? = null
    var results: ArrayList<T> = ArrayList()
}