package bean;

public class Student {
    private String name;
    private String sex;
    private int age;
    private String grade;
    private String number;

    public Student() {
    }

    public Student(String name, String sex, int age, String grade, String number) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.grade = grade;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "student{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", grade='" + grade + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
