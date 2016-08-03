package com.jiezh.content.leads.product.service.imp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.util.Node;
import com.jiezh.content.base.pub.util.Tools;
import com.jiezh.content.leads.product.service.ProductService;
import com.jiezh.dao.leads.client.ProductVO;
import com.jiezh.dao.leads.client.QaDetailVO;
import com.jiezh.dao.leads.client.QaDetailVODao;
import com.jiezh.dao.leads.client.ProductDao;

/**
 * @ClassName: ProductServiceImp
 * @Description: 产品内容
 * @author wp
 * @date 2016年2月
 * 
 */
@Service("leads.urge.service.ProductService")
public class ProductServiceImp implements ProductService {
    @Autowired
    ProductDao productDao;

    @Autowired
    QaDetailVODao detailVODao;

    @Override
    public int insert(ProductVO record) {
        return productDao.insert(record);
    }

    @Override
    public int update(ProductVO record) {
        return productDao.updateByPrimaryKey(record);
    }

    @Override
    public ProductVO selectByPrimaryKey(long id) {
        return productDao.selectByPrimaryKey(id);
    }

    @Override
    public String queryProducts(AuthorUser user) {
        List<Node> nodes = new ArrayList<Node>();

        Node node = null;
        List<Map<String, Object>> listR = null;
        // 得到列表
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("roles", user.getUserRole());
        listR = productDao.queryProducts(condition);
        for (Map<String, Object> obj : listR) {
            node = new Node();
            node.setId(obj.get("ID").toString());
            node.setName(obj.get("NAME").toString());
            node.setpId(obj.get("PID").toString());
            node.setHasChild(obj.get("HASCHILD").toString());
            node.setRoles(obj.get("ROLES").toString().replace(",", ""));

            node.setOpen(false);
            node.setNocheck(true);
            if (obj.get("HASCHILD").equals("0")) {
                node.setNocheck(false);
            }

            node.setIsParent(false);
            node.setChecked(false);
            nodes.add(node);
        }
        String str0 = Tools.listToJson(nodes);
        return str0;
    }

    @Override
    public void deleteProduect(long id) {
        productDao.deleteByPrimaryKey(id);
    }

    @Override
    public ProductVO queryRecord(long id) {
        return productDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Node> queryProductList() {
        List<Node> nodes = new ArrayList<Node>();

        Node node = null;
        List<Map<String, Object>> listR = null;
        // 得到列表
        listR = productDao.queryRootProducts();
        for (Map<String, Object> obj : listR) {
            node = new Node();
            node.setId(obj.get("ID").toString());
            node.setName(obj.get("NAME").toString());
            node.setpId(obj.get("PID").toString());
            node.setOpen(false);
            node.setIsParent(false);
            node.setNocheck(false);
            node.setChecked(false);
            nodes.add(node);
        }
        return nodes;
    }

    @Override
    public List<ProductVO> queryChildProductsByPid(long pid) {
        return productDao.queryChildProductsByPid(pid);
    }

    @Override
    public int addQuestion(QaDetailVO record) {

        return detailVODao.insert(record);
    }

    @Override
    public int changeQuestion(QaDetailVO record) {

        return detailVODao.updateByPrimaryKeySelective(record);
    }

    @Override
    public PageInfo<QaDetailVO> getDetails(QaDetailVO condition, int curPage) {
        PageHelper.startPage(curPage, 5);
        Page<QaDetailVO> page = (Page<QaDetailVO>) detailVODao.selectDetails(condition);
        if (page == null) {
            page = new Page<>();
        }
        return new PageInfo<QaDetailVO>(page);
    }

    @Override
    public String processImport(MultipartFile file, AuthorUser user, int pid) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("sysvarCode", "file_dir");
        String fileDir = productDao.selectFileDir(param).get("FILEDIR").toString();
        File newfile = new File(
            fileDir + "/" + String.valueOf(System.nanoTime()) + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        if (!newfile.getParentFile().exists()) {
            newfile.getParentFile().mkdirs();
            newfile.createNewFile();
        }
        QaDetailVO detailVO = new QaDetailVO();
        file.transferTo(newfile);
        detailVO.setFileName(file.getOriginalFilename());
        detailVO.setCreatedTime(new Date());
        detailVO.setCreatedUserId(user.getUserId());
        detailVO.setStatus("1");// 有效
        detailVO.setFilePath(newfile.getAbsolutePath());
        detailVO.setPid((long) pid);
        detailVODao.insert(detailVO);
        return null;
    }

    @Override
    public int modifyFile(QaDetailVO record) {
        return detailVODao.updateByPrimaryKeySelective(record);
    }

}
