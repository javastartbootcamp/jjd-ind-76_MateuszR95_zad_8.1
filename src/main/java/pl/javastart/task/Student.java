package pl.javastart.task;

import java.util.Arrays;

public class Student {
    private int index;
    private String groupCode;
    private String firstName;
    private String lastName;
    private double[] grades = new double[100];
    private int gradeCounter = 0;
    private String[] groupCodes = new String[100];

    public Student(int index, String groupCode, String firstName, String lastName) {
        this.index = index;
        this.groupCode = groupCode;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addGrade(double grade, String groupCode) {
        if (gradeCounter < grades.length) {
            grades[gradeCounter] = grade;
            groupCodes[gradeCounter] = groupCode;
            gradeCounter++;
        } else {
            grades = Arrays.copyOf(grades, grades.length * 2);
            groupCodes = Arrays.copyOf(groupCodes, groupCodes.length * 2);

        }
    }

    public double[] getGradesForGroup(String groupCode) {

        double[] groupGrades = new double[gradeCounter];
        int count = 0;
        for (int i = 0; i < gradeCounter; i++) {
            if (groupCodes[i] != null && groupCodes[i].equals(groupCode)) {
                groupGrades[count++] = grades[i];
            }
        }
        return Arrays.copyOf(groupGrades, count);
    }

    public double[] getGrades() {
        return Arrays.copyOf(grades, gradeCounter);
    }

    public int getIndex() {
        return index;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean hasGrade() {
        return gradeCounter > 0;
    }
}