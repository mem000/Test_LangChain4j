// import classes for chat memory management, AI model interaction, and service management.

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

public class ServiceWithMemoryForEachUserExample {

    /**
     * This example demonstrates how to handle chat memory separately for each user.
     * This allows different users to have their own independent chat contexts.
     * For a different approach to memory management, see {@link ServiceWithPersistentMemoryForEachUserExample}.
     */

    // Define an interface for the assistant service.
    // The chat method includes memoryId to differentiate chat contexts for different users.
    interface Assistant {

        /**
         * Method to send a user message to the assistant and receive a response.
         * The memoryId parameter allows maintaining separate chat memories for different users.
         *
         * @param memoryId    Unique identifier for the user's chat memory.
         * @param userMessage The message sent by the user.
         * @return The response from the assistant.
         */
        String chat(@MemoryId int memoryId, @UserMessage String userMessage);
    }

    public static void main(String[] args) {

        // Build an instance of OpenAiChatModel using the builder pattern.
        // The model is configured with an API key and the GPT-3.5-Turbo model name.
        // This model will handle generating responses based on user messages.
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_API_KEY) // Replace ApiKeys.OPENAI_API_KEY with your actual OpenAI API key.
                .modelName(GPT_4_O_MINI) // Specifies the model to use (GPT-3.5-Turbo).
                .build();

        // Create an instance of the Assistant service using AiServices.
        // This service integrates the chat model and a memory provider to handle chat contexts.
        // The chatMemoryProvider function returns a new MessageWindowChatMemory instance for each memoryId.
        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(model) // Set the chat language model to use for generating responses.
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10)) // Create a new MessageWindowChatMemory for each memoryId with a limit of 10 messages.
                .build();

        // Send a message to the assistant with memoryId 1 and print the response.
        // The response should include the user's name as part of the context.
        System.out.println(assistant.chat(1, "Hello, my name is Mahmoud"));
        // Expected output: "Hi Mahmoud! How can I assist you today?"

        // Send a message to the assistant with memoryId 2 and print the response.
        // This should respond independently of the first user's context.
        System.out.println(assistant.chat(2, "Hello, my name is Esmat"));
        // Expected output: "Hello Esmat! How can I assist you today?"

        // Send another message to the assistant with memoryId 1 and print the response.
        // This should refer to the previous context for memoryId 1.
        System.out.println(assistant.chat(1, "What is my name?"));
        // Expected output: "Your name is Mahmoud."

        // Send another message to the assistant with memoryId 2 and print the response.
        // This should refer to the previous context for memoryId 2.
        System.out.println(assistant.chat(2, "What is my name?"));
        // Expected output: "Your name is Esmat."
    }
}
