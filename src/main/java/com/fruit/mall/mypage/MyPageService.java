package com.fruit.mall.mypage;

import com.fruit.mall.mypage.dto.MyPageSearchCond;
import com.fruit.mall.mypage.dto.OrderDetail;
import com.fruit.mall.user.dto.UserInfoUpdateDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MyPageRepository myPageRepository;

    public PageInfo<OrderDetail> getOrderDetailsByUserID(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, "ORDER_DATE DESC");
        List<OrderDetail> orderDetails = myPageRepository.selectOrderDetailsByUserId(userId);
        return new PageInfo<>(orderDetails);
    }

    public PageInfo<OrderDetail> getOrderDetailsBySearchFilter(MyPageSearchCond cond, Long userId) {
        PageHelper.startPage(cond.getPageNum(), cond.getPageSize(), "ORDER_DATE DESC");
        List<OrderDetail> orderDetails = myPageRepository.selectOrderDetailsBySearchFilter(cond, userId);
        return new PageInfo<>(orderDetails);
    }

    public UserInfoUpdateDto selectUserByUserId(Long userIdNo) {
        return myPageRepository.selectUserByUserId(userIdNo);
    }
}
