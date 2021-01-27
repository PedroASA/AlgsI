#include <iostream>
#include <vector>
#include <algorithm>
#include <cassert>
#include <set>

using Nut = int;
using Bolt = int;

using namespace std;

class Sort {

    private:

    vector<Nut> nuts;
    vector<Bolt> bolts;

    vector<Bolt>::iterator pivot = bolts.begin();

    int fit(Nut n, Bolt b) {
        return n - b;
    }

    match() {
        for(auto nut = nuts.begin(), bolt = bolts.begin(); nut != nuts.end() && bolt != bolts.end(); nut++, bolt++)
            if(fit(*nut, *bolt) != 0) 
                return false;
        return true;
    }

    auto partition (const vector<Nut>::iterator lo, const vector<Nut>::iterator hi, const vector<Nut>::iterator pivot) { 

        auto i = lo, j = hi + 1;

        while (true) { 

            while (fit(*(++i), *pivot) < 0) 
                if (i == hi) break;
            

            while (fit(*(--j), *pivot) >= 0) 
                if (j == lo) break; 
            

            if (i >= j) break;

            swap(*i, *j);
        }

        swap(*lo, *j);

        return j;
    } 
    

    void quickSort(const vector<Nut>::iterator lo, const vector<Nut>::iterator hi) { 
        
        if (lo < hi) { 

            auto pivot = partition(lo, hi, bolts.begin() + (hi - nuts.begin())); 
            quickSort(lo, pivot - 1); 
            quickSort(pivot + 1, hi); 
        } 
    } 

    public:

    Sort(initializer_list<int> nuts, initializer_list<int> bolts) {
        this->nuts = vector<Nut>(nuts.begin(), nuts.end());
        this->bolts = vector<Bolt>(bolts.begin(), bolts.end());

        quickSort(this->nuts.begin(), this->nuts.end()-1);

        for(auto t : this->nuts) cout << t << "\t";
        cout << endl;
        for(auto t : this->bolts) cout << t << "\t";
        cout << endl;

        //assert(match());
    }
};

int main() {
    Sort s = {  {5, 4, 3, 2, 1}, {4, 5, 3, 2, 1} };
    
}