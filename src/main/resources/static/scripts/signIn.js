document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?
});

function validateForm() {
    //head//

    if (employeeId.value === undefined || employeeId.value === "") {
        displayError("The id is not valid because it is blank");
    }

    if (isNaN(employeeId.value)) {
        displayError("The id is not valid because it is not at number");
    }

    if (password.value === undefined || password.value === "") {
        displayError("The password is not valid because it is blank");
    }

	// TODO: Validate the user input
    let idIsNotEmpty = document.getElementById('id-input').value.length > 0;

    let passIsNotEmpty = document.getElementById('pass-input').value.length > 0;

    if(idIsNotEmpty && passIsNotEmpty && isANumber(document.getElementById('id-input').value)){
        let errors = document.getElementsByClassName('invalid-input');
        errors[0].style.display= "none";
        errors[1].style.display = "none";
        return true;
    }
    else{
        if(!idIsNotEmpty){
            document.getElementById('blank-employeeId-error').style = "block";
        }
        if(!passIsNotEmpty){
            document.getElementById('blank-pass-error').style = "block";
        }
        return false;
    }
}

function isANumber(str) {
    return !isNaN(str) && !isNaN(parseFloat(str))
}