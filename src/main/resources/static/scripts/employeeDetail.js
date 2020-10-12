let hideEmployeeSavedAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
	// TODO: Things that need doing when the view is loaded
	getSaveActionElement().addEventListener("click", saveActionClick);
});

// Save
function saveActionClick(event) {
	if (!validateSave()) {
		return;
	}

	const saveActionElement = event.target;
	saveActionElement.disabled = true;

	const employeeId = getEmployeeId();
	const employeeIdIsDefined = ((employeeId != null) && (employeeId.trim() !== ""));
	const saveActionUrl = ("/api/employee/" + (employeeIdIsDefined ? employeeId : ""));
	const saveEmployeeRequest = {
		id: employeeId,
		firstName: getFirstName(),
		lastName: getLastName(),
		password: getPassword(),
		type: getEmployeeType()
	};

	if (employeeIdIsDefined) {
		ajaxPatch(saveActionUrl, saveEmployeeRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayEmployeeSavedAlertModal();
			}
		});
	} else {
		ajaxPost(saveActionUrl, saveEmployeeRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayEmployeeSavedAlertModal();

				if ((callbackResponse.data != null) && (callbackResponse.data.id != null) && (callbackResponse.data.id.trim() !== "")) {
					document.getElementById("employeeEmployeeId").classList.remove("hidden");

					setEmployeeId(callbackResponse.data.id.trim());
				}
			}
		});
	}
};

function validateSave() {

	const firstName = getFirstName();
	const firstNameElement = getFirstNameElement();
	if ((firstName == null) || (firstName.trim() === "")) {
		displayError("Please provide a valid first name.");
		firstNameElement.focus();
		firstNameElement.select();
		return false;
	}

	const lastName = getLastName();
	const lastNameElement = getLastNameElement();
	if ((lastName == null) || (lastName.trim() === "")) {
		displayError("Please provide a valid last name.");
		lastNameElement.focus();
		lastNameElement.select();
		return false;
	}

	const password = getPassword();
	const passwordElement = getPasswordElement();
	if ((password == null) || (password.trim() === "")) {
		displayError("Please provide a password.");
		passwordElement.focus();
		passwordElement.select();
		return false;
	}

	const confirmPassword = getConfirmPassword();
	const confirmPasswordElement = getConfirmPasswordElement();
	if ((confirmPassword == null) || (confirmPassword.trim() === "")) {
		displayError("Please provide a valid confirm password.");
		confirmPasswordElement.focus();
		confirmPasswordElement.select();
		return false;
	}

	if(password != confirmPassword) {
		displayError("Passwords do not match.");
		passwordElement.focus();
		passwordElement.select();
		confirmPasswordElement.focus();
		confirmPasswordElement.select();
		return false;
	}

	const type = getEmployeeType();
	const typeElement = getEmployeeTypeElement();
	if (type == "Select") {
		displayError("Please choose an employee type");
		typeElement.focus();
		typeElement.select();
		return false;
	}

}

function displayEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	const savedAlertModalElement = getSavedAlertModalElement();
	savedAlertModalElement.style.display = "none";
	savedAlertModalElement.style.display = "block";

	hideEmployeeSavedAlertTimer = setTimeout(hideEmployeeSavedAlertModal, 1200);
}

function hideEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	getSavedAlertModalElement().style.display = "none";
}
// End save

//Getters and setters
function getSavedAlertModalElement() {
	return document.getElementById("employeeSavedAlertModal");
}

function getSaveActionElement() {
	return document.getElementById("saveButton");
}

function setEmployeeId(employeeId) {
	getEmployeeIdElement().value = employeeId;
}

function getEmployeeIdElement() {
	return document.getElementById("employeeEmployeeId");
}

function getFirstName() {
	return getFirstNameElement().value;
}

function getFirstNameElement() {
	return document.getElementById("employeeFirstName");
}

function getLastName() {
	return getLastNameElement().value;
}

function getLastNameElement() {
	return document.getElementById("employeeLastName");
}

function getPassword() {
	return getPasswordElement().value;
}

function getPasswordElement() {
	return document.getElementById("employeePassword");
}

function getConfirmPassword() {
	return getConfirmPasswordElement().value;
}

function getConfirmPasswordElement() {
	return document.getElementById("employeeConfirmPassword");
}

function getEmployeeType() {
	var e = getEmployeeTypeElement();
	return e.options[e.selectedIndex].value;
}

function getEmployeeTypeElement() {
	return document.getElementById("employeeType");
}
//End getters and setters