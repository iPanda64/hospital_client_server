// --- GLOBAL VARIABLES ---
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
            </div>
        `;
    }
}

function next() { if(window.javaBridge) javaBridge.next(); }
function previous() { if(window.javaBridge) javaBridge.previous(); }

displayPacienti();