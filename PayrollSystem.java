import java.io.*;
import java.util.*;

class Employee {
    int empId;
    String name;
    double basicSalary;

    // Default constructor
    Employee() {
        empId = 0;
        name = "";
        basicSalary = 0.0;
    }

    // Parameterized constructor
    Employee(int id, String n, double salary) {
        empId = id;
        name = n;
        basicSalary = salary;
    }

    // Method to calculate salary
    double calculateSalary() {
        double hra = 0.20 * basicSalary;
        double da = 0.10 * basicSalary;
        return basicSalary + hra + da;
    }

    // Overloaded method (with bonus)
    double calculateSalary(double bonus) {
        double hra = 0.20 * basicSalary;
        double da = 0.10 * basicSalary;
        return basicSalary + hra + da + bonus;
    }

    void display() {
        System.out.println("Employee ID: " + empId);
        System.out.println("Name: " + name);
        System.out.println("Basic Salary: " + basicSalary);
        System.out.println("Net Salary: " + calculateSalary());
        System.out.println("-----------------------");
    }

    String toFileString() {
        return empId + "," + name + "," + basicSalary;
    }
}

public class PayrollSystem {

    static final String FILE_NAME = "employees.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Employee Payroll System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Search by ID");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addEmployee(sc);
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    searchEmployee(sc);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 4);

        sc.close();
    }

    static void addEmployee(Scanner sc) {
        try {
            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Basic Salary: ");
            double salary = sc.nextDouble();

            Employee emp = new Employee(id, name, salary);

            FileWriter fw = new FileWriter(FILE_NAME, true);
            fw.write(emp.toFileString() + "\n");
            fw.close();

            System.out.println("Employee added successfully!");

        } catch (IOException e) {
            System.out.println("Error writing file.");
        }
    }

    static void viewEmployees() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Employee emp = new Employee(
                        Integer.parseInt(data[0]),
                        data[1],
                        Double.parseDouble(data[2])
                );
                emp.display();
            }

            br.close();
        } catch (IOException e) {
            System.out.println("No records found.");
        }
    }

    static void searchEmployee(Scanner sc) {
        try {
            System.out.print("Enter ID to search: ");
            int searchId = sc.nextInt();

            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);

                if (id == searchId) {
                    Employee emp = new Employee(
                            id,
                            data[1],
                            Double.parseDouble(data[2])
                    );
                    emp.display();
                    found = true;
                    break;
                }
            }

            br.close();

            if (!found) {
                System.out.println("Employee not found.");
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}
