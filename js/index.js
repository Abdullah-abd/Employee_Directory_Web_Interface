import {
  addEmployee,
  deleteEmployee,
  getEmployeeById,
  getEmployees,
  updateEmployee,
} from "./data.js";

const modal = document.getElementById("modal");
const form = document.getElementById("employeeForm");
const modalTitle = document.getElementById("modalTitle");

function openModal(emp) {
  modal.style.display = "flex";
  if (emp) {
    modalTitle.innerText = "Edit Employee";
    form.empId.value = emp.id;
    form.firstName.value = emp.firstName;
    form.lastName.value = emp.lastName;
    form.email.value = emp.email;
    form.department.value = emp.department;
    form.role.value = emp.role;
  } else {
    modalTitle.innerText = "Add Employee";
    form.reset();
    form.empId.value = "";
  }
}

function closeModal() {
  modal.style.display = "none";
}

document.getElementById("addEmployeeBtn").addEventListener("click", () => {
  openModal();
});

document.getElementById("cancelBtn").addEventListener("click", closeModal);

form.addEventListener("submit", (e) => {
  e.preventDefault();
  const employee = {
    id: form.empId.value ? Number(form.empId.value) : Date.now(),
    firstName: form.firstName.value.trim(),
    lastName: form.lastName.value.trim(),
    email: form.email.value.trim(),
    department: form.department.value.trim(),
    role: form.role.value.trim(),
  };

  if (form.empId.value) {
    updateEmployee(employee);
  } else {
    addEmployee(employee);
  }

  closeModal();
  renderEmployees(getEmployees());
});

function renderEmployees(employees) {
  const container = document.getElementById("employeeList");
  container.innerHTML = "";

  if (!employees.length) {
    container.innerHTML = "<p>No employees found.</p>";
    return;
  }

  employees.forEach((emp) => {
    const card = document.createElement("div");
    card.className = "employee-card";
    card.innerHTML = `
      <h3>${emp.firstName} ${emp.lastName}</h3>
      <p><strong>Email:</strong> ${emp.email}</p>
      <p><strong>Department:</strong> ${emp.department}</p>
      <p><strong>Role:</strong> ${emp.role}</p>
      <div class="card-actions">
        <button class="editBtn" data-id="${emp.id}">Edit</button>
        <button class="deleteBtn" data-id="${emp.id}">Delete</button>
      </div>
    `;
    container.appendChild(card);
  });

  addListeners();
}

function addListeners() {
  document.querySelectorAll(".editBtn").forEach((btn) => {
    btn.addEventListener("click", (e) => {
      const id = Number(e.target.dataset.id);
      const emp = getEmployeeById(id);
      openModal(emp);
    });
  });

  document.querySelectorAll(".deleteBtn").forEach((btn) => {
    btn.addEventListener("click", (e) => {
      const id = Number(e.target.dataset.id);
      deleteEmployee(id);
      renderEmployees(getEmployees());
    });
  });
}

document.getElementById("filterBtn").addEventListener("click", () => {
  const query = document.getElementById("searchInput").value.toLowerCase();
  const filtered = getEmployees().filter(
    (emp) =>
      emp.firstName.toLowerCase().includes(query) ||
      emp.lastName.toLowerCase().includes(query) ||
      emp.email.toLowerCase().includes(query)
  );
  renderEmployees(filtered);
});

renderEmployees(getEmployees());
