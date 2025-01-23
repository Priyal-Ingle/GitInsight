package com.esteco.gitinsight.utils;
import net.minidev.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GraphqlUtils {
//    @Value("${github.graphql.url}")
    private String urlString = "https://api.github.com/graphql";

//    @Value("${github.token}")
    private String token = "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";



    public void writeStringBuilderToJsonFile(StringBuilder stringBuilder, String filePath) {
        try {
            String res = stringBuilder.toString();

            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(res);
            bufferedWriter.close();
        } catch (IOException e) {
            System.err.println("Error writing JSON to file: " + e.getMessage());
        }
    }

    public void executeGraphQLQuery(String graphqlQuery, String outputFilePath) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer "+token);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);


        JSONObject jsonBody = new JSONObject();
        jsonBody.put("query", graphqlQuery);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonBody.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        writeStringBuilderToJsonFile(response, outputFilePath);
    }
}
