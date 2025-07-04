package com.heds.project.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heds.project.Service.AdminService;
import com.heds.project.config.modul.Admin;
import com.heds.project.mapper.AdminMapper;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
}
