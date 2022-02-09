package minesweeper

import java.time.temporal.TemporalAmount
import kotlin.random.Random
import kotlin.random.nextInt

val field = MutableList(9){MutableList(9){"."} }

var amount = 0
fun main() {

    print("How many mines do you want on the field? >  ")
    amount = readln().toInt()
    do {
        field[Random.nextInt(0..8)][Random.nextInt(0..8)] = "X"
    }
    while (field.toString().count(){it.toString() == "X"} < amount)

drawDigit()
    for (i in field.indices) println(field[i].joinToString(""))

}

fun MutableList<MutableList<String>>.isMine(row: Int, col: Int): Boolean {
        return this[row][col] == "X"
}
fun MutableList<MutableList<String>>.isDigit(row: Int, col: Int): Boolean{
        return Regex("\\d+").matches(this[row][col])
     //   return this[row][col] != "X" && this[row][col] != "."
}

fun MutableList<MutableList<String>>.cellToDigit(row: Int, col: Int) {
    if (!field.isDigit(row, col)) {
        this[row][col] = "1"
    } else {
        this[row][col] = (this[row][col].toInt() + 1).toString()

    }
}

fun drawDigit() {
    for (i in field.indices) {
        for (j in field[i].indices) {
            if (field.isMine(i, j)) {
                if ((i in 1..7) && (j in 1..7)) {
                    field.cellToDigit(i, j - 1)
                    field.cellToDigit(i, j + 1)
                    field.cellToDigit(i - 1, j - 1)
                    field.cellToDigit(i - 1, j)
                    field.cellToDigit(i - 1, j + 1)
                    field.cellToDigit(i + 1, j - 1)
                    field.cellToDigit(i + 1, j)
                    field.cellToDigit(i + 1, j + 1)
                }
                if ((i == 0) && (j in 1..7)) {
                    field.cellToDigit(i, j - 1)
                    field.cellToDigit(i, j + 1)
                    //   field.cellToDigit(i-1, j-1)
                    //   field.cellToDigit(i-1, j)
                    //   field.cellToDigit(i-1, j+1)
                    field.cellToDigit(i + 1, j - 1)
                    field.cellToDigit(i + 1, j)
                    field.cellToDigit(i + 1, j + 1)
                }
                if ((i == 8) && (j in 1..7)) {
                    field.cellToDigit(i, j - 1)
                    field.cellToDigit(i, j + 1)
                    field.cellToDigit(i - 1, j - 1)
                    field.cellToDigit(i - 1, j)
                    field.cellToDigit(i - 1, j + 1)
                    //   field.cellToDigit(i+1, j-1)
                    //   field.cellToDigit(i+1, j)
                    //   field.cellToDigit(i+1, j+1)
                }
                if ((i in 1..7) && (j == 0)) {
                    //   field.cellToDigit(i, j-1)
                    field.cellToDigit(i, j + 1)
                    //   field.cellToDigit(i-1, j-1)
                    field.cellToDigit(i - 1, j)
                    field.cellToDigit(i - 1, j + 1)
                    //   field.cellToDigit(i+1, j-1)
                    field.cellToDigit(i + 1, j)
                    field.cellToDigit(i + 1, j + 1)
                }
                if ((i in 1..7) && (j == 8)) {
                    field.cellToDigit(i, j - 1)
                    //   field.cellToDigit(i, j+1)
                    field.cellToDigit(i - 1, j - 1)
                    field.cellToDigit(i - 1, j)
                    //   field.cellToDigit(i-1, j+1)
                    field.cellToDigit(i + 1, j - 1)
                    field.cellToDigit(i + 1, j)
                    //   field.cellToDigit(i+1, j+1)
                }
                // corners
                if ((i == 0) && (j == 0)) {
                 //   field.cellToDigit(i, j - 1)
                    field.cellToDigit(i, j + 1)
                 //   field.cellToDigit(i - 1, j - 1)
                 //   field.cellToDigit(i - 1, j)
                 //   field.cellToDigit(i - 1, j + 1)
                 //   field.cellToDigit(i + 1, j - 1)
                    field.cellToDigit(i + 1, j)
                    field.cellToDigit(i + 1, j + 1)
                }
                 if ((i == 8) && (j == 0)) {
                 //   field.cellToDigit(i, j - 1)
                    field.cellToDigit(i, j + 1)
                 //   field.cellToDigit(i - 1, j - 1)
                    field.cellToDigit(i - 1, j)
                    field.cellToDigit(i - 1, j + 1)
                 //   field.cellToDigit(i + 1, j - 1)
                 //   field.cellToDigit(i + 1, j)
                 //   field.cellToDigit(i + 1, j + 1)
                }
                 if ((i ==8) && (j == 8)) {
                    field.cellToDigit(i, j - 1)
                 //   field.cellToDigit(i, j + 1)
                    field.cellToDigit(i - 1, j - 1)
                    field.cellToDigit(i - 1, j)
                 //   field.cellToDigit(i - 1, j + 1)
                 //   field.cellToDigit(i + 1, j - 1)
                 //   field.cellToDigit(i + 1, j)
                 //   field.cellToDigit(i + 1, j + 1)
                }
                 if ((i == 0) && (j == 8)) {
                    field.cellToDigit(i, j - 1)
                 //   field.cellToDigit(i, j + 1)
                 //   field.cellToDigit(i - 1, j - 1)
                 //   field.cellToDigit(i - 1, j)
                 //   field.cellToDigit(i - 1, j + 1)
                    field.cellToDigit(i + 1, j - 1)
                    field.cellToDigit(i + 1, j)
                 //   field.cellToDigit(i + 1, j + 1)
                }



            }
        }
    }
}










////////////////////////////
/*
  for (i in field.indices){
       for (j in field[i].indices){
           if (field.isMine(i, j)) println("$i $j")
       }
   }

 */




