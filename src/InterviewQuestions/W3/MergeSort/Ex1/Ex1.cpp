#include <iostream>
#include <vector>
#include <algorithm>
#include <cassert>

using namespace std;

using Numbers = vector<int>;

inline bool is_sorted(Numbers::iterator begin, Numbers::iterator end) {
    for(;begin != end - 1; begin++) {
        if(*begin > *(begin + 1)) return false;
        //cout << *begin << "\t";
    }
    cout << endl; 
    return true;
}

void merge( Numbers::iterator it1, Numbers::iterator it2, Numbers::iterator it3) {
    
    assert(is_sorted(it1, it2));
    assert(is_sorted(it2, it3));

    Numbers v = Numbers(it1, it2);
    for(auto t : v) cout << t << "\t";
    cout << endl;
    auto it_aux = v.begin(), mid = it2;

    while(it1 < mid) {
        if(it_aux < v.end()  && *it_aux < *it2) {
            *it1 = *it_aux;
            it1++;it_aux++;
        } 
        else { 
            *it1 = *it2;
            it2++;it1++;
        }
    }
    while(it1 < it3) {
        if(it_aux < v.end()  && *it_aux < *it2) {
            *it1 = *it_aux;
            it1++;it_aux++;
        } 
        else { 
            *it2 = *it1;
            it1++;it2++;
        }
    }

}


void merge_sort( const Numbers::iterator begin, const Numbers::iterator end) {
    if(begin + 1 < end) {
        auto t1 = end - begin;
        auto mid = begin + (end - begin)/ 2;
        merge_sort(begin, mid);
        merge_sort(mid, end);
        merge(begin, mid, end);
        assert(t1 == end - begin);
    }

}

int main() {
    Numbers a {9, 2, 3, 10, 9, 8, 5, 3, 2, 3, 2, 1, 0, 9, 5, 5, 5, 9, 3, 4, 2, 2, 8, 6, 6};
    merge_sort(a.begin(), a.end());
    for(auto& x : a ) cout << x << "\t";
}