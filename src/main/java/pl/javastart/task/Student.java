package pl.javastart.task;

import java.util.Arrays;

public class Student extends Person {
    private int index;
    private double[] grades = new double[100];
    private int gradeCounter = 0;
    private String[] groupCodes = new String[100];

    public Student(int index, String firstName, String lastName) {
        super(firstName, lastName);
        this.index = index;
    }

    public void addGrade(double grade, String groupCode) {
        if (gradeCounter < grades.length) {
            grades[gradeCounter] = grade;
            groupCodes[gradeCounter] = groupCode;
            gradeCounter++;
        } else {
            int newLength = grades.length * 2;
            grades = Arrays.copyOf(grades, newLength);
            groupCodes = Arrays.copyOf(groupCodes, newLength);
            grades[gradeCounter] = grade;
            groupCodes[gradeCounter] = groupCode;
            gradeCounter++;
        }
    }

    public String[] getGroupCodes() {
        String[] result = new String[gradeCounter];
        System.arraycopy(groupCodes, 0, result, 0, gradeCounter);
        return result;
    }

    public boolean isInGroup(String groupCode) {
        for (String code : groupCodes) {
            if (code != null && code.equals(groupCode)) {
                return true;
            }
        }
        return false;
    }

    public double[] getGradesForGroup(String groupCode) {
        double[] groupGrades = new double[gradeCounter];
        int count = 0;
        for (int i = 0; i < gradeCounter; i++) {
            if (groupCodes[i].equals(groupCode)) {
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

}