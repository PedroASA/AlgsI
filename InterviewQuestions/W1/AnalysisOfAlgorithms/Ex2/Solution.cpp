class Solution {
public:
    
/*

The idea behind the proof is that any binary search algorithm for bitônic arrays would demand that either array[left] or array[right] (possibly array[mid-1] or array[mid+1] I suppose) be accessed in order to determine in which half of the array the target is or should be.

The addition of this access changes the recurrence relation of the binary search algorithm 
from:
 T(n) = T(n/2) + 1 (one array access in normal binary search) 
to:  
 T(n) = T(n/2) + 2 (two array accesses)
Due to that change the algorithm goes from 1lg(N) to 2lg(N).

*/
    
    int search(vector<int>& array, int target) {
        
        int size = array.size(),left =0, right = size, mid, arr_mid, arr_left;
        
        if(array[0] == target) return 0;
        if(right <= 1 ) return -1;
        
        while(left < right) {
            
            mid =  left + (right - left) / 2;
            arr_mid = array[mid];
            arr_left = array[left];
            
            //target in (mid, left) or target not in [left, mid]
            if((arr_mid < arr_left
                && target < arr_left
                && target > arr_mid) 
               || (arr_mid >= arr_left
                   && (target > arr_mid 
                       || target < arr_left))) 
                
                left = mid + 1;
            
            else right = mid;

        }
        if(left < size && array[left] == target) return left;

        return -1;
    }
};
