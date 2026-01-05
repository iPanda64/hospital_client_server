programari = [
    {
        nume: "Loading...",
        prenume: "Loading...",
        data_emitere: "Loading...",
        status: "Loading..."
    }
];

function displayProgramari() {
    const tableBody = document.getElementById("programariTableBody");
    if (!tableBody) {
        console.error("Element with id 'programariTableBody' not found");
        return;
    }
    tableBody.innerHTML = "";
    programari.forEach(programare => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${programare.nume}</td>
            <td>${programare.prenume}</td>
            <td>${programare.data_emitere}</td>
            <td>${programare.status}</td>
        `;
        tableBody.appendChild(row);
    });
}

function setProgramari(programariJson) {
    programari = JSON.parse(programariJson);
    displayProgramari();
}

function next() {
    javaBridge.next();
}

function previous() {
    javaBridge.previous();
}

displayProgramari();