package pl.javastart.task;

import java.util.Arrays;

public class UniversityApp {

    private Lecturer[] lecturers = new Lecturer[100];
    private int lecturerCounter = 0;
    private Group[] groups = new Group[100];
    private int groupCounter = 0;

    private Student[] students = new Student[100];
    private int studentCounter = 0;

    /**
     * Tworzy prowadzącego zajęcia.
     * W przypadku gdy prowadzący z zadanym id już istnieje, wyświetlany jest komunikat:
     * "Prowadzący z id [id_prowadzacego] już istnieje"
     *
     * @param id        - unikalny identyfikator prowadzącego
     * @param degree    - stopień naukowy prowadzącego
     * @param firstName - imię prowadzącego
     * @param lastName  - nazwisko prowadzącego
     */
    public void createLecturer(int id, String degree, String firstName, String lastName) {

        Lecturer lecturer = findLecturerById(id);

        if (lecturer != null) {
            System.out.println("Prowadzący z id " + id + " już istnieje");

        } else {
            if (lecturerCounter < lecturers.length) {
                lecturer = new Lecturer(id, degree, firstName, lastName);
                lecturers[lecturerCounter] = lecturer;
                lecturerCounter++;
            } else {
                lecturers = Arrays.copyOf(lecturers, lecturers.length * 2);
            }
        }

    }

    public Lecturer findLecturerById(int id) {
        for (int i = 0; i < lecturerCounter; i++) {
            if (lecturers[i].getId() == id) {
                return lecturers[i];
            }
        }
        return null;
    }

    /**
     * Tworzy grupę zajęciową.
     * W przypadku gdy grupa z zadanym kodem już istnieje, wyświetla się komunikat:
     * "Grupa [kod grupy] już istnieje"
     * W przypadku gdy prowadzący ze wskazanym id nie istnieje wyświetla się komunikat:
     * "Prowadzący o id [id prowadzacego] nie istnieje"
     *
     * @param code       - unikalny kod grupy
     * @param name       - nazwa przedmiotu (np. "Podstawy programowania")
     * @param lecturerId - identyfikator prowadzącego. Musi zostać wcześniej utworzony za pomocą metody {@link #createLecturer(int, String, String, String)}
     */
    public void createGroup(String code, String name, int lecturerId) {

        Lecturer lecturer = findLecturerById(lecturerId);
        Group group = findGroupByCode(code);

        if (lecturer == null) {
            System.out.println("Prowadzący o id " + lecturerId + " nie istnieje");
            return;
        }
        if (group != null) {
            System.out.println("Grupa " + code + " już istnieje");
        } else {
            if (groupCounter < groups.length) {
                group = new Group(code, name, lecturer);
                groups[groupCounter] = group;
                groupCounter++;
            } else {
                groups = Arrays.copyOf(groups, groups.length * 2);
            }

        }
    }

    public Group findGroupByCode(String groupCode) {
        for (int i = 0; i < groupCounter; i++) {
            if (groups[i].getCode().equalsIgnoreCase(groupCode)) {
                return groups[i];
            }
        }
        return null;
    }

