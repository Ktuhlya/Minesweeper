fun main() {
    println("How many mines do you want on the field?")
    val field = " │123456789│\n—│—————————│\n1│.........│\n2│.........│\n3│.........│\n4│.........│\n5│.........│\n6│.........│\n7│.........│\n8│.........│\n9│.........│\n—│—————————│".toCharArray()
    val count = field.map { it.code - '.'.code }.toIntArray()
    fun show(message: String) = println("${field.joinToString("")}\n$message")
    fun neigh(i: Int) = (i - 14..i + 12 step 13).flatMap { it..it + 2 }
    fun openCell(i: Int) {
        field[i] = "/1234568"[count[i]]
        if (count[i] == 0) neigh(i).filter { field[it] in ".*" }.forEach(::openCell)
    }
    val mines = count.indices.filter { count[it] == 0 }.shuffled().take(readLine()!!.toInt())
    mines.flatMap(::neigh).forEach { count[it]++ }
    while (mines.size < field.count { it in ".*" }) {
        show("Set/unset mines marks or claim a cell as free:")
        val (i, s) = readLine()!!.let { it[0].code + it[2].code * 13 - 658 to it.substring(4) }
        if (s == "mine") field[i] = (field[i].code xor 4).toChar()
        else if (i in mines) {
            mines.forEach { field[it] = 'X' }
            return show("You stepped on a mine and failed!")
        } else openCell(i)
    }
    show("Congratulations! You found all the mines!")
}