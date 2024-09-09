import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;


public class _06_FewShot {

    public static void main(String[] args) {

        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_API_KEY) // Replace ApiKeys.OPENAI_API_KEY with your actual OpenAI API key.
                .modelName("gpt-4o-mini") // Specifies the model to use (GPT-3.5-Turbo).
                .build();

        ChatMemory chatMemory = TokenWindowChatMemory.withMaxTokens(300, new OpenAiTokenizer());

        //chatMemory.add(userMessage("Hello, my name is Mahmoud"));
        // Adding positive feedback example to history
        chatMemory.add(UserMessage.from(
                "I love the new update! The interface is very user-friendly and the new features are amazing!"));
        chatMemory.add(AiMessage.from(
                "Action: forward input to positive feedback storage\nReply: Thank you very much for this great feedback! We have transmitted your message to our product development team who will surely be very happy to hear this. We hope you continue enjoying using our product."));

        // Adding negative feedback example to history
        chatMemory.add(UserMessage
                .from("I am facing frequent crashes after the new update on my Android device."));
        chatMemory.add(AiMessage.from(
                "Action: open new ticket - crash after update Android\nReply: We are so sorry to hear about the issues you are facing. We have reported the problem to our development team and will make sure this issue is addressed as fast as possible. We will send you an email when the fix is done, and we are always at your service for any further assistance you may need."));

        // Adding another positive feedback example to history
        chatMemory.add(UserMessage
                .from("Your app has made my daily tasks so much easier! Kudos to the team!"));
        chatMemory.add(AiMessage.from(
                "Action: forward input to positive feedback storage\nReply: Thank you so much for your kind words! We are thrilled to hear that our app is making your daily tasks easier. Your feedback has been shared with our team. We hope you continue to enjoy using our app!"));

        // Adding another negative feedback example to history
        chatMemory.add(UserMessage
                .from("The new feature is not working as expected. It’s causing data loss."));
        chatMemory.add(AiMessage.from(
                "Action: open new ticket - data loss by new feature\nReply:We apologize for the inconvenience caused. Your feedback is crucial to us, and we have reported this issue to our technical team. They are working on it on priority. We will keep you updated on the progress and notify you once the issue is resolved. Thank you for your patience and support."));

        // Adding real user's message
        ChatMessage customerComplaint = UserMessage
                .from("How can your app be so slow? Please do something about it!");
        chatMemory.add(customerComplaint);

        System.out.println("[User]: " + customerComplaint.text());
        System.out.print("[LLM]: ");

        // Generate a new response from the AI model based on the updated chat memory.
        AiMessage answerWithName = model.generate(chatMemory.messages()).content();

        // Print the new response.
        // Expected output: "Your name is Mahmoud."
        System.out.println(answerWithName.text());

        // Add the new AI response to the chat memory to maintain the conversation context.
        chatMemory.add(answerWithName);

        // Extract reply and send to customer
        // Perform necessary action in back-end
    }
}