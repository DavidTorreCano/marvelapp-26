package edu.iesam.marvelapp.features.superheroes.data.local.xml

import android.content.Context
import edu.iesam.marvelapp.R
import edu.iesam.marvelapp.core.cache.AppCache
import edu.iesam.marvelapp.core.serialization.JsonSerializer
import edu.iesam.marvelapp.features.superheroes.domain.SuperHero

/*
*
* Conextor para gestionar datos en un ficheo xml a traves de SharedPreferences
*
*/
class SuperHeroXmlLocalDataSource (
    private val appCache: AppCache,
    private val context: Context,
    private val serializer: JsonSerializer<List<SuperHero>>){
    private val keySuperHero: String = "superhero"
    private val ttl: Long = 5 * 1000 * 60


    //-- me creo la fuente de datos xml: SharedPreferences
    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.local_source_superhero), Context.MODE_PRIVATE
    )

    /*
    *
     * metodo para guarar un listado de superhero
     *
     */

    fun save(superHeroes: List<SuperHero>){
        sharedPref.edit().apply {
            putString(keySuperHero, serializer.toJson(superHeroes))
            apply()
            }
        appCache.updateTime(keySuperHero)
        }



    fun findAll(): List<SuperHero> {
        val jsonList: String? = sharedPref.getString(keySuperHero, null)
        jsonList?.let {
            serializer.fromJson(it)
        }
        return emptyList()
    }

    fun findByid(id: String): SuperHero? {
        val superHeroes = findAll()
        return superHeroes.find { it.id == id }
    }

    fun deleteById(id: String) {
        save(findAll().filterNot { it.id == id })
    }

    fun deleteAll(){
        sharedPref.edit().apply {
            remove(keySuperHero)
            apply()
        }
    }
}
