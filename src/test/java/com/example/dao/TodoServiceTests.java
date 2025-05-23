package com.example.dao;

import com.example.cookie.dto.TodoDTO;
import com.example.cookie.service.TodoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@Log4j2
public class TodoServiceTests {
    private TodoService todoService;

    @BeforeEach
    public void ready() {
        todoService=TodoService.INSTANCE;
    }

    @Test
    public void testRegister() throws Exception {
        TodoDTO dto=TodoDTO.builder()
                .title("JDBC Test Title")
                .dueDate(LocalDate.now())
                .build();

        log.info("----------------------------------------------------------------------");
        log.info(dto);
        todoService.register(dto);
    }

}
