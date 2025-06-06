package com.example.pokedexapp.utils

enum class FilterOption {
    ALL, FAVORITES
}
enum class GenerationOption(val range: IntRange?) {
    ALL(null),
    GEN1(1..151),
    GEN2(152..251),
    GEN3(252..386),
    GEN4(387..494),
    GEN5(495..649),
    GEN6(650..721),
    GEN7(722..809),
    GEN8(810..905),
    GEN9(906..1010)
}