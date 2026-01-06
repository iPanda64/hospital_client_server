programari = [
    {
        id: 0,
        nume: "Loading...",
        prenume: "Loading...",
        data_emitere: "Loading...",
        status: "Loading..."
    }
];

const modal = document.getElementById('consultatieModal');
const form = document.getElementById('consultatieForm');
const modalTitle = document.getElementById('modalTitle');

function displayProgramari() {
    const tableBody = document.getElementById("programariTableBody");
    if (!tableBody) {
        console.error("Element with id 'programariTableBody' not found");
        return;
    }
    tableBody.innerHTML = "";
    programari.forEach(programare => {
        const row = document.createElement("tr");
        row.onclick = () => openModal(programare.id);
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

function openModal(programareId) {
    modal.style.display = 'flex';
    form.reset();
    document.getElementById('programareId').value = programareId;
}

function closeModal() {
    modal.style.display = 'none';
}

window.onclick = function(event) {
    if (event.target == modal) closeModal();
}

function handleFormSubmit(e) {
    e.preventDefault();
    const programareId = document.getElementById('programareId').value;
    
    javaBridge.createConsultatie(
        programareId,
        document.getElementById('diagnostic').value,
        document.getElementById('simptome').value,
        document.getElementById('cost').value,
        document.getElementById('data').value
    );

    closeModal();
}

function next() {
    javaBridge.next();
}

function previous() {
    javaBridge.previous();
}

displayProgramari();
