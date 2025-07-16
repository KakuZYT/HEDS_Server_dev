package com.heds.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.heds.project.Service.AdminService;
import com.heds.project.Service.ConsultationServer;
import com.heds.project.config.modul.Admin;
import com.heds.project.config.modul.Consultation;
import com.heds.project.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manage")
public class ContectInfoController {
    @Autowired
    private ConsultationServer consultationServer;
    @Autowired
    private AdminService adminService;
    @GetMapping("/ShowAdmin")
    public Result<List<Admin>> showAdmin(HttpServletRequest request) {
        //show all admins:
        return Result.ok(adminService.list());
    }

    @GetMapping("/ShowCommon")
    public Result<List<Consultation>> showcommon(HttpServletRequest request) {
        try{
            consultationServer.list(new QueryWrapper<Consultation>().eq("status",0));
        }catch (Exception e){
            return Result.fail();
        }
        //show all common:
        return Result.ok();
    }

    @GetMapping("/del")
    public Result<List<Consultation>> ChangeStatus(@RequestParam Integer id, HttpServletRequest request) {
        if (id == null) {
            // 如果未传 id，返回 400 Bad Request
            return Result.fail();
        }
        //更改状态
        //change status
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", id);
        updateWrapper.set("status", 1);
        consultationServer.update(updateWrapper);
        //show all common:
        return Result.ok(consultationServer.list(new QueryWrapper<Consultation>().like("status",0)));
    }

}
