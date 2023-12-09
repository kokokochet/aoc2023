import kotlin.collections.ArrayList

fun main() {
    fun part1(input: List<String>): Long {
        var sm = 0L
        for (line in input) {
            val numbers = line.split(" ").map { it.toLong() }.toLongArray()
            var diffs = LongArray(numbers.size)
            val newDiffs = LongArray(numbers.size)
            for (i in numbers.indices) {
                diffs[i] = numbers[i]
            }
            val prevs = ArrayList<Long>()
            prevs.add(numbers.last())
            var last_diff = true
            var cnt = 1
            while (last_diff) {
                last_diff = false
                for (i in cnt..<diffs.size) {
                    newDiffs[i] = diffs[i] - diffs[i - 1]
                    last_diff = last_diff || (diffs[i] != 0L)
                }
                diffs = newDiffs.clone()
                prevs.add(diffs.last())
                cnt++
            }
            sm += prevs.fold(0L) { acc, x -> acc + x }
        }
        return sm
    }

    fun part2(input: List<String>): Long {
        var sm = 0L
        for (line in input) {
            val numbers = line.split(" ").map { it.toLong() }.toLongArray()
            var diffs = LongArray(numbers.size)
            val newDiffs = LongArray(numbers.size)
            for (i in numbers.indices) {
                diffs[i] = numbers[i]
            }
            val prevs = ArrayList<Long>()
            prevs.add(numbers[0])
            var last_diff = true
            var cnt = 1
            while (last_diff) {
                last_diff = false
                for (i in cnt..<diffs.size) {
                    newDiffs[i] = diffs[i] - diffs[i - 1]
                    last_diff = last_diff || (newDiffs[i] != 0L)
                }
                diffs = newDiffs.clone()
                prevs.add(diffs[cnt])
                cnt++
            }
            sm += prevs.dropLast(1).foldRight(0L) { x, acc ->
                x - acc
            }
        }
        return sm
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
