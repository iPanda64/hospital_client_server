let pacienti = [];

let fisaMedicala = []; 

function displayPacienti() {
    const list = document.getElementById("list-patients");
    if (!list) return;
    
    list.innerHTML = "";

    pacienti.forEach(p => {
        const li = document.createElement("li");
        li.textContent = `${p.nume} ${p.prenume}`;
        li.onclick = () => loadFisa(p.id, li); 
        list.appendChild(li);
    });
}

function setPacienti(json) {
    pacienti = JSON.parse(json);
    displayPacienti();
}

function loadFisa(idPacient, clickedLi) {
    document.getElementById("list-consultations").innerHTML = "<li class='placeholder'>Se incarca...</li>";
    document.getElementById("detail-content").innerHTML = "<p class='placeholder'>Selectati o consultatie din mijloc.</p>";

    if(window.javaBridge) {
        javaBridge.loadFisaMedicala(idPacient);
    } 
}

function setFisaMedicala(json) {
    try {
        fisaMedicala = JSON.parse(json);
    } catch (e) {
        console.error("Invalid JSON", e);
        return;
    }
    
    const list = document.getElementById("list-consultations");
    list.innerHTML = "";

    if (fisaMedicala.length === 0) {
        list.innerHTML = "<li class='placeholder'>Nu exista istoric pentru acest pacient.</li>";
        return;
    }

    fisaMedicala.forEach((cons, index) => {
        const li = document.createElement("li");
        li.innerHTML = `
            <strong>${cons.data_consultatie}</strong><br>
            <span style="color:#676">${cons.diagnostic}</span>
        `;
        li.onclick = () => showDetails(index, li); 
        list.appendChild(li);
    });
}


function showDetails(index, clickedLi) {
    const container = document.getElementById("detail-content");
    const item = fisaMedicala[index];

    if (item.prescriptie) {
        container.innerHTML = `
            <div class="card prescription-card">
                <h3 style="margin-top:0; color:#28a745;">${item.prescriptie.medicament}</h3>
                <hr style="border:0; border-top:1px solid #eee; margin:10px 0;">
                <p><strong>Doza Zilnica:</strong> <br>${item.prescriptie.doza_zilnica}</p>
                <p><strong>Durata:</strong> <br>${item.prescriptie.durata_tratament_in_zile} zile</p>
            </div>
        `;
    } else {
        container.innerHTML = `
            <div class="card empty-card">
                <p>Nu a fost prescris niciun medicament la aceasta consultatie.</p>
                <button class="btn btn-primary" onclick="showPrescriptionForm(${item.id})">Add</button>
            </div>
        `;
    }
}

function showPrescriptionForm(consultatieId) {
    const container = document.getElementById("detail-content");
    container.innerHTML = `
        <form id="prescriptionForm" onsubmit="handlePrescriptionSubmit(event, ${consultatieId})">
            <div class="form-group">
                <label for="medicament">Medicament</label>
                <input type="text" id="medicament" required>
            </div>
            <div class="form-group">
                <label for="doza">Doza Zilnica</label>
                <input type="number" id="doza" required>
            </div>
            <div class="form-group">
                <label for="durata">Durata Tratament (zile)</label>
                <input type="number" id="durata" required>
            </div>
            <button type="submit" class="btn btn-primary">Save Prescription</button>
        </form>
    `;
}

function handlePrescriptionSubmit(event, consultatieId) {
    event.preventDefault();
    const medicament = document.getElementById('medicament').value;
    const doza = document.getElementById('doza').value;
    const durata = document.getElementById('durata').value;

    if (window.javaBridge) {
        javaBridge.createPrescription(consultatieId, medicament, doza, durata);
    } else {
        console.log(`Creating prescription for consultatie ${consultatieId}:`, { medicament, doza, durata });
        const consultatie = fisaMedicala.find(f => f.id === consultatieId);
        if (consultatie) {
            consultatie.prescriptie = {
                medicament: medicament,
                doza_zilnica: doza,
                durata_tratament_in_zile: durata
            };
            const index = fisaMedicala.findIndex(f => f.id === consultatieId);
            showDetails(index, null);
        }
    }
}

function next() { if(window.javaBridge) javaBridge.next(); }
function previous() { if(window.javaBridge) javaBridge.previous(); }

displayPacienti();
