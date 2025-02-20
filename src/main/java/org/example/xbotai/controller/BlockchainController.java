package org.example.xbotai.controller;

import org.example.xbotai.service.core.impl.BlockchainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/blockchain")
public class BlockchainController {

    private final BlockchainService blockchainService;

    public BlockchainController(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }

    @GetMapping("/logs")
    public ResponseEntity<List<String>> getLogs() {
        List<String> logs = blockchainService.getLogs();
        return ResponseEntity.ok(logs);
    }
}
