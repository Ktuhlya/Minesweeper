package minesweeper

import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random.Default.nextInt

class Cell() {
    var cellChar = '.'
    var marked: Boolean = false
    var opened: Boolean = false
}

class GameField(val mines: Int) {
    var field = initEmptyField()

    var userIsDead = false

    fun initEmptyField(): ArrayList<Array<Cell>> {
        val field = ArrayList<Array<Cell>>()

        for (i in 0 until 9) {
            field.add(Array<Cell>(9) {i -> Cell()})
        }
        return field
    }

    fun placeMines(mines: Int): ArrayList<Array<Cell>> {
        val field = initEmptyField()

        for (i in 1..mines) {
            var x = nextInt(from = 0, until = 9)
            var y = nextInt(from = 0, until = 9)
            while (field[y][x].cellChar != '.') {
                x = nextInt(from = 0, until = 9)
                y = nextInt(from = 0, until = 9)
            }
            field[y][x].cellChar = 'X'
        }

        for (i in 0 until 9) {
            for (j in 0 until 9) {
                if (field[j][i].cellChar != 'X') {
                    var minesCount = 0
                    if (j - 1 in 0 until 9) {
                        if (i - 1 in 0 until 9) {
                            if (field[j - 1][i - 1].cellChar == 'X')
                                minesCount++
                        }
                        if (field[j - 1][i].cellChar == 'X')
                            minesCount++
                        if (i + 1 in 0 until 9) {
                            if (field[j - 1][i + 1].cellChar == 'X')
                                minesCount++
                        }
                    }
                    if (j + 1 in 0 until 9) {
                        if (i - 1 in 0 until 9) {
                            if (field[j + 1][i - 1].cellChar == 'X')
                                minesCount++
                        }
                        if (field[j + 1][i].cellChar == 'X')
                            minesCount++
                        if (i + 1 in 0 until 9) {
                            if (field[j + 1][i + 1].cellChar == 'X')
                                minesCount++
                        }
                    }
                    if (i - 1 in 0 until 9 && field[j][i - 1].cellChar == 'X')
                        minesCount++
                    if (i + 1 in 0 until 9 && field[j][i + 1].cellChar == 'X')
                        minesCount++

                    if (minesCount in 1..8)
                        field[j][i].cellChar = minesCount.toString().first()
                }
            }
        }
        return field
    }

    fun placeMinesFirstMove(mines: Int, xInit: Int, yInit: Int): ArrayList<Array<Cell>> {
        for (i in 1..mines) {
            var x = nextInt(from = 0, until = 9)
            var y = nextInt(from = 0, until = 9)
            while (field[y][x].cellChar == 'X' || (y == yInit && x == xInit)) {
                x = nextInt(from = 0, until = 9)
                y = nextInt(from = 0, until = 9)
            }
            field[y][x].cellChar = 'X'
        }

        for (i in 0 until 9) {
            for (j in 0 until 9) {
                if (field[j][i].cellChar != 'X') {
                    var minesCount = 0
                    if (j - 1 in 0 until 9) {
                        if (i - 1 in 0 until 9) {
                            if (field[j - 1][i - 1].cellChar == 'X')
                                minesCount++
                        }
                        if (field[j - 1][i].cellChar == 'X')
                            minesCount++
                        if (i + 1 in 0 until 9) {
                            if (field[j - 1][i + 1].cellChar == 'X')
                                minesCount++
                        }
                    }
                    if (j + 1 in 0 until 9) {
                        if (i - 1 in 0 until 9) {
                            if (field[j + 1][i - 1].cellChar == 'X')
                                minesCount++
                        }
                        if (field[j + 1][i].cellChar == 'X')
                            minesCount++
                        if (i + 1 in 0 until 9) {
                            if (field[j + 1][i + 1].cellChar == 'X')
                                minesCount++
                        }
                    }
                    if (i - 1 in 0 until 9 && field[j][i - 1].cellChar == 'X')
                        minesCount++
                    if (i + 1 in 0 until 9 && field[j][i + 1].cellChar == 'X')
                        minesCount++

                    if (minesCount in 1..8)
                        field[j][i].cellChar = minesCount.toString().first()
                }
            }
        }
        openCell(xInit, yInit)
        return field
    }

