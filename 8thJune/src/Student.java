import java.sql.*;

public class Student {
    
    private int id;
    private String name;
    private double grade;

    public Student(int id, String name, double grade){
        this.setId(id);
        this.setName(name);
        this.setGrade(grade);
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "id: " + id + ", name: " + name +", grade: " + grade + "\n";
    }

}
