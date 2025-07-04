package org.example.xbotai.service.core.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.xbotai.dto.TweetLogDto;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlockchainService {

    public String logTweetToBlockchain(TweetLogDto tweetLog) {
        File tempFile = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(tweetLog);

            tempFile = File.createTempFile("tweetPayload", ".json");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(jsonString);
            }

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "node", "./blockchain/near-logger.js", "--file=" + tempFile.getAbsolutePath()
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
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.deleteOnExit();
            }
        }
    }

    /** Retrieves tweet logs from the blockchain by calling an external script without arguments. */
    public List<String> getLogs() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "node", "./blockchain/near-logger.js"
            );
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            List<String> logs = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                logs.add(line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return logs;
            } else {
                StringBuilder errorOutput = new StringBuilder();
                for (String logLine : logs) {
                    errorOutput.append(logLine).append("\n");
                }
                throw new RuntimeException("Failed to retrieve logs from blockchain. Script output:\n" + errorOutput.toString());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error occurred while retrieving logs from blockchain: " + e.getMessage(), e);
        }
    }
}
