### Union-find with specific canonical element
<hr>

 Add a method _find()_ to the union-find data type so that _find(**i**)_ returns the largest element in the connected component containing __i__. The operations, _union()_, _connected()_, and _find()_ should all take logarithmic time or better.

For example, if one of the connected components is **{1,2,6,9}**, then the _find()_ method should return **9** for each of the four elements in the connected components.
