var javaBridge;

window.onload = function() {};

function createAccount() {
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;
  const name     = document.getElementById('name').value;
  const surname  = document.getElementById('surname').value;
  const phone    = document.getElementById('phone').value;
  const email    = document.getElementById('email').value;
  const birth    = document.getElementById('birth').value;

  if (username && password && name && surname && phone && email && birth && window.javaBridge) {
    javaBridge.getAccount(username, password,name,surname,phone,email,birth);
  } else {
    if (!window.javaBridge) {
      console.error("Java bridge is not set up.");
    }
  }
}

function goBackToLogin() {
  if (window.javaBridge) {
    javaBridge.goBackToLogin();
  } else {
    console.error("Java bridge is not set up.");
  }
}