/**
 * 
 */
package com.jiezh.content.leads.vist.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.util.DateUtil;
import com.jiezh.dao.leads.client.ClientTraceVO;
import com.jiezh.dao.leads.client.ClientVO;

/**
 * @author liangds
 * @since 2016年3月2日 上午9:12:48
 */
public class VistBean {
    private ClientVO clientVO;
    private ClientTraceVO clientTraceVO;
    private String rankbefore;
    private long urgeid;

    public long getUrgeid() {
        return urgeid;
    }

    public void setUrgeid(long urgeid) {
        this.urgeid = urgeid;
    }

    public String getRankbefore() {
        return rankbefore;
    }

    public void setRankbefore(String rankbefore) {
        this.rankbefore = rankbefore;
    }

    public ClientVO getClientVO() {
        return clientVO;
    }

    public ClientTraceVO getClientTraceVO() {
        return clientTraceVO;
    }

    public void setClientTraceVO(ClientTraceVO clientTraceVO) {
        this.clientTraceVO = clientTraceVO;
    }

    public void setClientVO(ClientVO clientVO) {
        this.clientVO = clientVO;
    }

    /**
     * 取主表信息
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public void getClientVO(HttpServletRequest request) throws Exception {
        clientVO = new ClientVO();
        if (null != request.getParameter("id") && !request.getParameter("id").equals("")) clientVO.setId(Long.parseLong(request.getParameter("id")));
        clientVO.setRank(request.getParameter("rank"));
        clientVO.setStatus(request.getParameter("status"));
        if ("A".equals(clientVO.getRank()) || "B".equals(clientVO.getRank()) || "C".equals(clientVO.getRank()) || "O".equals(clientVO.getRank()))
            clientVO.setStatus("");
        clientVO.setNextdate(DateUtil.toDate(request.getParameter("nextdate")));
        // add by cj 加入 等级-C类 的原因
        clientVO.setIdd(request.getParameter("idd"));
        clientVO.setIfk(request.getParameter("ifk"));
        clientVO.setReason(request.getParameter("reason"));
        clientVO.setReasonCont(request.getParameter("reasonCont"));
        clientVO.setContractno(request.getParameter("contractno"));
        clientVO.setInit_rank(request.getParameter("init_rank") == null ? "" : request.getParameter("init_rank"));
        // add by cj 原始等级
        String rankbefore = request.getParameter("rankbefore") == null ? "" : request.getParameter("rankbefore");
        setRankbefore(rankbefore);
        // add by houjq for JZLM-74
        clientVO.setProduct(request.getParameter("product"));
        clientVO.setWill(request.getParameter("will"));
        clientVO.setIsSubMaterial(request.getParameter("isSubMaterial"));
        clientVO.setIsGetCar(request.getParameter("isGetCar"));
        if (StringUtils.isNotEmpty(request.getParameter("getCarDate")))
            clientVO.setGetCarDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("getCarDate")));
        clientVO.setCarno(request.getParameter("carno"));
        if (StringUtils.isNotEmpty(request.getParameter("firstTimeComing")))
            clientVO.setFirstTimeComing(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("firstTimeComing")));
        clientVO.setInnDeposit(request.getParameter("innDeposit"));

        String[] gifts = request.getParameterValues("gift");
        String gift = "";
        if (gifts != null) {
            for (String str : gifts) {
                gift += str;
            }
            clientVO.setGift(gift);
        }

    }

    /**
     * 取回复表信息
     * 
     * @param request
     */
    public void getClientTraceVO(AuthorUser user, HttpServletRequest request) throws Exception {
        clientTraceVO = new ClientTraceVO();
        clientTraceVO.setRedate(new Date());
        if (null != request.getParameter("id") && !request.getParameter("id").equals(""))
            clientTraceVO.settId(Long.parseLong(request.getParameter("id")));
        clientTraceVO.setuId(user.getRealName());
        clientTraceVO.setTitle(request.getParameter("t_title"));
        // add by houjiq start 战败自动分配
        clientTraceVO.setRank(request.getParameter("rank"));
        clientTraceVO.setReason(request.getParameter("reason"));
        clientTraceVO.setSid(user.getUserId());
        // add by houjiq end 战败自动分配
        if (StringUtils.isNotEmpty(request.getParameter("firstTimeComing")))
            clientTraceVO.setFirstTimeComing(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("firstTimeComing")));
        clientTraceVO.setIdd(request.getParameter("idd"));
        clientTraceVO.setIfk(request.getParameter("ifk"));
        clientTraceVO.setReason(request.getParameter("reason"));
        clientTraceVO.setProduct(request.getParameter("product"));
        clientTraceVO.setWill(request.getParameter("will"));
        clientTraceVO.setIsSubMaterial(request.getParameter("isSubMaterial"));
        clientTraceVO.setIsGetCar(request.getParameter("isGetCar"));
        if (StringUtils.isNotEmpty(request.getParameter("getCarDate")))
            clientTraceVO.setGetCarDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("getCarDate")));
        clientTraceVO.setCarno(request.getParameter("carno"));
        clientTraceVO.setInnDeposit(request.getParameter("innDeposit"));

        String[] gifts = request.getParameterValues("gift");
        String gift = "";
        if (gifts != null) {
            for (String str : gifts) {
                gift += str;
            }
            clientTraceVO.setGift(gift);
        }
    }

}
