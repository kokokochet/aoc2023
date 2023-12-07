fun main() {
    val cards = listOf(
        'A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J'
    )

    fun getPower(h: String) = cards.minOfOrNull { c ->
        val cnt = h.map {
            if (it == 'J') cards.indexOf(c)
            else cards.indexOf(it)
        }.groupingBy { it }.eachCount().toList().sortedByDescending { it.second }

        val res = when {
            cnt.size == 1 -> 1
            cnt.size == 2 && cnt[0].second == 4 -> 2
            cnt.size == 2 && cnt[0].second == 3 -> 3
            cnt.size == 3 && cnt[0].second == 3 -> 4
            cnt.size == 3 && cnt[0].second == 2 && cnt[1].second == 2 -> 5
            cnt.size == 4 && cnt[0].second == 2 -> 6
            else -> 7
        }
        res
    }!!

    fun part1(input: List<String>) = input.map {
        val (hand, bid) = it.split(" ")
        val power = getPower(hand)
        power to (hand.map { c -> cards.indexOf(c) } to bid.toLong())
    }.sortedWith { a, b ->
        if (a.first < b.first) return@sortedWith 1
        if (a.first > b.first) return@sortedWith -1
        for (i in a.second.first.indices) {
            if (a.second.first[i] < b.second.first[i]) return@sortedWith 1
            if (a.second.first[i] > b.second.first[i]) return@sortedWith -1
        }
        return@sortedWith 0
    }.withIndex().sumOf { (i, a) ->
        (i + 1) * a.second.second
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 5905L)

    val input = readInput("Day07")
    part1(input).println()
}
