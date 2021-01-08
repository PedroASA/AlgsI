#include <iostream>
#include <vector>
#include <algorithm>
#include <cassert>

using namespace std;

using Numbers = vector<int>;

Numbers merge(Numbers& v1, Numbers& v2) {
    Numbers res {};
    Numbers::iterator it1 = v1.begin(), it2 = v2.begin();

    //cout << res.size() << endl;
    while(it1 != v1.end() && it2 != v2.end()) {
        if(*it1 < *it2) {
            res.push_back(*it1);
            it1++;
        } 
        else { 
            res.push_back(*it2);
            it2++;
        }
    }
    if(it1 != v1.end()) {
        for(;it1 != v1.end(); it1++) {
            res.push_back(*it1);
        }
    }
    else {
        for(;it2 != v2.end(); it2++) {
           res.push_back(*it2);
        }
    }
    return res;


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
    
    Numbers a = { 3, 1, 3, 5, 6, 1, 2, 7, 3, 2};
    Numbers b = { 3, 5, 3, 1, 3, 7, 2, 2, 6, 1};

    assert(a.size() == b.size()); 

    auto sorted_a = merge_sort(a.begin(), a.end());
    auto sorted_b = merge_sort(b.begin(), b.end());

    bool permutation = true;
    for(int i=0; i< a.size(); i++) {
        if(sorted_a[i] != sorted_b[i]) {
            permutation = false;
            break;
        }
    }
    cout << (permutation ? "Permutation!" : "Not a permutation!") << endl;
    
    return 0;
}