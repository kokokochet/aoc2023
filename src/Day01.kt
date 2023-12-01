fun main() {
    fun part1(input: List<String>): Long {
        return input.sumOf {
            (it.find { ch -> ch.isDigit() }!! + "" + it.findLast { ch -> ch.isDigit() }).toLong()
        }
    }

    fun decode(line: String) = line.replace("one", "one1one").replace("two", "two2two").replace("three", "three3three")
        .replace("four", "four4four").replace("five", "five5five").replace("six", "six6six")
        .replace("seven", "seven7seven").replace("eight", "eight8eight").replace("nine", "nine9nine")

    fun part2(input: List<String>) = part1(input.map { decode(it) })

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142L)

    val test2Input = readInput("Day01p2_test")
    check(part2(test2Input) == (281L + 28 + 79))

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
