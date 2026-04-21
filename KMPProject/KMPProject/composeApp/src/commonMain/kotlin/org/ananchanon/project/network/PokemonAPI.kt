package org.ananchanon.project.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.ananchanon.project.models.PokedexResponse

object PokemonNetwork {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun getKantoPokedex(): PokedexResponse {
        return httpClient.get("${BASE_URL}pokedex/2/").body()
    }
}
