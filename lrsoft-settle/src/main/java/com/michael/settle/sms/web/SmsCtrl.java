package com.michael.settle.sms.web;

import com.michael.common.JspAccessType;
import com.michael.core.pager.PageVo;
import com.michael.core.web.BaseController;
import com.michael.settle.sms.bo.SmsBo;
import com.michael.settle.sms.domain.Sms;
import com.michael.settle.sms.service.SmsService;
import com.michael.settle.sms.vo.SmsVo;
import com.michael.utils.gson.GsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Michael
 */
@Controller
@RequestMapping(value = {"/settle/sms/sms"})
public class SmsCtrl extends BaseController {
    @Resource
    private SmsService smsService;


    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "settle/sms/sms/sms_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.ADD);
        return "settle/sms/sms/sms_edit";
    }

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        smsService.init();
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = "/modify", params = {"id"}, method = RequestMethod.GET)
    public String toModify(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.MODIFY);
        request.setAttribute("id", id);
        return "settle/sms/sms/sms_edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        Sms sms = GsonUtils.wrapDataToEntity(request, Sms.class);
        smsService.update(sms);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = {"/detail"}, params = {"id"}, method = RequestMethod.GET)
    public String toDetail(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "settle/sms/sms/sms_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public void findById(HttpServletResponse response) {
        Sms vo = smsService.get();
        GsonUtils.printData(response, vo);
    }

    @ResponseBody
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST)
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) {
        SmsBo bo = GsonUtils.wrapDataToEntity(request, SmsBo.class);
        PageVo pageVo = smsService.pageQuery(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public void query(HttpServletRequest request, HttpServletResponse response) {
        SmsBo bo = GsonUtils.wrapDataToEntity(request, SmsBo.class);
        List<SmsVo> vos = smsService.query(bo);
        GsonUtils.printData(response, vos);
    }

    @ResponseBody
    @RequestMapping(value = "/enable", params = {"ids"}, method = RequestMethod.POST)
    public void enable(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        smsService.enable(idArr);
        GsonUtils.printSuccess(response);
    }

    @ResponseBody
    @RequestMapping(value = "/disable", params = {"ids"}, method = RequestMethod.POST)
    public void disable(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        smsService.disable(idArr);
        GsonUtils.printSuccess(response);
    }

    // 查询所有有效的数据，不使用分页
    @ResponseBody
    @RequestMapping(value = "/query-valid", method = RequestMethod.POST)
    public void queryValid(HttpServletRequest request, HttpServletResponse response) {
        SmsBo bo = GsonUtils.wrapDataToEntity(request, SmsBo.class);
        List<Sms> data = smsService.queryValid(bo);
        GsonUtils.printData(response, data);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        smsService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }


}
