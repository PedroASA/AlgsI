public class Ex3 {
    private class UFCanElem(private val n: Int) {
        private val size: IntArray
        private val contained: BooleanArray
        private val succ: IntArray

        fun remove(x: Int) {
            succ[x - 1] = succ[x];
        }

        fun successor(x: Int): Int =
                if (contained[succ[x]])
                    succ[x];
                else
                    succesor(succ[x]])


        init {
            size = IntArray(n)
            contained = Array<Boolean>.init(size) { true }
            succ = Array<Int>.init(size) { it }
        }
    }

}
