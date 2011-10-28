var navigator = navigator || {};
navigator.response = navigator.response || {};

navigator.response.connectToDom = function(dom) {
    return navigator.response.view(dom.getElementById("response"));
}

navigator.response.view = function(rootElement) {
    var responseBodyDiv;

    var interface = {
        setBodyText : function(bodyText) {
            console.log("Updating body...");
            responseBodyDiv.innerHTML = bodyText;
        },

        clear : function() {
            interface.setBodyText("Response body...");
            markAsEmpty();
        }
    };

    var markAsEmpty = function() {
        responseBodyDiv.setAttribute("class", "body empty");
    }

    var init = function() {
        responseBodyDiv = rootElement.getElementsByClassName("body")[0];
        interface.clear();
    };


    init();
    return interface;
}




