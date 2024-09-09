// Import necessary classes from the Langchain4j library for creating a chat language model and connecting to OpenAI's API.

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

// Import Java networking classes to set up a proxy for the API connection.
import java.net.InetSocketAddress;
import java.net.Proxy;

// Import static methods/constants for OpenAI model names and proxy types.
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;
import static java.net.Proxy.Type.HTTP;

public class ProxyExample {

    public static void main(String[] args) {

        // Create an instance of ChatLanguageModel using the OpenAiChatModel builder.
        // The API key for OpenAI and model name (GPT-3.5 Turbo) are provided.
        // A proxy is also configured using the HTTP protocol and the specified IP address and port.
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_API_KEY)      // Set the OpenAI API key (replace with your actual key).
                .modelName(GPT_4_O_MINI)            // Use the GPT-3.5 Turbo model from OpenAI.
                .proxy(new Proxy(HTTP,               // Set up a proxy using HTTP protocol.
                        new InetSocketAddress("39.175.77.7", 30001)))  // Provide the proxy's IP address and port.
                .build();                            // Build the chat language model instance.

        // Generate a response from the model for the input string "hello".
        String answer = model.generate("hello");

        // Print the model's response to the console.
        System.out.println(answer);
    }
}
