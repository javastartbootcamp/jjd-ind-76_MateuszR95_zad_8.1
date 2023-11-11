package pl.javastart.task;

import java.util.Arrays;

public class Group {

    private String code;
    private String name;
    private int lecturerId;
    private Student[] students = new Student[100];
    private int studentCounter = 0;

    public Group(String code, String name, int lecturerId) {
        this.code = code;
        this.name = name;
        this.lecturerId = lecturerId;
    }

    public void addStudent(Student student) {
        if (isStudentExist(student.getIndex())) {
            System.out.println("Student o numerze indeksu " + student.getIndex() + " jest już w grupie " + getCode());
        } else {
            if (studentCounter < students.length) {
                students[studentCounter] = student;
                studentCounter++;
            } else {
                students = Arrays.copyOf(students, students.length * 2);
                students[studentCounter] = student;
                studentCounter++;
            }
        }
    }

    public double[] getGradesForStudent(int studentIndex) {
        for (Student student : students) {
            if (student != null && student.getIndex() == studentIndex) {
                return student.getGradesForGroup(getCode());
            }
        }
        return new double[0];
    }

    public void addGrade(int studentIndex, double grade) {
        Student student = findStudent(studentIndex);
        if (isStudentExist(studentIndex)) {
            if (student != null) {
                student.addGrade(grade, getCode());
            }
        }
    }

    private Student findStudent(int studentIndex) {
        for (Student student : students) {
            if (student != null && student.getIndex() == studentIndex) {
                return student;
            }
        }
        return null;
    }

    public boolean isStudentExist(int index) {
        for (int i = 0; i < studentCounter; i++) {
            if (students[i] != null && students[i].getIndex() == index) {
                return true;
            }
        }
        return false;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    public Student[] getStudents() {
        return students;
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }

    public int getStudentCounter() {
        return studentCounter;
    }

    public void setStudentCounter(int studentCounter) {
        this.studentCounter = studentCounter;
    }

    public void printGroupInfo() {
        System.out.println("Kod grupy: " + code + ", Nazwa grupy: " + name + ", Id prowadzącego grupy: " + lecturerId);
    }
}