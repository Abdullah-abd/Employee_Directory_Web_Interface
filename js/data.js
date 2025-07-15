// js/data.js

let employees = [
  {
    id: 1,
    firstName: "Abdullah",
    lastName: "Abd",
    email: "abdullah@example.com",
    department: "Engineering",
    role: "Frontend Developer",
  },
  {
    id: 2,
    firstName: "Sara",
    lastName: "Khan",
    email: "sara.k@example.com",
    department: "Marketing",
    role: "SEO Specialist",
  },
];

export function getEmployees() {
  return employees;
}

export function addEmployee(emp) {
  emp.id = Date.now(); // simple unique ID
  employees.push(emp);
}

export function updateEmployee(updatedEmp) {
  employees = employees.map((emp) =>
    emp.id === updatedEmp.id ? updatedEmp : emp
  );
}

export function deleteEmployee(id) {
  employees = employees.filter((emp) => emp.id !== id);
}

export function getEmployeeById(id) {
  return employees.find((emp) => emp.id === id);
}
