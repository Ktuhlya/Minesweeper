package minesweeper

import kotlin.random.Random
import kotlin.random.nextInt

val field = MutableList(9){MutableList(9){"."} }

var amount = 0
fun main() {

    print("How many mines do you want on the field? >  ")
    amount = readln().toInt()
    do{
        field[Random.nextInt(field.indices)][Random.nextInt(field.indices)] = "X"
    }
    while (field.toString().count(){it.toString() == "X"} < amount)
    drawDigit()

    for (i in field.indices) println(field[i].joinToString(""))

}

fun MutableList<MutableList<String>>.isMine(row: Int, col: Int): Boolean {
        return this[row][col] == "X"
}
fun MutableList<MutableList<String>>.isDigit(row: Int, col: Int): Boolean{
       // return Regex("\\d+").matches(this[row][col])
       return this[row][col] != "X" && this[row][col] != "."
}

fun MutableList<MutableList<String>>.cellToDigit(row: Int, col: Int) {
    if (!field.isDigit(row, col)) {
        if (field[row][col] != "X") this[row][col] = "1"

    } else {
        this[row][col] = (this[row][col].toInt() + 1).toString()

    }
}

fun drawDigit() {

    for (i in field.indices){
        for (j in field[i].indices){
            if (field.isMine(i, j)){
                if ((j-1) != -1 ) field.cellToDigit(i, j - 1)
                if ((j+1) != 9 ) field.cellToDigit(i, j + 1)
                if ((i-1 != -1) && (j-1 != -1 )) field.cellToDigit(i - 1, j - 1)
                if ((i-1) != -1 ) field.cellToDigit(i - 1, j)
                if ((i-1) != -1 &&(j+1) != 9 ) field.cellToDigit(i - 1, j + 1)
                if ((i+1) != 9 &&(j-1) != -1 ) field.cellToDigit(i + 1, j - 1)
                if ((i+1) != 9 ) field.cellToDigit(i + 1, j)
                if ((i+1) != 9 &&(j+1) !=9 ) field.cellToDigit(i + 1, j + 1)
            }
        }
    }
}





