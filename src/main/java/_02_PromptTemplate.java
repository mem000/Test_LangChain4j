// Importing necessary classes for chat language model and OpenAI chat model

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;

// Importing constants for specifying the model name (GPT-3.5 Turbo in this case) and setting duration
import java.util.HashMap;
import java.util.Map;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;
import static java.time.Duration.ofSeconds;

public class _02_PromptTemplate {
    public static void main(String[] args) {
        // Setting up parameters for the OpenAI API
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_API_KEY) // Setting the API key for authentication
                .modelName(GPT_4_O_MINI) // Specifying the model name to use (GPT-3.5 Turbo)
                .timeout(ofSeconds(60)) // Setting the timeout duration for API requests to 60 seconds
                .build(); // Building the model with the specified parameters

        // Defining a template for the prompt with placeholders for variables
        String template = "Create a recipe for a {{dishType}} with the following ingredients: {{ingredients}}";

        // Creating a PromptTemplate object from the template string
        PromptTemplate promptTemplate = PromptTemplate.from(template);

        // Creating a map to hold the variables and their values
        Map<String, Object> variables = new HashMap<>();
        variables.put("dishType", "oven dish"); // Adding a value for the dishType variable
        variables.put("ingredients", "potato, tomato, feta, olive oil"); // Adding a value for the ingredients variable

        // Applying the variables to the template to generate the final prompt
        Prompt prompt = promptTemplate.apply(variables);

        // Generating a response from the model based on the final prompt
        String response = model.generate(prompt.text());

        // Printing the response to the console
        System.out.println(response);
    }
}
