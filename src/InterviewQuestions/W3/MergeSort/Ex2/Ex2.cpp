#include <iostream>
#include <vector>
#include <algorithm>
#include <cassert>

using namespace std;

using Numbers = vector<int>;

class Count {

  private:

    size_t cnt = 0;

    Numbers merge(Numbers&, Numbers&);
  
  public:

    size_t counter () {
        auto tmp = this->cnt;
        this->cnt = 0;
        return tmp;
    }

    Numbers merge_sort(Numbers::iterator, Numbers::iterator);

};

Numbers Count::merge(Numbers& v1, Numbers& v2) {
    Numbers res {};
    Numbers::iterator it1 = v1.begin(), it2 = v2.begin();

    while(it1 != v1.end() && it2 != v2.end()) 

        if(*it1 < *it2) 
            res.push_back(*it1++);
        
        else {
            res.push_back(*it2++);
            cnt++;
        }
        
    if(it1 != v1.end()) 

        for(;it1 != v1.end(); it1++) 
            res.push_back(*it1);
        
    
    else 
        for(;it2 != v2.end(); it2++) 
            res.push_back(*it2);
    
    return res;


}

    
Numbers Count::merge_sort(Numbers::iterator begin, Numbers::iterator end) {
    
    if(begin + 1 < end) {

        auto mid = begin + (end - begin)/ 2;
        auto nums1 = merge_sort(begin, mid);
        auto nums2 = merge_sort(mid, end);
        return merge(nums1, nums2);
    }

    if(begin != end) 
        return Numbers { *begin };
    
    return Numbers {};
}

int main() {

    Numbers three_inversions {
        1, 2, 5, 4, 3
        
        /* Inversions: 
        - 5 and 4;
        - 5 and 3;
        - 4 and 3.
    */
    };

    Numbers twelve_inversions {
        1, 2, 5, 4, 3, 8, 7, 6, 5, 5 
    };

    Count cnt;

    cnt.merge_sort(three_inversions.begin(), three_inversions.end());
    assert(cnt.counter() == 3);

    cnt.merge_sort(twelve_inversions.begin(), twelve_inversions.end());
    assert(cnt.counter() == 12);
}