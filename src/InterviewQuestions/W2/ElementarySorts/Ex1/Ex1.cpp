#include <iostream>
#include <vector>
#include <cassert>
#include <algorithm>

using namespace std;

using Point = pair<float, float>;

using Points = vector<Point>;

void shell_sort(Points& points) {
    
    int h = 1, size = points.size();
    while(h < size/3)
        h = 3*h + 1; 

    auto swap = [&points] (int p1, int p2) {
        auto tmp = points[p1];
        points[p1] = points[p2];
        points[p2] = tmp;
    };

    while(h > 0) {
        for(int i=h; i<size; i++) {
            for(int j = i; j > 0 && points[j-h] > points[j]; j -= h) 
                swap(j - h, j);
        }
        h /= 3;
    }
}

inline float random() {
    float t = static_cast <float> (rand()) / static_cast <float> (RAND_MAX);
    return (int) (t * 10) / 10.0 ;
}

inline void put(Points& vec) {
    for(int i =0; i< 100; i++) {
        vec.push_back(make_pair<float, float>(random(), random()));
    }

}


int main() {

	Points a;
    Points b;

       
    put(a);
    put(b);    

    shell_sort(a);
    shell_sort(b);

    assert(b.size() == a.size());
    
    int i = 0, cnt = i, j = i, size = a.size();
    while(i < size && j < size) {
        if(a[i] == b[j]) {
            cnt++;
            i++;
            j++;
        }
        else if(a[i] > a[j]) j++;
        else i++;
    }
    if(i != size) {
        for(;i < size; i++) {
            if(a[i] == b[size - 1])
                cnt++;
        }
    }
    else {
        for(;j < size; j++) {
            if(a[size - 1] == b[j])
                cnt++;
        }
    }

    cout <<"Number of equal points: " << cnt << endl;

	return 0;
}