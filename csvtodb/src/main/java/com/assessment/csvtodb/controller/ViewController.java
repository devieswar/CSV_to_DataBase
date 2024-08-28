package com.assessment.csvtodb.controller;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import com.assessment.csvtodb.entity.Node;
import com.assessment.csvtodb.service.NodeService;


@Controller
public class ViewController {
	@Autowired
    private NodeService nodeService;

    
    @GetMapping("/nodes")
    public String getAllNodes(Model model) {
    	List<Node> nodes = nodeService.getAllNodes();
        model.addAttribute("nodes", nodes);
        return "viewnodes"; 
    }
}
