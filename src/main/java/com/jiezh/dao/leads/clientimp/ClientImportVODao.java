package com.jiezh.dao.leads.clientimp;

import java.util.List;
import java.util.Map;

public interface ClientImportVODao {
    int deleteByPrimaryKey(Long id);

    int insert(ClientImportVO record);

    int insertSelective(ClientImportVO record);

    ClientImportVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClientImportVO record);

    int updateByPrimaryKey(ClientImportVO record);

    int selectByTel(String tel);

    List<ClientImportVO> selectByVo(ClientImportVO vo);

    List<Map<String, Object>> selectByMap(Map<String, Object> param);

    int insertByList(List<ClientImportVO> voList);

    String queryCodeByLabel(Map<String, Object> paraMap);

    int queryClientIsExistByTel(String tel);
}
