package com.nunoguzpractice.fullstackspringbootreact.web;

import com.nunoguzpractice.fullstackspringbootreact.domain.ProjectTask;
import com.nunoguzpractice.fullstackspringbootreact.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
@CrossOrigin
public class ProjectTaskController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @PostMapping("")
    public ResponseEntity<?> addPTBoard(@Validated @RequestBody ProjectTask projectTask, BindingResult result){
        if (result.hasErrors()){
            Map<String ,String> errorMap = new HashMap<>();

            for(FieldError error: result.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<Map<String ,String>>(errorMap, HttpStatus.BAD_REQUEST);
        }

        ProjectTask newPt = projectTaskService.saveOrUpdate(projectTask);

        return new ResponseEntity<ProjectTask>(newPt, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public Iterable<ProjectTask> getAllPTs(){
        return projectTaskService.findAll();
    }

    @GetMapping("/{pt_id}")
    public ResponseEntity<?> getPTById(@PathVariable Long pt_id){
        ProjectTask projectTask = projectTaskService.findById(pt_id);
        return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
    }

    @DeleteMapping("/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable Long pt_id){
        projectTaskService.delete(pt_id);
        return new ResponseEntity<String>("Project task deleted - id: "+pt_id,HttpStatus.OK);
    }
}
