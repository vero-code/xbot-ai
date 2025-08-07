package org.example.xbotai.service.core.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import org.example.xbotai.dto.TweetLogDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlockchainService {

    private final ObjectMapper objectMapper;
    private final OkHttpClient httpClient;
    private final String contractId;
    private static final String NEAR_RPC_URL = "https://rpc.testnet.near.org";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public BlockchainService(ObjectMapper objectMapper, @Value("${xbot.blockchain.contract-id}") String contractId) {
        this.objectMapper = objectMapper;
        this.httpClient = new OkHttpClient();
        this.contractId = contractId;
    }

    /**
     * Reads data directly from Java.
     * Fetches and filters logs via a direct RPC call.
     */
    public List<TweetLogDto> getLogsForUser(String userIdToFilter) throws IOException {
        ObjectNode args = objectMapper.createObjectNode();
        String argsBase64 = Base64.getEncoder().encodeToString("{}".getBytes());

        ObjectNode params = objectMapper.createObjectNode();
        params.put("request_type", "call_function");
        params.put("finality", "final");
        params.put("account_id", this.contractId);
        params.put("method_name", "getLogs");
        params.put("args_base64", argsBase64);

        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("jsonrpc", "2.0");
        requestBody.put("id", "dontcare");
        requestBody.put("method", "query");
        requestBody.set("params", params);

        RequestBody body = RequestBody.create(requestBody.toString(), JSON);
        Request request = new Request.Builder().url(NEAR_RPC_URL).post(body).build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            JsonNode responseJson = objectMapper.readTree(response.body().string());
            JsonNode resultNode = responseJson.path("result").path("result");
            if (resultNode.isMissingNode() || !resultNode.isArray()) {
                return Collections.emptyList();
            }

            byte[] resultBytes = new byte[resultNode.size()];
            for(int i=0; i < resultNode.size(); i++) {
                resultBytes[i] = (byte) resultNode.get(i).asInt();
            }

            List<TweetLogDto> allLogs = objectMapper.readValue(resultBytes, new TypeReference<>() {});

            return allLogs.stream()
                    .filter(log -> userIdToFilter.equals(log.getUserId()))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Writes data via a Node.js script.
     * This method uses your working script to handle the complex task of signing and sending a transaction.
     */
    public String logTweetToBlockchain(TweetLogDto tweetLog) {
        File tempFile = null;
        try {
            String jsonString = objectMapper.writeValueAsString(tweetLog);
            tempFile = File.createTempFile("payload", ".json");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(jsonString);
            }

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "node", "./blockchain/near-logger.js", "--file=" + tempFile.getAbsolutePath()
            );
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Node.js script output:\n" + output);
                return "Tweet successfully logged.";
            } else {
                System.err.println("Node.js script failed with exit code " + exitCode + ". Output:\n" + output);
                return "Failed to log tweet. See server logs for details.";
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Error calling Node.js script: " + e.getMessage();
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
