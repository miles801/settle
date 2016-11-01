package com.michael.settle.report.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.michael.common.JspAccessType;
import com.michael.core.pager.PageVo;
import com.michael.core.web.BaseController;
import com.michael.poi.exp.ExportEngine;
import com.michael.settle.report.bo.GroupVipBo;
import com.michael.settle.report.domain.GroupVip;
import com.michael.settle.report.service.GroupVipService;
import com.michael.settle.report.vo.GroupVipVo;
import com.michael.utils.gson.DateStringConverter;
import com.michael.utils.gson.DoubleConverter;
import com.michael.utils.gson.GsonUtils;
import com.michael.utils.string.StringUtils;
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
@RequestMapping(value = {"/settle/report/groupVip"})
public class GroupVipCtrl extends BaseController {
    @Resource
    private GroupVipService groupVipService;


    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "settle/report/groupVip/groupVip_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.ADD);
        return "settle/report/groupVip/groupVip_edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        GroupVip groupVip = GsonUtils.wrapDataToEntity(request, GroupVip.class);
        groupVipService.save(groupVip);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = "/modify", params = {"id"}, method = RequestMethod.GET)
    public String toModify(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.MODIFY);
        request.setAttribute("id", id);
        return "settle/report/groupVip/groupVip_edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        GroupVip groupVip = GsonUtils.wrapDataToEntity(request, GroupVip.class);
        groupVipService.update(groupVip);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = {"/detail"}, params = {"id"}, method = RequestMethod.GET)
    public String toDetail(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "settle/report/groupVip/groupVip_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        GroupVipVo vo = groupVipService.findById(id);
        GsonUtils.printData(response, vo);
    }

    @ResponseBody
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST)
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) {
        GroupVipBo bo = GsonUtils.wrapDataToEntity(request, GroupVipBo.class);
        PageVo pageVo = groupVipService.pageQuery(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public void query(HttpServletRequest request, HttpServletResponse response) {
        GroupVipBo bo = GsonUtils.wrapDataToEntity(request, GroupVipBo.class);
        List<GroupVipVo> vos = groupVipService.query(bo);
        GsonUtils.printData(response, vos);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        groupVipService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }

    // 导出数据
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public String export(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateStringConverter("yyyy-MM"))
                .create();
        GroupVipBo bo = GsonUtils.wrapDataToEntity(request, GroupVipBo.class);
        List<GroupVipVo> data = groupVipService.query(bo);
        String json = gson.toJson(data);
        JsonElement element = gson.fromJson(json, JsonElement.class);
        JsonObject o = new JsonObject();
        o.add("c", element);
        String disposition = null;//
        try {
            disposition = "attachment;filename=" + URLEncoder.encode("团队会员数据" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xlsx", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", disposition);
        try {
            InputStream inputStream = GroupVipCtrl.class.getClassLoader().getResourceAsStream("export_groupVip.xlsx");
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
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
        InputStream input = GroupVipCtrl.class.getClassLoader().getResourceAsStream("import_groupVip.xlsx");
        Assert.notNull(input, "模板下载失败!团队会员数据导入模板不存在!");
        response.setContentType("application/vnd.ms-excel");
        String disposition = null;//
        try {
            disposition = "attachment;filename=" + URLEncoder.encode("团队会员数据导入模板.xlsx", "UTF-8");
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


    // 设置返佣
    @RequestMapping(value = "/bonus", method = RequestMethod.POST)
    @ResponseBody
    public void setBonus(HttpServletRequest request, HttpServletResponse response) {
        GroupVipBo bo = GsonUtils.wrapDataToEntity(request, GroupVipBo.class);
        groupVipService.setBonus(bo);
        GsonUtils.printSuccess(response);
    }


    // 发送短信
    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    @ResponseBody
    public void sendSms(HttpServletRequest request, HttpServletResponse response) {
        GroupVipBo bo = GsonUtils.wrapDataToEntity(request, GroupVipBo.class);
        int total = groupVipService.sendSms(bo);
        GsonUtils.printData(response, total);
    }

    // 分析1
    @RequestMapping(value = "/analysis1", method = RequestMethod.POST)
    @ResponseBody
    public void analysis1(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> data = groupVipService.analysis1(request.getParameter("m1"), request.getParameter("m2"), request.getParameter("company"));
        GsonUtils.printData(response, data);
    }

    // 分析2
    @RequestMapping(value = "/analysis2", method = RequestMethod.POST)
    @ResponseBody
    public void analysis2(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> data = groupVipService.analysis2(request.getParameter("company"));
        GsonUtils.printData(response, data);
    }

    // 分析3
    @RequestMapping(value = "/analysis3", method = RequestMethod.POST)
    @ResponseBody
    public void analysis3(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> data = groupVipService.analysis3(StringUtils.decodeByUTF8(request.getParameter("groupName")));
        GsonUtils.printData(response, data);
    }


    // 导出数据-- 团队佣金
    @RequestMapping(value = "/export-bonus", method = RequestMethod.GET)
    public String exportBonus(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateStringConverter("yyyy-MM"))
                .registerTypeAdapter(Double.class, new DoubleConverter())
                .create();
        GroupVipBo bo = GsonUtils.wrapDataToEntity(request, GroupVipBo.class);
        List<GroupVipVo> data = groupVipService.query(bo);
        String json = gson.toJson(data);
        JsonElement element = gson.fromJson(json, JsonElement.class);
        JsonObject o = new JsonObject();
        o.add("c", element);
        String disposition = null;//
        try {
            disposition = "attachment;filename=" + URLEncoder.encode("团队佣金数据" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xlsx", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", disposition);
        try {
            InputStream inputStream = GroupVipCtrl.class.getClassLoader().getResourceAsStream("export_groupBonus.xlsx");
            Assert.notNull(inputStream, "数据导出失败!模板文件不存在，请与管理员联系!");
            new ExportEngine().export(response.getOutputStream(), inputStream, o);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 导出数据 -- 会员活跃度
    @RequestMapping(value = "/export-activity", method = RequestMethod.GET)
    public String exportVipActivity(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateStringConverter("yyyy-MM"))
                .registerTypeAdapter(Double.class, new DoubleConverter())
                .create();
        GroupVipBo bo = GsonUtils.wrapDataToEntity(request, GroupVipBo.class);
        List<GroupVipVo> data = groupVipService.query(bo);
        String json = gson.toJson(data);
        JsonElement element = gson.fromJson(json, JsonElement.class);
        JsonObject o = new JsonObject();
        o.add("c", element);
        String disposition = null;//
        try {
            disposition = "attachment;filename=" + URLEncoder.encode("会员活跃度数据" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xlsx", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", disposition);
        try {
            InputStream inputStream = GroupVipCtrl.class.getClassLoader().getResourceAsStream("export_vipActivity.xlsx");
            Assert.notNull(inputStream, "数据导出失败!模板文件不存在，请与管理员联系!");
            new ExportEngine().export(response.getOutputStream(), inputStream, o);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 导出数据 -- 汇总
    @RequestMapping(value = "/export-total", method = RequestMethod.GET)
    public String exportTotal(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateStringConverter("yyyy-MM"))
                .registerTypeAdapter(Double.class, new DoubleConverter())
                .create();
        GroupVipBo bo = GsonUtils.wrapDataToEntity(request, GroupVipBo.class);
        String name = request.getParameter("_name");
        if (StringUtils.isEmpty(name)) {
            name = "汇总数据";
        } else {
            name = StringUtils.decodeByUTF8(name);
        }
        List<GroupVipVo> data = groupVipService.query(bo);
        String json = gson.toJson(data);
        JsonElement element = gson.fromJson(json, JsonElement.class);
        JsonObject o = new JsonObject();
        o.add("c", element);
        String disposition = null;//
        try {
            disposition = "attachment;filename=" + URLEncoder.encode(name + "-" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xlsx", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", disposition);
        try {
            InputStream inputStream = GroupVipCtrl.class.getClassLoader().getResourceAsStream("export_abcd.xlsx");
            Assert.notNull(inputStream, "数据导出失败!模板文件不存在，请与管理员联系!");
            new ExportEngine().export(response.getOutputStream(), inputStream, o);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
