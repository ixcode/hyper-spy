var navigator = navigator || {};


navigator.init = function() {
    console.log("Initialising the navigator....");

    var requestView = navigator.request.connectToDom(document);
    var responseView = navigator.response.connectToDom(document);

    requestView.onGoButton_click(function() {
        navigator.http.GET(requestView.uri());
    })
}

window.onLoad = navigator.init();
