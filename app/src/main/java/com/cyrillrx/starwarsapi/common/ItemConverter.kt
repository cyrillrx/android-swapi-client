package com.cyrillrx.starwarsapi.common

import android.content.Intent
import android.view.View
import com.cyrillrx.starwarsapi.IntentKey
import com.cyrillrx.starwarsapi.RootItem
import com.cyrillrx.starwarsapi.film.FilmActivity
import com.cyrillrx.starwarsapi.planet.PlanetActivity
import com.cyrillrx.swapi.model.*
import com.cyrillrx.templates.model.Converter
import com.cyrillrx.templates.model.Header
import com.cyrillrx.templates.model.Item
import com.cyrillrx.utils.prettyPrint

/**
 * @author Cyril Leroux
 *         Created on 01/03/2019.
 */
class ItemConverter : Converter {

    override fun toHeader(input: Any?): Header = when (input) {
        is Header -> input
        is String -> Header(input)
        else -> Header("???")
    }

    override fun toItem(input: Any?): Item = when (input) {
        is Item -> input
        is RootItem -> bindRootItem(input)
        is Film -> bindFilm(input)
        is Person -> bindPerson(input)
        is Planet -> bindPlanet(input)
        is Species -> bindSpecies(input)
        is Starship -> bindStarship(input)
        is Vehicle -> bindVehicle(input)
        else -> Item("???", "???", null)
    }

    private fun bindRootItem(root: RootItem): Item =
            Item(root.label, root.url) { v ->
                startActivity(root.url, v, root.activityClass)
            }

    private fun bindFilm(film: Film): Item =
            Item(film.title, film.release_date.toString()) { v ->
                startActivity(film, v, FilmActivity::class.java)
            }

    private fun bindPerson(person: Person): Item =
            Item(person.name, person.homeworld) { v ->
                startActivity(person, v, FilmActivity::class.java)
            }

    private fun bindPlanet(planet: Planet): Item =
            Item(planet.name, planet.terrain) { v ->
                startActivity(planet, v, PlanetActivity::class.java)
            }

    private fun bindSpecies(species: Species): Item =
            Item(species.name, species.homeworld) { v ->
                startActivity(species, v, FilmActivity::class.java)
            }

    private fun bindStarship(starship: Starship): Item =
            Item(starship.name, starship.model) { v ->
                startActivity(starship, v, FilmActivity::class.java)
            }

    private fun bindVehicle(vehicle: Vehicle): Item =
            Item(vehicle.name, vehicle.model) { v ->
                startActivity(vehicle, v, FilmActivity::class.java)
            }

    private fun startActivity(entity: Entity, view: View, clazz: Class<*>?) {
        view.context?.let { context ->
            val intent = Intent(context, clazz)
            intent.putExtra(IntentKey.ENTITY, entity.prettyPrint())
            context.startActivity(intent)
        }
    }

    private fun startActivity(url: String, view: View, clazz: Class<*>?) {
        view.context?.let { context ->
            val intent = Intent(context, clazz)
            intent.putExtra(IntentKey.URL, url)
            context.startActivity(intent)
        }
    }
}