package org.example.xbotai.service.impl;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class BlockchainService {

    public String logTweetToBlockchain(String tweetContent) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "node", "./blockchain/near-logger.js", tweetContent
            );
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return "Tweet successfully logged to blockchain: " + output;
            } else {
                return "Failed to log tweet to blockchain: " + output;
            }
        } catch (IOException | InterruptedException e) {
            return "Error occurred while logging tweet to blockchain: " + e.getMessage();
        }
    }
}
