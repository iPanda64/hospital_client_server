const programariBody = document.getElementById('programariBody');
const pacientiBody = document.getElementById('pacientiBody');
const modal = document.getElementById('appModal');

window.onload = () => {
    javaBridge.getProgramari();
    javaBridge.getListaPacienti();
};

function displayProgramari(jsonJson) {
    const apps = JSON.parse(jsonJson);
    programariBody.innerHTML = '';
    apps.forEach(app => {
        const row = `
            <tr>
                <td>${app.id}</td>
                <td>${app.id_pacient}</td>
                <td>${app.data}</td>
                <td><span class="status-badge status-${app.status.toLowerCase()}">${app.status}</span></td>
                <td>
                    ${app.status !== 'Aprobata' ? `<button class="btn-action btn-approve" onclick="javaBridge.approveProgramare(${app.id})">Aproba</button>` : ''}
    <button class="btn-action btn-delete" onclick="javaBridge.deleteProgramare(${app.id})">Respinge</button>
    <button class="btn-action" style="background-color: #9b59b6; color: white; border: none;" onclick="javaBridge.printFactura(${app.id})">Factura</button>
                </td>
            </tr>
        `;
        programariBody.innerHTML += row;
    });
}

function displayPacienti(jsonJson) {
    const pacienti = JSON.parse(jsonJson);
    pacientiBody.innerHTML = '';
    pacienti.forEach(p => {
        const row = `
            <tr>
                <td>${p.id}</td>
                <td>${p.nume}</td>
                <td>${p.prenume}</td>
                <td>${p.telefon}</td>
                <td>
                    <button class="btn-action" onclick="javaBridge.getPrescriptii(${p.id})">Prescriptii</button>
                </td>
            </tr>
        `;
        pacientiBody.innerHTML += row;
    });
}

function showSection(sectionId) {
    document.querySelectorAll('.content-section').forEach(s => s.style.display = 'none');
    document.querySelectorAll('.btn-tab').forEach(b => b.classList.remove('active'));

    document.getElementById('section-' + sectionId).style.display = 'block';
    document.getElementById('tab-' + sectionId).classList.add('active');

    if(sectionId === 'programari') javaBridge.getProgramari();
    if(sectionId === 'pacienti') javaBridge.getListaPacienti();
}

function openModal() { modal.style.display = 'flex'; }
function closeModal() { modal.style.display = 'none'; }

function handleFormSubmit(e) {
    e.preventDefault();
    const idPac = document.getElementById('idPacient').value;
    const idDoc = document.getElementById('idDoctor').value;
    const data = document.getElementById('dataProg').value;

    javaBridge.createProgramare(data, parseInt(idPac), parseInt(idDoc));
    closeModal();
}
function displayPrescriptii(jsonJson) {
    const prescriptii = JSON.parse(jsonJson);
    const sectiune = document.getElementById('section-prescriptii');
    const container = document.getElementById('listaPrescriptiiUI');

    if (!sectiune || !container) {
        console.error("Required elements for prescriptions not found.");
        return;
    }

    document.querySelectorAll('.content-section').forEach(s => s.style.display = 'none');
    sectiune.style.display = 'block';
    document.querySelectorAll('.btn-tab').forEach(b => b.classList.remove('active'));

    if (prescriptii.length === 0) {
        container.innerHTML = "<p class='placeholder'>Nu exista prescriptii pentru acest pacient.</p>";
    } else {
        let html = "<ul style='list-style: none; padding: 0;'>";
        prescriptii.forEach(p => {
            const medicament = p.medicament.replace(/[\[\]]/g, ''); // Clean up the medicament string
            html += `<li style='background: #f1f2f6; margin: 5px 0; padding: 10px; border-radius: 5px;'>
                        <strong>Medicament:</strong> ${medicament} | 
                        <strong>Doza:</strong> ${p.doza}mg | 
                        <strong>Durata:</strong> ${p.durata} zile
                     </li>`;
        });
        html += "</ul>";
        container.innerHTML = html;
    }
}

function displayPacientDetails(jsonJson) {
    const p = JSON.parse(jsonJson);
    alert(`Detalii Pacient:\nNume: ${p.nume} ${p.prenume}\nTelefon: ${p.telefon}\nEmail: ${p.email}`);
}

function showFacturaPreview(jsonJson) {
    const f = JSON.parse(jsonJson);
    alert(`Factura ID: ${f.id}\nSuma: ${f.suma} RON\nData: ${f.data}`);
}
function next(){
    javaBridge.next()
}
function previous(){
    javaBridge.previous()
}