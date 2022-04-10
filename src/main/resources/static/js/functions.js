function searchSong() {
    console.log("in search song");
    let searchString = document.getElementById("searchSongField");
    console.log(searchString);
    sessionStorage.
    fetch("https://api.spotify.com/v1/search?q=livin&type=track&limit=4\"", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': sessionStorage.getItem("token")
        },
    }).then(function (response) {
        // The API call was successful!
        if (response.ok) {
            console.log(response);
        } else {
            return Promise.reject(response);
        }
    }).catch(function (err) {
        // There was an error
        console.warn('Something went wrong.', err);
    });
}

window.onload = function () {
    console.log("window ready");

}