// Importing necessary classes for chat language model and OpenAI chat model

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

// Importing constants for specifying the model name (GPT-3.5 Turbo in this case) and setting duration
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;
import static java.time.Duration.ofSeconds;

public class _01_ModelParameters {
    public static void main(String[] args) {
        // Setting up parameters for the OpenAI API
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_API_KEY) // Setting the API key for authentication
                .modelName(GPT_4_O_MINI) // Specifying the model name to use (GPT-3.5 Turbo)
                .temperature(0.3) // Setting the temperature for response variability (0.3 for less randomness)
                .timeout(ofSeconds(60)) // Setting the timeout duration for API requests to 60 seconds
                .logRequests(true) // Enabling logging of requests for debugging purposes
                .logResponses(true) // Enabling logging of responses for debugging purposes
                .build(); // Building the model with the specified parameters

        // Defining the prompt to be sent to the model
        String prompt = "Explain in three lines how to make a beautiful painting";

        // Generating a response from the model based on the prompt
        String response = model.generate(prompt);

        // Printing the response to the console
        System.out.println(response);
    }
}
