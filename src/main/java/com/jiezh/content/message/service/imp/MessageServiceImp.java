package com.jiezh.content.message.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.message.service.MessageService;
import com.jiezh.dao.message.MessageAnswerVO;
import com.jiezh.dao.message.MessageAnswerVODao;
import com.jiezh.dao.message.MessageTitleVO;
import com.jiezh.dao.message.MessageTitleVODao;

/**
 * 留言板Service实现类
 * 
 * @author houjiaqiang
 * @since 2016年9月6日 下午5:35:08
 */
@Service
public class MessageServiceImp implements MessageService {

    @Autowired
    MessageTitleVODao messageTitleVODao;
    @Autowired
    MessageAnswerVODao messageAnswerVODao;

    @Override
    public int saveMessage(AuthorUser user, MessageTitleVO vo) {
        vo.setCreatedUserId(user.getUserId());
        vo.setCreatedTime(new Date());
        vo.setMsgUserId(user.getUserId());
        vo.setMsgUserName(user.getRealName());
        return messageTitleVODao.insert(vo);
    }

    @Override
    public PageInfo<MessageTitleVO> getTitlePage(int curPage, MessageTitleVO vo) {
        PageHelper.startPage(curPage, Env.PAGE_SIZE);
        Page<MessageTitleVO> page = (Page<MessageTitleVO>) messageTitleVODao.selectListByVo(vo);
        if (page == null) {
            page = new Page<>();
        }
        return new PageInfo<MessageTitleVO>(page);
    }

    @Override
    public List getReplyList(String messageId, int curPage) {
        List list = new ArrayList<>();
        MessageTitleVO message = messageTitleVODao.selectByPrimaryKey(Long.valueOf(messageId));
        list.add(message);
        MessageAnswerVO vo = new MessageAnswerVO();
        vo.setMsgId(Long.valueOf(messageId));
        PageInfo<MessageAnswerVO> answer = getAnswerPage(curPage, vo);
        list.add(answer);
        return list;
    }

    public PageInfo<MessageAnswerVO> getAnswerPage(int curPage, MessageAnswerVO vo) {
        PageHelper.startPage(curPage, Env.PAGE_SIZE);
        Page<MessageAnswerVO> page = (Page<MessageAnswerVO>) messageAnswerVODao.selectAnswerByVo(vo);
        if (page == null) {
            page = new Page<>();
        }
        return new PageInfo<MessageAnswerVO>(page);
    }

    @Override
    public int saveMessageAnswer(AuthorUser user, MessageAnswerVO vo) {
        vo.setCreatedTime(new Date());
        vo.setCreatedUserId(user.getUserId());
        vo.setAnswerUserId(user.getUserId());
        vo.setAnswerUserName(user.getRealName());
        return messageAnswerVODao.insert(vo);
    }

}
