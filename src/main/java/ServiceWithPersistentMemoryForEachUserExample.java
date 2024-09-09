//import dev.langchain4j.data.message.ChatMessage;
//import dev.langchain4j.memory.chat.ChatMemoryProvider;
//import dev.langchain4j.memory.chat.MessageWindowChatMemory;
//import dev.langchain4j.model.chat.ChatLanguageModel;
//import dev.langchain4j.model.openai.OpenAiChatModel;
//import dev.langchain4j.service.AiServices;
//import dev.langchain4j.service.MemoryId;
//import dev.langchain4j.service.UserMessage;
//import dev.langchain4j.store.memory.chat.ChatMemoryStore;
//import org.mapdb.DB;
//import org.mapdb.DBMaker;
//
//import java.util.List;
//import java.util.Map;
//
//import static dev.langchain4j.data.message.ChatMessageDeserializer.messagesFromJson;
//import static dev.langchain4j.data.message.ChatMessageSerializer.messagesToJson;
//import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;
//import static org.mapdb.Serializer.INTEGER;
//import static org.mapdb.Serializer.STRING;
//
//public class ServiceWithPersistentMemoryForEachUserExample {
//
//    // Define an interface for the assistant with a method to handle chat requests.
//    interface Assistant {
//        // Method signature for the chat function; takes a memoryId and userMessage, and returns a response.
//        String chat(@MemoryId int memoryId, @UserMessage String userMessage);
//    }
//
//    public static void main(String[] args) {
//
//        // Instantiate a persistent chat memory store.
//        PersistentChatMemoryStore store = new PersistentChatMemoryStore();
//
//        // Create a provider for chat memory with a maximum of 10 messages per memory ID.
//        ChatMemoryProvider chatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
//                .id(memoryId) // Unique ID for the chat memory.
//                .maxMessages(10) // Limit the number of messages stored.
//                .chatMemoryStore(store) // Use the previously created store.
//                .build();
//
//        // Create a chat language model instance using OpenAI's GPT-3.5 Turbo.
//        ChatLanguageModel model = OpenAiChatModel.builder()
//                .apiKey(ApiKeys.OPENAI_API_KEY) // API key for OpenAI.
//                .modelName(GPT_4_O_MINI) // Specify the model to use.
//                .build();
//
//        // Build an assistant service with the chat language model and chat memory provider.
//        Assistant assistant = AiServices.builder(Assistant.class)
//                .chatLanguageModel(model) // Set the language model.
//                .chatMemoryProvider(chatMemoryProvider) // Set the memory provider.
//                .build();
//
//        // Example chat interactions with different memory IDs.
//        System.out.println(assistant.chat(1, "Hello, my name is Mahmoud"));
//        System.out.println(assistant.chat(2, "Hi, my name is Esmat"));
//
//        // Uncomment these lines to test retrieving names from the memory.
//        // System.out.println(assistant.chat(1, "What is my name?"));
//        // System.out.println(assistant.chat(2, "What is my name?"));
//    }
//
//    // Custom implementation of ChatMemoryStore that persists chat memory in a database.
//    static class PersistentChatMemoryStore implements ChatMemoryStore {
//
//        // Initialize a MapDB database for persistent storage.
//        private final DB db = DBMaker.fileDB("multi-user-chat-memory.db").transactionEnable().make();
//        private final Map<Integer, String> map = db.hashMap("messages", INTEGER, STRING).createOrOpen();
//
//        // Retrieve messages for a specific memory ID.
//        @Override
//        public List<ChatMessage> getMessages(Object memoryId) {
//            String json = map.get((int) memoryId); // Get the JSON representation from the map.
//            return messagesFromJson(json); // Deserialize the JSON into a list of ChatMessage objects.
//        }
//
//        // Update messages for a specific memory ID.
//        @Override
//        public void updateMessages(Object memoryId, List<ChatMessage> messages) {
//            String json = messagesToJson(messages); // Serialize the list of messages into JSON.
//            map.put((int) memoryId, json); // Store the JSON in the map.
//            db.commit(); // Commit the transaction to persist changes.
//        }
//
//        // Delete messages for a specific memory ID.
//        @Override
//        public void deleteMessages(Object memoryId) {
//            map.remove((int) memoryId); // Remove the entry from the map.
//            db.commit(); // Commit the transaction to persist changes.
//        }
//    }
//}
