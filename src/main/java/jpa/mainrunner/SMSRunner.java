package jpa.mainrunner;

        import static java.lang.System.in;
        import static java.lang.System.out;

        import java.util.InputMismatchException;
        import java.util.List;
        import java.util.Scanner;
        import jpa.entitymodels.Course;
        import jpa.entitymodels.Student;
        import jpa.service.CourseService;
        import jpa.service.StudentService;

        import javax.persistence.*;

public class SMSRunner {

    private static Scanner sin;
    private static StringBuilder sb;

    private static CourseService courseService;
    private static StudentService studentService;
    private Student currentStudent;

    public SMSRunner() {
        sin = new Scanner(System.in);
        sb = new StringBuilder();
        courseService = new CourseService();
        studentService = new StudentService();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SMSPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        SMSRunner sms = new SMSRunner();

        out.println("get a list of courses:");
        List<Course> courseList = courseService.getAllCourses();
        for (Course course : courseList) {
            out.println(course);
        }
        List<Student> studentList = studentService.getAllStudents();
        for (Student student : studentList) {
            out.println(student);
        }
        Student student = studentService.getStudentByEmail("cstartin3@flickr.com");
        out.println(student.toString());

        Course course = courseService.getCourseById(5);
        out.println(course);

        entityManager.getTransaction().begin();
        String sEmail = "hluckham0@google.ru";
        String sPassword = "X1uZcoIh0dj";
        Query query = entityManager.createQuery("select s from Student s where s.sEmail=:sEmail and s.sPass=:sPassword", Student.class).setParameter("sEmail", sEmail).setParameter("sPassword", sPassword);
        out.println(query.getSingleResult());
        sms.run();
    }

    private void run() {
        // Login or quit
        switch (menu1()) {
            case 1:
                if (studentLogin()) {
                    registerMenu();
                }
                break;
            case 2:
            default:
                out.println("Goodbye!");
                break;
        }
    }

    private int menu1() {
        int input = 0;
        sb.append("\n1.Student Login\n2. Quit Application\nPlease Enter Selection: ");
        out.print(sb.toString());
        sb.delete(0, sb.length());
        try {
            input = sin.nextInt();
        } catch (NumberFormatException e) {
            out.println("Wrong input! Input only integer numbers please: ");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    private boolean studentLogin() {
        out.print("Enter your email address: ");
        String email = sin.next();
        out.print("Enter your password: ");
        String password = sin.next();

        boolean isValid = studentService.validateStudent(email, password);
        if (!isValid) {
            out.println("No student found. Check email and/or password.");
            return false;
        } else {
            currentStudent = studentService.getStudentByEmail(email);
        }
        //out.println("Logged in successfully.");
        return true;
    }

    private void registerMenu() {
        boolean quit = false;
        while (!quit) {
            out.println("MyClasses: ");
            displayCourses(studentService.getStudentCourses(currentStudent.getsEmail()));
            sb.append("\n1.Register a class\n2. Logout\nPlease Enter Selection: ");
            out.print(sb.toString());
            sb.delete(0, sb.length());
            switch (sin.nextInt()) {
                case 1:
                    out.println("All courses: ");
                    displayCourses(courseService.getAllCourses());
                    out.print("\nEnter Course Number: ");
                    String line = sin.next();
                    Course newCourse = null;
                    int number = 0;
                    try {
                        number = Integer.parseInt(line);
                        newCourse = courseService.getCourseById(number);
                    } catch (NumberFormatException e) {
                        out.println("Wrong input! Input only integer numbers please: ");
                        e.printStackTrace();
                    } catch (NoResultException e) {
                        e.printStackTrace();
                    }
                    if (newCourse != null) {
                        studentService.registerStudentToCourse(currentStudent.getsEmail(), number);
                    }
                    break;
                case 2:
                    quit = true;
                    out.println("Goodbye!");
                    break;
            }
        }
    }
    public static void displayCourses (List < Course > list) {
        if (list != null) {
            for (Course course : list) {
                out.printf("%5s%20S%20s\n", course.getcId(), course.getcName(), course.getcInstructorName());
            }
        } else out.println("No courses found");
    }
}

