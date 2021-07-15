let orderValue = null;



function getOrderValue() {
    const data = {orderId: document.getElementById("order_id").dataset.id};
    console.log(data);
    fetch(`/get-order-value?order-id=${data.orderId}`, {
        method: 'GET', // or 'PUT'
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => response.json())
        .then((json) => {
            console.log(data, "we expect object")
            orderValue = json.order;
        });
}

paypal.Buttons({
    createOrder: function(data, actions) {
        return actions.order.create({
            purchase_units: [{
                amount: {
                    value: orderValue
                }
            }]
        });
    },
    onApprove: function(data, actions) {
        return actions.order.capture().then(function(details) {
            alert('Transaction completed by ' + details.payer.name.given_name);
        });
    }
}).render('#paypal-button-container');