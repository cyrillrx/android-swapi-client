package com.cyrillrx.swapi

import com.cyrillrx.swapi.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * @author Cyril Leroux
 *         Created on 03/04/2018.
 */
interface SwApi {

    @GET("/api/")
    fun getRoot(): Call<Root>

    @GET()
    fun getFilms(@Url url: String): Call<ResultList<Film>>

    @GET("/api/films/")
    fun getFilms(): Call<ResultList<Film>>

    @GET("/api/films/{id}")
    fun getFilm(@Path("id") id: String): Call<Film>

    @GET()
    fun getPeople(@Url url: String): Call<ResultList<Person>>

    @GET("/api/people/")
    fun getPeople(): Call<ResultList<Person>>

    @GET("/api/people/{id}")
    fun getPerson(@Path("id") id: String): Call<Person>

    @GET()
    fun getPlanets(@Url url: String): Call<ResultList<Planet>>

    @GET("/api/planets/")
    fun getPlanets(): Call<ResultList<Planet>>

    @GET("/api/planets/{id}")
    fun getPlanet(@Path("id") id: String): Call<Planet>

    @GET()
    fun getSpecies(@Url url: String): Call<ResultList<Species>>

    @GET("/api/species/")
    fun getSpecies(): Call<ResultList<Species>>

    @GET("/api/species/{id}")
    fun getASpecies(@Path("id") id: String): Call<Species>

    @GET()
    fun getStarships(@Url url: String): Call<ResultList<Starship>>

    @GET("/api/starships/")
    fun getStarships(): Call<ResultList<Starship>>

    @GET("/api/starships/{id}")
    fun getStarship(@Path("id") id: String): Call<Starship>

    @GET()
    fun getVehicles(@Url url: String): Call<ResultList<Vehicle>>

    @GET("/api/vehicles/")
    fun getVehicles(): Call<ResultList<Vehicle>>

    @GET("/api/vehicles/{id}")
    fun getVehicle(@Path("id") id: String): Call<Vehicle>
}