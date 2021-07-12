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
        .then(data => console.log(data));
    updateSumForProduct(prodId, newQuantity);

}

function updateSumForProduct(prodId, newQuantity) {
    const productSum = document.querySelector(`span[data-id="${prodId}"]`);
    const price = productSum.dataset.price;
    const currency = productSum.dataset.currency;
    let newSum = price * newQuantity;
    productSum.innerText = `${newSum.toFixed(2)} ${currency}`;
    console.log(`${newSum} ${currency}`);
}