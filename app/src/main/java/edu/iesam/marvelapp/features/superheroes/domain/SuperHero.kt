package edu.iesam.marvelapp.features.superheroes.domain

import java.net.URL

data class SuperHero(
    val id: String,
    val name: String,
    val slug: String,
    val urlImage: URL
)