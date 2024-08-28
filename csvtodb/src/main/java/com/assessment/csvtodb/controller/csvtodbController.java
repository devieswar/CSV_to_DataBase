package com.assessment.csvtodb.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assessment.csvtodb.entity.Node;
import com.assessment.csvtodb.service.NodeService;

@RestController
public class csvtodbController {

    // Injecting the NodeService to handle business logic related to Node entities
    @Autowired
    private NodeService nodeService;

    // Simple endpoint that returns a greeting message
    @GetMapping("/hello")
    public String sendGreetings() {
        return "Hello, World!";
    }

    // Endpoint to retrieve all nodes from the database and display them using a Thymeleaf template
    @GetMapping("/nodesp")
    public String getAllNodes(Model model) {
        // Fetching all nodes from the database using the NodeService
        List<Node> nodes = nodeService.getAllNodes();
        
        model.addAttribute("nodes", nodes);
        
        // Returning the name of the Thymeleaf template (viewnodes.html) to be rendered
        return "viewnodes"; 
    }

    // Endpoint to add a new Node entity using request parameters
    @PostMapping("/addnode")
    public Node addNode(@RequestParam Node node) {
        // Adding the node using NodeService and returning the added node
        return nodeService.addNode(node);
    }

    // Endpoint to handle the upload of a CSV file, parse its contents, and save to the database
    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        List<Node> nodes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                // Creating a new Node object and setting its properties based on CSV columns
                Node node = new Node();
                node.setNodeId(csvRecord.get("Node ID"));
                node.setNodeName(csvRecord.get("Node name"));
                node.setDescription(csvRecord.get("Description"));
                node.setMemo(csvRecord.get("Memo"));
                node.setNodeType(csvRecord.get("Node type"));
                node.setParentNodeGroupName(csvRecord.get("Parent node group name"));
                node.setParentNodeGroupId(csvRecord.get("Parent node group ID"));
                node.setParentNodeName(csvRecord.get("Parent node name"));

                // Adding the Node object to the list
                nodes.add(node);
            }

            // Saving all parsed nodes to the database using NodeService
            nodeService.saveAll(nodes);

            // Returning a success response
            return ResponseEntity.ok("CSV file uploaded and data saved successfully!");

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error reading CSV file: " + e.getMessage());
        }
    }
}
