var javaBridge;

// This function is called by Java when the page loads to set up the bridge object.
window.onload = function() {};

function login() {
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const username = usernameInput.value;
    const password = passwordInput.value;

    if (username && password && window.javaBridge) {
        javaBridge.login(username, password);
    } else {
        if (!window.javaBridge) {
            console.error("Java bridge is not set up.");
        }
        if (!username || !password) {
            console.error("Username and password are required.");
        }
    }
}

function switchToCreateAccount() {
    if (window.javaBridge) {
        javaBridge.switchToCreateAccount();
    } else {
        console.error("Java bridge is not set up.");
    }
}
