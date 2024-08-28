package com.assessment.csvtodb.service;
import com.assessment.csvtodb.entity.Node;
import com.assessment.csvtodb.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeService {
	    @Autowired
	    private NodeRepository nodeRepository;

	    public Node addNode(Node node) {
	        return nodeRepository.save(node);
	    }

	    public List<Node> getAllNodes() {
	        return nodeRepository.findAll();
	    }

	    public void saveAll(List<Node> nodes) {
	        nodeRepository.saveAll(nodes);
	    }

}
