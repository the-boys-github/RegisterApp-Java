document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?
});

function validateForm() {
	// TODO: Validate the user input
    let idIsNotEmpty = document.getElementById('id-input').value.length > 0;

    let passIsNotEmpty = document.getElementById('pass-input').value.length > 0;

    return  idIsNotEmpty && passIsNotEmpty && isANumber(document.getElementById('id-input').value);
}

function isANumber(str) {
    return !isNaN(str) && !isNaN(parseFloat(str))
}