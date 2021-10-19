/*
 * Name: Yusuf Khan
 * EID: yk7862
 */

// Implement your heap here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;
import java.util.Collections;

public class Heap {
    private ArrayList<Student> minHeap;

    public Heap() {
        minHeap = new ArrayList<Student>();
    }

    /**
     * buildHeap(ArrayList<Student> students)
     * Given an ArrayList of Students, build a min-heap keyed on each Student's minCost
     * Time Complexity - O(nlog(n)) or O(n)
     *
     * @param students ArrayList of Students
     */
    public void buildHeap(ArrayList<Student> students) {
        for (Student student : students) { //0-based indexing for heap
            insertNode(student);
        }
    }

    /**
     * heapifyDown(ArrayList<Student> heap, int i)
     * Swaps nodes down the graph to follow property of min Heaps:
     * Child Key value > Parent Key value
     *
     * @param heap ArrayList of Students
     * @param i index of node to heapify
     */
    private void heapifyDown(ArrayList<Student> heap, int i){
        int min = i;
        int left = 2 * i + 1; //left child index
        int right = 2 * i + 2; //right child index

        if(left < heap.size() && heap.get(left).getminCost() < heap.get(min).getminCost()) //left child has lower cost
            min = left;
        else if(left < heap.size() && heap.get(left).getminCost() == heap.get(min).getminCost()){ //equal cost so check name
            if(heap.get(left).getName() < heap.get(min).getName())
                min = left;
        }

        if(right < heap.size() && heap.get(right).getminCost() < heap.get(min).getminCost()) //right child has lower cost
            min = right;
        else if(right < heap.size() && heap.get(right).getminCost() == heap.get(min).getminCost()){ //equal cost so check name
            if(heap.get(right).getName() < heap.get(min).getName())
                min = right;
        }

        if(min != i){
            Collections.swap(heap, i, min); //smallest is not parent so swap them
            heapifyDown(heap, min); //recursive call
        }
    }

    /**
     * heapifyUp(ArrayList<Student> heap, int i)
     * Swaps nodes up the graph to follow property of min Heaps:
     * Child Key value > Parent Key value
     *
     * @param heap ArrayList of Students
     * @param i index of node to heapify
     */
    private void heapifyUp(ArrayList<Student> heap, int i){
        if(i > 0){
            int parent = (i - 1) / 2;
            if(heap.get(i).getminCost() < heap.get(parent).getminCost()){
                Collections.swap(heap, i, parent);
                heapifyUp(heap, parent);
            }
            else if(heap.get(i).getminCost() == heap.get(parent).getminCost()){
                if(heap.get(i).getName() < heap.get(parent).getName()){ //equal cost so check name
                    Collections.swap(heap, i, parent);
                    heapifyUp(heap, parent);
                }
            }
        }
    }

    /**
     * insertNode(Student in)
     * Insert a Student into the heap.
     * Time Complexity - O(log(n))
     *
     * @param in - the Student to insert.
     */
    public void insertNode(Student in) {
        minHeap.add(in);
        heapifyUp(minHeap, minHeap.size() - 1);
    }

    /**
     * findMin()
     * Time Complexity - O(1)
     *
     * @return the minimum element of the heap.
     */
    public Student findMin() {
        if(minHeap.isEmpty()){
            return null;
        }
        return minHeap.get(0);
    }

    /**
     * extractMin()
     * Time Complexity - O(log(n))
     *
     * @return the minimum element of the heap, AND removes the element from said heap.
     */
    public Student extractMin() {
        if(minHeap.isEmpty()){
            return null;
        }
        Student last = minHeap.get(minHeap.size() - 1);
        Student min = minHeap.set(0, last); //returns min Student at index 0 before replacing it with last
        minHeap.remove(minHeap.size() - 1);
        heapifyDown(minHeap, 0);
        return min;
    }

    /**
     * delete(int index)
     * Deletes an element in the min-heap given an index to delete at.
     * Time Complexity - O(log(n))
     *
     * @param index - the index of the item to be deleted in the min-heap.
     */
    public void delete(int index) {
        Student last = minHeap.get(minHeap.size() - 1);
        minHeap.set(index, last);
        minHeap.remove(minHeap.size() - 1);
        int parent = (index - 1) / 2;

        if(index == 0 || minHeap.get(parent).getminCost() < minHeap.get(index).getminCost()){
            heapifyDown(minHeap, index);
        }
        else if(minHeap.get(parent).getminCost() > minHeap.get(index).getminCost()){
            heapifyUp(minHeap, index);
        }
        else{
            if(minHeap.get(parent).getName() < minHeap.get(index).getName()){ //minCosts are equal
                heapifyDown(minHeap, index);
            }
            else
                heapifyUp(minHeap, index);
        }
    }

    /**
     * changeKey(Student r, int newCost)
     * Changes minCost of Student s to newCost and updates the heap.
     * Time Complexity - O(log(n))
     *
     * @param r       - the Student in the heap that needs to be updated.
     * @param newCost - the new cost of Student r in the heap (note that the heap is keyed on the values of minCost)
     */
    public void changeKey(Student r, int newCost) {
        int index = -1;
        for(int i = 0; i < minHeap.size(); i++){
            if(minHeap.get(i).getName() == r.getName()){ //Student to be updated with newCost
                index = i;
                break;
            }
        }
        if(index != -1){ //makes sure Student r is in Heap
            minHeap.get(index).setminCost(newCost);
            int parent = (index - 1) / 2;
            if(index == 0 || minHeap.get(parent).getminCost() < minHeap.get(index).getminCost()){
                heapifyDown(minHeap, index);
            }
            else if(minHeap.get(parent).getminCost() > minHeap.get(index).getminCost()){
                heapifyUp(minHeap, index);
            }
            else{
                if(minHeap.get(parent).getName() < minHeap.get(index).getName()){ //minCosts are equal
                    heapifyDown(minHeap, index);
                }
                else
                    heapifyUp(minHeap, index);
            }
        }
        else
            System.out.println("Student not found in heap");
    }

    public boolean isEmpty() {
        return minHeap.isEmpty();
    }

    public boolean contains(Student student) {
        return minHeap.contains(student);
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < minHeap.size(); i++) {
            output += minHeap.get(i).getName() + "(" + minHeap.get(i).getminCost() + ") "; //CHANGE BACK TO GETNAME
        }
        return output;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public ArrayList<Student> toArrayList() {
        return minHeap;
    }
}
