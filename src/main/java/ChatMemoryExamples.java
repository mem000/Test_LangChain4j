
// import various classes required for managing chat memory and interacting with the OpenAI model.

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;

import static dev.langchain4j.data.message.UserMessage.userMessage;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

public class ChatMemoryExamples {

    /**
     * This example demonstrates how to use a low-level {@link ChatMemory} API.
     * For a high-level API with AI Services see {@link ServiceWithMemoryExample}.
     */
    public static void main(String[] args) {

        // Create an instance of ChatMemory with a TokenWindowChatMemory implementation.
        // This implementation maintains a sliding window of a maximum number of tokens (300 in this case).
        // OpenAiTokenizer is used to tokenize the messages.
        ChatMemory chatMemory = TokenWindowChatMemory.withMaxTokens(300, new OpenAiTokenizer());

        // Build an instance of OpenAiChatModel using the builder pattern.
        // The model is configured with an API key and the GPT-3.5-Turbo model name.
        // This model will be used to generate AI responses.
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_API_KEY) // Replace ApiKeys.OPENAI_API_KEY with your actual OpenAI API key.
                .modelName(GPT_4_O_MINI) // Specifies the model to use (GPT-3.5-Turbo).
                .build();

        // You have full control over the chat memory.
        // You can decide if you want to add a particular message to the memory
        // (e.g. you might not want to store few-shot examples to save on tokens).
        // You can process/modify the message before saving if required.

        // Add a user message to the chat memory.
        // This message will be processed by the AI model to generate a response.
        chatMemory.add(userMessage("Hello, my name is Mahmoud"));

        // Generate a response from the AI model based on the current state of chat memory.
        // The model generates a response using the messages stored in chatMemory.
        AiMessage answer = model.generate(chatMemory.messages()).content();

        // Print the generated response.
        // Expected output: "Hello Mahmoud! How can I assist you today?"
        System.out.println(answer.text());

        // Add the AI's response to the chat memory to maintain the conversation context.
        chatMemory.add(answer);

        // Add another user message to the chat memory.
        chatMemory.add(userMessage("What is my name?"));

        // Generate a new response from the AI model based on the updated chat memory.
        AiMessage answerWithName = model.generate(chatMemory.messages()).content();

        // Print the new response.
        // Expected output: "Your name is Mahmoud."
        System.out.println(answerWithName.text());

        // Add the new AI response to the chat memory to maintain the conversation context.
        chatMemory.add(answerWithName);
    }
}
