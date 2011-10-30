var navigator = navigator || {};
navigator.request = navigator.request || {};

navigator.request.connectToDom = function(dom) {
    return navigator.request.view(dom.getElementById("request"));
}

navigator.request.view = function(rootElement) {

    var uriInput;
    var goButton;

    var interface = {
        onGoButton_click : function(handler) {
            goButton.onclick = handler;
        },

        uri: function() {
            return uriInput.value;
        }
    }

    var init = function() {
        uriInput = rootElement.getElementsByClassName("uri")[0];
        goButton = rootElement.getElementsByClassName("go")[0];
    }

    init();
    return interface;
}

