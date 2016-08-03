package com.jiezh.content.leads.product.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.util.Node;
import com.jiezh.dao.leads.client.ProductVO;
import com.jiezh.dao.leads.client.QaDetailVO;

/**
 * @ClassName: ProductService
 * @Description:
 * @author
 * @date 2016年2
 * 
 */
public interface ProductService {

    int insert(ProductVO record);

    int update(ProductVO record);

    ProductVO selectByPrimaryKey(long id);

    // 查询所有内容树
    String queryProducts(AuthorUser user);

    void deleteProduect(long parseInt);

    ProductVO queryRecord(long id);

    // add by cj 产品列表
    List<Node> queryProductList();

    List<ProductVO> queryChildProductsByPid(long pid);

    public int addQuestion(QaDetailVO record);

    public PageInfo<QaDetailVO> getDetails(QaDetailVO condition, int curPage);

    public int changeQuestion(QaDetailVO record);

    public String processImport(MultipartFile file, AuthorUser user, int pid) throws Exception;

    public int modifyFile(QaDetailVO record);
}
