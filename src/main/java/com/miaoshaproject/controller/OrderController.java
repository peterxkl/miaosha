package com.miaoshaproject.controller;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.mq.MqProducer;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.OrderService;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller(value = "order")
@RequestMapping(value = "/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")   //处理跨域请求
public class OrderController extends BaseController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private MqProducer mqProducer;

    @Autowired
    private RedisTemplate redisTemplate;

    //封装下单请求
    @RequestMapping(value = "/createorder",method ={RequestMethod.POST},consumes = {CONTEND_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(value = "itemId") Integer itemId,
                                        @RequestParam(value = "amount") Integer amount,
                                        @RequestParam(value = "promoId",required = false) Integer promoId)  //required = false如果不传promoId则为平时的价格
                                        throws BusinessException {
        Boolean isLogin=(Boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin==null||!isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN_,"用户未登录,不能下单");
        }

        //获取用户登录信息
        UserModel userModel=(UserModel)httpServletRequest.getSession().getAttribute("LOGIN_USER");
//        OrderModel orderModel=orderService.createOrder(userModel.getId(),itemId,promoId,amount);

        //判断是否库存售罄，若对应的售罄key存在，则直接返回下单失败
        if (redisTemplate.hasKey("promo_item_stock_invalid_"+itemId)) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        //初始化库存流水
        String stockLogId = itemService.initStockLog(itemId, amount);

        boolean result = mqProducer.transactionAsyncReduceStock(userModel.getId(), promoId, itemId, amount, stockLogId);
        if (!result) {
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "下单失败");
        }
        return CommonReturnType.create(null);
    }
}
