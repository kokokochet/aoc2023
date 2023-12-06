import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Long {
        val map = Array(8) { LongArray(0) }
        map[0] = input[0].substringAfter("seeds: ").split(" ").map { it.toLong() }.toLongArray()
        var splitCnt = 0
        for (line in input.subList(1, input.size)) {
            if (line.isEmpty()) {
                continue
            }
            if (line.contains("map")) {
                if (splitCnt > 0) map[splitCnt] = map[splitCnt].mapIndexed { index, x ->
                    if (x < 0) map[splitCnt - 1][index] else x
                }.toLongArray()
                map[++splitCnt] = LongArray(map[0].size) { -1 }
                continue
            }

            val (to, from, sz) = line.split(" ").map { it.toLong() }
            for ((i, seed) in map[splitCnt - 1].withIndex()) {
                if (seed in from..<(from + sz)) {
                    map[splitCnt][i] = to + (seed - from)
                }
            }
        }
        return map.last().mapIndexed { index, x -> if (x < 0) map[splitCnt - 1][index] else x }.min()
    }

    fun part2(input: List<String>): Long {
        val seeds = input[0].substringAfter("seeds: ").split(" ").map { it.toLong() }.toLongArray()
        val segments = ArrayList<Pair<Long, Long>>()
        for (i in 0..seeds.lastIndex step 2) {
            segments.add(seeds[i] to seeds[i] + seeds[i + 1])
        }
        var mapSegments = ArrayList<Triple<Long, Long, Long>>()
        for (line in input.subList(3, input.size)) {
            if (line.isEmpty()) {
                continue
            }
            if (line.contains("map")) {
                val newSegments = ArrayList<Pair<Long, Long>>()
                for ((start, end, diff) in mapSegments) {
                    var i = 0
                    while (i < segments.size) {
                        val (left, right) = segments[i]
                        val commonL = max(left, start)
                        val commonR = min(right, end)
                        if (commonL > commonR) {
                            i++
                            continue
                        }
                        newSegments.add(commonL + diff to commonR + diff)
                        segments.removeAt(i)
                        if (commonL - 1 >= left) {
                            segments.add(left to commonL - 1)
                        }
                        if (right >= commonR + 1) {
                            segments.add(commonR + 1 to right)
                        }
                    }
                }
                segments.addAll(newSegments)
                mapSegments = ArrayList()
                continue
            }
            val (dest, from, sz) = line.split(" ").map { it.toLong() }
            mapSegments.add(Triple(from, from + sz, dest - from))
        }

        val newSegments = ArrayList<Pair<Long, Long>>()
        for ((start, end, diff) in mapSegments) {
            var i = 0
            while (i < segments.size) {
                val (left, right) = segments[i]
                val commonL = max(left, start)
                val commonR = min(right, end)
                if (commonL > commonR) {
                    i++
                    continue
                }
                newSegments.add(commonL + diff to commonR + diff)
                segments.removeAt(i)
                if (commonL - 1 >= left) {
                    segments.add(left to commonL - 1)
                }
                if (right >= commonR + 1) {
                    segments.add(commonR + 1 to right)
                }
            }
        }
        segments.addAll(newSegments)
        return min(segments.minBy { it.second }.second, segments.minBy { it.first }.first)
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    //check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
// 3763466 -- too low
// 12634633 -- NO
// 216039041 -- too big
// 4206359309 -- ??????
// 21603904 -- too high
// 7526932 -- no