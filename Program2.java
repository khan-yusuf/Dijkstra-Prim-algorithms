/*
 * Name: Yusuf Khan
 * EID: yk7862
 */

// Implement your algorithms here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;

public class Program2 {
    private ArrayList<Student> students;    // this is a list of all Students, populated by Driver class
    private Heap minHeap;

    // additional constructors may be added, but don't delete or modify anything already here
    public Program2(int numStudents) {
        minHeap = new Heap();
        students = new ArrayList<Student>();
    }

    /**
     * findMinimumStudentCost(Student start, Student dest)
     *
     * @param start - the starting Student.
     * @param dest  - the end (destination) Student.
     * @return the minimum cost possible to get from start to dest.
     * Assume the given graph is always connected. Uses Dijkstra's Algorithm
     */
    public int findMinimumStudentCost(Student start, Student dest) {
        for (Student student : students){ //Single source initialization
            if (student.equals(start)){
                student.setminCost(0);
            } else{
                student.resetminCost(); //setting upperbounds for all to infinity except start node
            }
        }
        minHeap.buildHeap(students);

        while (!minHeap.isEmpty()){
            Student currentStudent = minHeap.extractMin(); //finds smallest minCost student in heap
            if(currentStudent.equals(dest)){
                return currentStudent.getminCost(); //dest student reached
            }
            ArrayList<Student> neighborsList = students.get(currentStudent.getName()).getNeighbors();
            for (int i = 0; i < neighborsList.size(); i++) { //for each neighbor of extracted currentStudent
                Student neighbor = neighborsList.get(i);
                if (minHeap.contains(neighbor)){
                    int newCost = currentStudent.getminCost() + students.get(currentStudent.getName()).getPrices().get(i);
                    if (newCost < neighbor.getminCost()){ //RELAX all neighbors
                        neighbor.setminCost(newCost);
                        minHeap.changeKey(neighbor, newCost);
                    }
                }
            }
        }
        return -1; //cannot connect start student to dest
    }

    /**
     * findMinimumClassCost()
     *
     * @return the minimum total cost required to connect (span) each student in the class.
     * Assume the given graph is always connected. Uses Prim's Algorithm
     */
    public int findMinimumClassCost() {
        for (Student student : students){
            student.resetminCost(); //setting upperbounds for all to infinity except first Student
        }
        students.get(0).setminCost(0); //Initialize first student as root
        minHeap.buildHeap(students);

        while(!minHeap.isEmpty()){
            Student currentStudent = minHeap.extractMin();
            ArrayList<Student> neighborsList = students.get(currentStudent.getName()).getNeighbors();
            ArrayList<Integer> pricesList = students.get(currentStudent.getName()).getPrices();
            for (int i = 0; i < neighborsList.size(); i++) {
                Student neighbor = neighborsList.get(i);
                int priceToNeighbor = pricesList.get(i); //cost for edge between currentStudent and neighbor
                if(minHeap.contains(neighbor) && priceToNeighbor < neighbor.getminCost()){ //cost < neighbor's minCost
                    neighbor.setminCost(priceToNeighbor);
                    minHeap.changeKey(neighbor, priceToNeighbor); //update heap
                }
            }
        }
        int totalCost = 0;
        for(Student student : students){
            totalCost += student.getminCost(); //adding edge weights of all nodes in MST
        }
        return totalCost;
    }

    //returns edges and prices in a string.
    public String toString() {
        String o = "";
        for (Student v : students) {
            boolean first = true;
            o += "Student ";
            o += v.getName();
            o += " has neighbors ";
            ArrayList<Student> ngbr = v.getNeighbors();
            for (Student n : ngbr) {
                o += first ? n.getName() : ", " + n.getName();
                first = false;
            }
            first = true;
            o += " with prices ";
            ArrayList<Integer> wght = v.getPrices();
            for (Integer i : wght) {
                o += first ? i : ", " + i;
                first = false;
            }
            o += System.getProperty("line.separator");

        }

        return o;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public Heap getHeap() {
        return minHeap;
    }

    public ArrayList<Student> getAllstudents() {
        return students;
    }

    // used by Driver class to populate each Student with correct neighbors and corresponding prices
    public void setEdge(Student curr, Student neighbor, Integer price) {
        curr.setNeighborAndPrice(neighbor, price);
    }

    // used by Driver.java and sets students to reference an ArrayList of all Students
    public void setAllNodesArray(ArrayList<Student> x) {
        students = x;
    }
}
