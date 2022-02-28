package com.pokemondemo.di.module

import com.pokemondemo.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideListPokemonRepository(listPokemonRepositoryImpl: ListPokemonRepositoryImpl): ListPokemonRepository

    @Binds
    @Singleton
    abstract fun provideFavoritePokemonRepository(favoritePokemonRepositoryImpl: FavoritePokemonRepositoryImpl): FavoritePokemonRepository

    @Binds
    @Singleton
    abstract fun provideDetailPokemonRepository(detailPokemonRepositoryImpl: DetailPokemonRepositoryImpl): DetailPokemonRepository
}