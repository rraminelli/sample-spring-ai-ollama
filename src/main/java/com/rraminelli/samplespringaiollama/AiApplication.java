package com.rraminelli.samplespringaiollama;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.jdbc.core.simple.JdbcClient;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@SpringBootApplication
public class AiApplication {

	@Bean
	ChatClient chatClient(ChatClient.Builder builder) {
		return builder.build();
	}

	//	@Bean
	ApplicationRunner basics(ChatClient chatClient) {
		return args -> {

			record Joke(String prompt, String punchline) {
			}

			var joke = chatClient
					.prompt()
					.user("tell me a joke")
					.call()
					.entity(Joke.class);
			System.out.println("joke: " + joke);
		};
	}

	@Bean
	JdbcClient jdbcClient(DataSource dataSource) {
		return JdbcClient.create(dataSource);
	}

	@Bean
	ApplicationRunner rag(ChatClient chatClient,
						  JdbcClient jdbcClient,
						  VectorStore vectorStore,
						  DogRepository dogRepository,
						  //DocumentRepository documentRepository,
						  //VectorStoreRepository vectorStoreRepository,
						  EmbeddingModel embeddingModel) {
		return args -> {

			dogRepository.save(new Dog(1, "jolie", "cachorro gripado"));

			jdbcClient.sql("delete from vector_store").update();
			//
			dogRepository.findAll().forEach(dog -> {
				var document = new Document("id: %s, name: %s, description: %s".formatted(
						dog.getId(), dog.getName(), dog.getDescription()
				));
				vectorStore.add(List.of(document));

//				var document = new DocumentEntity();
//				document.setDocumentName(dog.getName());
//				document.setMetadata(Map.of("description", "PUBLIC"));
//				var savedDocument = documentRepository.save(document);
//
//				var vectorStoreEntity = new VectorStoreEntity();
//				vectorStoreEntity.setId(savedDocument.getId());
//				vectorStoreEntity.setContent(dog.getDescription());
//				vectorStoreEntity.setMetadata(document.getMetadata());
//				vectorStoreEntity.setEmbedding(embeddingModel.embed(dog.getDescription()));
//				vectorStoreEntity.setDocument(document);
//				vectorStoreRepository.save(vectorStoreEntity);

			});

			var content = chatClient
					.prompt()
					.system("""
							voce é um veterinario, dentro do seu historico voce possui algum cao com um problema descrito {description} na pergunta?
							se voce tiver, responda com o {NAME} do animal

							""")
					.user("voce tem ja atendeu algum cachorro gripado?")
					.advisors(new QuestionAnswerAdvisor(vectorStore))
					.call()
					.content();
			System.out.println(content);

		};
	}


	//	@Bean
	ApplicationRunner memory(ChatClient chatClient) {
		return args -> {
			var memory = new InMemoryChatMemory();
			// if you're using OpenAI you should be using the MessageChatMemoryAdvisor
			var promptChatMemoryAdvisor = new PromptChatMemoryAdvisor(memory);
			System.out.println( chatClient
					.prompt()
					.user("hi, my name is Rod. How are you?")
					.advisors(promptChatMemoryAdvisor)
					.call()
					.content()
			);
			System.out.println( chatClient
					.prompt()
					.user("what's my name?")
					.advisors(promptChatMemoryAdvisor)
					.call()
					.content()
			);
		};
	}

//	@Bean
	ApplicationRunner functions(ChatClient chatClient) {
		return args -> {
			var reply = chatClient
					.prompt()
					.user("you are an assistant helping people answer " +
							"questions about the status of their orders.")
					.functions("checkAccountStatus")
					.user("what is the status of my order (order number #23232)?")
					.call()
					.content();
			System.out.println("reply: " + reply);
		};
	}

	//@Bean
	ApplicationRunner observability(ChatClient chatClient) {
		return args -> {
			this.memory(chatClient);
			// then login to zipkin and actuator metrics
		};

	}

	//@Bean
	//@Description("checkAccountStatus")
	Function<CheckAccountStatusRequest, CheckAccountStatusResponse> checkAccountStatus() {
		return request -> {
			var status = new CheckAccountStatusResponse(request.orderNumber(), Math.random() > .5);
			System.out.println("status: [" + status + "]");
			return status;
		};
	}

	record CheckAccountStatusRequest(String orderNumber) {
	}

	record CheckAccountStatusResponse(String orderNumber,
									  boolean completed) {
	}


	public static void main(String[] args) {
		SpringApplication.run(AiApplication.class, args);
	}

}

@Entity
class Dog {
	public Dog() {}
	public Dog(Integer id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	@Id Integer id; String name; String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

interface DogRepository extends JpaRepository<Dog, Integer> {
}

//interface DocumentRepository extends JpaRepository<DocumentEntity, UUID> {
//}
//interface VectorStoreRepository extends JpaRepository<VectorStoreEntity, UUID> {
//}
