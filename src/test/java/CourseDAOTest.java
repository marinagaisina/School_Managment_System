import jpa.entitymodels.Course;
import jpa.service.CourseService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import javax.management.ConstructorParameters;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CourseDAOTest {
    private static CourseService courseServiceTest;

    @BeforeClass
    public static void beforeAll() {
        //System.out.println("Before all method");
        courseServiceTest = new CourseService();
        System.out.println("Course tests are running*************************************************");
    }

    @Test
    public void getCourseByIdTest1() {
        Course course = courseServiceTest.getCourseById(1);
        Assert.assertEquals("courseId=1 should get the \"English\" course from the DB", course.getcName(), "English");
    }
    @Test
    public void getCourseByIdTest2() {
        Course course = courseServiceTest.getCourseById(500);
        Assert.assertNull(course);
    }
    @Test
    public void getCourseByIdTest3() {
        Course course = courseServiceTest.getCourseById(-9);
        Assert.assertNull(course);
    }
    @Test
    public void getCourseByIdTest4() {
        Course course = courseServiceTest.getCourseById(3);
        Course course1 = courseServiceTest.getCourseById(8);
        Assert.assertNotSame(course, course1);
    }

//    @ParameterizedTest
//    @CsvFileSource(resources = "/courses.csv", numLinesToSkip = 1)
//    public void getAllCoursesTest1() throws FileNotFoundException {
////        List<Course> expectedList = new ArrayList<>();
////        File readInFile = new File("test/resources/courses.csv");
////        Scanner scanner = new Scanner(readInFile);
////        while (scanner.hasNextLine()) {
////            String[] courseInfo = scanner.nextLine().split(",");
////            Course course = new Course(Integer.valueOf(courseInfo[0]), courseInfo[1], courseInfo[2]);
////            expectedList.add(course);
////        }
//        Assert.assertEquals(Arrays.asList(new Course()), courseServiceTest.getAllCourses().size());
//
//    }

}
