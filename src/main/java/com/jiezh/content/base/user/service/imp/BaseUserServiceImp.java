package com.jiezh.content.base.user.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.login.model.GroupBean;
import com.jiezh.content.base.pub.util.DaoUtil;
import com.jiezh.content.base.pub.util.Node;
import com.jiezh.content.base.pub.util.Tools;
import com.jiezh.content.base.user.service.BaseUserService;
import com.jiezh.dao.base.user.BaseUserDao;
import com.jiezh.dao.base.user.RoleVO;
import com.jiezh.dao.base.user.UserVO;

@Service("base.user.BaseUserService")
public class BaseUserServiceImp implements BaseUserService {
    @Autowired
    BaseUserDao baseUserDao;

    public int addUser(UserVO record) {
        return baseUserDao.insert(record);
    }

    public PageInfo<UserVO> search(int currenPage, UserVO vo) {
        PageHelper.startPage(currenPage, 20);
        Page<UserVO> page = (Page<UserVO>) baseUserDao.selectByrecord(vo);
        if (page == null) {
            page = new Page<UserVO>();
        }
        return new PageInfo<UserVO>(page);
    }

    public String getOrganInfo(String organId, String organCode, String defVal) {
        if (defVal == null) {
            defVal = "";
        }
        StringBuffer sb = new StringBuffer("");
        Map<String, String> param = new HashMap<String, String>();
        param.put("parentId", organId);
        param.put("status", "1");
        param.put("organCode", organCode);
        List<Map<String, String>> items = DaoUtil.instance().cacheDao().getOrganInfo(param);
        for (int i = 0; i < items.size(); i++) {
            if (i == 0) {
                sb.append("<select onchange='organ.change(this);'>").append("<option value=''>请选择</option>");
            }
            sb.append(
                "<option  " + (defVal.equals(items.get(i).get("ORGAN_ID")) ? "selected" : "") + "  value='" + items.get(i).get("ORGAN_ID") + "'>")
                .append(items.get(i).get("NAME")).append("</option>");
            if (i == items.size() - 1) {
                sb.append("</select>");
            }
        }
        return sb.toString();
    }

    /***
     * 
     * @author 陈继龙 E-mail: cqcnihao@139.com
     * 
     * @date 2015年12月22日 下午3:14:58
     * 
     *       <p>
     *       Title: findOne
     *       </p>
     * 
     *       <p>
     *       Description:
     *       </p>
     * 
     * @param userId
     * @return
     * 
     * @see com.jiezh.content.base.user.service.BaseUserService#findOne(long)
     * 
     */
    @Override
    public UserVO findOne(long userId) {
        return baseUserDao.selectByPrimaryKey(userId);
    }

    /***
     * 
     * @author 陈继龙 E-mail: cqcnihao@139.com
     * 
     * @date 2015年12月22日 下午5:28:39
     * 
     *       <p>
     *       Title: save
     *       </p>
     * 
     *       <p>
     *       Description:
     *       </p>
     * 
     * @param uservoform
     * @return
     * 
     * @see com.jiezh.content.base.user.service.BaseUserService#save(com.jiezh.dao.base.user.UserVO)
     * 
     */
    @Override
    public int save(String[] roles, UserVO record) throws Exception {
        int z = baseUserDao.insert(record);
        if (z == 1) {
            if (!("".equals(roles) || null == roles || roles.length == 0)) {
                String userId = String.valueOf(record.getUserId());
                for (int i = 0; i < roles.length; i++) {
                    String roleId = roles[i].trim();
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userId", userId);
                    map.put("roleId", roleId);
                    baseUserDao.insertUser2Role(map);
                }
            }
        } else {
            throw new Exception("用户写入失败，联系管理员");
        }
        return z;
    }

