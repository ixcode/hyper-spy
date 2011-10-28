var navigator = navigator || {};


navigator.init = function() {
    console.log("Initialising the navigator....");

    var requestView = navigator.request.connectToDom(document);
    var responseView = navigator.response.connectToDom(document);

    requestView.onGoButton_click(function() {
        var requestUri = "http://localhost:9393/proxy?uri=" +  requestView.uri();
        navigator.http.GET(requestUri, handleResponse);
    });

    var handleResponse = function(response) {
        response.log();
        responseView.setBodyText(response.body());
    };
}

window.onLoad = navigator.init();
