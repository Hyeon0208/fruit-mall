$(() => {
    let totalPrice = 0;
    $(".orderProductPrice").each((i, e) => {
        const price = parseInt($(e).text().replace(/[^0-9]/g, ''));
        totalPrice += price;
    });
    $("#orderTotalPrice").text(totalPrice.toLocaleString() + "원");

    let totalDiscount = 0;
    $(".orderDiscountAppliedPrice").each((i, e) => {
        let discount = parseInt($(e).text().replace(/[^0-9]/g, ''));
        totalDiscount += discount;
    });
    $("#orderTotalDiscount").text("-" + totalDiscount.toLocaleString() + "원");

    if (totalPrice - totalDiscount >= 50000) {
        $("#orderDeliveryFee").text("무료");
    } else {
        $("#orderDeliveryFee").text("3,000원");
    }

    let orderDeliveryFee = $("#orderDeliveryFee").text();
    if (orderDeliveryFee === "무료") {
        orderDeliveryFee = 0;
    } else {
        orderDeliveryFee = 3000;
    }
    $("#paymentPrice").text((totalPrice - totalDiscount + orderDeliveryFee).toLocaleString() + "원");
});