    @Override
    public int updateModurle(String[] roles, UserVO record) throws Exception {
        if (!("".equals(roles) || null == roles || roles.length == 0)) {
            String userId = String.valueOf(record.getUserId());
            Map<String, String> user2Rolemap = new HashMap<String, String>();
            user2Rolemap.put("userId", userId);
            baseUserDao.deleteUser2RoleById(user2Rolemap);
            for (int i = 0; i < roles.length; i++) {
                String roleId = roles[i].trim();
                Map<String, String> map = new HashMap<String, String>();
                map.put("userId", userId);
                map.put("roleId", roleId);
                baseUserDao.insertUser2Role(map);
            }
        }
        return baseUserDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<RoleVO> selectUsreRoles(RoleVO roleVO) {
        return baseUserDao.selectUsreRoles(roleVO);
    }

    /***
     * 
     * @author 陈继龙 E-mail: cqcnihao@139.com
     * 
     * @date 2015年12月23日 下午1:40:17
     * 
     *       <p>
     *       Title: selectUsreRolesByUserId
     *       </p>
     * 
     *       <p>
     *       Description:
     *       </p>
     * 
     * @param userId
     * @return List<RoleVO>
     * 
     * @see com.jiezh.content.base.user.service.BaseUserService#selectUsreRolesByUserId(long)
     * 
     */
    @Override
    public List<RoleVO> selectUsreRolesByUserId(long userId) {
        return baseUserDao.selectUsreRolesByUserId(userId);
    }

    /***
     * 
     * @author 陈继龙 E-mail: cqcnihao@139.com
     * 
     * @date 2015年12月23日 下午6:20:00
     * 
     *       <p>
     *       Title: saveUserGroup
     *       </p>
     * 
     *       <p>
     *       Description:
     *       </p>
     * 
     * @param newBeans
     * @param userId
     * 
     * @see com.jiezh.content.base.user.service.BaseUserService#saveUserGroup(java.util.List,
     *      java.lang.String)
     * 
     */
    @Override
    public void saveUserGroup(List<?> newBeans, long userId) {
        baseUserDao.deleteUserGroupByUserid(userId);
        for (int i = 0; i < newBeans.size(); i++) {
            GroupBean groupBean = (GroupBean) newBeans.get(i);
            baseUserDao.insertUserGroup(groupBean);
        }

    }

    // add by cj 通讯录
    @Override
    public PageInfo<UserVO> searchOfAdBook(int currenPage, UserVO vo) {
        PageHelper.startPage(currenPage, 10);
        Page<UserVO> page = (Page<UserVO>) baseUserDao.selectByrecordOfAdBook(vo);
        if (page == null) {
            page = new Page<UserVO>();
        }
        return new PageInfo<UserVO>(page);
    }

    /*
     * <p>Title: checkUserOnly</p>
     * <p>Description: </p>
     * 
     * @param userCode
     * 
     * @return String
     * 
     * @see com.jiezh.content.base.user.service.BaseUserService#checkUserOnly(java.lang.String)
     */
    @Override
    public String checkUserOnly(String userCode, long userId) {
        UserVO vo = new UserVO();
        vo.setUserCode(userCode);
        List<UserVO> list = baseUserDao.selectByrecord(vo);
        if (list.size() == 0) {
            return "OK"; // 该用户可以用
        } else if (list.size() == 1) {
            UserVO uservo = (UserVO) list.get(0);
            if (uservo.getUserId() == userId) {
                return "OK"; // 该用户可以更改
            } else {
                return "NO"; // 不可以更改
            }
        } else {
            return "NO";
        }
    }

    @Override
    public Map<String, Object> getSmsInfo(Map<String, Object> params) {
        return baseUserDao.selectSmsInfo(params);
    }

    @Override
    public String getUserAndOrg(Map<String, Object> params) {
        List<Node> nodes = new ArrayList<Node>();
        List<Map<String, Object>> list = baseUserDao.selectUserAndOrg(params);
        for (Map<String, Object> map : list) {
            Node node = new Node();
            node.setId(map.get("ID").toString());
            node.setName(map.get("NAME").toString());
            node.setpId(map.get("PID").toString());
            if(map.get("ID").toString().startsWith("U")){
                node.setIsParent(false);
            }else{
                node.setNocheck(true);
                node.setIsParent(true);
            }
            nodes.add(node);
        }
        return Tools.listToJson(nodes);
    }

}
