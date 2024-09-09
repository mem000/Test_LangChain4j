// Import necessary classes from Langchain4j to create and interact with chat models.

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

// Import constant for specifying the model name (GPT-3.5 Turbo in this case).
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

public class HelloWorldExample {

    public static void main(String[] args) {

        // Create an instance of ChatLanguageModel using OpenAiChatModel's builder method.
        // The model is initialized with an API key and a specific model name (GPT-3.5 Turbo).
        // OpenAiChatModel is used to communicate with OpenAI's language model.
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_API_KEY)      // Sets the OpenAI API key to authenticate requests. Replace with your actual API key.
                .modelName(GPT_4_O_MINI)            // Specifies the model to use (GPT-3.5 Turbo).
                .build();                            // Builds the model object.

        // Generate a response from the model by providing an input prompt ("Hello!").
        // The 'generate' method sends the input text to the language model and retrieves the generated response.
        String answer = model.generate("Hello!");

        // Print the model's generated response to the console.
        // Example response might be: "Hello! How can I assist you today?"
        System.out.println(answer);
    }
}
