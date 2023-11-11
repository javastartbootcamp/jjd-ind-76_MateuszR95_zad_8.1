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

        for (int i = 0; i < lecturerCounter; i++) {
            if (lecturers[i] != null && lecturers[i].getId() == id) {
                System.out.println("Prowadzący z id " + id + " już istnieje");
            }
        }

        if (lecturerCounter < lecturers.length) {
            Lecturer lecturer = new Lecturer(id, degree, firstName, lastName);
            lecturers[lecturerCounter] = lecturer;
            lecturerCounter++;
        } else {
            lecturers = Arrays.copyOf(lecturers, lecturers.length * 2);
        }
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
        boolean lecturerExist = false;

        for (int i = 0; i < groupCounter; i++) {
            if (groups[i] != null && groups[i].getCode().equalsIgnoreCase(code)) {
                System.out.println("Grupa " + code + " już istnieje");
            }
        }

        if (groupCounter < groups.length) {
            Group group = new Group(code, name, lecturerId);
            groups[groupCounter] = group;
            groupCounter++;
        } else {
            groups = Arrays.copyOf(groups, groups.length * 2);
        }

        for (int j = 0; j < lecturerCounter; j++) {
            if (lecturers[j] != null && lecturerId == lecturers[j].getId()) {
                lecturerExist = true;
                break;
            }
        }

        if (!lecturerExist) {
            System.out.println("Prowadzący o id " + lecturerId + " nie istnieje");

        }
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

        Group group = getGroupForStudent(groupCode);

        if (group == null) {
            System.out.println("Grupa: " + groupCode + " nie istnieje");
        } else {
            if (isStudentInGroup(index, groupCode)) {
                System.out.println("Student o numerze indeksu " + index + " jest już przypisany do grupy " + groupCode);
            } else {
                Student student = findStudentInGroup(index, groupCode);

                if (student == null) {
                    student = new Student(index, groupCode, firstName, lastName);

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
                }
            }
        }
    }

    public boolean isStudentInGroup(int index, String groupCode) {
        for (int i = 0; i < studentCounter; i++) {
            if (students[i] != null && students[i].getIndex() == index && students[i].getGroupCode().equals(groupCode)) {
                return true;
            }
        }
        return false;
    }

    public Group getGroupForStudent(String groupCode) {
        for (int i = 0; i < groupCounter; i++) {
            if (groups[i] != null && groups[i].getCode().equalsIgnoreCase(groupCode)) {
                return groups[i];
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

        boolean groupFound = false;

        for (int i = 0; i < groupCounter; i++) {
            if (groups[i] != null && groups[i].getCode().equals(groupCode)) {
                groupFound = true;
                System.out.println("Kod: " + groups[i].getCode());
                System.out.println("Nazwa: " + groups[i].getName());

                for (int j = 0; j < lecturerCounter; j++) {
                    if (lecturers[j] != null && lecturers[j].getId() == groups[i].getLecturerId()) {
                        System.out.println("Prowadzący: " + lecturers[j].getDegree() + " " + lecturers[j].getFirstName() + " " + lecturers[j].getLastName());

                    }
                }

                System.out.println("Uczestnicy:");
                for (int j = 0; j < studentCounter; j++) {
                    if (students[j] != null && students[j].getGroupCode().equals(groupCode)) {
                        System.out.println(students[j].getIndex() + " " + students[j].getFirstName() + " " + students[j].getLastName());
                    }
                }
            }
        }

        if (!groupFound) {
            System.out.println("Grupa " + groupCode + " nie znaleziona");
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

        Group group = getGroupForStudent(groupCode);
        if (group == null) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
            return;
        }

        Student student = findStudentInGroup(studentIndex, groupCode);
        if (student == null) {
            System.out.println("Student o indeksie " + studentIndex + " nie jest zapisany do grupy " + groupCode);
            return;
        }

        if (hasGradeForGroup(studentIndex, groupCode)) {
            System.out.println("Student o indeksie " + studentIndex + " ma już wystawioną ocenę dla grupy " + groupCode);
            return;
        }

        group.addGrade(studentIndex, grade);
    }

    public Student findStudentInGroup(int studentIndex, String groupCode) {
        for (int i = 0; i < studentCounter; i++) {
            if (students[i] != null && students[i].getIndex() == studentIndex && students[i].getGroupCode().equals(groupCode)) {
                return students[i];
            }
        }
        return null;
    }

    public boolean hasGradeForGroup(int studentIndex, String groupCode) {
        Student student = findStudentInGroup(studentIndex, groupCode);
        if (student != null) {
            return student.hasGrade();
        }
        return false;
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
        for (int i = 0; i < studentCounter; i++) {
            if (students[i] != null && students[i].getIndex() == index) {
                String groupCode = students[i].getGroupCode();
                double[] grades = students[i].getGradesForGroup(groupCode);

                if (grades.length > 0) {
                    for (int j = 0; j < grades.length; j++) {
                        String groupName = getGroupName(groupCode);
                        if (groupName != null) {
                            System.out.println(groupName + ": " + grades[j]);
                        }
                    }
                }
            }
        }
    }

    public String getGroupName(String groupCode) {
        for (int i = 0; i < groupCounter; i++) {
            if (groups[i] != null && groups[i].getCode().equals(groupCode)) {
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

        boolean groupExists = false;
        for (int i = 0; i < groupCounter; i++) {
            if (groups[i] != null && groups[i].getCode().equals(groupCode)) {
                groupExists = true;
                break;
            }
        }

        if (groupExists) {
            System.out.println("Oceny dla grupy " + groupCode + ":");
            for (int i = 0; i < studentCounter; i++) {
                if (students[i] != null && students[i].getGroupCode().equals(groupCode)) {
                    double[] grades = students[i].getGradesForGroup(groupCode);
                    if (grades.length > 0) {
                        for (int j = 0; j < grades.length; j++) {
                            System.out.println(students[i].getIndex() + " " + students[i].getFirstName() + " " +
                                    students[i].getLastName() + ": " + grades[j]);
                        }
                    }
                }
            }
        } else {
            System.out.println("Grupa " + groupCode + " nie istnieje");
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
