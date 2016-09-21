package com.michael.settle.mapping.web;

import com.michael.common.JspAccessType;
import com.michael.core.pager.PageVo;
import com.michael.core.web.BaseController;
import com.michael.settle.mapping.bo.MappingBo;
import com.michael.settle.mapping.domain.Mapping;
import com.michael.settle.mapping.service.MappingService;
import com.michael.settle.mapping.vo.MappingVo;
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
@RequestMapping(value = {"/settle/mapping/mapping"})
public class MappingCtrl extends BaseController {
    @Resource
    private MappingService mappingService;


    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "settle/mapping/mapping/mapping_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.ADD);
        return "settle/mapping/mapping/mapping_edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        Mapping mapping = GsonUtils.wrapDataToEntity(request, Mapping.class);
        mappingService.save(mapping);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = "/modify", params = {"id"}, method = RequestMethod.GET)
    public String toModify(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.MODIFY);
        request.setAttribute("id", id);
        return "settle/mapping/mapping/mapping_edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        Mapping mapping = GsonUtils.wrapDataToEntity(request, Mapping.class);
        mappingService.update(mapping);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = {"/detail"}, params = {"id"}, method = RequestMethod.GET)
    public String toDetail(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "settle/mapping/mapping/mapping_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        MappingVo vo = mappingService.findById(id);
        GsonUtils.printData(response, vo);
    }

    @ResponseBody
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST)
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) {
        MappingBo bo = GsonUtils.wrapDataToEntity(request, MappingBo.class);
        PageVo pageVo = mappingService.pageQuery(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public void query(HttpServletRequest request, HttpServletResponse response) {
        MappingBo bo = GsonUtils.wrapDataToEntity(request, MappingBo.class);
        List<MappingVo> vos = mappingService.query(bo);
        GsonUtils.printData(response, vos);
    }

    @ResponseBody
    @RequestMapping(value = "/enable", params = {"ids"}, method = RequestMethod.POST)
    public void enable(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        mappingService.enable(idArr);
        GsonUtils.printSuccess(response);
    }

    @ResponseBody
    @RequestMapping(value = "/disable", params = {"ids"}, method = RequestMethod.POST)
    public void disable(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        mappingService.disable(idArr);
        GsonUtils.printSuccess(response);
    }

    // 查询所有有效的数据，不使用分页
    @ResponseBody
    @RequestMapping(value = "/query-valid", method = RequestMethod.POST)
    public void queryValid(HttpServletRequest request, HttpServletResponse response) {
        MappingBo bo = GsonUtils.wrapDataToEntity(request, MappingBo.class);
        List<Mapping> data = mappingService.queryValid(bo);
        GsonUtils.printData(response, data);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        mappingService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }


}
