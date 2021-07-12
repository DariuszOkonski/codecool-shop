const increasingInputs = document.querySelectorAll(".quantity-input-js");
console.log(increasingInputs, " ALE ZMIENIONE!!!")

function setEventListeners(){
    increasingInputs.forEach( (input) =>
        input.addEventListener("change", (event) => {
            console.log(input.dataset.id, input.value);
            postQuantityValueChange(input.dataset.id, input.value)
        })
    )
}

function setDeleteCartItemListener() {

}

setEventListeners();


function postQuantityValueChange(prodId, newQuantity) {
    const data = {prodId: prodId, newQuantity: newQuantity};
    fetch('/modify-cart', {
        method: 'POST', // or 'PUT'
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => response.json())
        .then(() => console.log(data, "we expect object"));
    updateSumForProduct(prodId, newQuantity);
    updateTotal();

}

function _api_delete (url, callback) {
    fetch(url, {
        method: 'DELETE'
    }).then(callback)
}

function updateSumForProduct(prodId, newQuantity) {
    const productSum = document.querySelector(`span[data-id="${prodId}"]`);
    const price = productSum.dataset.price;
    const currency = productSum.dataset.currency;
    let newSum = price * newQuantity;
    productSum.innerText = `${newSum.toFixed(2)} ${currency}`;
    console.log(`${newSum} ${currency}`);
}

function updateTotal() {
    const totalElement = document.querySelector("div.summary-item > span.price");
    const priceElements =  document.querySelectorAll("div.price > span");
    let newTotal = 0;
    console.log(totalElement.innerText);
    // let currency = totalElement.innerText.split(" ")[1];
    // console.log(currency);

    for (let prices of priceElements) {
     newTotal += parseFloat(prices.innerText.split(" ")[0]);
    }

    totalElement.innerText = `${newTotal.toFixed(2)}`;



}

