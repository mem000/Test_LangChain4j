// Importing necessary classes for chat language model and OpenAI chat model

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.structured.StructuredPrompt;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import dev.langchain4j.model.openai.OpenAiChatModel;

// Importing constants for specifying the model name (GPT-3.5 Turbo in this case) and setting duration
import java.util.Arrays;
import java.util.List;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;
import static java.time.Duration.ofSeconds;

public class _03_StructuredPromptTemplate {
    @StructuredPrompt({
            "Create a recipe of a {{dish}} that can be prepared using only {{ingredients}}.",
            "Structure your answer in the following way:",
            "Recipe name:",
            "Description:",
            "Preparation time: ...",
            "Required ingredients:",
            "Instructions:"
    })
    static class CreateRecipePrompt {
        String dish;
        List<String> ingredients;

        // Constructor to initialize the dish and ingredients
        CreateRecipePrompt(String dish, List<String> ingredients) {
            this.dish = dish;
            this.ingredients = ingredients;
        }
    }

    public static void main(String[] args) {
        // Setting up parameters for the OpenAI API
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_API_KEY) // Setting the API key for authentication
                .timeout(ofSeconds(60)) // Setting the timeout duration for API requests to 60 seconds
                .build(); // Building the model with the specified parameters

        // Creating an instance of CreateRecipePrompt with the dish and ingredients
        CreateRecipePrompt createRecipePrompt = new CreateRecipePrompt(
                "salad", // Specifying the dish type
                Arrays.asList("cucumber", "tomato", "feta", "onion", "olives") // Specifying the ingredients
        );

        // Converting the CreateRecipePrompt object to a Prompt object
        Prompt prompt = StructuredPromptProcessor.toPrompt(createRecipePrompt);

        // Generating a response from the model based on the prompt
        String recipe = model.generate(prompt.text());

        // Printing the generated recipe to the console
        System.out.println(recipe);
    }
}
