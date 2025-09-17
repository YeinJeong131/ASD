const download_simple_txt = document.getElementById("button_download_as_txt");

download_simple_txt.onclick = () => {
    window.location.href = "/api/download/1/txt";
    console.log("The button was clicked");
};

const result = document.getElementById("results");
