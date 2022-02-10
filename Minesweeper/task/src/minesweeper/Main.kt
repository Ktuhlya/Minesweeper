package minesweeper

import kotlin.random.Random
import kotlin.random.nextInt

val field = MutableList(9){MutableList(9){"."} }
var fieldFalse = mutableListOf<MutableList<String>>()
val listMines = mutableListOf<String>()

var amount = 0
fun main() {

    print("How many mines do you want on the field? >  ")
    amount = readln().toInt()
    do{
        field[Random.nextInt(field.indices)][Random.nextInt(field.indices)] = "X"
    }
    while (field.toString().count(){it.toString() == "X"} < amount)
    recMineList()
    drawDigit()
    Game().printField(Game().copyField(field))

}

fun recMineList() {
    for (i in field.indices){
        for (j in field[i].indices){
            if (field[i][j] == "X") listMines.add("${i.toString()},${j.toString()}")
        }
    }
    println(listMines)
}

class Game() {


    fun copyField(field: MutableList<MutableList<String>>):
            MutableList<MutableList<String>> {

        fieldFalse.clear()
        fieldFalse=MutableList(9){MutableList(9){"."} }

        for (i in field.indices) {
            for (j in field[i].indices) {
                var temp =field[i][j]
                fieldFalse[i][j] = temp
            }
        }

        for (i in fieldFalse.indices) {
            for (j in fieldFalse[i].indices) {
                if (fieldFalse[i][j] == "X") {
                    fieldFalse[i][j] = "."
                }
            }
        }
        return fieldFalse
    }

    fun printField(field: MutableList<MutableList<String>>) {

        fieldFalse.add(
            0, mutableListOf(
                " ", "|", "1", "2", "3", "4", "5", "6",
                "7", "8", "", "9", "|"
            )
        )
        fieldFalse.add(
            1, mutableListOf(
                "-", "|", "-", "-", "-", "-", "-",
                "-", "-", "-", "-", "|"
            )
        )
        for (i in 2..10) {
            fieldFalse[i].add(0, (i - 1).toString())
            fieldFalse[i].add(1, "|")
            fieldFalse[i].add(11, "|")
        }
        fieldFalse.add(
            11, mutableListOf(
                "-", "|", "-", "-", "-", "-", "-",
                "-", "-", "-", "-", "|"
            )
        )
        for (i in fieldFalse.indices) println(fieldFalse[i].joinToString(""))
        checkField()
       // letsPlay()
    }

    private fun checkField() {


    }

    private fun letsPlay() {
     //   for (i in field.indices) println(field[i].joinToString(""))
        println("Set/delete mines marks (x and y coordinates): > ")
        var (x, y) = readln().split(" ").map { it.toInt() }
         when {
             field.isDigit(x-1, y-1) -> {
                 println("There is a number here!")
                 letsPlay()
             }
             field.isMark(x-1, y-1) -> {
                 field[x-1][y-1] = "."
                 printField(copyField(field))
             }
             field.isMine(x-1, y-1) -> {

             }
             else -> {
                 field[x-1][y-1] = "*"
                 printField(copyField(field))
             }
         }
    }


}
fun MutableList<MutableList<String>>.isMine(row: Int, col: Int): Boolean {
        return this[row][col] == "X"
}
fun MutableList<MutableList<String>>.isDigit(row: Int, col: Int): Boolean{
       // return Regex("\\d+").matches(this[row][col])
       return this[row][col] != "X" && this[row][col] != "." && this[row][col] != "*"
}

fun MutableList<MutableList<String>>.cellToDigit(row: Int, col: Int) {
    if (!field.isDigit(row, col)) {
        if (field[row][col] != "X") this[row][col] = "1"

    } else {
        this[row][col] = (this[row][col].toInt() + 1).toString()

    }
}

fun MutableList<MutableList<String>>.isMark( row: Int, col: Int): Boolean {
    return this[row][col] == "*"

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




/*
  for (i in fieldFalse.indices){
        for (j in fieldFalse[i].indices){
            if (fieldFalse[i][j] == "X") {
                fieldFalse[i][j] = "."
                println("TTTT")
            }
        }
    }
 */
