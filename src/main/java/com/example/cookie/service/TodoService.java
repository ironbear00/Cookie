package com.example.cookie.service;

import com.example.cookie.dao.TodoDAO;
import com.example.cookie.domain.TodoVO;
import com.example.cookie.dto.TodoDTO;
import com.example.cookie.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public enum TodoService {
    INSTANCE;

    private TodoDAO dao;
    private ModelMapper modelMapper;

    TodoService() {
        dao=new TodoDAO();
        modelMapper= MapperUtil.INSTANCE.get();
    }

    public void register(TodoDTO dto) throws Exception{
        TodoVO vo=modelMapper.map(dto, TodoVO.class);

        //System.out.println(vo);
        log.info(vo);
        dao.insert(vo);
    }

    public List<TodoDTO> listAll() throws Exception {
        List<TodoVO> voList=dao.selectAll();
        log.info("voList.........................................................................");
        log.info(voList);

        List<TodoDTO> dtoList=voList.stream().map(vo->modelMapper.map(vo, TodoDTO.class)).collect(Collectors.toList());
        return dtoList;
    }

    public TodoDTO get(Long tno) throws Exception {
        log.info(tno);
        TodoVO vo=dao.selectOne(tno);
        TodoDTO dto=modelMapper.map(vo, TodoDTO.class);
        return dto;
    }

    public void remove(Long tno) throws Exception {
        log.info(tno);
        dao.deleteOne(tno);
    }

    public void modify(TodoDTO dto) throws Exception {
        log.info(dto);
        TodoVO vo=modelMapper.map(dto, TodoVO.class);
        dao.updateOne(vo);
    }
}
