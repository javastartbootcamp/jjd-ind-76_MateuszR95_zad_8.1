package pl.javastart.task;

public class Main {

    // uzupełnij metody w UniversityApp zgodnie z dokumentacją
    public static void main(String[] args) {
        UniversityApp universityApp = new UniversityApp();

        universityApp.createLecturer(1, "dr", "Janusz", "Rataj");
        universityApp.createLecturer(2, "dr", "Michał", "Kowalski");

        universityApp.createGroup("pp-2022", "Podstawy Programowania", 1);
        universityApp.createGroup("po-2022", "Programowanie Obiektowe", 2);

        universityApp.addStudentToGroup(179126, "pp-2022", "Marcin", "Abacki");
        universityApp.addStudentToGroup(179127, "po-2022", "Mariusz", "Nowak");
        universityApp.addStudentToGroup(179128, "pp-2022", "Marcin", "Kubacki");
        universityApp.addStudentToGroup(179128, "po-2022", "Marcin", "Kubacki");
        universityApp.addStudentToGroup(179129, "po-2022", "Adam", "Browarski");
        
        universityApp.addGrade(179126, "pp-2022", 5);
        universityApp.addGrade(179127, "po-2022", 5);
        universityApp.addGrade(179128, "pp-2022", 5);
        universityApp.addGrade(179128, "po-2022", 4);
        universityApp.addGrade(179129, "po-2022", 5.5);

        System.out.println("----------------------");
        universityApp.printGroupInfo("pp-2022");
        System.out.println("----------------------");
        universityApp.printGroupInfo("po-2022");

        System.out.println("----------------------");
        universityApp.printGradesForStudent(179128);
        System.out.println("----------------------");
        universityApp.printGradesForGroup("pp-2022");
        System.out.println("----------------------");
        universityApp.printGradesForGroup("po-2022");
        System.out.println("----------------------");
        universityApp.printGradesForGroup("pi-2022");
        System.out.println("----------------------");
        universityApp.printAllStudents();

    }
}
