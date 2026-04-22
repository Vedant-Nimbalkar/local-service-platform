const originalFetch = window.fetch;

window.fetch = function(url, options = {}) {

    let token = localStorage.getItem("token");

    if (!options.headers) {
        options.headers = {};
    }

    if (token) {
        options.headers["Authorization"] = "Bearer " + token;
    }

    return originalFetch(url, options);
};