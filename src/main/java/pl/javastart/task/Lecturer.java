package pl.javastart.task;

public class Lecturer extends Person {

    private int id;
    private String degree;

    public Lecturer(int id, String degree, String firstName, String lastName) {
        super(firstName, lastName);
        this.id = id;
        this.degree = degree;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String printInfo() {
        return degree + " " + firstName + " " + lastName;
    }

}


