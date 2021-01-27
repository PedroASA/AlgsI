#include <iostream>
#include <vector>
#include <algorithm>
#include <cassert>

using namespace std;

using Numbers = vector<int>;

inline bool is_sorted(Numbers::iterator begin, Numbers::iterator end) {

    for(;begin != end - 1; begin++) {
        if(*begin > *(begin + 1)) return false;
    }
    return true;
}

inline bool is_equal(Numbers nums1, Numbers nums2) {
    
    assert(nums1.size() == nums2.size());

    stable_sort(nums1.begin(), nums1.end());
    for(auto it1 =nums1.begin(), it2 = nums2.begin(); it1 != nums1.end() && it2 != nums2.end(); it1++, it2++) {
        if(*it1 != *it2) return false;
    }
    return true;
}


void merge( Numbers::iterator it1, Numbers::iterator it2, Numbers::iterator it3) {
    

    Numbers aux = Numbers(it1, it2);
    auto it_aux = aux.begin(), mid = it2;

    while(it1 < mid) 

        if((it_aux < aux.end()  && *it_aux < *it2) || it2 == it3)

            *it1++ = *it_aux++;

        else 
            *it1++ = *it2++;

    
    while(it1 < it3) 

        if((it_aux < aux.end()  && *it_aux < *it2) || it2 == it3)

            *it1++ = *it_aux++;

        else 
            *it1++ = *it2++;
    

}


void merge_sort( const Numbers::iterator begin, const Numbers::iterator end) {
    if(begin + 1 < end) {
        auto mid = begin + (end - begin)/ 2;
        merge_sort(begin, mid);
        merge_sort(mid, end);
        merge(begin, mid, end);
    }

}

int main() {
    Numbers a {9, 2, 3, 10, 9, 8, 5, 3, 2, 3, 2, 1, 0, 9, 5, 5, 5, 9, 3, 4, 2, 2, 8, 6, 6};
    auto cp = Numbers(a.begin(), a.end());

    merge_sort(a.begin(), a.end());

    for(auto& x : a ) cout << x << "\t";

    assert(is_sorted(a.begin(), a.end()));
    assert(is_equal(cp, a));
}