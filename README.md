## Combinations of Non-repeating Elements in a Pleasing Order

We can list combinations[1] of non-repeating elements, either all
possible combinations or a subset. This algorithm lists such
combinations in a pleasing order rather than in a machine
order. Suppose, for example, we wish to list all possible combinations of the
elements *A*, *B*, *C* and *D*. A person would likely list
the combinations in this order:

| combination |
|:-----------:|
|``` - - - - ```|
|``` A - - - ```|
|``` - B - - ```|
|``` - - C - ```|
|``` - - - D ```|
|``` A B - - ```|
|``` A - C - ```|
|``` A - - D ```|
|``` - B C - ```|
|``` - B - D ```|
|``` - - C D ```|
|``` A B C - ```|
|``` A B - D ```|
|``` A - C D ```|
|``` - B C D ```|
|``` A B C D ```|


#### Machine Ordering
The algorithm that lists combinations in a machine order involves
forming an integer where each bit-position indicates the presence or
absence of one of the elements in the set. When the integer is
incremented, the bit pattern at each step represents one unique
combination.

Again, suppose we wish to list all possible combinations of the
elements *A*, *B*, *C* and *D*. In this example, the number of
elements *n* is four. We form an integer *i* that has *n*
bit-positions. The lowest-order bit in position zero indicates the
presence of element *A*, position one the presence of element *B*,
position two the presence of *C* and position three the presence of
*D*. The total number of possible combinations[2] including the empty or
null combination is *2^n*, in this example 16. We increment *i* from
zero until 16 yielding the following bit patterns and combinations:

| *i* | bits | combination |
|--:|-----:|:-----------:|
| 0 | ```0000``` | ```- - - - ```|
| 1 | ```0001``` | ```A - - - ```|
| 2 | ```0010``` | ```- B - - ```|
| 3 | ```0011``` | ```A B - - ```|
| 4 | ```0100``` | ```- - C - ```|
| 5 | ```0101``` | ```A - C - ```|
| 6 | ```0110``` | ```- B C - ```|
| 7 | ```0111``` | ```A B C - ```|
| 8 | ```1000``` | ```- - - D ```|
| 9 | ```1001``` | ```A - - D ```|
|10 | ```1010``` | ```- B - D ```|
|11 | ```1011``` | ```A B - D ```|
|12 | ```1100``` | ```- - C D ```|
|13 | ```1101``` | ```A - C D ```|
|14 | ```1110``` | ```- B C D ```|
|15 | ```1111``` | ```A B C D ```|

This is a fast and foolproof method for listing all possible
combinations of a set of non-repeating elements. It can also be
modified to list subsets of combinations. For example, it can be used
to list all combinations containing a specified number *m* of the
elements from the set of *n* available elements. This is done by
counting the number of set bits at each iteration and keeping only
those iterations where *m* bits are set.

We can illustrate by extending the previous example. Suppose we wish
to list all combinations that contain two elements of the set *A*, *B*, *C*
and *D*. In this example *m=2* and *n=4*. We iterate *i* as before but this
time we keep only those iterations where the number of set bits equals
two:

| *i* | bits | combination |
|--:|-----:|:-----------:|
| 3 | ```0011``` | ```A B - - ```|
| 5 | ```0101``` | ```A - C - ```|
| 6 | ```0110``` | ```- B C - ```|
| 9 | ```1001``` | ```A - - D ```|
|10 | ```1010``` | ```- B - D ```|
|12 | ```1100``` | ```- - C D ```|

The problem with this method is that combinations are listed in a
machine order and not in an order that is easy for people to use.
And, as the number of elements in the set increases the disparity
between machine and pleasing ordering becomes more pronounced.

#### Pleasing Ordering
This algorithm makes several changes to the machine order algorithm
described above. First, it exploits the fact that when an integer is
incremented or decremented the less significant bits change more
rapidly than the more significant bits. Second, the integer that is
incremented in the machine order algorithm is instead decremented and
the orders of the set of elements and the combinations formed from
them are reversed. Finally, subsets are listed in order from a subset
containing zero elements to one containing all the elements. Suppose
we wish to list all possible combinations of the elements *A*, *B*,
*C* and *D*. This algorithm would list the combinations in this desired order:

| *i* | bits | combination |
|--:|-----:|:-----------:|
| 0 | ```0000``` | ```- - - -``` |
| 8 | ```1000``` | ```A - - -``` |
| 4 | ```0100``` | ```- B - -``` |
| 2 | ```0010``` | ```- - C -``` |
| 1 | ```0001``` | ```- - - D``` |
|12 | ```1100``` | ```A B - -``` |
|10 | ```1010``` | ```A - C -``` |
| 9 | ```1001``` | ```A - - D``` |
| 6 | ```0110``` | ```- B C -``` |
| 5 | ```0101``` | ```- B - D``` |
| 3 | ```0011``` | ```- - C D``` |
|14 | ```1110``` | ```A B C -``` |
|13 | ```1101``` | ```A B - D``` |
|11 | ```1011``` | ```A - C D``` |
| 7 | ```0111``` | ```- B C D``` |
|15 | ```1111``` | ```A B C D``` |


[1] Combinations in the strict mathematical sense, not
permutations. For example see: Jeffrey, Alan; page 59; "Handbook of
Mathematical Formulas and Integrals"; 1995; Academic Press, Inc.

[2] From a theory of combinatorics, ibid.
