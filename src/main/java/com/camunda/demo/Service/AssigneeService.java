package com.camunda.demo.Service;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Whatmore
 * @created 23/11/2020 - 14:45
 * @project my-project
 */
@Service
public class AssigneeService implements TaskListener {


    @Autowired
    TaskService taskService;

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee(String.valueOf(delegateTask.getVariable("assignee")));

    }
}
