package com.example.pokedexapp.data.mapper

import com.example.pokedexapp.PokemonDetail
import com.example.pokedexapp.Stat
import com.example.pokedexapp.api.*
import com.example.pokedexapp.data.local.entities.pokemondetail.PokemonDetailEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.AbilityEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.CriesEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.FormEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.Generation
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.SpriteType
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.SpritesEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.StatEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.TypeEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.Game
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.PokemonDetailEntities
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.SpeciesEntity
import com.example.pokedexapp.data.local.entities.views.PokemonDetailView
import com.example.pokedexapp.data.mapper.interfaces.IGame
import com.example.pokedexapp.data.mapper.interfaces.IGeneration
import kotlin.io.println
import kotlin.reflect.KProperty1


object PokemonMapper {

    fun fromResponseToEntities(detailResponse: PokemonDetailResponse): PokemonDetailEntities {
        val detailEntity = PokemonDetailEntity(
            id = detailResponse.id,
            name = detailResponse.name.orEmpty(),
            pokemonOrder = detailResponse.order,
            isDefault = detailResponse.is_default,
            height = (detailResponse.height ?: 0).toString(),
            weight = detailResponse.weight ?: 0
        )

        val speciesEntity = detailResponse.species?.let {
            SpeciesEntity(
                pokemonId = detailResponse.id ?: 0,
                name = it.name.orEmpty(),
                url = it.url.orEmpty()
            )
        }

        val abilities = detailResponse.abilities?.map {
            AbilityEntity(
                pokemonId = detailResponse.id ?: 0,
                name = it.ability?.name.orEmpty(),
                url = it.ability?.url.orEmpty()
            )
        } ?: emptyList()

        val types = detailResponse.types?.map {
            TypeEntity(
                pokemonId = detailResponse.id ?: 0,
                name = it.type?.name.orEmpty(),
                url = it.type?.url.orEmpty()
            )
        } ?: emptyList()

        val criesEntity = detailResponse.cries?.let {
            CriesEntity(
                pokemonId = detailResponse.id ?: 0,
                latest = it.latest.orEmpty(),
                legacy = it.legacy.orEmpty()
            )
        }

        val formEntity = detailResponse.form?.let {
            FormEntity(
                pokemonId = detailResponse.id ?: 0,
                name = it.name.orEmpty(),
                url = it.url.orEmpty()
            )
        }
        val stats = detailResponse.stats?.map {
            StatEntity(
                pokemonId = detailResponse.id ?: 0,
                name = it.stat?.name.orEmpty(),
                value = it.base_stat ?: 0
            )
        } ?: emptyList()

        val sprites = convertToSpriteEntity(detailResponse)

        return PokemonDetailEntities(
            detailEntity = detailEntity,
            speciesEntity = speciesEntity,
            abilities = abilities,
            types = types,
            criesEntity = criesEntity,
            formEntity = formEntity,
            sprites = sprites,
            stats = stats
        )
    }

    fun PokemonDetail.toEntity(): PokemonDetailEntity {
        return PokemonDetailEntity(
            id = this.id,
            name = this.name,
            pokemonOrder = this.order,
            isDefault = this.isDefault,
            height = (this.height?.replace(" m", "")?.toIntOrNull() ?: 0).toString(),
            weight = this.weight?.replace(" kg", "")?.toIntOrNull() ?: 0
        )
    }
    fun PokemonDetailView.toDomain(): PokemonDetail {
        return PokemonDetail(
            id = this.id,
            order = this.order ?: 0,
            name = this.name,
            species = this.species?.let { Species(it.name, it.url.orEmpty()) } ?: Species("", ""),
            types = this.types?.map { TypeName(it.name, it.url.orEmpty()) } ?: emptyList(),
            form = this.form?.let { Form(it.name.orEmpty(), it.url.orEmpty()) } ?: Form("", ""),
            isDefault = this.isDefault ?: false,
            cries = this.cries?.let { Cries(it.latest.orEmpty(), it.legacy.orEmpty()) } ?: Cries("", ""),
            sprites = this.sprites?.let { mapSpritesToDomain(it) },
            abilities = this.abilities?.map { ability ->
                AbilityDetail(
                    name = ability.name,
                    url = ability.url
                )
            } ?: emptyList(),
            stats = this.stats?.map { stat ->
                Stat(stat.name, stat.value)
            } ?: emptyList(),
            weight = "${this.weight ?: 0} kg",
            height = "${this.height ?: 0} m"
        )
    }

