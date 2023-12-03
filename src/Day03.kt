fun main() {
    val moves = listOf(
        -1 to -1, -1 to 0, -1 to 1, 0 to 1, 0 to -1, 1 to 1, 1 to 0, 1 to -1
    )

    fun isSymbolClear(c: Char) = !c.isDigit() && c != '.'
    fun step(inp: List<String>, x: Int, y: Int) =
        x >= 0 && y >= 0 && x < inp[0].length && y < inp.size && isSymbolClear(inp[x][y])

    fun checkNb(inp: List<String>, curX: Int, curY: Int) = moves.any {
        step(inp, curX + it.first, curY + it.second)
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for ((ind, line) in input.withIndex()) {
            var i = 0
            var prev_dig = -1
            var cur_state = false
            while (i < line.length) {
                if (!line[i].isDigit()) {
                    i++
                    if (prev_dig >= 0 && cur_state) {
                        sum += line.substring(prev_dig, i - 1).toInt()
                    }
                    prev_dig = -1
                    cur_state = false
                    continue
                }
                if (prev_dig < 0) {
                    prev_dig = i
                }
                cur_state = cur_state || checkNb(input, ind, i)
                i++
            }
            if (prev_dig >= 0 && cur_state) {
                sum += line.substring(prev_dig, i).toInt()
            }
        }
        return sum
    }
    fun step2(inp: List<String>, x: Int, y: Int) =
        x >= 0 && y >= 0 && x < inp[0].length && y < inp.size && inp[x][y].isDigit()

    fun getAllNB(inp: List<String>, curX: Int, curY: Int) = moves.mapNotNull {
        if (step2(inp, curX + it.first, curY + it.second)) (curX + it.first) to (curY + it.second)
        else null
    }

    fun part2(input: List<String>): Long {
        var sum = 0L
        for ((ind, line) in input.withIndex()) {
            var i = 0
            while (i < line.length) {
                if (line[i] == '*') {
                    val nbs = getAllNB(input, ind, i).map { digit ->
                        val start = input[digit.first]
                            .substring(0, digit.second)
                            .withIndex()
                            .findLast { !it.value.isDigit() }
                            ?.index?:-1
                        var end = input[digit.first]
                            .substring(digit.second)
                            .withIndex()
                            .find { !it.value.isDigit() }
                            ?.index
                        if (end == null) {
                            end = input[digit.first].length
                        } else {
                            end += digit.second
                        }
                        input[digit.first].substring(start + 1, end) to start
                    }.toHashSet()
                    if (nbs.size != 2) {
                        i++
                        continue
                    }
                    sum += nbs.fold(1L) { a, b -> a * b.first.toInt() }
                }
                i++
            }
        }
        return sum
    }

    val input = readInput("Day03")


    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835L)
    part1(input).println()
    part2(input).println()
}
