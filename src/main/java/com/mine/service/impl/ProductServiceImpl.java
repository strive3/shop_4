package com.mine.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mine.common.Const;
import com.mine.common.ServerResponse;
import com.mine.dao.CategoryMapper;
import com.mine.dao.ProductMapper;
import com.mine.pojo.Category;
import com.mine.pojo.Product;
import com.mine.service.CategoryService;
import com.mine.service.ProductService;
import com.mine.utils.DateUtils;
import com.mine.utils.FTPUtils;
import com.mine.utils.PropertiesUtils;
import com.mine.vo.ProductDetailVO;
import com.mine.vo.ProductListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author 杜晓鹏
 * @create 2019-01-08 13:49
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategoryService categoryService;

    @Override
    public ServerResponse saveOrUpdate(Product product) {
        //step1:非空验证
        if (product == null)
            return ServerResponse.serverResponseError("商品不存在");
        //step2：拿到商品的主图，子图
        String subImage = product.getSubImages();

        if (subImage != null && !subImage.equals("")) {
            String[] subImages = subImage.split(",");
            if (subImages.length > 0)
                product.setMainImage(subImages[0]);
        }

        //step3：判断是添加还是更新
        if (product.getId() == null) {
            int insert = productMapper.insert(product);
            if (insert > 0) {
                return ServerResponse.serverResponseSuccess();
            } else {
                return ServerResponse.serverResponseError("添加失败");
            }
        } else {
            int update = productMapper.update(product);
            if (update > 0) {
                return ServerResponse.serverResponseSuccess();
            } else {
                return ServerResponse.serverResponseError("更新失败");
            }
        }
    }

    @Override
    public ServerResponse set_sale_status(Integer productId, Integer status) {
        //step1:非空验证
        if (productId == null)
            return ServerResponse.serverResponseError("商品ID不能为空");
        if (status == null)
            return ServerResponse.serverResponseError("商品状态不能为空");

        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int update = productMapper.update(product);
        if (update > 0)
            return ServerResponse.serverResponseSuccess();
        return ServerResponse.serverResponseError("更新失败");
    }

    @Override
    public ServerResponse detail(Integer productId) {
        //step1:非空验证
        if (productId == null)
            return ServerResponse.serverResponseError("商品ID不能为空");

        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null)
            return ServerResponse.serverResponseError("商品不存在");
        ProductDetailVO productDetailByProduct = getProductDetailByProduct(product);


        return ServerResponse.serverResponseSuccess(productDetailByProduct);
    }

    /**
     * 将product转化为ProductDetail
     **/
    private ProductDetailVO getProductDetailByProduct(Product product) {
        ProductDetailVO productDetailVO = new ProductDetailVO();
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setCreateTime(DateUtils.dateToString(product.getCreateTime()));
        productDetailVO.setDetail(product.getDetail());
        productDetailVO.setImageHost(PropertiesUtils.getValue("imageHost"));
        productDetailVO.setName(product.getName());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setId(product.getId());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setStock(product.getStock());
        productDetailVO.setSubImages(product.getSubImages());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setUpdateTime(DateUtils.dateToString(product.getUpdateTime()));
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category != null) {
            productDetailVO.setParentCategoryId(category.getParentId());
        } else {
            //默认根节点
            productDetailVO.setParentCategoryId(0);
        }


        return productDetailVO;
    }

    @Override
    public ServerResponse findAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<Product> products = productMapper.selectAll();
        List<ProductListVO> productListVOList = new ArrayList<>();
        if (products != null && products.size() > 0) {
            for (Product product : products) {
                ProductListVO productListVOByProduct = getProductListVOByProduct(product);
                productListVOList.add(productListVOByProduct);
            }
        }

        PageInfo pageInfo = new PageInfo(productListVOList);

        return ServerResponse.serverResponseSuccess(pageInfo);
    }

    @Override
    public ServerResponse search(String productName, Integer productId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (productName != null && !productName.equals(""))
            productName = "%" + productName + "%";
        List<Product> products = productMapper.selectByProductIdOrProductName(productId, productName);
        List<ProductListVO> productListVOList = new ArrayList<>();
        if (products != null && products.size() > 0) {
            for (Product product : products) {
                ProductListVO productListVOByProduct = getProductListVOByProduct(product);
                productListVOList.add(productListVOByProduct);
            }
        }

        PageInfo pageInfo = new PageInfo(productListVOList);

        return ServerResponse.serverResponseSuccess(pageInfo);
    }


    /**
     * 将product转化为ProductListVO
     *
     * @param product
     * @return
     */
    private ProductListVO getProductListVOByProduct(Product product) {
        ProductListVO productListVO = new ProductListVO();
        productListVO.setId(product.getId());
        productListVO.setCategoryId(product.getCategoryId());
        productListVO.setMainImage(product.getMainImage());
        productListVO.setName(product.getName());
        productListVO.setPrice(product.getPrice());
        productListVO.setStatus(product.getStatus());
        productListVO.setSubtitle(product.getSubtitle());
        return productListVO;
    }


    /**
     * 前台验证
     */
    @Override
    public ServerResponse detail_portal(Integer productId) {
        //step1 非空验证
        if (productId == null)
            return ServerResponse.serverResponseError("商品id不能为空");

        //step2 查询product
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null)
            return ServerResponse.serverResponseError("商品不存在");
        //step3 检验商品状态
        if (product.getStatus() != Const.ProductCodeEnum.PRODUCT_ONLINE.getCode())
            return ServerResponse.serverResponseError("商品已下线或者被删除");
        //step4 换成vo
        ProductDetailVO productDetailByProduct = getProductDetailByProduct(product);
        //step5 返回结果
        return ServerResponse.serverResponseSuccess(productDetailByProduct);
    }

    @Override
    public ServerResponse searchAndOrderBy(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy) {

        //step1 参数校验，两个参数不能同时为空
        if (categoryId == null && (keyword == null || keyword.equals(""))) {
            return ServerResponse.serverResponseError("没有查询条件，无法查找");
        }
        //step2 categoryId
        Set<Integer> categories = Sets.newHashSet();
        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            //如果 结果为空
            if (category == null && (keyword == null || keyword.equals(""))) {
                PageHelper.startPage(pageNum, pageSize);
                List<ProductListVO> productListVOList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVOList);
                return ServerResponse.serverResponseSuccess(pageInfo);
            }
            //如果 category不为空
            ServerResponse serverResponse = categoryService.get_deep_category(categoryId);
            if (serverResponse.isSuccess()) {
                categories = (Set<Integer>) serverResponse.getData();
            }
        }

        /**
         * 以下的 就算categoryId为空 也要执行
         */
        //对关键字进行处理
        if (keyword != null) {
            keyword = "%" + keyword + "%";
        }
        //进行分页
        if (orderBy == null || orderBy.equals("")) {
            PageHelper.startPage(pageNum, pageSize);
        } else {
            String[] orderByArr = orderBy.split("_");
            if (orderByArr.length > 1) {
                PageHelper.startPage(pageNum, pageSize, orderByArr[0] + " 0 " + orderByArr[1]);
            }
        }
        //查询   模糊查询
        List<Product> products = productMapper.searchProduct(categories, keyword);
        //新建一个productListVOList的集合
        List<ProductListVO> productListVOList = Lists.newArrayList();
        for (Product product : products) {
            ProductListVO productListVO = getProductListVOByProduct(product);
            productListVOList.add(productListVO);
        }
        //返回结果
        return ServerResponse.serverResponseSuccess(productListVOList);
    }

    @Override
    public ServerResponse upload(MultipartFile file, String path) {

        if (file == null) {
            return ServerResponse.serverResponseError();
        }

        //step1:获取图片名称    上传文件的原名
        String orignalFileName = file.getOriginalFilename();
        //获取图片的扩展名
        String exName = orignalFileName.substring(orignalFileName.lastIndexOf(".")); // .jpg
        //为图片生成新的唯一的名字
        String newFileName = UUID.randomUUID().toString() + exName;

        File pathFile = new File(path);
        if (!pathFile.exists()) {
            pathFile.setWritable(true);
            pathFile.mkdirs();
        }

        File file1 = new File(path, newFileName);

        try {
            file.transferTo(file1);   //转存文件
            //上传到图片服务器
            FTPUtils.uploadFile(Lists.newArrayList(file1));
            //.....
            Map<String, String> map = Maps.newHashMap();
            map.put("uri", newFileName);
            map.put("url", PropertiesUtils.getValue("imageHost") + "/" + newFileName);
            //删除应用服务器上的图片
            file1.delete();

            return ServerResponse.serverResponseSuccess(map);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
