$(() => {
    // 세션 정보가 없으면 로컬 스토리지에 담긴 cart의 수를 표시
    if (!$('#localCartCount').data('login-user')) {
        const cart = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
        $('#localCartCount').text(cart.length);
    }

    // 세션 정보가 없으면 로컬 스토리지에 담긴 cart를 cart.html에 표시
    if (!$('#cart').data('session')) {
        const cartItems = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
        const cartList = cartItems.map((cart) => {
            const product = cart.product;
                return `<tr>
                        <td>
                            <input type="checkbox">
                        </td>
                        
                        <td>
                            <div class="td_wrap">
                                <img src="${product.imageUrl}" alt="${product.productName}">
                                
                                <div class="txt">
                                    <span>${product.productName}</span>
                                    <span>
                                        <button>+</button>
                                        <button>${cart.quantity}</button>
                                        <button>-</button>
                                    </span>
                                </div>
                                
                                <div class="price">
                                    <span>${product.productPrice.toLocaleString()}원</span>
                                    <button>x</button>
                                </div>
                            </div>
                        </td> 
                        
                        <td>${(product.productPrice * cart.quantity).toLocaleString()}원</td>
                        <td>${product.productDiscount}%</td>
                        </tr>`;
            });

            $('#showCart').empty().append(cartList);
    }
});

$(document).on("click", "#goHomeBtn", () => {
    window.location.replace("/");
});

$(document).on("click", "#goPaymentBtn", () => {
    window.location.replace("/user/payment");
});


$(document).on("click", ".addCartBtn", (e) => {
    let cart = $(e.currentTarget);
    const productId = cart.data("product-id");
    const userIdNo = cart.data("user-id");

    if(cart.attr("data-btn-status") == 0) {
        cart.attr("data-btn-status", 1);
    }

    console.log(productId);
    console.log(userIdNo);
    console.log(cart.attr("data-btn-status"))

    // 로그인 시
    if (userIdNo != 0) {
        // // 장바구니 취소
        // if (cart.attr("data-btn-status") == 0) {
        //     axios({
        //         method: "post",
        //         url: "/main/cart/remove",
        //         data: {
        //             productId: productId,
        //             userIdNo: userIdNo
        //         },
        //         dataType: "json",
        //         headers: {'Content-Type': 'application/json'}
        //     });
        // }

        // 장바구니 담기
        if (cart.attr("data-btn-status") == 1) {
            let localCart = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
            axios({
                method: "post",
                url: "/main/cart/add",
                data: {
                    productId: productId,
                    userIdNo: userIdNo,
                    localCart: localCart
                },
                dataType: "json",
                headers: {'Content-Type': 'application/json'}
            }).then((res) =>{
                $('.txt04.right__modal.add__cart').show();
                $('#closeCartModal').click(() => {
                    $('.txt04.right__modal.add__cart').hide();
                });
            })
        }
    }

    // 비로그인 시
    if (userIdNo == 0) {
        axios({
            method: "get",
            url: `/local/cart/${productId}`,
            params: {
                productId: productId
            },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        }).then((res) =>{
            const product = res.data

            const cartItem = localStorage.getItem('cart');
            let exist = false;

            if (cartItem !== null) {
                exist = JSON.parse(cartItem).find(p => p.product.productId === product.productId);
            }

            if (exist) {
                $('.txt04.right__modal.exist__cart').show();
                $('#existCartModalClose').click(() => {
                    $('.txt04.right__modal.exist__cart').hide();
                });
            } else {
                addToLocalStorageCart(product, 1);
                $('.txt04.right__modal.add__cart').show();
            }
        })
    }
})

function addToLocalStorageCart(product, quantity) {
    const cart = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
    const existingProduct = cart.find(p => p.product.productId === product.productId);
    if (!existingProduct) {
        cart.push({ product, quantity });
    }
    localStorage.setItem('cart', JSON.stringify(cart));
}