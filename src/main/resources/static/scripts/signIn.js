document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?
});

function validateForm() {
	if (empID.value === undefined || empID.value === "") {
		console.log("not valid b/c id is blank");
		return false;
	}

	if (isNaN(empID.value)) {
		console.log("not valid b/c id is not a number");
		return false;
	}

	if (pass.value === undefined || pass.value === "") {
		console.log("not valid b/c pass is blank");
		return false;
	}
	console.log("valid");
	return true;
}
