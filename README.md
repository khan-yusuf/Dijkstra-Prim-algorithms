# Dijkstra-Prim-algorithms

## Classes

### Student
Keeps track of int minCost (the minimum cost needed to connect a student to a central node) and
int name (the numerical id of the student). Also contains ArrayList<Student> neighbors
(list of students that are connected to this student via a single wire) and ArrayList<Integer>
prices (price of wires between this student and his/her neighbors).

### Heap
Has all the properties of a min heap. This class functions independently; the
methods have been tested separately.

### Program2 
The class with Dijkstra's and Prim's algorithm implemented.

### Driver
Reads file input and populates ArrayList students. testRun() is used for testing
purposes.
  
## Heap Methods
  
### void buildHeap(ArrayList<Student> students)
Given an ArrayList of students, builds a minimum heap based on each student’s minCost in O(n log(n)) time.

### void insertNode(Student in)
Inserts Student in heap in O(log(n)) time.

### Student findMin()
Return minimum in O(1) time.
  
### Student extractMin()
Returns minimum and deletes it from heap in O(log(n)) time.

### void delete(int index)
Deletes the Student at int index in O(log(n)) time.

### void changeKey(Student s, int newCost)
Changes minCost of Student s to newCost and update the heap in O(log(n)) time.
  
