var javaBridge;

// This function is called by Java when the page loads to set up the bridge object.
window.onload = function() {};

function login() {
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const username = usernameInput.value;
    const password = passwordInput.value;

    if (username && password && window.javaBridge) {
        // As requested, sending the content of the inputs to the server.
        // The server will need to handle this "login <user> <pass>" message.
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
