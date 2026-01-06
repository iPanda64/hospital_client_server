const appointmentsBody = document.getElementById('appointmentsBody');
const historyBody = document.getElementById('historyBody');
const facturiBody = document.getElementById('facturiBody');
const modal = document.getElementById('appModal');

window.onload = () => {
    javaBridge.getProgramari();
};

function displayProgramari(jsonJson) {
    const apps = JSON.parse(jsonJson);
    appointmentsBody.innerHTML = '';
    apps.forEach(app => {
        const row = `
            <tr>
                <td>${app.id}</td>
                <td>${app.data}</td>
                <td><span class="status-badge">${app.status}</span></td>
            </tr>
        `;
        appointmentsBody.innerHTML += row;
    });
}

function displayHistory(jsonJson) {
    console.log("Date primite pentru istoric:", jsonJson); // Debug
    const history = JSON.parse(jsonJson);
    const historyBody = document.getElementById('historyBody');
    if (!historyBody) return;
    historyBody.innerHTML = '';
    history.forEach(h => {
        const row = `
            <tr>
                <td>${h.id}</td>
                <td>${h.data}</td>
                <td><strong>${h.diagnostic}</strong></td>
            </tr>
        `;
        historyBody.innerHTML += row;
    });
}

function displayFacturi(jsonJson) {
    const facturi = JSON.parse(jsonJson);
    facturiBody.innerHTML = '';
    facturi.forEach(f => {
        const statusPlata = f.platita ? '<span style="color: green;">Platita</span>' : '<span style="color: red;">Neplatita</span>';
        const row = `
            <tr>
                <td>${f.id}</td>
                <td>${f.suma} RON</td>
                <td>${statusPlata}</td>
            </tr>
        `;
        facturiBody.innerHTML += row;
    });
}
function showSection(sectionId) {
    document.querySelectorAll('.content-section').forEach(section => {
        section.style.display = 'none';
    });
    document.getElementById('section-' + sectionId).style.display = 'block';
    const titles = { 'programari': 'Programările Mele', 'istoric': 'Istoric Medical', 'facturi': 'Facturi și Plăți' };
    document.getElementById('mainTitle').innerText = titles[sectionId];
    if (sectionId === 'programari') javaBridge.getProgramari();
    if (sectionId === 'istoric') javaBridge.getHistory();
    if (sectionId === 'facturi') javaBridge.getFacturi();
}

function openModal() { modal.style.display = 'flex'; }
function closeModal() { modal.style.display = 'none'; }

function handleFormSubmit(e) {
    e.preventDefault();
    const data = document.getElementById('dataProg').value;
    const docId = document.getElementById('idDoctor').value;

    javaBridge.createProgramare(data, parseInt(docId));

    closeModal();
}window.onclick = (event) => { if (event.target == modal) closeModal(); }