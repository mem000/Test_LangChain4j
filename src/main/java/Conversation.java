
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.Scanner;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

public class Conversation {

    public static void main(String[] args) {

        // Create a scanner object to capture user input
        Scanner scanner = new Scanner(System.in);

        // Create an instance of ChatLanguageModel using OpenAiChatModel's builder method.
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_API_KEY)      // Set your OpenAI API key here.
                .modelName(GPT_4_O_MINI)            // Use GPT-3.5 Turbo model.
                .build();

        // Initialize a variable to store the user's input
        String userInput = "";

        // Continue interacting with the AI until the user enters "end"
        while (true) {
            // Prompt the user to enter a question or input
            System.out.print("You: ");
            userInput = scanner.nextLine();

            // Check if the user wants to end the conversation
            if (userInput.equalsIgnoreCase("end")) {
                System.out.println("Conversation ended.");
                break;
            }

            // Generate a response from the model based on user input
            String aiResponse = model.generate(userInput);

            // Print the AI's response
            System.out.println("AI: " + aiResponse);
        }

        // Close the scanner object to prevent resource leakage
        scanner.close();
    }
}
