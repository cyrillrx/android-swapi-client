package com.cyrillrx.swapi

import com.cyrillrx.swapi.model.Film
import com.cyrillrx.swapi.model.Person
import com.cyrillrx.swapi.model.Planet
import com.cyrillrx.swapi.model.ResultList
import com.cyrillrx.swapi.model.Root
import com.cyrillrx.swapi.model.Species
import com.cyrillrx.swapi.model.Starship
import com.cyrillrx.swapi.model.Vehicle
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @author Cyril Leroux
 *         Created on 03/04/2018.
 */
interface SwApi {

    @GET("/api/")
    suspend fun getRoot(): Root?

    @GET
    fun getFilms(@Url url: String): Call<ResultList<Film>>

    @GET("/api/films/")
    fun getFilms(): Call<ResultList<Film>>

    @GET
    fun getFilm(@Url url: String): Call<Film>

    @GET
    fun getPeople(@Url url: String): Call<ResultList<Person>>

    @GET("/api/people/")
    fun getPeople(): Call<ResultList<Person>>

    @GET
    fun getPerson(@Url url: String): Call<Person>

    @GET
    fun getPlanets(@Url url: String): Call<ResultList<Planet>>

    @GET("/api/planets/")
    fun getPlanets(): Call<ResultList<Planet>>

    @GET
    fun getPlanet(@Url url: String): Call<Planet>

    @GET
    fun getSpecies(@Url url: String): Call<ResultList<Species>>

    @GET("/api/species/")
    fun getSpecies(): Call<ResultList<Species>>

    @GET
    fun getASpecies(@Url url: String): Call<Species>

    @GET
    fun getStarships(@Url url: String): Call<ResultList<Starship>>

    @GET("/api/starships/")
    fun getStarships(): Call<ResultList<Starship>>

    @GET
    fun getStarship(@Url url: String): Call<Starship>

    @GET
    fun getVehicles(@Url url: String): Call<ResultList<Vehicle>>

    @GET("/api/vehicles/")
    fun getVehicles(): Call<ResultList<Vehicle>>

    @GET
    fun getVehicle(@Url url: String): Call<Vehicle>
}