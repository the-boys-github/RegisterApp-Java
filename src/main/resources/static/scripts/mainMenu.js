document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("startTransactionButton").addEventListener("click",startTransaction);
    document.getElementById("viewProductsButton").addEventListener("click",showProducts);
    document.getElementById("createEmployeeButton").addEventListener("click",showEmployee);
    document.getElementById("salesReportButton").addEventListener("click",salesReport);
    document.getElementById("cashierReportButton").addEventListener("click",cashierReport);
});

function showProducts(event) {
    window.location.assign("/productListing/");
}

function showEmployee(event) {
    window.location.assign("/employeeDetail/");
}

function startTransaction() {
    displayError("Functionality has not yet been implemented.");
}

function salesReport() {
    displayError("Functionality has not yet been implemented.");
}

function cashierReport() {
    displayError("Functionality has not yet been implemented.");
}