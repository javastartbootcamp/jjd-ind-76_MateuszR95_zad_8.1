package pl.javastart.task;

public class Lecturer {

    private int id;
    private String degree;
    private String firstName;
    private String lastName;

    public Lecturer(int id, String degree, String firstName, String lastName) {
        this.id = id;
        this.degree = degree;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void printLecturerInfo() {
        System.out.println("Id prowadzącego: " + id + ", Imię prowadzącego: " + firstName +
                ", Nazwisko prowadzącego: " + lastName + ", Stopień naukowy prowadzącego: " + degree);
    }
}