    private fun mapSpritesToDomain(sprites: List<SpritesEntity>?): List<SpritesEntity> {
        return sprites ?: emptyList()
    }

    private fun convertToSpriteEntity(detailResponse: PokemonDetailResponse): List<SpritesEntity> {
        val spriteEntities = mutableListOf<SpritesEntity>()

        detailResponse.sprites?.let {
            for (property in it::class.members) {
                if (property is KProperty1<*, *> && property.returnType.classifier == String::class) {
                    val url = property.call(it) as? String
                    if (!url.isNullOrEmpty()) {
                        try {
                            val spriteType = SpriteType.valueOf(property.name.uppercase())
                            spriteEntities.add(
                                SpritesEntity(
                                    pokemonId = detailResponse.id ?: 0,
                                    generation = Generation.NONE,
                                    type = spriteType,
                                    game = Game.NONE,
                                    url = url
                                )
                            )
                            println("Sprite básico agregado: $spriteType -> $url")

                        } catch (e: IllegalArgumentException) {
                            println("Tipo de sprite no reconocido: ${property.name}")

                        }
                    }
                }
            }
        }

        detailResponse.sprites?.versions?.let { versions ->
            Generation.entries.forEach { generation ->
                val generationData = versions::class.members.find { it.name == generation.jsonKey }?.call(versions)
                if (generationData != null && (generationData is IGeneration || generationData is String)) {
                    println("Procesando generación: ${generation.name}")
                    getGames(generationData, spriteEntities, detailResponse, generation)
                }
            }
        }
        println("Total de sprites procesados: ${spriteEntities.size}")
        println(spriteEntities)
        return spriteEntities
    }

    private fun getGames(
        generationData: Any,
        spriteEntities: MutableList<SpritesEntity>,
        detailResponse: PokemonDetailResponse,
        generation: Generation
    ) {
        if (generationData is IGeneration || generationData is String) {
            Game.entries.forEach { game ->
                val gameData = generationData::class.members.find { it.name == game.jsonKey }?.call(generationData)
                if (gameData != null && (gameData is IGame || gameData is String)) {
                    println("Procesando juego: ${game.name} en generación: ${generation.name}")
                    spriteEntities.addAll(getSprites(gameData, detailResponse, generation, game))
                }
            }
        }
    }

    private fun getSprites(
        gameData: Any,
        detailResponse: PokemonDetailResponse,
        generation: Generation,
        game: Game
    ): List<SpritesEntity> {
        val spriteEntities = mutableListOf<SpritesEntity>()

        if (gameData is IGame || gameData is String) {
            SpriteType.entries.forEach { spriteType ->
                val url = gameData::class.members.find { it.name == spriteType.jsonKey }
                    ?.call(gameData) as? String
                if (!url.isNullOrEmpty()) {
                    spriteEntities.add(
                        SpritesEntity(
                            pokemonId = detailResponse.id ?: 0,
                            generation = generation,
                            game = game,
                            type = spriteType,
                            url = url
                        )
                    )
                    println("Sprite agregado: ${spriteType.name} -> $url (Generación: ${generation.name}, Juego: ${game.name})")
                }
            }
        }
        return spriteEntities
    }

    private fun normalizeSpriteEntity(sprite: SpritesEntity): SpritesEntity {
        return sprite.copy(
            generation = Generation.entries.find { it.name == sprite.generation.name } ?: Generation.NONE,
            game = Game.entries.find { it.name == sprite.game.name } ?: Game.NONE,
            type = sprite.type
        )
    }

     fun normalizeSprites(sprites: List<SpritesEntity>): List<SpritesEntity> {
        return sprites.map { normalizeSpriteEntity(it) }
    }
}