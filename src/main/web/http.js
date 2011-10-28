var navigator = navigator || {};

navigator.http = navigator.http || {};

navigator.http.GET = function(uri) {
    console.log("GET " + uri + " HTTP/1.1");


    var request = new XMLHttpRequest();
    request.open("GET", uri);
    request.onreadystatechange = function (event) {
        if (request.readyState == 4) {
            if (request.status == 200)
                console.log(request.responseText)
            else
                console.log("Error: " +  request.status + " : " + request.statusText);
        }
    };
    request.send(null);
}

navigator.http.response = function(body) {

    var interface = {
        body : function() {
            return body;
        }
    }

    return interface;
}