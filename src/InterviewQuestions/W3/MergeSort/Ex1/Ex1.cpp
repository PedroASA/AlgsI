#include <iostream>
#include <vector>
#include <algorithm>
#include <cassert>

using namespace std;

using Numbers = vector<int>;

Numbers merge(Numbers& v1, Numbers& v2) {

    Numbers aux = Numbers(v1.begin(), v1.end());
    Numbers::iterator it1 = v1.begin(), it2 = v2.begin(), it_aux = aux.begin();

    while(it1 != v1.end()) {
        if(*it_aux < *it2) {
            *it1 = *it_aux;
            it1++;it_aux++;
        } 
        else { 
            *it1 = *it2;
            it2++;it1++;
        }
    }
    while(it2 != v2.end()) {
        if(*it_aux < *it1) {
            *it2 = *it_aux;
            it2++;it_aux++;
        } 
        else { 
            *it2 = *it1;
            it2++;it1++;
        }
    }
    return Numbers(v1.begin(), v2.end());


}


Numbers merge_sort(Numbers::iterator begin, Numbers::iterator end) {
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
    Numbers a {9, 2, 3, 10, 9, 8, 5, 3, 2, 3, 2, 1, 0, 9, 5, 5, 5, 9, 3, 4, 2, 2, 8, 6, 6};
    for(auto i = ) cout << x << "\t";
}