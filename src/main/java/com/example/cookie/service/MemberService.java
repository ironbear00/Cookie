package com.example.cookie.service;

import com.example.cookie.dao.MemberDAO;
import com.example.cookie.domain.MemberVO;
import com.example.cookie.dto.MemberDTO;
import com.example.cookie.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

@Log4j2
public enum MemberService {
    INSTANCE;

    private MemberDAO dao;
    private ModelMapper modelMapper;

    MemberService() {
        dao=new MemberDAO();
        modelMapper= MapperUtil.INSTANCE.get();
    }

    public MemberDTO login(String mid, String mpw) throws Exception {
        MemberVO vo=dao.getWithPassword(mid, mpw);

        MemberDTO dto=modelMapper.map(vo, MemberDTO.class);

        return dto;
    }

    public void updateUuid(String mid, String uuid) throws Exception{
        dao.updateUuid(mid, uuid);
    }

    public MemberDTO getByUuid(String uuid) throws Exception {
        MemberVO vo=dao.selectUUID(uuid);
        MemberDTO dto=modelMapper.map(vo, MemberDTO.class);
        return dto;
    }
}
