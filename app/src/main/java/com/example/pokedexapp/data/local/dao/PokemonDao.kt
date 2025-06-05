package com.example.pokedexapp.data.local.dao

import androidx.room.*
import com.example.pokedexapp.PokemonList
import com.example.pokedexapp.data.local.entities.views.PokemonListWithFavoriteView
import com.example.pokedexapp.data.local.entities.pokemondetail.PokemonDetailEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.AbilityEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.CriesEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.FormEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.SpeciesEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.SpriteType
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.SpritesEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.StatEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.TypeEntity
import com.example.pokedexapp.data.local.entities.pokemonfavorite.PokemonFavoriteEntity
import com.example.pokedexapp.data.local.entities.pokemonlist.PokemonListEntity
import com.example.pokedexapp.data.local.entities.views.PokemonDetailView
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonDetailEntity(detailEntity: PokemonDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeciesEntity(speciesEntity: SpeciesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpritesEntity(spritesEntity: SpritesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbilityEntity(abilityEntity: AbilityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTypeEntity(typeEntity: TypeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCriesEntity(criesEntity: CriesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFormEntity(formEntity: FormEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatEntity(statEntity: StatEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonList(pokemonList: List<PokemonListEntity>)
    


    @Query("""
    SELECT
        pokemon_list.id,
        pokemon_list.name,
        pokemon_list.url,
        COALESCE(pokemon_favorite.isFavorite, 0) AS isFavorite
    FROM pokemon_list
    LEFT JOIN pokemon_favorite ON pokemon_list.id = pokemon_favorite.id
    LIMIT :limit OFFSET :offset
    """)
    suspend fun getAllPokemonList(limit: Int, offset: Int): List<PokemonListWithFavoriteView>

    @Query("""
    SELECT
        pokemon_list.name,
        pokemon_list.url,
        COALESCE(pokemon_favorite.isFavorite, 0) AS isFavorite
    FROM pokemon_list
    LEFT JOIN pokemon_favorite ON pokemon_list.id = pokemon_favorite.id
    """)
    fun allPokemonList(): Flow<List<PokemonList>>

    @Query("""
    SELECT
        pokemon_list.id,
        pokemon_list.name,
        pokemon_list.url,
        COALESCE(pokemon_favorite.isFavorite, 0) AS isFavorite
    FROM pokemon_list
    LEFT JOIN pokemon_favorite ON pokemon_list.id = pokemon_favorite.id
    WHERE pokemon_list.id = :id
""")
    suspend fun getPokemonById(id: Int): PokemonListWithFavoriteView?

    @Query("""
    SELECT
        pokemon_list.id,
        pokemon_list.name,
        pokemon_list.url,
        COALESCE(pokemon_favorite.isFavorite, 0) AS isFavorite
    FROM pokemon_list
    LEFT JOIN pokemon_favorite ON pokemon_list.id = pokemon_favorite.id
    WHERE pokemon_list.name = :name
    """)
    suspend fun getPokemonByName(name: String): PokemonListWithFavoriteView?

    @Query("SELECT * FROM pokemon_list WHERE id = :id")
    suspend fun getPokemonListById(id: Int): PokemonListEntity?

    @Query(
        """
    SELECT 
        pd.id AS id,
        pd.name AS name,
        pd.pokemonOrder AS `order`,
        pd.isDefault AS isDefault,
        pd.height AS height,
        pd.weight AS weight,
        s.name AS speciesName,
        s.url AS speciesUrl,
        GROUP_CONCAT(t.name, ',') AS typeNames,
        GROUP_CONCAT(t.url, ',') AS typeUrls,
        GROUP_CONCAT(a.name, ',') AS abilityNames,
        GROUP_CONCAT(a.url, ',') AS abilityUrls,
        c.latest AS criesLatest,
        c.legacy AS criesLegacy,
        (
            SELECT GROUP_CONCAT(sp.url || ':' || sp.type, ',')
            FROM pokemon_sprites sp
            WHERE sp.pokemonId = pd.id
        ) AS sprites,
        f.name AS formName,
        f.url AS formUrl
    FROM pokemon_detail pd
    LEFT JOIN pokemon_species s ON pd.id = s.pokemonId
    LEFT JOIN pokemon_type t ON pd.id = t.pokemonId
    LEFT JOIN pokemon_ability a ON pd.id = a.pokemonId
    LEFT JOIN pokemon_cries c ON pd.id = c.pokemonId
    LEFT JOIN form f ON pd.id = f.pokemonId
    WHERE pd.id = :id
    GROUP BY pd.id
    """
    )
    suspend fun getPokemonDetailViewById(id: Int): PokemonDetailView?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(pokemonFavorite: PokemonFavoriteEntity)

    @Query("DELETE FROM pokemon_favorite WHERE id = :id")
    suspend fun deleteFavorite(id: Int)

    @Query("""
SELECT * FROM pokemon_sprites
WHERE pokemonId = :pokemonId AND
      generation = :generation AND
      game = :game AND
      type = :type
""")
    suspend fun getSpriteByKeys(
        pokemonId: Int,
        generation: String,
        game: String,
        type: SpriteType
    ): SpritesEntity?

    @Query("""
    SELECT
        pokemon_list.id,
        pokemon_list.name,
        pokemon_list.url,
        COALESCE(pokemon_favorite.isFavorite, 0) AS isFavorite
    FROM pokemon_list
    LEFT JOIN pokemon_favorite ON pokemon_list.id = pokemon_favorite.id
    WHERE pokemon_list.id BETWEEN :startId AND :endId
    LIMIT :limit OFFSET :offset
""")
    suspend fun getPokemonByGeneration(
        startId: Int,
        endId: Int,
        limit: Int,
        offset: Int
    ): List<PokemonListWithFavoriteView>
}