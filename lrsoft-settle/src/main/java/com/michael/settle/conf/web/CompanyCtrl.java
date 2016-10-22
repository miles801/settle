package com.michael.settle.conf.web;

import com.michael.common.JspAccessType;
import com.michael.core.context.SecurityContext;
import com.michael.core.pager.PageVo;
import com.michael.core.web.BaseController;
import com.michael.settle.conf.bo.CompanyBo;
import com.michael.settle.conf.domain.Company;
import com.michael.settle.conf.service.CompanyService;
import com.michael.settle.conf.vo.CompanyVo;
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
@RequestMapping(value = {"/settle/conf/company"})
public class CompanyCtrl extends BaseController {
    @Resource
    private CompanyService companyService;


    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "settle/conf/company/company_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.ADD);
        return "settle/conf/company/company_edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        Company company = GsonUtils.wrapDataToEntity(request, Company.class);
        companyService.save(company);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = "/modify", params = {"id"}, method = RequestMethod.GET)
    public String toModify(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.MODIFY);
        request.setAttribute("id", id);
        return "settle/conf/company/company_edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        Company company = GsonUtils.wrapDataToEntity(request, Company.class);
        companyService.update(company);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = {"/detail"}, params = {"id"}, method = RequestMethod.GET)
    public String toDetail(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "settle/conf/company/company_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        CompanyVo vo = companyService.findById(id);
        GsonUtils.printData(response, vo);
    }

    @ResponseBody
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST)
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) {
        CompanyBo bo = GsonUtils.wrapDataToEntity(request, CompanyBo.class);
        if (bo == null) {
            bo = new CompanyBo();
        }
        bo.setOrgId(SecurityContext.getOrgId());
        PageVo pageVo = companyService.pageQuery(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public void query(HttpServletRequest request, HttpServletResponse response) {
        CompanyBo bo = GsonUtils.wrapDataToEntity(request, CompanyBo.class);
        if (bo == null) {
            bo = new CompanyBo();
        }
        bo.setOrgId(SecurityContext.getOrgId());
        bo.setDeleted(false);
        List<CompanyVo> vos = companyService.query(bo);
        GsonUtils.printData(response, vos);
    }

    @ResponseBody
    @RequestMapping(value = "/enable", params = {"ids"}, method = RequestMethod.POST)
    public void enable(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        companyService.enable(idArr);
        GsonUtils.printSuccess(response);
    }

    @ResponseBody
    @RequestMapping(value = "/disable", params = {"ids"}, method = RequestMethod.POST)
    public void disable(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        companyService.disable(idArr);
        GsonUtils.printSuccess(response);
    }

    // 查询所有有效的数据，不使用分页
    @ResponseBody
    @RequestMapping(value = "/query-valid", method = RequestMethod.POST)
    public void queryValid(HttpServletRequest request, HttpServletResponse response) {
        CompanyBo bo = GsonUtils.wrapDataToEntity(request, CompanyBo.class);
        List<Company> data = companyService.queryValid(bo);
        GsonUtils.printData(response, data);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        companyService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }


}
