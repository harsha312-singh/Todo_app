package com.example.todo_app;

public class todo {
    private String task;
    private String status;
    public todo(String task, String status){
        this.task=task;
        this.status=status;
    }
    public String getTask(){
        return task;
    }
    public void setTask(String task){
        this.task=task;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status=status;
    }
}
