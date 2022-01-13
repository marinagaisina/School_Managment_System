package jpa.entitymodels;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "email")
    private String sEmail;
    @Column(name="name")
    private String sName;
    @Column(name = "password")
    private String sPass;

    //(mappedBy = "student", fetch = FetchType.LAZY,
            //cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @ManyToMany(cascade={CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name ="student_course",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> sCourses;

    public Student(String sEmail, String sName, String sPass) {
        this.sEmail = sEmail;
        this.sName = sName;
        this.sPass = sPass;
        this.sCourses = new ArrayList<>();
    }

    public Student() {
        this.sEmail ="";
        this.sName = "";
        this.sPass = "";
        this.sCourses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        if (sCourses == null) {
            sCourses = new ArrayList<>();
        }
        sCourses.add(course);
    }
    public String getsEmail() {
        return sEmail;
    }

    public String getsName() {
        return sName;
    }

    public String getsPass() {
        return sPass;
    }

    public List<Course> getsCourses() {
        return sCourses;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public void setsPass(String sPass) {
        this.sPass = sPass;
    }

    public void setsCourses(List<Course> sCourses) {
        this.sCourses = sCourses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Email='" + sEmail + '\'' +
                ", Name='" + sName + '\'' +
                ", Pass='" + sPass + '\'' +
                ", Courses: " + Arrays.toString(sCourses.toArray()) + '\''+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getsEmail().equals(student.getsEmail())
                && getsName().equals(student.getsName())
                && getsPass().equals(student.getsPass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getsEmail(), getsName(), getsPass());
    }
}
