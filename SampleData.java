import java.util.*;

public class SampleData {
    // Return ALL employees
    public static List<Map<String, String>> getEmployees() {
        List<Map<String, String>> employees = new ArrayList<>();

        Map<String, String> emp1 = new HashMap<>();
        emp1.put("id", "1");
        emp1.put("firstName", "John");
        emp1.put("lastName", "Doe");
        emp1.put("email", "john@example.com");
        emp1.put("department", "Engineering");
        emp1.put("role", "Developer");

        Map<String, String> emp2 = new HashMap<>();
        emp2.put("id", "2");
        emp2.put("firstName", "Jane");
        emp2.put("lastName", "Smith");
        emp2.put("email", "jane@example.com");
        emp2.put("department", "HR");
        emp2.put("role", "Recruiter");

        employees.add(emp1);
        employees.add(emp2);

        return employees;
    }

    // ✅ NEW: Find single employee by ID
    public static Map<String, String> getEmployeeById(int id) {
        for (Map<String, String> emp : getEmployees()) {
            if (Integer.parseInt(emp.get("id")) == id) {
                return emp;
            }
        }
        return null;
    }
}
