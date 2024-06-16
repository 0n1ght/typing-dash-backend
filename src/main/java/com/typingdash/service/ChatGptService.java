package com.typingdash.service;

import java.io.IOException;

public interface ChatGptService {
    String generateText(String topic) throws IOException;
}
