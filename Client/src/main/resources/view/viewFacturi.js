 facturi = [
{
    id:   "Loading...",
    id_consultatie:  "Loading...",
    data_emitere: "Loading...",
    suma: "Loading..."}]

function displayFacturi() {
    const tableBody=document.getElementById("facturiTableBody");
    tableBody.innerHTML = "";
    facturi.forEach(factura=>{
        const row = document.createElement("tr");
        row.innerHTML = `
        <td>${factura.id}</td>
        <td>${factura.id_consultatie}</td>
        <td>${factura.data_emitere}</td>
        <td>${factura.suma} RON</td> 
        `;
    tableBody.appendChild(row); }
    )
}
function setFacturi(facturiJson){
    facturi=JSON.parse(facturiJson);
    displayFacturi();
}
function next(){
    javaBridge.next()
}
function previous(){
    javaBridge.previous()
}
displayFacturi()
