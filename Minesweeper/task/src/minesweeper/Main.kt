package minesweeper

import java.time.temporal.TemporalAmount
import kotlin.random.Random
import kotlin.random.nextInt

val field = MutableList(9){MutableList(9){"."} }

var amount = 0
fun main() {

    print("How many mines do you want on the field? >  ")
    amount = readln().toInt()
    while (field.toString().count(){it.toString() == "X"} < amount)
        field[Random.nextInt(0..8)][Random.nextInt(0..8)] = "X"

    for (i in field.indices) println(field[i].joinToString(""))

}


