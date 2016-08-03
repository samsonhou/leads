package com.jiezh.content.leads.product.web;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.leads.product.service.ProductService;
import com.jiezh.dao.leads.client.ProductVO;
import com.jiezh.dao.leads.client.QaDetailVO;

/**
 * @ClassName: ProductController
 * @Description: 产品内容控制类
 * @author wp
 * @date 2016年2月
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/leads/product/")
public class ProductController extends WebAction {
    @Autowired
    ProductService productService;

    @RequestMapping("index") //
    public ModelAndView index() throws Exception {
        ModelAndView mv = new ModelAndView("leads/product/productinfo");
        // 是否总公司管理员
        boolean ifCManager = false;
        AuthorUser user = getUser();
        if (user.getOrganId().equals("00")) {
            if (this.checkRole("1").equals("1")) ifCManager = true;
        }
        if (!ifCManager) mv = new ModelAndView("leads/product/productinfo1"); 
        return mv;
    }

    @RequestMapping("modify")
    @ResponseBody
    public ModelAndView modifyProduct() throws Exception {
        ModelAndView mv = new ModelAndView("leads/product/productinfo");
        String id = request.getParameter("id");
        String newName = request.getParameter("rName");
        String hasChild = request.getParameter("hChild");
        String[] roles = request.getParameterValues("mroles");
        ProductVO productVO = new ProductVO();
        productVO.setId(Long.valueOf(id));
        productVO.setAbname(newName);
        productVO.setHasChild(hasChild);
        productVO.setRoles(String.join(",", roles));
        productService.update(productVO);

        // 是否总公司管理员
        boolean ifCManager = false;
        AuthorUser user = getUser();
        if (user.getOrganId().equals("00")) {
            if (this.checkRole("1").equals("1")) ifCManager = true;
        }
        if (ifCManager) mv.addObject("ifCManager", "true");
        else mv.addObject("ifCManager", "false");
        mv.addObject("message", "已修改成功!");
        return mv;
    }

    @RequestMapping("add")
    @ResponseBody
    public ModelAndView addProduct() throws Exception {
        ModelAndView mv = new ModelAndView("leads/product/productinfo");
        String pid = request.getParameter("pId");
        String newName = request.getParameter("newName");
        String hasChild = request.getParameter("hasChild");
        String[] roles = request.getParameterValues("nroles");
        ProductVO productVO = new ProductVO();
        productVO.setPid(Long.valueOf(pid));
        productVO.setAbname(newName);
        productVO.setHasChild(hasChild);
        productVO.setRoles(String.join(",", roles));
        productService.insert(productVO);

        // 是否总公司管理员
        boolean ifCManager = false;
        AuthorUser user = getUser();
        if (user.getOrganId().equals("00")) {
            if (this.checkRole("1").equals("1")) ifCManager = true;
        }
        if (ifCManager) mv.addObject("ifCManager", "true");
        else mv.addObject("ifCManager", "false");
        mv.addObject("message", "已保存成功!");
        return mv;
    }

    @RequestMapping("delete")
    @ResponseBody
    public ModelAndView removeProduct() throws Exception {
        ModelAndView mv = new ModelAndView("leads/product/productinfo");
        String pid = request.getParameter("dId");
        productService.deleteProduect(Long.parseLong(pid));

        // 是否总公司管理员
        boolean ifCManager = false;
        AuthorUser user = getUser();
        if (user.getOrganId().equals("00")) {
            if (this.checkRole("1").equals("1")) ifCManager = true;
        }
        if (ifCManager) mv.addObject("ifCManager", "true");
        else mv.addObject("ifCManager", "false");
        return mv;
    }

    /**
     * @throws Exception
     *             查询内容
     */
    @RequestMapping(value = "queryProducts")
    @ResponseBody
    public void queryProducts() throws Exception {
        AuthorUser user = getUser();
        String str0 = productService.queryProducts(user);

        response.getWriter().print(str0);
    }

    /**
     * @throws Exception
     *             查询一个具体的记录
     */
    @RequestMapping("queryRecord")
    @ResponseBody
    public ProductVO queryRecord(long id) throws Exception {
        ProductVO productVO = productService.queryRecord(id);
        return productVO;
    }

    @RequestMapping("saveQuestion")
    public ModelAndView saveQuestion() throws Exception {
        QaDetailVO detailVO = (QaDetailVO) getBean(QaDetailVO.class);
        detailVO.setQuestion(URLDecoder.decode(detailVO.getQuestion(), "UTF-8"));
        detailVO.setAnswer(URLDecoder.decode(detailVO.getAnswer(), "UTF-8"));
        detailVO.setStatus("1");// 有效
        AuthorUser user = getUser();
        detailVO.setCreatedUserId(user.getUserId());
        detailVO.setCreatedTime(new Date());
        productService.addQuestion(detailVO);
        return null;
    }

    @RequestMapping("modifyQuestion")
    public ModelAndView modifyQuestion() throws Exception {
        QaDetailVO detailVO = (QaDetailVO) getBean(QaDetailVO.class);
        detailVO.setQuestion(URLDecoder.decode(detailVO.getQuestion(), "UTF-8"));
        detailVO.setAnswer(URLDecoder.decode(detailVO.getAnswer(), "UTF-8"));
        AuthorUser user = getUser();
        detailVO.setUpdatedUserId(user.getUserId());
        detailVO.setUpdatedTime(new Date());
        productService.changeQuestion(detailVO);
        return null;
    }

    @RequestMapping("deleteQuestion")
    public ModelAndView deleteQuestion() throws Exception {
        QaDetailVO detailVO = (QaDetailVO) getBean(QaDetailVO.class);
        detailVO.setStatus("0");// 无效
        AuthorUser user = getUser();
        detailVO.setUpdatedUserId(user.getUserId());
        detailVO.setUpdatedTime(new Date());
        productService.changeQuestion(detailVO);
        return null;
    }

    @RequestMapping("queryDetails")
    @ResponseBody
    public List<PageInfo<QaDetailVO>> queryDetails() throws Exception {
        String pid = request.getParameter("pid");
        String keyword = request.getParameter("keyword");
        keyword = URLDecoder.decode(keyword, "UTF-8");
        QaDetailVO condition = new QaDetailVO();
        condition.setPid(Long.valueOf(pid));
        condition.setQuestion(keyword);
        int currenPage = 1;
        if (request.getParameter("pagenum") != null && !"".equals(request.getParameter("pagenum"))) {
            currenPage = Integer.parseInt(request.getParameter("pagenum"));
        }
        List<PageInfo<QaDetailVO>> list = new ArrayList<PageInfo<QaDetailVO>>();
        condition.setFileName("empty");
        PageInfo<QaDetailVO> question = productService.getDetails(condition, currenPage);
        list.add(question);
        condition.setFileName(keyword);
        condition.setQuestion(null);
        PageInfo<QaDetailVO> files = productService.getDetails(condition, currenPage);
        list.add(files);
        return list;
    }

    /**
     * 文件上传
     * 
     * @param
     * @return Exception
     */
    @RequestMapping("importFile")
    public ModelAndView importFile(@RequestParam(value = "file") MultipartFile file) throws Exception {
        ModelAndView mv = new ModelAndView("leads/product/productinfo");
        String pid = request.getParameter("filePid");
        AuthorUser user = this.getUser();
        productService.processImport(file, user, Integer.valueOf(pid));
        mv.addObject("message", "已保存成功!");
        return mv;
    }

    @RequestMapping("downFile")
    public ModelAndView downFile() throws Exception {
        String filePath = request.getParameter("filePath");
        String fileName = request.getParameter("fileName");
        filePath = URLDecoder.decode(filePath, "UTF-8");
        InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        response.setHeader("Content-Disposition",
            "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(buffer);
        outputStream.close();
        buffer = null;
        return null;
    }

    @RequestMapping("changeFile")
    @ResponseBody
    public Map<String, Object> changeFile() throws Exception {
        String id = request.getParameter("id");
        QaDetailVO detailVO = new QaDetailVO();
        detailVO.setId(Long.valueOf(id));
        detailVO.setStatus("0");
        AuthorUser user = getUser();
        detailVO.setUpdatedTime(new Date());
        detailVO.setUpdatedUserId(user.getUserId());
        int flag = productService.modifyFile(detailVO);
        Map<String, Object> map = new HashMap<String, Object>();
        if(flag > 0){
            map.put("msg", "Y");
        }else{
            map.put("msg", "N");
        }
        return map;
    }
}
