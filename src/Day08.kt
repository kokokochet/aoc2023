fun main() {
    fun part1(input: List<String>): Int {
        val steps = input[0]
        val gr = input.subList(2, input.size).associate {
            val src = it.substring(0, 3)
            val left = it.substring(7, 10)
            val right = it.substring(12, 15)
            src to (left to right)
        }
        var current = "AAA"
        var curStep = 0
        var cnt = 0
        while (current != "ZZZ") {
            cnt++
            current = if (steps[curStep] == 'L') {
                gr[current]!!.first
            } else {
                gr[current]!!.second
            }
            curStep = (curStep + 1) % steps.length
        }
        return cnt
    }

    fun part2(input: List<String>): Long {
        val steps = input[0]
        val gr = input.subList(2, input.size).associate {
            val src = it.substring(0, 3)
            val left = it.substring(7, 10)
            val right = it.substring(12, 15)
            src to (left to right)
        }
        val current = gr.entries.map { it.key }.filter { it.last() == 'A' }.toTypedArray()
        var curStep = 0
        val needSteps = current.map {
            var cnt = 0L
            var cur = it
            while (cur.last() != 'Z') {
                cnt++
                cur = if (steps[curStep] == 'L') {
                    gr[cur]!!.first
                } else {
                    gr[cur]!!.second
                }
                curStep = (curStep + 1) % steps.length
            }
            cnt
        }
        tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

        return needSteps.fold(1L) { acc, x -> acc * x / gcd(acc, x) }
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 6)
    check(part2(readInput("Day08_test2")) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