    fun printWithoutMines() {
        println(" |123456789|")
        println("—|—————————|")
        for (i in field.indices) {
            print("${i + 1}|")
            for (j in field[i].indices) {
                if (field[i][j].cellChar == 'X')
                    print(if (field[i][j].marked) '*' else '.')
                else print(if (field[i][j].marked) '*'
                else field[i][j].cellChar)
            }
            println("|")
        }
        println("—|—————————|")
    }

    fun printFinal() {
        println(" |123456789|")
        println("—|—————————|")
        for (i in field.indices) {
            print("${i + 1}|")
            for (j in field[i].indices) {
                if (field[i][j].opened) {
                    if (field[i][j].cellChar == '.')
                        print('/')
                    else
                        print(field[i][j].cellChar)
                } else if (field[i][j].marked)
                    print('*')
                else
                    print('.')
            }
            println("|")
        }
        println("—|—————————|")
    }

    fun gameIsOverAllMarked(): Boolean {
        for (i in field.indices) {
            for (j in field[i].indices) {
                if (field[i][j].cellChar == 'X' && !field[i][j].marked ||
                    field[i][j].cellChar != 'X' && field[i][j].marked)
                    return false
            }
        }
        return true
    }

    fun gameIsOverAllOpened(): Boolean {
        for (i in field.indices) {
            for (j in field[i].indices) {
                if (field[i][j].cellChar != 'X' && !field[i][j].opened)
                    return false
            }
        }
        return true
    }

    fun openCell(x: Int, y: Int) {
        if (field[y][x].cellChar == 'X') {
            userIsDead = true
            printDead()
            println("You stepped on a mine and failed!")
        } else if (field[y][x].cellChar == '.') {
            field[y][x].opened = true
            field[y][x].marked = false
            if (y - 1 in 0 until 9) {
                if (x - 1 in 0 until 9) {
                    if (!field[y - 1][x - 1].opened)
                        openCell(x - 1, y - 1)
                }
                if (!field[y - 1][x].opened)
                    openCell(x, y - 1)
                if (x + 1 in 0 until 9) {
                    if (!field[y - 1][x + 1].opened)
                        openCell(x + 1, y - 1)
                }
            }
            if (y + 1 in 0 until 9) {
                if (x - 1 in 0 until 9) {
                    if (!field[y + 1][x - 1].opened)
                        openCell(x - 1, y + 1)
                }
                if (!field[y + 1][x].opened)
                    openCell(x, y + 1)
                if (x + 1 in 0 until 9) {
                    if (!field[y + 1][x + 1].opened)
                        openCell(x + 1, y + 1)
                }
            }
            if (x - 1 in 0 until 9 && !field[y][x - 1].opened)
                openCell(x - 1, y)
            if (x + 1 in 0 until 9 && !field[y][x + 1].opened)
                openCell(x + 1, y)

        } else {
            field[y][x].opened = true
        }
    }

    fun printDead() {
        println(" |123456789|")
        println("—|—————————|")
        for (i in field.indices) {
            print("${i + 1}|")
            for (j in field[i].indices) {
                if (field[i][j].opened && field[i][j].cellChar == '.') {
                    print('/')
                } else if (field[i][j].cellChar == '.' && field[i][j].marked) {
                    print('*')
                } else {
                    print(field[i][j].cellChar)
                }
            }
            println("|")
        }
        println("—|—————————|")
    }
}

