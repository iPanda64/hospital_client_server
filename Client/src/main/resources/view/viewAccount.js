var javaBridge;
window.onload = function() {};

function setAccount(info)
{
    const[username,nume,prenume,telefon,email,data]=info.split(',')
    document.getElementById("username").innerText=username
    document.getElementById("nume").innerText=nume
    document.getElementById("prenume").innerText=prenume
    document.getElementById("numar_telefon").innerText=telefon
    document.getElementById("email").innerText=email
    document.getElementById("data_nasterii").innerText=data

}
async function fetchData(){
    javaBridge.getAccount()
}
function next(){
    javaBridge.next()
}
function goBackToLogin(){
    javaBridge.goBackToLogin()
}
function previous(){
    javaBridge.previous()
}
