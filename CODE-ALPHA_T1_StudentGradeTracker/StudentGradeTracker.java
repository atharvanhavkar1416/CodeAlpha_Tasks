import java.util.ArrayList;
import java.util.*;

public class StudentGradeTracker{
 public static void main(String[] args){
  
  
  Scanner sc = new Scanner(System.in);
  
  ArrayList<String> names = new ArrayList<>();
  ArrayList<Integer> marks = new ArrayList<>();

  System.out.println("=================================");
  System.out.println("====== STUDENT GRADE SYSTEM =====");
  System.out.println("=================================");

  System.out.println("\nENTER NO OF STUDENTS:");
  int n = sc.nextInt();
  for(int i= 0; i<n; i++) {

    sc.nextLine();
    
    System.out.println("Enter Names of Students:");
    String name = sc.nextLine();
    names.add(name);

    System.out.println("Enter Marks:");
    int mark = sc.nextInt();
    marks.add(mark);

  }
  int total = 0;
  int highest = marks.get(0);
  int lowest = marks.get(0);

  for(int m : marks){
    total += m;
    if (m > highest) highest = m;
    if (m < lowest) lowest = m;
  }
   double average = (double) total / marks.size();

   System.out.println("\n------------------------------");
        System.out.println("        STUDENT REPORT");
        System.out.println("------------------------------");
        System.out.println("Name Marks");

        for(int i = 0;i <n ;i++){
            System.out.println(names.get(i) + "\t " + marks.get(i));
        }
        System.out.println("------------------------------");
        System.out.println("AVERAGE SCORE: " + average);
        System.out.println("Highest Score " + highest);
        System.out.println("Lowest Score " + lowest);
    sc.close(); 
    
     }
}