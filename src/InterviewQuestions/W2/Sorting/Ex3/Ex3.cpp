#include <iostream>
#include <vector>
#include <algorithm>
#include <cassert>
//#include <numeric>

using namespace std;

struct Array {

    
    vector<int> v;

    Array (initializer_list<int> list) {

        v = list;
    }

    void swap(int i, int j) {
        auto tmp = v[i];
        v[i] = v[j];
        v[j] = tmp;
    }

    int color(int index) {
        return v[index];
    }

   
    void sort() {

    }
};

int main() {

    Array arr { 1, 2, 0, 2, 0, 1, 0, 2, 0, 1, 1, 1, 0, 0, 2, 2, 0, 1, 2, 0, 2 };
    arr.sort();
    for(auto& t: arr.v) cout << t << "\t";
}

