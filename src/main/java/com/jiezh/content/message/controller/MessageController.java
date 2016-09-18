package com.jiezh.content.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.message.service.MessageService;
import com.jiezh.dao.message.MessageAnswerVO;
import com.jiezh.dao.message.MessageTitleVO;

/**
 * 留言板controller类
 * 
 * @author houjiaqiang
 * @since 2016年9月6日 下午4:52:08
 */
@Controller
@Scope("prototype")
@RequestMapping("/message/")
public class MessageController extends WebAction {

    @Autowired
    MessageService messageService;

    @RequestMapping("queryList")
    public ModelAndView queryList() {
        ModelAndView mv = new ModelAndView("/message/messagelist");
        MessageTitleVO vo = (MessageTitleVO) getBean(MessageTitleVO.class);
        int currenPage = 1;
        if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
            currenPage = Integer.parseInt(request.getParameter("currenPage"));
        }
        mv.addObject("page", messageService.getTitlePage(currenPage, vo));
        mv.addObject("vo", vo);
        return mv;
    }

    @RequestMapping("addMessage")
    public ModelAndView addMessage() throws Exception {
        MessageTitleVO vo = (MessageTitleVO) getBean(MessageTitleVO.class);
        messageService.saveMessage(getUser(), vo);
        return queryList();
    }

    @RequestMapping("doReply")
    public ModelAndView doReply() throws Exception {
        ModelAndView mv = new ModelAndView("/message/messageReply");
        String messageId = request.getParameter("id");
        int currenPage = 1;
        if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
            currenPage = Integer.parseInt(request.getParameter("currenPage"));
        }
        mv.addObject("list", messageService.getReplyList(messageId, currenPage));
        return mv;
    }

    @RequestMapping("addAnswer")
    public ModelAndView addAnswer() throws Exception {
        ModelAndView mv = new ModelAndView("/message/messageReply");
        MessageAnswerVO vo = (MessageAnswerVO) getBean(MessageAnswerVO.class);
        messageService.saveMessageAnswer(getUser(), vo);
        int currenPage = 1;
        if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
            currenPage = Integer.parseInt(request.getParameter("currenPage"));
        }
        mv.addObject("list", messageService.getReplyList(vo.getMsgId() + "", currenPage));
        return mv;
    }

}
