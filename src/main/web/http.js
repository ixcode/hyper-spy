var navigator = navigator || {};

navigator.http = navigator.http || {};

navigator.http.GET = function(uri, callback) {
    console.log("GET " + uri + " HTTP/1.1");


    var request = new XMLHttpRequest();
    request.open("GET", uri);
    request.onreadystatechange = function (event) {
        if (request.readyState == 4) {
            callback(navigator.http.response(request.status, request.statusText,
                                             request.getAllResponseHeaders(), request.responseText))
        }
    };
    request.send(null);
}

navigator.http.response = function(status, statusText, headers, responseText) {

    var interface = {
        log : function() {
            console.log("HTTP/1.1 " + status + " " + statusText);
            console.log(headers);
            console.log(responseText);
        },

        body : function() {
            return responseText;
        }
    }


    return interface;
}