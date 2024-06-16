//package com.ETechAdvisor.ETechAdvisor.ChatGPT;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import java.util.Map;
//
//@RestController
//public class ChatGptController {
//
//    private final ChatGptService chatGptService;
//
//    @Autowired
//    public ChatGptController(ChatGptService chatGptService) {
//        this.chatGptService = chatGptService;
//    }
//
//    @GetMapping("/chatgpt")
//    public ResponseEntity<Map<String, Double>> getParameters(@RequestParam String prompt) {
//        String staticPrompt = "Please evaluate the importance of the following features for a smartphone, providing a rating between 0.1 and 1.0, where 1.0 signifies utmost importance , the parameters that do not have any role in the specific request must be kept with value 0.1. Format your response as 'Feature: Importance' and nothing more. Features to evaluate include: storage capacity, battery power, screen size, camera megapixels, Android OS, presence of an audio jack. ";
//        String combinedPrompt = staticPrompt + prompt;
//
//        Map<String, Double> importanceValues = chatGptService.getImportanceValues(combinedPrompt);
//        return ResponseEntity.ok(importanceValues);
//    }
//}
