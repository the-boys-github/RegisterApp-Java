document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?
});

function validateForm() {
	if (empID.value === undefined || empID.value === "") {
		displayError("The id is not valid because it is blank");
		return false;
	}

	if (isNaN(empID.value)) {
		displayError("The id is not valid because it is not at number");
		return false;
	}

	if (pass.value === undefined || pass.value === "") {
		displayError("The password is not valid because it is blank");
		return false;
	}
	return true;
}

function isANumber(str) {
    return !isNaN(str) && !isNaN(parseFloat(str))
}