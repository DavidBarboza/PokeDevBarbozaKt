import java.util.*

enum class Pokemones(val id: Int){
    BULBASAUR(1),
    CHARMANDER(2),
    SQUIRTLE(3),
    PIKACHU(4)
}
fun main(args: Array<String>) {
    println("Elige un pokemon: ")

    for ((index, p) in Pokemones.values().withIndex()) {
        println("${index + 1}) $p")
    }

    val scanner = Scanner(System.`in`)
    val seleccion = scanner.nextInt()

    val pokemon: Pokemon = generarPokemon(seleccion)

    //println(pokemon.nombre)

    mostrarDatos(pokemon)

    val random = Random()
    val numeroRandom = 1 + random.nextInt(4)
    val pokemonSalvaje: Pokemon = generarPokemon(numeroRandom)

    println("Un ${pokemonSalvaje.nombre} salvaje ha aparecido!!!\n")

    do {
        println("${pokemon.nombre} HP: ${pokemon.hp} VS ${pokemonSalvaje.nombre} HP: ${pokemonSalvaje.hp}")

        println("Elige un ataque:")

        for((index, a) in pokemon.listaAtaques.withIndex()){
            println("${index+1}: ${a.nombre}")
        }

        val ataqueSeleccionado = scanner.nextInt()

        if(procesarAtaque(pokemon, pokemonSalvaje, ataqueSeleccionado)){
            break
        }

        val ataqueRandom: Int = 1 + random.nextInt(pokemonSalvaje.listaAtaques.size)

        if(procesarAtaque(pokemonSalvaje, pokemon, ataqueRandom)){
            break
        }else{
            println("Los dos pokemones siguen luchando!")
            println("...continuamos")
        }

    }while(pokemon.hp > 0 && pokemonSalvaje.hp > 0)
}

fun procesarAtaque(pokemonAtacante: Pokemon, pokemonDefensor: Pokemon, ataqueSeleccionado: Int): Boolean {

    val ataque = pokemonAtacante.obtenerAtaque(ataqueSeleccionado)
    println("${pokemonAtacante.nombre} ha usado ${ataque.nombre}")

    val valorDanio =  calcularDanio(pokemonAtacante.ataque, pokemonAtacante.defensa, ataque)
    println("${pokemonDefensor.nombre} ha recibido ${valorDanio} puntos de daño!")
    pokemonDefensor.hp -= valorDanio

    if(pokemonDefensor.hp <= 0){
        println("${pokemonDefensor.nombre} se murió!")
        println("${pokemonAtacante.nombre} ganó la batalla!")

        return true
    }

    return false
}

fun calcularDanio(valorAtaque: Int, valorDefensa: Int, ataque: Ataque): Int =
        ((((2 * 1 + 10.00) / 250) * (valorAtaque / valorDefensa) * ataque.poder + 2) * 1.5).toInt()

fun mostrarDatos(pokemon: Pokemon) {
    println("Has elegido a ${pokemon.nombre}\n" +
            "HP: ${pokemon.hp}\n" +
            "ATAQUE: ${pokemon.ataque}\n" +
            "DEFENSA: ${pokemon.defensa}\n")
}

fun generarPokemon(seleccion: Int): Pokemon = when (seleccion){
    Pokemones.BULBASAUR.id -> Pokemon("Bulbasaur", 17, 49,49,arrayOf(Ataque("vine whip",45), Ataque("tackle",35)))
    Pokemones.CHARMANDER.id -> Pokemon("Charmander", 18, 59,49,arrayOf(Ataque("ember",59), Ataque("scratch",25)))
    Pokemones.SQUIRTLE.id -> Pokemon("Squirtle", 20, 54,59,arrayOf(Ataque("water gun",55), Ataque("tackle",35)))
    Pokemones.PIKACHU.id -> Pokemon("Pikachu", 16, 59,49,arrayOf(Ataque("thunder shock",55), Ataque("quick attack",35)))
    else -> Pokemon("Missingno", 33,136, 0, arrayOf(Ataque("pay day",20),Ataque("blind",15)))
}
