var navigator = navigator || {};
navigator.request = navigator.request || {};

navigator.request.view = function(dom) {

    var uriInput;
    var goButton;

    var interface = {

    }

    var init = function() {
        uriInput = dom.getElementsByName("uri")[0];
        goButton = dom.getElementsByName("go")[0];
    }

    init();
    return interface;
}

