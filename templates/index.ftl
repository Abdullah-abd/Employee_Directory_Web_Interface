<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Employee Directory</title>
  <link rel="stylesheet" href="css/styles.css" />
</head>
<body>
  <header class="header">
    <h1>Employee Directory</h1>
    <div class="search-filter">
      <input type="text" id="searchInput" placeholder="Search by name or email" />
      <button id="filterBtn">Filter</button>
    </div>
  </header>

  <section class="controls">
    <label for="sortSelect">Sort by:</label>
    <select id="sortSelect">
      <option value="">--Select--</option>
      <option value="firstName">First Name</option>
      <option value="department">Department</option>
    </select>

    <label for="limitSelect">Show:</label>
    <select id="limitSelect">
      <option>10</option>
      <option>25</option>
      <option>50</option>
      <option>100</option>
    </select>

    <button id="addEmployeeBtn">+ Add Employee</button>
  </section>

  <main>
    <div id="employeeList" class="employee-grid">
      <#list employees as emp>
        <div class="employee-card" data-id="${emp.id}">
          <h3>${emp.firstName} ${emp.lastName}</h3>
          <p><strong>Email:</strong> ${emp.email}</p>
          <p><strong>Department:</strong> ${emp.department}</p>
          <p><strong>Role:</strong> ${emp.role}</p>
          <div class="card-actions">
            <button class="editBtn" data-id="${emp.id}">Edit</button>
            <button class="deleteBtn" data-id="${emp.id}">Delete</button>
          </div>
        </div>
      </#list>
    </div>
  </main>

  <footer class="footer">
    <p>&copy; 2025 Employee Directory</p>
  </footer>

  <!-- ✅ Modal -->
  <div id="modal" class="modal">
    <div class="modal-content">
      <h2 id="modalTitle">Add Employee</h2>
      <form id="employeeForm">
        <input type="hidden" id="empId" />
        <div class="form-group">
          <label for="firstName">First Name<span>*</span></label>
          <input type="text" id="firstName" name="firstName" required />
        </div>
        <div class="form-group">
          <label for="lastName">Last Name</label>
          <input type="text" id="lastName" name="lastName" />
        </div>
        <div class="form-group">
          <label for="email">Email<span>*</span></label>
          <input type="email" id="email" name="email" required />
        </div>
        <div class="form-group">
          <label for="department">Department<span>*</span></label>
          <input type="text" id="department" name="department" required />
        </div>
        <div class="form-group">
          <label for="role">Role</label>
          <input type="text" id="role" name="role" />
        </div>
        <div class="form-actions">
          <button type="submit" id="submitBtn">Save</button>
          <button type="button" id="cancelBtn">Cancel</button>
        </div>
      </form>
    </div>
  </div>

  <script type="module" src="js/index.js"></script>
</body>
</html>
