
// This object will be replaced by the Java backend.
// It provides a bridge for JavaScript to call Java methods.
var javaBridge;

window.onload = function() {
    // This is called by Java to set up the bridge.
    // The 'bridge' parameter is a Java object.
    window.setBridge = function(bridge) {
        javaBridge = bridge;
    }
};

// Called when the 'Send' button is clicked in the HTML.
function sendMessage() {
    const messageInput = document.getElementById('message');
    const message = messageInput.value;
    if (message && javaBridge) {
        // Add message to the UI
        displayMessage(message, 'client');
        // Call the Java method to send the message to the server
        javaBridge.sendMessage(message);
        messageInput.value = '';
    }
}

// This function is called by the Java backend to display messages from the server.
function displayMessage(message, type) {
    const log = document.getElementById('log');
    const messageDiv = document.createElement('div');
    messageDiv.className = 'message ' + type + '-message';
    messageDiv.textContent = message;
    log.appendChild(messageDiv);
    log.scrollTop = log.scrollHeight; // Scroll to bottom
}
