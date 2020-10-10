document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?
});

function validateForm() {
	if (empID.value === undefined || empID.value === "") {
		// console.log("not valid b/c id is blank");
		displayError("The id is not valid because it is blank");
		return false;
	}

	if (isNaN(empID.value)) {
		// console.log("not valid b/c id is not a number");
		displayError("The id is not valid because it is not at number");
		return false;
	}

	if (pass.value === undefined || pass.value === "") {
		// console.log("not valid b/c pass is blank");
		displayError("The password is not valid because it is blank");
		return false;
	}
	console.log("valid");
	return true;
}
