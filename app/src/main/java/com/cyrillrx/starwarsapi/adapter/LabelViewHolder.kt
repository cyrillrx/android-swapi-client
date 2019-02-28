package com.cyrillrx.starwarsapi.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cyrillrx.starwarsapi.R
import com.cyrillrx.starwarsapi.RootItem
import com.cyrillrx.starwarsapi.film.FilmActivity
import com.cyrillrx.starwarsapi.inflate
import com.cyrillrx.starwarsapi.planet.PlanetActivity
import com.cyrillrx.swapi.model.*
import com.cyrillrx.utils.serialize

/**
 * @author Cyril Leroux
 *         Created on 06/04/2018.
 */
class LabelViewHolder(parent: ViewGroup)
    : RecyclerView.ViewHolder(parent.inflate(R.layout.item_name)) {

    private var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
    private var tvSubtitle: TextView = itemView.findViewById(R.id.tv_subtitle)

    fun bind(item: Any?) {
        when (item) {
            null -> bind(item, item) {}
            is String -> bind(item, "") {}
            is RootItem -> bindRootItem(item)
            is Film -> bindFilm(item)
            is Person -> bindPerson(item)
            is Planet -> bindPlanet(item)
            is Species -> bindSpecies(item)
            is Starship -> bindStarship(item)
            is Vehicle -> bindVehicle(item)
        }
    }

    private fun bind(title: String?, subtitle: String?, click: (View) -> Unit) {
        tvTitle.text = title
        tvSubtitle.text = subtitle

        itemView.setOnClickListener(click)
    }

    private fun startActivity(entity: Entity, view: View, clazz: Class<*>?) {
        view.context?.let {
            val intent = Intent(it, clazz)
            intent.putExtra("Entity", entity.serialize())
            it.startActivity(intent)
        }
    }

    private fun startActivity(url: String, view: View, clazz: Class<*>?) {
        view.context?.let {
            val intent = Intent(it, clazz)
            intent.putExtra("url", url)
            it.startActivity(intent)
        }
    }

    private fun bindRootItem(item: RootItem) {
        bind(item.label, item.url) { v ->
            startActivity(item.url, v, item.activityClass)
        }
    }

    private fun bindFilm(film: Film) {
        bind(film.title, film.release_date.toString()) { v ->
            startActivity(film, v, FilmActivity::class.java)
        }
    }

    private fun bindPerson(person: Person) {
        bind(person.name, person.homeworld) { v ->
            startActivity(person, v, FilmActivity::class.java)
        }
    }

    private fun bindPlanet(planet: Planet) {
        bind(planet.name, planet.terrain) { v ->
            startActivity(planet, v, PlanetActivity::class.java)
        }
    }

    private fun bindSpecies(species: Species) {
        bind(species.name, species.homeworld) { v ->
            startActivity(species, v, FilmActivity::class.java)
        }
    }

    private fun bindStarship(starship: Starship) {
        bind(starship.name, starship.model) { v ->
            startActivity(starship, v, FilmActivity::class.java)
        }
    }

    private fun bindVehicle(vehicle: Vehicle) {
        bind(vehicle.name, vehicle.model) { v ->
            startActivity(vehicle, v, FilmActivity::class.java)
        }
    }
}
