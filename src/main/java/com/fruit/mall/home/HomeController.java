package com.fruit.mall.home;

import com.fruit.mall.annotaion.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.product.ProductService;
import com.fruit.mall.product.dto.PageResDto;
import com.fruit.mall.product.dto.ProductAndImageInfo;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;

    @GetMapping("favicon.ico")
    @ResponseBody
    void noFavicon() {
    }

    @GetMapping("/")
    public String home(
            @Login SessionUser sessionUser,
            Model model,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "9") Integer pageSize) {
        Long userId = sessionUser != null ? sessionUser.getUserIdNo() : null;
        PageInfo<ProductAndImageInfo> products = productService.getProductsAndImageByFilter(pageNum, pageSize, null, null, userId);
        model.addAttribute("pageInfo", products);
        return "user/index";
    }

    @GetMapping("/api/v1/home/searchfilter")
    @ResponseBody
    public PageResDto userMainSearchFilter(
            @Login SessionUser sessionUser,
            @RequestParam HashMap<String, String> params,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "9") Integer pageSize) {
        String category = params.get("category");
        String searchCond = params.get("searchCond");
        Long userId = sessionUser != null ? sessionUser.getUserIdNo() : null;
        PageInfo<ProductAndImageInfo> pageInfo = productService.getProductsAndImageByFilter(pageNum, pageSize, category, searchCond, userId);
        return new PageResDto(pageInfo, category, userId);
    }

    @GetMapping("/{pageName}")
    public String goSubPage(@PathVariable String pageName) {
        return "user/" + pageName;
    }

    @GetMapping("/join/{email}")
    public String joinConfirm(@PathVariable String email, Model model) {
        model.addAttribute("email", email);
        return "user/joinConfirm";
    }

    @PostMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/changePw")
    public String changePw(@RequestParam String user_email, Model model) {
        model.addAttribute("email", user_email);
        return "user/changePw";
    }
}
