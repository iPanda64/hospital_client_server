const tableBody = document.getElementById('appointmentsBody');
const modal = document.getElementById('appModal');

window.onload = () => {
    javaBridge.getProgramari();
};

function displayProgramari(jsonJson) {
    const apps = JSON.parse(jsonJson);
    tableBody.innerHTML = '';
    apps.forEach(app => {
        const row = `
            <tr>
                <td>${app.id}</td>
                <td>${app.data}</td>
                <td><span class="status-badge">${app.status}</span></td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
}

function openModal() { modal.style.display = 'flex'; }
function closeModal() { modal.style.display = 'none'; }

function handleFormSubmit(e) {
    e.preventDefault();
    const data = document.getElementById('dataProg').value;
    const docId = document.getElementById('idDoctor').value;
    javaBridge.createProgramare(data, parseInt(docId)); //pt creare programare

    closeModal();
}

window.onclick = (event) => { if (event.target == modal) closeModal(); }