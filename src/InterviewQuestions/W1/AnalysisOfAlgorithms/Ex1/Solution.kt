class Solution { 
    
    var array:IntArray = intArrayOf()
    
    
    tailrec fun bin_search(x:Int, left:Int = x+1, right:Int = array.size - 1, acc: List<List<Int>> = listOf<List<Int>>()): List<List<Int>> =
        if(left < right)
            (array[x] + array[left] + array[right]).let {
                when {
                    it == 0 -> bin_search(x, left + 1, right - 1, acc + listOf<List<Int>>(listOf<Int>(array[x], array[left], array[right])))
                    it > 0 -> bin_search(x, left, right - 1, acc)
                    else -> bin_search(x, left+1, right, acc)
                }
            }
        else 
            acc
    
    fun threeSum(a:IntArray): List<List<Int>> = 
        when {
            a.size < 3 -> listOf<List<Int>>()
            a.size == 3 -> if( a[0] + a[1] + a[2]  == 0 ) 
            listOf<List<Int>>(listOf<Int>(a[0], a[1], a[2])) 
            else  listOf<List<Int>>()
            else -> helper(a)
        }
            
    
    fun helper(a:IntArray): List<List<Int>>{
        this.array = a
        array.sort()
        val res = mutableSetOf<List<Int>>()
        (0 until array.size)
            .map { 
                bin_search(it)
                .let {
                    if(!it.isEmpty()) res += (it)    
                }
            }
        return res.toList()
    }
}
