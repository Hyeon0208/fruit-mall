package com.fruit.mall.orders;

import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.orders.dto.OrderReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/user/order/one/{productId}/{productCount}")
    public String orderOneProduct(@Login SessionUser sessionUser, @PathVariable Long productId, @PathVariable int productCount, Model model) {
        List<OrderReqDto> orderReqDtos = orderService.selectOneOrderInfoByProductId(productId);
        OrderReqDto orderReqDto = orderReqDtos.get(0);
        orderReqDto.setProductCount(productCount);

        model.addAttribute("products", orderReqDtos);
        model.addAttribute("totalSize", orderReqDtos.size());

        return "user/payment";
    }
}