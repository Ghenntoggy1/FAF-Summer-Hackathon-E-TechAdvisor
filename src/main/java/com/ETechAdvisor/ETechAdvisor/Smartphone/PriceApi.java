package com.ETechAdvisor.ETechAdvisor.Smartphone;

import okhttp3.*;
import org.json.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PriceApi {

    private static final String API_TOKEN = "QQVPGGOLZVFZBGPVHRXIYYFCNBGKXGUNZCVPMBRZVFDMJARGPIEXMSVSSUXWGQIX";
    private OkHttpClient client;

    public PriceApi() {
        this.client = new OkHttpClient();
    }

    public Price getPrice(String name, String shop) throws IOException {
        // Step 1: Submitting a Job
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "{\"source\":\"" + shop + "\",\"country\":\"us\",\"topic\":\"product_and_offers\",\"key\":\"term\"," +
                        "\"values\":\"" + name + "\",\"max_pages\":1,\"max_age\":1200}");

        Request request = new Request.Builder()
                .url("https://api.priceapi.com/v2/jobs?token=" + API_TOKEN)
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        String responseBody = response.body().string();
        JSONObject jsonResponse = new JSONObject(responseBody);
        String jobId = jsonResponse.getString("job_id");
        System.out.println("Job ID: " + jobId);

        response.close();

        // Step 2: Checking Job Status
        boolean jobFinished = false;
        String status = "";

        while (!jobFinished) {
            request = new Request.Builder()
                    .url("https://api.priceapi.com/v2/jobs/" + jobId + "?token=" + API_TOKEN)
                    .get()
                    .addHeader("accept", "application/json")
                    .build();

            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            responseBody = response.body().string();
            jsonResponse = new JSONObject(responseBody);
            status = jsonResponse.getString("status");
            System.out.println("Job Status: " + status);

            jobFinished = status.equals("finished");

            response.close();

            if (!jobFinished) {
                try {
                    Thread.sleep(500); // Wait for 500 milliseconds before checking status again
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // Step 3: Downloading Job Results
        request = new Request.Builder()
                .url("https://api.priceapi.com/v2/jobs/" + jobId + "/download?token=" + API_TOKEN)
                .get()
                .addHeader("accept", "application/json")
                .build();

        response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        responseBody = response.body().string();
        jsonResponse = new JSONObject(responseBody);

        JSONArray resultsArray = jsonResponse.getJSONArray("results");
        Price price = null;
        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject result = resultsArray.getJSONObject(i);
            JSONObject contentObject = result.getJSONObject("content");
            String name1;
            String url;
            String rrp;
            if (shop.equalsIgnoreCase("amazon")) {
                name1 = contentObject.getString("name");
                url = contentObject.getString("url");
                if (contentObject.has("rrp")) {
                    rrp = contentObject.getString("rrp");
                } else {
                    rrp = null;
                }
            } else if (shop.equalsIgnoreCase("google_shopping")) {
                JSONArray offersArray = contentObject.getJSONArray("offers");
                JSONObject offer = offersArray.getJSONObject(0);
                name1 = contentObject.getString("name");
                url = offer.getString("url");
                if (offer.has("price")) {
                    rrp = offer.getString("price");
                } else {
                    rrp = null;
                }
            } else {
                JSONArray offersArray = contentObject.getJSONArray("offers");
                JSONObject offer = offersArray.getJSONObject(0);
                name1 = offer.getString("name");
                url = offer.getString("url");
                if (offer.has("price")) {
                    rrp = offer.getString("price");
                } else {
                    rrp = null;
                }
            }

            price = Price.builder()
                    .name(name1)
                    .url(url)
                    .price(rrp.toString())
                    .merchant(shop)
                    .build();
        }

        response.close();
        return price;
    }


}
