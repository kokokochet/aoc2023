fun main() {
    fun part1(input: List<String>): Int {
        val bag = mapOf(
            "red" to 12, "green" to 13, "blue" to 14
        )

        return input.map { line ->
            val (id, colorsSets) = line.substring(5)
                .split(":")
                .map { it.trim() }
            id to colorsSets.split(";").map { set ->
                set.split(",").map { cl ->
                    val pr = cl.trim().split(" ")
                    pr[1].trim() to pr[0].toInt()
                }.all { bag[it.first]!! >= it.second }
            }.all { it }
        }.filter { it.second }.sumOf { it.first.toInt() }
    }

    fun part2(input: List<String>) = input.map { line ->
        val (id, colorsSets) = line.substring(5).split(":").map { it.trim() }
        id to colorsSets.split(";").flatMap { set ->
            set.split(",").map { cl ->
                val pr = cl.trim().split(" ")
                pr[1].trim() to pr[0].toInt()
            }
        }.groupBy { it.first }.map {
            it.value.maxBy { pr -> pr.second }.second
        }.fold(1L) { acc, cur ->
            acc * cur
        }
    }.sumOf { it.second }


    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    check(part2(testInput) == 2286L)
    part1(input).println()
    part2(input).println()
}
