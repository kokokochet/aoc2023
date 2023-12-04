import java.util.PriorityQueue

fun main() {
    fun part1(input: List<String>) = input.sumOf { line ->
        val (wins, cards) = line.split(":")[1].split("|").map { s -> s.trim().split(" ").filter { it.isNotEmpty() } }
        val winSet = wins.toHashSet()
        val onlyWin = cards.filter { winSet.contains(it) }
        if (onlyWin.isEmpty()) 0 else onlyWin.fold(1L) { acc, _ -> acc * 2 } / 2
    }

    fun part2(input: List<String>): Long {
        val cardCnt = HashMap<Int, Int>()
        val cardQueue = PriorityQueue<Int>()
        input.forEachIndexed { ind, line ->
            val (wins, cards) = line.split(":")[1].split("|").map { s -> s.trim().split(" ").filter { it.isNotEmpty() } }
            val winSet = wins.toHashSet()
            val onlyWin = cards.filter { winSet.contains(it) }
            cardCnt[ind + 1] = onlyWin.size
            cardQueue.add(ind + 1)
        }
        var res = 1L
        var cur = cardQueue.poll()
        while (!cardQueue.isEmpty()) {
            val curCnt = cardCnt[cur]!!
            for (i in 1 .. curCnt) {
                if (cur + i > input.size) {
                    break
                }
                cardQueue.add(cur + i)
            }
            res++
            cur = cardQueue.poll()
        }
        return res
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13L)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