fun main() {
    val scanner = Scanner(System.`in`)
    print("How many mines do you want on the field? ")
    val minesNumber = scanner.nextInt()
    val field = GameField(minesNumber)
    field.printFinal()

    var xInit = 0
    var yInit = 0
    var commandInit = ""
    while (commandInit != "free") {
        print("Set/unset mines marks or claim a cell as free: ")
        xInit = scanner.nextInt()
        yInit = scanner.nextInt()
        commandInit = scanner.next()
        if (commandInit == "mine") {
            field.field[yInit - 1][xInit - 1].marked = !field.field[yInit - 1][xInit - 1].marked
            field.printFinal()
            continue
        } else if (commandInit != "free") {
            println("Unknown command.")
            continue
        }
    }
    field.field = field.placeMinesFirstMove(minesNumber, xInit - 1, yInit - 1)
    field.printFinal()

    while (!field.gameIsOverAllMarked() && !field.userIsDead && !field.gameIsOverAllOpened()) {
        print("Set/unset mines marks or claim a cell as free: ")
        val x = scanner.nextInt()
        val y = scanner.nextInt()
        val command = scanner.next()
        if (command == "mine") {
            if (field.field[y - 1][x - 1].opened) {
                println("The cell is already opened.")
                continue
            } else {
                field.field[y - 1][x - 1].marked = !field.field[y - 1][x - 1].marked
                field.printFinal()
                continue
            }
        } else if (command == "free") {
            if (field.field[y - 1][x - 1].opened) {
                println("The cell is already opened.")
                continue
            } else if (field.field[y - 1][x - 1].marked) {
                println("Remove mine mark from the cell before open.")
                continue
            } else {
                field.openCell(x - 1, y - 1)
                if (!field.userIsDead)
                    field.printFinal()
                continue
            }
        } else {
            println("Unknown command.")
            continue
        }
    }
    if (!field.userIsDead)
        println("Congratulations! You found all mines!")
}






