package org.example.xbotai.controller;

import org.example.xbotai.dto.TweetLogDto;
import org.example.xbotai.service.core.impl.BlockchainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/blockchain")
public class BlockchainController {

    private final BlockchainService blockchainService;

    public BlockchainController(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }

    @GetMapping("/logs")
    public ResponseEntity<List<TweetLogDto>> getLogsForUser(@RequestParam String userId) throws IOException {
        List<TweetLogDto> logs = blockchainService.getLogsForUser(userId);
        return ResponseEntity.ok(logs);
    }

    @PostMapping("/test-log")
    public ResponseEntity<String> testLog(@RequestBody TweetLogDto tweetLog) {
        String result = blockchainService.logTweetToBlockchain(tweetLog);
        return ResponseEntity.ok(result);
    }
}
