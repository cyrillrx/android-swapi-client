package com.cyrillrx.swapi.model

import java.util.*

/**
 * @author Cyril Leroux
 *         Created on 03/04/2018.
 */
class Film : Entity() {

    val title: String? = null
    val episode_id: Int = 0
    val opening_crawl: String? = null
    val director: String? = null
    val producer: String? = null
    val release_date: Date? = null

    val characters: List<String>? = ArrayList()
}