/////////////////////////
/*
package minesweeper


import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.system.exitProcess

val field = MutableList(9){MutableList(9){"."} }
val fieldFalse = MutableList(9){MutableList(9){"."} }
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
    Game().printField(Game().copyFieldF(field))
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

    fun copyFieldF(field: MutableList<MutableList<String>>):
            MutableList<MutableList<String>> {


        for (i in field.indices) {
            for (j in field[i].indices) {
                var temp =field[i][j]
                fieldFalse[i][j] = temp
            }
        }

        for (i in fieldFalse.indices) {
            for (j in fieldFalse[i].indices) {
                if ((fieldFalse[i][j] == "X") || (fieldFalse.isDigit(i, j)))
                fieldFalse[i][j]  = "."

            }
        }
        return fieldFalse
    }




    private fun copyField(field: MutableList<MutableList<String>>, x: Int = 0, y: Int = 0):
            MutableList<MutableList<String>> {

    //    fieldFalse.clear()
     //   fieldFalse=MutableList(9){MutableList(9){"."} }


        for (i in fieldFalse.indices) {
            for (j in fieldFalse[i].indices) {
                fieldFalse[i][j]= field[i][j]
            }
        }
        return fieldFalse
    }

    fun printField(fieldIn: MutableList<MutableList<String>>) {


        val fieldFalsePrint=MutableList(9){MutableList(9){"."} }

        for (i in fieldIn.indices) {
            for (j in fieldIn[i].indices) {
                var temp = fieldIn[i][j]
                fieldFalsePrint[i][j] = temp
            }
        }

        fieldFalsePrint.add(
            0, mutableListOf(
                " ", "|", "1", "2", "3", "4", "5", "6",
                "7", "8", "", "9", "|"
            )
        )
        fieldFalsePrint.add(
            1, mutableListOf(
                "-", "|", "-", "-", "-", "-", "-",
                "-", "-", "-", "-", "|"
            )
        )
        for (i in 2..10) {
            fieldFalsePrint[i].add(0, (i - 1).toString())
            fieldFalsePrint[i].add(1, "|")
            fieldFalsePrint[i].add(11, "|")
        }
        fieldFalsePrint.add(
            11, mutableListOf(
                "-", "|", "-", "-", "-", "-", "-",
                "-", "-", "-", "-", "|"
            )
        )
        println()
        for (i in fieldFalsePrint.indices)
            println(fieldFalsePrint[i].joinToString(""))
        checkField()
    }

    private fun checkField() {

        var checkList = mutableListOf<String>()
        for (i in field.indices){
            for (j in field[i].indices){
                if (field[i][j] == "*") checkList.add("${i},${j}")
            }
        }

        if (checkList.size == listMines.size){
            for (i in checkList.indices){
                if (checkList[i] != listMines[i]) letsPlay()
                else{
                    println("Congratulations! You found all the mines!")
                    System.exit(0)
                    break
                }
            }
        } else letsPlay()
    }

    private fun letsPlay() {

        print("Set/unset mine marks or claim a cell as free: > ")

        var (b, a, type) = readln().split(" ")
        var x = a.toInt() - 1
        var y = b.toInt() - 1
        if (type == "mine") typeMine(x, y)
        if (type == "free") typeFree(x, y)

    }

    private fun typeFree(x: Int, y: Int) {

        when {
            (listMines.contains("$x,$y")) -> {
                println("You stepped on a mine and failed!")
                exitProcess(0)
            }
            (field.isDigit(x, y)) -> {
                fieldFalse[x][y] = field[x][y]
                printField(fieldFalse)
            }
            field[x][y] =="." -> {
             //   fieldFalse[x][y] = "/"
             //   checkAround(x, y)
                openEmpty(x, y)
            }
           // else -> checkAround(x, y)
            }
        }



    private fun openEmpty( x: Int, y: Int) {

        val a = x
        val b = y
        if ( x>=9 || y>=9 || x <0 || y <0) return
        if (field.isDigit(x, y)) {
            fieldFalse[x][y] = field[x][y]
            return
        }
        if (fieldFalse[x][y] == "/") return
        ///
         fieldFalse[x][y] = "/"

        openEmpty(x+1, y)
        openEmpty(x-1, y)
        openEmpty(x, y+1)

        openEmpty(x, y-1)


      // for (i in field.indices) println(field[i].joinToString (""))
       // printField(fieldFalse)
        openEmpty2(a-1, b)

    }

    private fun openEmpty2(x: Int, y: Int) {

        if ( x>=9 || y>=9 || x <0 || y <0) return
        if (field.isDigit(x, y)) {
            fieldFalse[x][y] = field[x][y]
            return
        }
        if (fieldFalse[x][y] == "/") return
        ///
        fieldFalse[x][y] = "/"
        println("fgg")
        openEmpty2(x-1, y)
        openEmpty2(x+1, y)
        openEmpty2(x, y-1)
        openEmpty2(x, y+1)


        printField(fieldFalse)
    }


    private fun typeMine(x: Int, y: Int) {

        when {
            fieldFalse.isMark(x, y) -> {
                fieldFalse[x][y] = "."
                printField(fieldFalse)
            }
            else -> {
                fieldFalse[x][y] = "*"
                printField(fieldFalse)
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
            //   && this[row][col] != "/"
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
                field[x-1][y-1] = "*"
                 printField(copyField(field))
             }
             else -> {
                 field[x-1][y-1] = "*"
                 printField(copyField(field))
             }
         }
    }
    //////////////////

      private fun checkAround(x: Int, y: Int) {
        val listAround = mutableListOf<String>()
        val listToEmpty = mutableListOf<String>()
        listAround.clear()
            listAround.add("${x-1},${y+1}")
            listAround.add("${x-1},${y}")
            listAround.add("${x-1},${y-1}")
            listAround.add("${x},${y-1}")
            listAround.add("${x+1},${y-1}")
            listAround.add("${x+1},${y}")
            listAround.add("${x+1},${y+1}")
            listAround.add("${x},${y+1}")
        listAround.removeIf { it.split(",").contains("-1") }
        listAround.removeIf { it.split(",").contains("9") }
      //  listAround.removeIf { it.toString() == "/" }

       println(listAround)

        for (i in listAround.indices) {
            var (r, c) = listAround[i].split(",")

            if (field.isDigit(r.toInt(), c.toInt())) {
                fieldFalse[r.toInt()][c.toInt()] = field[r.toInt()][c.toInt()]
            }
            if (field[r.toInt()][c.toInt()] == ".") {
                // fieldFalse[r.toInt()][c.toInt()] = "/"
                    openEmpty(x,y)

            }
        }

        printField(fieldFalse)
    }


 */



 */
