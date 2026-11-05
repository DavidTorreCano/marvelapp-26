package edu.iesam.marvelapp.core.serialization

import edu.iesam.marvelapp.features.superheroes.domain.SuperHero

interface JsonSerializer<T> {
    fun toJson(obj: List<SuperHero>): String
    fun fromJson(json: String): T
}