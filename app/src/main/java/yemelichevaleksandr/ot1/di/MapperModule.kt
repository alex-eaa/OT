package yemelichevaleksandr.ot1.di

import dagger.Module
import dagger.Provides
import yemelichevaleksandr.ot1.data.mappers.MapperForQuestion


@Module
class MapperModule {

    @Provides
    fun provideMapper(): MapperForQuestion =
        MapperForQuestion()
}