var navigator = navigator || {};


navigator.init = function() {
    console.log("Initialising the navigator....");

    var view = navigator.response.connectToDom(document);
}

window.onLoad = navigator.init();
