package com.mine.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mine.common.ServerResponse;
import com.mine.dao.ShippingMapper;
import com.mine.pojo.Shipping;
import com.mine.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 杜晓鹏
 * @create 2019-01-10 20:15
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        if (shipping == null)
            return ServerResponse.serverResponseError("地址不能为空");
        //step2:添加
        shipping.setUserId(userId);
        int insert = shippingMapper.insert(shipping);
        if (insert > 0){
            //setp3:返回结果
            Map<String,Integer> map= Maps.newHashMap();
            map.put("shippingId",shipping.getId());
            return ServerResponse.serverResponseSuccess(map);
        }
        return ServerResponse.serverResponseError("添加失败");
    }

    @Override
    public ServerResponse del(Integer userId, Integer shippingId) {
        if (shippingId == null)
            return ServerResponse.serverResponseError("地址id不能为空");

        int delete = shippingMapper.delete(userId,shippingId);
        if (delete > 0)
            return ServerResponse.serverResponseSuccess();
        return ServerResponse.serverResponseError("删除失败");
    }

    @Override
    public ServerResponse update(Shipping shipping) {
        if (shipping == null)
            return ServerResponse.serverResponseError("要修改的内容不能为空");
        int update = shippingMapper.update(shipping);
        if (update > 0)
            return ServerResponse.serverResponseSuccess();
        return ServerResponse.serverResponseError("更新失败");
    }

    @Override
    public ServerResponse select(Integer userId, Integer shippingId) {
        if (shippingId == null)
            return ServerResponse.serverResponseError("地址id不能为空");
        Shipping shipping = shippingMapper.select(userId, shippingId);
        if (shipping != null)
            return ServerResponse.serverResponseSuccess(shipping);
        return ServerResponse.serverResponseError("该用户没有地址");
    }

    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<Shipping> shippings = shippingMapper.selectAll();

        if (shippings == null || shippings.size() <= 0)
            return ServerResponse.serverResponseError("没有地址...");
        PageInfo pageInfo = new PageInfo(shippings);
        return ServerResponse.serverResponseSuccess(pageInfo);
    }
}
