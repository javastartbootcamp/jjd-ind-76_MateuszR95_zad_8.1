package pl.javastart.task;

import java.util.Arrays;

public class Group {

    private String code;
    private String name;
    private Lecturer lecturer;
    private Student[] students = new Student[100];
    private double[] grades = new double[100];
    private int studentCounter = 0;

    public Group(String code, String name, Lecturer lecturer) {
        this.code = code;
        this.name = name;
        this.lecturer = lecturer;
    }

    public void addStudent(Student student) {
        if (studentCounter >= students.length) {
            students = expandArray(students);
            grades = expandArray(grades);
        }

        if (hasStudent(student.getIndex())) {
            System.out.println("Student o numerze indeksu " + student.getIndex() + " jest już w grupie " + getCode());
        } else {
            students[studentCounter] = student;
            studentCounter++;
        }
    }

    public void addGrade(int studentIndex, double grade) {
        if (hasGrade(studentIndex)) {
            System.out.println("Student o indeksie " + studentIndex + " ma już wystawioną ocenę");
        } else {
            int index = findStudentIndex(studentIndex);
            if (index >= 0) {
                grades[index] = grade;
            }
        }
    }

    public boolean hasGrade(int studentIndex) {
        return findStudentIndex(studentIndex) >= 0 && grades[findStudentIndex(studentIndex)] != 0;
    }

    public boolean hasStudent(int index) {
        return findStudentIndex(index) >= 0;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public Student[] getStudents() {
        Student[] result = new Student[studentCounter];
        System.arraycopy(students, 0, result, 0, studentCounter);
        return result;
    }

    private int findStudentIndex(int index) {
        for (int i = 0; i < studentCounter; i++) {
            if (students[i] != null && students[i].getIndex() == index) {
                return i;
            }
        }
        return -1;
    }

    private Student[] expandArray(Student[] array) {
        return Arrays.copyOf(array, array.length * 2);
    }

    private double[] expandArray(double[] array) {
        return Arrays.copyOf(array, array.length * 2);
    }

    public void printGrades() {

        System.out.println("Oceny dla grupy " + code + ":");

        boolean gradesPrinted = false;

        for (int i = 0; i < studentCounter; i++) {
            Student student = students[i];

            if (student != null) {
                double grade = getGradeForStudent(student.getIndex());

                if (grade != 0) {
                    System.out.println(student.printInfo() + ": " + grade);
                    gradesPrinted = true;
                }
            }
        }

        if (!gradesPrinted) {
            System.out.println("Brak ocen dla grupy " + code);
        }
    }

    private double getGradeForStudent(int studentIndex) {
        for (int i = 0; i < studentCounter; i++) {
            if (students[i].getIndex() == studentIndex) {
                return grades[i];
            }
        }
        return 0;

    }
}