    /**
     * Dodaje studenta do grupy zajęciowej.
     * W przypadku gdy grupa zajęciowa nie istnieje wyświetlany jest komunikat:
     * "Grupa [kod grupy] nie istnieje
     *
     * @param index     - unikalny numer indeksu studenta
     * @param groupCode - kod grupy utworzonej wcześniej za pomocą {@link #createGroup(String, String, int)}
     * @param firstName - imię studenta
     * @param lastName  - nazwisko studenta
     */
    public void addStudentToGroup(int index, String groupCode, String firstName, String lastName) {

        Group group = findGroupByCode(groupCode);

        if (group == null) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
        } else if (group.hasStudent(index)) {
            System.out.println("Student o indeksie " + index + " jest już w grupie " + groupCode);
        } else {
            Student student = findStudentByIndex(index);

            if (student == null) {
                student = new Student(index, firstName, lastName);
                if (studentCounter < students.length) {
                    students[studentCounter] = student;
                    studentCounter++;
                    group.addStudent(student);
                } else {
                    students = Arrays.copyOf(students, students.length * 2);
                    students[studentCounter] = student;
                    studentCounter++;
                    group.addStudent(student);
                }
            } else {
                if (studentCounter < students.length && !group.hasStudent(index)) {
                    students[studentCounter] = student;
                    studentCounter++;
                    group.addStudent(student);
                }
            }
        }
    }

    public Student findStudentByIndex(int index) {
        for (int i = 0; i < studentCounter; i++) {
            if (students[i].getIndex() == index) {
                return students[i];
            }
        }
        return null;
    }

    /**
     * Wyświetla informacje o grupie w zadanym formacie.
     * Oczekiwany format:
     * Kod: [kod_grupy]
     * Nazwa: [nazwa przedmiotu]
     * Prowadzący: [stopień naukowy] [imię] [nazwisko]
     * Uczestnicy:
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * W przypadku gdy grupa nie istnieje, wyświetlany jest komunikat w postaci: "Grupa [kod] nie znaleziona"
     *
     * @param groupCode - kod grupy, dla której wyświetlić informacje
     */
    public void printGroupInfo(String groupCode) {
        Group group = findGroupByCode(groupCode);

        if (group == null) {
            System.out.println("Grupa " + groupCode + " nie znaleziona");
        } else {
            System.out.println("Kod: " + group.getCode());
            System.out.println("Nazwa: " + group.getName());

            Lecturer lecturer = group.getLecturer();
            System.out.println("Prowadzący: " + lecturer.getDegree() + " " + lecturer.getFirstName() + " " + lecturer.getLastName());

            System.out.println("Uczestnicy:");
            Student[] students = group.getStudents();

            for (Student student : students) {
                if (student != null) {
                    System.out.println(student.getIndex() + " " + student.getFirstName() + " " + student.getLastName());
                }
            }
        }
    }

    /**
     * Dodaje ocenę końcową dla wskazanego studenta i grupy.
     * Student musi być wcześniej zapisany do grupy za pomocą {@link #addStudentToGroup(int, String, String, String)}
     * W przypadku, gdy grupa o wskazanym kodzie nie istnieje, wyświetlany jest komunikat postaci:
     * "Grupa pp-2022 nie istnieje"
     * W przypadku gdy student nie jest zapisany do grupy, wyświetlany jest komunikat w
     * postaci: "Student o indeksie 179128 nie jest zapisany do grupy pp-2022"
     * W przypadku gdy ocena końcowa już istnieje, wyświetlany jest komunikat w postaci:
     * "Student o indeksie 179128 ma już wystawioną ocenę dla grupy pp-2022"
     *
     * @param studentIndex - numer indeksu studenta
     * @param groupCode    - kod grupy
     * @param grade        - ocena
     */
    public void addGrade(int studentIndex, String groupCode, double grade) {

        Group group = findGroupByCode(groupCode);

        if (group == null) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
            return;
        }

        Student student = findStudentByIndex(studentIndex);

        if (student == null) {
            System.out.println("Student o indeksie " + studentIndex + " nie jest zapisany do grupy " + groupCode);
            return;
        }

        if (!group.hasStudent(studentIndex)) {
            System.out.println("Student o indeksie " + studentIndex + " nie jest zapisany do grupy " + groupCode);
            return;
        }

        boolean hasGradeForGroup = false;
        for (int i = 0; i < student.getGrades().length; i++) {
            if (student.getGrades()[i] != 0 && groupCode.equals(student.getGroupCodes()[i])) {
                hasGradeForGroup = true;
                break;
            }
        }

        if (hasGradeForGroup) {
            System.out.println("Student o indeksie " + studentIndex + " ma już wystawioną ocenę dla grupy " + groupCode);
            return;
        }

        group.addGrade(studentIndex, grade);

        student.addGrade(grade, groupCode);
    }

    /**
     * Wyświetla wszystkie oceny studenta.
     * Przykładowy wydruk:
     * Podstawy programowania: 5.0
     * Programowanie obiektowe: 5.5
     *
     * @param index - numer indesku studenta dla którego wyświetlić oceny
     */

    public void printGradesForStudent(int index) {

        System.out.println("Oceny dla studenta o indeksie " + index + ":");

        boolean studentPrinted = false;

        for (int i = 0; i < studentCounter; i++) {
            if (students[i].getIndex() == index) {
                double[] studentGrades = students[i].getGrades();

                for (int j = 0; j < studentGrades.length; j++) {
                    double grade = studentGrades[j];

                    String groupCode = getGroupCodeForStudent(index, j);
                    if (groupCode != null) {
                        String groupName = getGroupName(groupCode);
                        System.out.println(groupName + ": " + grade);
                    }
                }

                studentPrinted = true;
                break;
            }
        }

        if (!studentPrinted) {
            System.out.println("Brak ocen dla studenta o indeksie " + index);
        }
    }

    public String getGroupCodeForStudent(int studentIndex, int gradeIndex) {
        for (int i = 0; i < studentCounter; i++) {
            if (students[i].getIndex() == studentIndex) {
                double[] studentGrades = students[i].getGrades();
                if (gradeIndex < studentGrades.length) {
                    return students[i].getGroupCodes()[gradeIndex];
                }
            }
        }
        return null;

    }

    public String getGroupName(String groupCode) {
        for (int i = 0; i < groupCounter; i++) {
            if (groups[i].getCode().equals(groupCode)) {
                return groups[i].getName();

            }
        }
        return null;
    }

    /**
     * Wyświetla oceny studentów dla wskazanej grupy.
     * Przykładowy wydruk:
     * 179128 Marcin Abacki: 5.0
     * 179234 Dawid Donald: 4.5
     * 189521 Anna Kowalska: 5.5
     *
     * @param groupCode - kod grupy, dla której wyświetlić oceny
     */
    public void printGradesForGroup(String groupCode) {

        Group group = findGroupByCode(groupCode);

        if (group == null) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
            return;
        }

        System.out.println("Oceny dla grupy " + groupCode + ":");

        boolean gradesPrinted = false;

        for (int i = 0; i < studentCounter; i++) {
            Student student = students[i];
            if (student.isInGroup(groupCode)) {
                double[] studentGrades = student.getGradesForGroup(groupCode);

                if (studentGrades.length > 0) {

                    String studentInfo = student.getIndex() + " " + student.getFirstName() + " " + student.getLastName();
                    for (double grade : studentGrades) {
                        System.out.println(studentInfo + ": " + grade);
                    }
                    gradesPrinted = true;
                }
            }
        }

        if (!gradesPrinted) {
            System.out.println("Brak ocen dla grupy " + groupCode);
        }
    }

    /**
     * Wyświetla wszystkich studentów. Każdy student powinien zostać wyświetlony tylko raz.
     * Każdy student drukowany jest w nowej linii w formacie [nr_indesku] [imie] [nazwisko]
     * Przykładowy wydruk:
     * 179128 Marcin Abacki
     * 179234 Dawid Donald
     * 189521 Anna Kowalska
     */
    public void printAllStudents() {
        System.out.println("Lista wszystkich studentów:");

        int[] displayedIndexes = new int[studentCounter];
        int uniqueIndexCounter = 0;

        for (int i = 0; i < studentCounter; i++) {
            if (students[i] != null) {
                int index = students[i].getIndex();

                boolean isDisplayed = false;
                for (int j = 0; j < uniqueIndexCounter; j++) {
                    if (displayedIndexes[j] == index) {
                        isDisplayed = true;
                        break;
                    }
                }
                if (!isDisplayed) {
                    displayedIndexes[uniqueIndexCounter] = index;
                    uniqueIndexCounter++;

                    String firstName = students[i].getFirstName();
                    String lastName = students[i].getLastName();

                    System.out.println(index + " " + firstName + " " + lastName);
                }
            }
        }
    }

}


