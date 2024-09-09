import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

public class _00_Hello {
    public static void main(String[] args) {
        String apiKey = "demo";
        /*
        Be aware that when using the demo key, all requests to the OpenAI API go through our proxy,
         which injects the real key before forwarding your request to the OpenAI API. We do not collect
          or use your data in any way. The demo key has a quota and should only be used for demonstration
           purposes.
         */
        //OpenAiChatModel model = OpenAiChatModel.withApiKey(apiKey);
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_API_KEY)
                .modelName(GPT_4_O_MINI)
                .build();

        String answer = model.generate("Say 'Hello World'");
        System.out.println(answer); // Hello World
    }
}
