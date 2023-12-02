package com.study.grpware.todoList;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/todo")
public class TodoListController {

    @GetMapping(value = "/todoPage")
    public String goToTodoList(Model model){
        return "/todo/todoPage";
    }

}
