package model;

public class Employee {
    private String id, name;
    private float salary;

    // Constructor có tham số
    public Employee(String id, String name, float salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    // Constructor mặc định
    public Employee() {
        // Có thể để trống hoặc gán giá trị mặc định nếu muốn
    }

    // Getter - Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
}
