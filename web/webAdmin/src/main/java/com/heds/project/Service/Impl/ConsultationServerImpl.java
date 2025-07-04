package com.heds.project.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heds.project.Service.ConsultationServer;
import com.heds.project.config.modul.Consultation;
import com.heds.project.mapper.ConsultationMapper;
import org.springframework.stereotype.Service;

@Service
public class ConsultationServerImpl extends ServiceImpl<ConsultationMapper, Consultation> implements ConsultationServer {
}
