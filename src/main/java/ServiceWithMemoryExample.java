//import necessary classes for managing chat memory, interacting with the AI model, and providing AI services.

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

public class ServiceWithMemoryExample {

    /**
     * This example demonstrates a high-level API approach for integrating AI services with chat memory.
     * For more specific examples, refer to:
     * - {@link ServiceWithMemoryForEachUserExample} for handling memory for each user separately.
     * - {@link ServiceWithPersistentMemoryExample} for examples of persistent chat memory.
     * For a low-level API example, see {@link ChatMemoryExamples}.
     */

    // Define an interface for the assistant service.
    // This interface provides a method for chatting with the assistant.
    interface Assistant {

        /**
         * Method to send a message to the assistant and receive a response.
         *
         * @param message The message to send to the assistant.
         * @return The response from the assistant.
         */
        String chat(String message);
    }

    public static void main(String[] args) {

        // Create an instance of ChatMemory using MessageWindowChatMemory.
        // This implementation maintains a fixed number of most recent messages (10 in this case).
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        // Build an instance of OpenAiChatModel using the builder pattern.
        // The model is configured with an API key and the GPT-3.5-Turbo model name.
        // This model will handle generating responses.
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_API_KEY) // Replace ApiKeys.OPENAI_API_KEY with your actual OpenAI API key.
                .modelName(GPT_4_O_MINI) // Specifies the model to use (GPT-3.5-Turbo).
                .build();

        // Create an instance of the Assistant service using AiServices.
        // This service integrates the chat model and chat memory to provide chat functionalities.
        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(model) // Set the chat language model to use for generating responses.
                .chatMemory(chatMemory) // Set the chat memory to use for maintaining conversation context.
                .build();

        // Send a message to the assistant and receive a response.
        String answer = assistant.chat("Hello! My name is Mahmoud.");
        // Print the response from the assistant.
        // Expected output: "Hello Mahmoud! How can I assist you today?"
        System.out.println(answer);

        // Send another message to the assistant and receive a new response.
        String answerWithName = assistant.chat("What is my name?");
        // Print the response from the assistant.
        // Expected output: "Your name is Mahmoud."
        System.out.println(answerWithName);
    }
}
