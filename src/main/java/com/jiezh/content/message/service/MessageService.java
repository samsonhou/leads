package com.jiezh.content.message.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.dao.message.MessageAnswerVO;
import com.jiezh.dao.message.MessageTitleVO;

/**
 * 留言板Service接口
 * 
 * @author houjiaqiang
 * @since 2016年9月6日 下午5:30:32
 */
public interface MessageService {
    public int saveMessage(AuthorUser user, MessageTitleVO vo);

    public PageInfo<MessageTitleVO> getTitlePage(int curPage, MessageTitleVO vo);

    public List getReplyList(String messageId, int curPage);

    public int saveMessageAnswer(AuthorUser user, MessageAnswerVO vo);
}
