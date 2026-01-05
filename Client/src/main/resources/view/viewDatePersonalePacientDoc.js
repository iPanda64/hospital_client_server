datePersonale = [
    {
        username: "Loading...",
        nume: "Loading...",
        prenume: "Loading...",
        telefon: "Loading...",
        email: "Loading...",
        dataNastere: "Loading..."
    }
];

function displayDatePersonale() {
    const tableBody = document.getElementById("datePersonaleTableBody");
    if (!tableBody) {
        console.error("Element with id 'datePersonaleTableBody' not found");
        return;
    }
    tableBody.innerHTML = "";
    datePersonale.forEach(pacient => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${pacient.username}</td>
            <td>${pacient.nume}</td>
            <td>${pacient.prenume}</td>
            <td>${pacient.telefon}</td>
            <td>${pacient.email}</td>
            <td>${pacient.dataNastere}</td>
        `;
        tableBody.appendChild(row);
    });
}

function setDatePersonale(datePersonaleJson) {
    datePersonale = JSON.parse(datePersonaleJson);
    displayDatePersonale();
}

function next() {
    javaBridge.next();
}

function previous() {
    javaBridge.previous();
}

displayDatePersonale();