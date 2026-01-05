let users = [
    { 
        id: 1, 
        username: 'Loading...', 
        parola: 'Loading...', 
        nume: 'Loading...', 
        prenume: 'Loading...', 
        telefon: 'Loading...', 
        email: 'Loading...', 
        dataNasterii: 'Loading...' 
    }
];

const modal = document.getElementById('userModal');
const tableBody = document.getElementById('userTableBody');
const form = document.getElementById('userForm');
const modalTitle = document.getElementById('modalTitle');


async function fetchData(){
    javaBridge.getAllUsers()
}

function setAllUsers(usersJson){
    users = JSON.parse(usersJson);
    renderTable();
}

function renderTable() {
    tableBody.innerHTML = '';
    users.forEach(user => {
        const row = `
            <tr>
                <td>${user.id}</td>
                <td><strong>${user.username}</strong></td>
                <td>******</td> <td>${user.nume}</td>
                <td>${user.prenume}</td>
                <td>${user.telefon}</td>
                <td>${user.email}</td>
                <td>${user.dataNasterii}</td>
                <td>${user.tip}</td>
                <td>
                    <button class="btn btn-edit" onclick="editUser(${user.id})">Edit</button>
                    <button class="btn btn-danger" onclick="deleteUser(${user.id})">Delete</button>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
}

function openModal(isEdit = false) {
    modal.style.display = 'flex';
    modalTitle.innerText = isEdit ? 'Edit User' : 'Add New User';
    document.getElementById('tip-form-group').style.display = isEdit ? 'none' : 'block';
    
    if (!isEdit) {
        form.reset();
        document.getElementById('userId').value = '';
    }
}

function closeModal() {
    modal.style.display = 'none';
}

window.onclick = function(event) {
    if (event.target == modal) closeModal();
}

function handleFormSubmit(e) {
    e.preventDefault();
    const dateInput = document.getElementById('dataNasterii').value;
    const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
    if (dateInput && !dateRegex.test(dateInput)) {
        alert("Please enter the date in YYYY-MM-DD format.");
        return;
    }

    const id = document.getElementById('userId').value;

    if (id) {
        editUserBridge(id);
    } else {
        addUser();
    }

    fetchData();
    closeModal();
}

function addUser(){
    const telefonRaw = document.getElementById('telefon').value;
    const telefonNum = telefonRaw === '' ? null : parseInt(telefonRaw, 10);
    javaBridge.addUser(
        document.getElementById('username').value,
        document.getElementById('password').value,
        document.getElementById('nume').value,
        document.getElementById('prenume').value,
        telefonNum,
        document.getElementById('email').value,
        document.getElementById('dataNasterii').value,
        document.getElementById('tip').value
    );
}

function editUserBridge(id) {
    const telefonRaw = document.getElementById('telefon').value;
    const telefonNum = telefonRaw === '' ? null : parseInt(telefonRaw, 10);
    javaBridge.editUser(
        id,
        document.getElementById('username').value,
        document.getElementById('password').value,
        document.getElementById('nume').value,
        document.getElementById('prenume').value,
        telefonNum,
        document.getElementById('email').value,
        document.getElementById('dataNasterii').value,
        document.getElementById('tip').value
    );
}

function editUser(id) {
    const user = users.find(u => u.id === id);
    if (user) {
        document.getElementById('userId').value = user.id;
        document.getElementById('username').value = user.username;
        document.getElementById('password').value = user.parola;
        document.getElementById('nume').value = user.nume;
        document.getElementById('prenume').value = user.prenume;
        document.getElementById('telefon').value = user.telefon;
        document.getElementById('email').value = user.email;
        document.getElementById('dataNasterii').value = user.dataNasterii;
        document.getElementById('tip').value = user.tip;
        
        openModal(true);
    }
}

function deleteUser(id) {
        javaBridge.deleteUser(id);
        fetchData();
        renderTable();
}

function  next(){
    javaBridge.next();
}

function  previous(){
    javaBridge.previous();
}
renderTable();