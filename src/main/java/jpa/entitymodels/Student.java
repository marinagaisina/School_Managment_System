package jpa.entitymodels;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public Student(String sEmail, String sName, String sPass, List<Course> sCourses) {
        this.sEmail = sEmail;
        this.sName = sName;
        this.sPass = sPass;
        this.sCourses = sCourses;
    }

    public Student() {
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
                "sEmail='" + sEmail + '\'' +
                ", sName='" + sName + '\'' +
                ", sPass='" + sPass + '\'' +
                //", sCourses=" + sCourses +//movies.stream().map(Movie::getTitle).collect(Collectors.toList()) + '\''
                '}';
    }
}
