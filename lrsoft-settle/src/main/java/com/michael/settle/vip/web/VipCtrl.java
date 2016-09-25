package com.michael.settle.vip.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.michael.common.JspAccessType;
import com.michael.core.pager.PageVo;
import com.michael.core.web.BaseController;
import com.michael.poi.exp.ExportEngine;
import com.michael.settle.vip.bo.VipBo;
import com.michael.settle.vip.domain.Vip;
import com.michael.settle.vip.service.VipService;
import com.michael.settle.vip.vo.VipVo;
import com.michael.utils.gson.DateStringConverter;
import com.michael.utils.gson.GsonUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Michael
 */
@Controller
@RequestMapping(value = {"/settle/vip/vip"})
public class VipCtrl extends BaseController {
    @Resource
    private VipService vipService;


    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "settle/vip/vip/vip_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.ADD);
        return "settle/vip/vip/vip_edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        Vip vip = GsonUtils.wrapDataToEntity(request, Vip.class);
        vipService.save(vip);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = "/modify", params = {"id"}, method = RequestMethod.GET)
    public String toModify(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.MODIFY);
        request.setAttribute("id", id);
        return "settle/vip/vip/vip_edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        Vip vip = GsonUtils.wrapDataToEntity(request, Vip.class);
        vipService.update(vip);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = {"/detail"}, params = {"id"}, method = RequestMethod.GET)
    public String toDetail(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "settle/vip/vip/vip_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        VipVo vo = vipService.findById(id);
        GsonUtils.printData(response, vo);
    }

    @ResponseBody
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST)
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) {
        VipBo bo = GsonUtils.wrapDataToEntity(request, VipBo.class);
        PageVo pageVo = vipService.pageQuery(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public void query(HttpServletRequest request, HttpServletResponse response) {
        VipBo bo = GsonUtils.wrapDataToEntity(request, VipBo.class);
        List<VipVo> vos = vipService.query(bo);
        GsonUtils.printData(response, vos);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        vipService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }

    // 导出数据
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public String export(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateStringConverter("yyyy-MM-dd HH:mm:ss"))
                .create();
        VipBo bo = GsonUtils.wrapDataToEntity(request, VipBo.class);
        List<VipVo> data = vipService.query(bo);
        String json = gson.toJson(data);
        JsonElement element = gson.fromJson(json, JsonElement.class);
        JsonObject o = new JsonObject();
        o.add("c", element);
        String disposition = null;//
        try {
            disposition = "attachment;filename=" + URLEncoder.encode("会员数据" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xlsx", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", disposition);
        try {
            InputStream inputStream = VipCtrl.class.getClassLoader().getResourceAsStream("export_vip.xlsx");
            Assert.notNull(inputStream, "数据导出失败!模板文件不存在，请与管理员联系!");
            new ExportEngine().export(response.getOutputStream(), inputStream, o);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 下载模板
    @ResponseBody
    @RequestMapping(value = "/template", method = RequestMethod.GET)
    public void downloadTemplate(HttpServletResponse response) {
        InputStream input = VipCtrl.class.getClassLoader().getResourceAsStream("import_vip.xlsx");
        Assert.notNull(input, "模板下载失败!会员数据导入模板不存在!");
        response.setContentType("application/vnd.ms-excel");
        String disposition = null;//
        try {
            disposition = "attachment;filename=" + URLEncoder.encode("会员数据导入模板.xlsx", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-disposition", disposition);
        try {
            IOUtils.copy(input, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 跳转到导入页面
    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String toImport(HttpServletRequest request) {
        return "settle/vip/vip/vip_import";
    }

    // 执行导入
    @ResponseBody
    @RequestMapping(value = "/import", params = {"company", "attachmentIds"}, method = RequestMethod.POST)
    public void importData(
            @RequestParam String company,
            @RequestParam String attachmentIds, HttpServletResponse response) {
        vipService.importData(company, attachmentIds.split(","));
        GsonUtils.printSuccess(response);
    }

    // 清空
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    public void clear(HttpServletRequest request, HttpServletResponse response) {
        vipService.clear();
        GsonUtils.printSuccess(response);
    }

    // 产生报表
    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public void report(HttpServletRequest request, HttpServletResponse response) {
        vipService.report();
        GsonUtils.printSuccess(response);
    }

    // 统计
    @RequestMapping(value = "/analysis", method = RequestMethod.GET)
    public void analysis(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> o = GsonUtils.wrapDataToEntity(request, Map.class);
        List<Map<String, Object>> data = vipService.analysis(o);
        GsonUtils.printData(response, data);
    }


}
