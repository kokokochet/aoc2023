fun main() {
    fun parseInput(input: List<String>) =
        input[0].substringAfter("Time:")
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { it.toLong() } zip input [1]
                .substringAfter("Distance:")
                .split(" ")
                .filter { it.isNotEmpty() }
                .map { it.toLong() }

    fun parseInput2(input: List<String>) = listOf(
        input[0].substringAfter("Time:")
            .split(" ")
            .filter { it.isNotEmpty() }
            .fold("") { pr, cr ->
                pr + cr.trim()
            }.toLong() to input[1]
                .substringAfter("Distance:")
                .split(" ")
                .filter { it.isNotEmpty() }
                .fold("") { pr, cr ->
                    pr + cr.trim()
                }.toLong()
    )

    fun part1(input: List<Pair<Long, Long>>) = input.fold(1L) { acc, pr ->
        (1 ..< pr.first).fold(0L) { cnt, hold ->
            val dist = (pr.first - hold) * hold
            if (dist > pr.second) cnt + 1
            else cnt
        } * acc
    }

    val testInput = readInput("Day06_test")
    check(part1(parseInput(testInput)) == 288L)
    check(part1(parseInput2(testInput)) == 71503L)

    val input = readInput("Day06")
    part1(parseInput(input)).println()
    part1(parseInput2(input)).println()
}
