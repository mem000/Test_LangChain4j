// Import necessary classes from the Langchain4j library for creating a chat model and AI services.

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

// Import static constant for using the GPT-3.5 Turbo model.
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

public class SimpleServiceExample {

    // Define an interface 'Assistant' that declares a method 'chat' for interacting with the AI.
    interface Assistant {
        String chat(String message); // The method takes a string (user message) and returns a string (AI's response).
    }

    public static void main(String[] args) {

        // Create an instance of ChatLanguageModel using OpenAiChatModel's builder method.
        // This configures the model to use OpenAI's GPT-3.5 Turbo with an API key.
        ChatLanguageModel chatLanguageModel = OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_API_KEY)    // Set the OpenAI API key (replace with your actual API key).
                .modelName(GPT_4_O_MINI)          // Specify the OpenAI model to use (GPT-3.5 Turbo).
                .build();                          // Build the chat model instance.

        // Create an implementation of the 'Assistant' interface using the AiServices utility.
        // The create method dynamically implements the 'Assistant' interface, linking it to the specified chat model.
        Assistant assistant = AiServices.create(Assistant.class, chatLanguageModel);

        // Call the 'chat' method of the assistant interface with the message "Hello".
        // This sends the message to the AI model, which returns a response.
        String answer = assistant.chat("Hello");

        // Print the AI's response to the console. Example: "Hello! How can I assist you today?"
        System.out.println(answer);
    }
}
