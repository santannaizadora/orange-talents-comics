


## API REST com Spring Boot e Java: Gerenciando Comics de Usuários

### Visão Geral
&emsp;&emsp;Este guia irá explicar a criação de uma API REST que precisará gerenciar comics de usuários. Neste sistema iremos criar  3 endpoints, o cadastro do usuário, o cadastro de comics e a listagem dos comics para um usuário específico. Nosso sistema deve consumir a [ API da MARVEL](https://developer.marvel.com/) para obter os dados das Comics baseado no comicId informado.

&emsp;&emsp;Para a construção do cadastro de usuários, serão obrigatórios os dados: nome, e-mail, CPF e data de nascimento. E para o cadastro de Comics , serão obrigatórios os dados: comicId, título, preço, autores, o ISBN e descrição.

&emsp;&emsp;Além disso, nossas comics terão um dia de desconto baseado no último dígito do ISBN e quando listarmos as comics do usuário, caso seja o dia do desconto, o preço retornado  terá uma redução de 10% em relação ao valor retornado pela API da Marvel. 

&emsp;&emsp;Na criação dessa API REST utilizaremos Java 11 como linguagem e Spring boot, um framework Java criado com o objetivo de facilitar o desenvolvimento de aplicações. A IDE a ser utilizada será o Spring Tool Suite.

&emsp;&emsp;Todo o código desenvolvido nesse post está disponivel em: https://github.com/santannaizadora/orange-talents-comics.

### Criando a aplicação
&emsp;&emsp; Com o [Spring Initializr](https://start.spring.io) iremos criar um projeto spring com as seguintes dependências:  
-   Spring Web: Fornecerá acesso ao servidor web Tomcat para o tratamento do envio das solicitações de requisição e resposta HTTP;
-   Spring Security: Será utilizada para desabilitar as solicitações de autenticação;
-   Spring Boot DevTools: Será utilizada para atualizar automaticamente a aplicação quando os arquivos forem alterados;    
-   Spring Data JPA: Fornecerá suporte de repositório com métodos necessários para a criação de um CRUD;    
-   Validation: Fornecerá as anotações de validação de e-mail e CPF;  
-   MySQL Driver: Driver para a conexão com o banco de dados;
-   OpenFeign: Ela será a responsavel por fornecer os mecanismos para fazermos as requisições na API da Marvel.

&emsp;&emsp;De modo que ao final ficamos com a seguinte configuração:

![](https://i.imgur.com/OiHasqK.png)

&emsp;&emsp;Criando o projeto teremos já de início dois importantes arquivos. O primeiro é o arquivo pom.xml, que contém as dependências que adicionamos:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 		 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.5.2</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.marvelcomicsapi</groupId>
	<artifactId>marvel-comics-api</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>marvel-comics-api</name>
	<description>API for Orange Talents challenge</description>
	<properties>
		<java.version>11</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
		   <groupId>org.springframework.cloud</groupId>
		   <artifactId>spring-cloud-starter-openfeign</artifactId>
		   <version>3.0.3</version>
		</dependency>
		
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```
&emsp;&emsp;O segundo é o arquivo MarvelComicsApiApplication.java, que contém o método main, responsável por carregar todas dependências embutidas na aplicação, nele iremos adicionar a anotação _@EnableFeignClients_ para habilitar o suporte Spring Cloud para a nossa aplicação, de modo que possamos utilizar o _FeignClient_ para nossas requisições na API da Marvel. Sendo assim, teremos o seguinte código:
```java
@EnableFeignClients
@SpringBootApplication
public class MarvelComicsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarvelComicsApiApplication.class, args);
	}

}
```

### Configurando o banco de dados
&emsp;&emsp;Para configurar o banco de dados precisamos alterar o arquivo application.properties, colocando o nome do banco, usuário e senha:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/BANCO_AQUI?\
useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.username=USUARIO_AQUI
spring.datasource.password=SENHA_AQUI
 
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.hibernate.ddl-auto = update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jackson.serialization.fail-on-empty-beans=false
```
&emsp; &emsp; Com isso nosso banco de dados estará funcionando.

### Relacionamento das entidades

&emsp; &emsp;A modelagem escolhida para que o Usuário possa se relacionar com várias Comics e que a Comic com vários usuários também, é a seguinte:

![](https://i.imgur.com/hVTtj6P.png)

&emsp; &emsp;Para não limitar que o Usuário possa adicionar uma mesma Comic mais de uma vez, terá uma tabela que liga uma a outra, que possui um Id auto incremento e um campo Data para saber quando foi realizada uma nova inclusão.

### Security

&emsp; &emsp;O Spring Boot reconhece como componentes da aplicação, todas as classes definidas no mesmo pacote da classe principal ou em um pacote “abaixo” dele. Ou seja, como a nossa classe principal está definida no pacote com.marvelcomicsapi, qualquer classe definida nele ou em um “sub pacote” dele, será reconhecida pelo Spring Boot. 

&emsp; &emsp;Desta forma, para melhor organização de nossos arquivos, vamos definir a nossas configurações de segurança em um novo pacote que nomearemos como com.marvelcomicsapi.config.

&emsp; &emsp;Nesse pacote será criada uma classe chamada _SecurityConfig_, com ela as solicitações de autenticação ficarão desabilitadas, possibilitando assim, que os testes locais sejam realizados mais rapidamente:

  
```java
@Configuration

public class SecurityConfig extends WebSecurityConfigurerAdapter {

@Override

protected void configure(HttpSecurity httpSecurity) throws Exception {

httpSecurity.authorizeRequests().antMatchers("/" , "/**").permitAll().anyRequest()
.authenticated().and().csrf().disable();

}

}
```
  

Sobre as anotações:

-   @Configuration: indica que a classe possui métodos de definição @Bean;
-   @Override: indica que o método está sendo sobrescrito.



### Cadastro de usuários
#### &emsp; &emsp;Criando a entidade 
&emsp; &emsp; Primeiramente criaremos um novo pacote dedicado a nossas entidades. O nomearei como _com.marvelcomicsapi.entity_.

&emsp; &emsp;Criaremos nesse pacote a classe _User_:
```java
@Entity
public class User implements Serializable{
 	
	private static final long serialVersionUID = 1L;
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUser;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = true)
	@Email(message = "email invalido")
	private String email;
	
	@Column(nullable = false, unique = true)
	@CPF(message = "cpf invalido")
	private String cpf;
	
	@Column(nullable = false)
	private Date birthDate;
		
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<UserComic> userComics = new ArrayList<>();

	public User() {
		
	}
 
	public User(User user) {
		this.idUser = user.getIdUser();
		this.name = user.getName();
		this.email = user.getEmail();
		this.cpf = user.getCpf();
		this.birthDate = user.getBirthDate();
		this.userComics = user.getUserComics();
	}
		
		//Todos os Getters e Setters
		//...
}
```

&emsp; &emsp;A propriedade com a anotação _@OneToMany_ será a responsável pela associação entre usuários e comics e a implementação da entidade representada será mostrada mais a frente neste blog.

&emsp; &emsp;Sobre as anotações:

-   _@Id_: especifica que este atributo é a chave primária desta entidade;
-   _@GeneratedValue (strategy = GenerationType.IDENTITY)_: Informa a camada de persistência que a estratégia usada para gerar o valor de nossa chave primária é por aumento automático quando um novo usuário/vacinação é criado;
-   _@Column_: É usado para especificar a coluna mapeada para uma propriedade ou campo persistente;
-   _@Column(nullable = false)_: previne que dados em branco sejam adicionados ao banco de dados;
-   _@Column(unique = true)_: previne que dados repetidos sejam adicionados ao banco de dados;
-   _@Entity_: informa que nossa classe pode ser representada por uma tabela no banco de dados, com cada instância representando uma linha;
-   _@Email_: verifica se a string inserida é um endereço de email válido;
-   _@CPF_: verifica se a string inserida é um CPF válido;
 - _@OneToMany_: Indica que há uma relação "um para muitos" entre as entidades.

#### &emsp; &emsp;Criando o repositório
&emsp; &emsp;O proximo passo é criar o repositório para o usuário. Para isso criaremos um novo pacote que será nomeado como _com.marvelcomicsapi.repository_, nele criaremos uma interface com o nome _UserRepository_:

```java
@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {
	Optional<User> findByCpf(String cpf);
	Optional<User> findByEmail(String email);
}
```
&emsp;&emsp;Note que essa interface estende a interface do Spring Data chamada JpaRepository, por estender a interface, o repositório já ganhou a implementação de vários métodos necessários para a criação de um CRUD.

&emsp;&emsp;A anotação @Repository é do Spring e seu propósito é simplesmente indicar que a classe fornece o mecanismo para armazenamento, recuperação, pesquisa, atualização e operação de exclusão em objetos.
 
#### &emsp; &emsp;Criando o service
&emsp; &emsp;Seguindo o padrão, criaremos um novo pacote para os services com o nome com.marvelcomicsapi.service. Nesse pacote criaremos a classe _UserService_:
```java
@Service
public class UserService {
 
	@Autowired
	private UserRepository userRepository;
	
	public User save(User dto) throws DuplicatedEntryException {
		Optional<User> userCpf = this.userRepository.findByCpf(dto.getCpf());	
		if(!userCpf.isEmpty())
			throw new DuplicatedEntryException("Cpf " + dto.getCpf() + " já cadastrado");
			
		Optional<User> userEmail = this.userRepository.findByEmail(dto.getEmail());	
		if(!userEmail.isEmpty())
			throw new DuplicatedEntryException("Email " + dto.getEmail() + " já cadastrado");


		dto.setIdUser(null);
		User userToSave = this.fromDto(dto);

		return this.userRepository.save(userToSave);	
	}
		
	public User fromDto(User user) {
		return new User(user);
	}

}
```
&emsp; &emsp;Note que a IDE irá apontar um erro em _DuplicatedEntryException_, pois ainda não implementamos esta classe. A implementação será mostrada mais à frente neste post. 

&emsp; &emsp;Sobre as anotações:
-   @Service:  é usada para marcar a classe como um provedor de serviços;
- @Autowired:  fornece um controle mais refinado sobre onde e como a autowiring deve ser realizada.

#### &emsp; &emsp;Criando o controller
&emsp; &emsp;Por fim iremos criar um novo pacote dedicado ao controller, com o nome _com.marvelcomicsapi.controller_. Nele será criada a classe _UserController_:
```java
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<User> save(@RequestBody User dto) throws DuplicatedEntryException {
		
		return new ResponseEntity<User>(userService.save(dto), HttpStatus.CREATED);
		
	}
		
}
```
&emsp; &emsp;Sobre as anotações:

-   @RestController: é aplicada a uma classe para marcá-la como um manipulador de solicitação;
-   @RequestMapping: mapeia solicitações de HTTP para métodos de tratamento de controladores MVC e REST;  
-   @PostMapping: é uma versão especializada da anotação @RequestMapping que atua como um atalho para @RequestMapping(method = RequestMethod.POST).

#### Testando os Endpoints

 &emsp; &emsp;Para salvar um usuário iremos fazer uma requisição do tipo POST em http://localhost:8080/users, passando os valores de nome, email, cpf e data de nascimento:
 
![](https://i.imgur.com/b8hYT73.png)

&emsp; &emsp;Quando a requisição for feita a aplicação irá retornar os dados do usuário

&emsp; &emsp;Caso seja inserido um e-mail ou cpf que já esteja cadastrado no banco de dados, nossa aplicação irá retornar um erro informando que o dado em questão já foi cadastrado:

![](https://i.imgur.com/4rr2sEt.png)

![](https://i.imgur.com/fKjKmBM.png)

### Cadastro de Comic para um usuário
#### &emsp; &emsp;Criando a entidade Comic 
&emsp; &emsp;No pacote _com.marvelcomicsapi.entity_ iremos criar mais uma classe. A nomearemos como  _Comic_:
```java
@Entity
public class Comic implements  Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer idComic;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private Float price;
	
	@Column(nullable = true)
	private String author;
	
	@Column(nullable = true)
	private String isbn;
	
	@Column(nullable = true, length = 1500)
	private String description;
	
	@OneToMany(mappedBy = "comic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UserComic> userComics = new ArrayList<>();

	public Comic() {
		
	}

	public Comic(Comic comic) {
		this.idComic = comic.getIdComic();
		this.title = comic.getTitle();
		this.price = comic.getPrice();
		this.author = comic.getAuthor();
		this.isbn = comic.getIsbn();
		this.description = comic.getDescription();
		this.userComics = comic.getUserComics();
	}
	
	//Todos os getters e setters
	//...
}

```
&emsp;&emsp;E como foi dito anteriormente na entidade de _User_, a entidade _UserComic_ será apresentada mais à frente neste post.

&emsp;&emsp;Note que para a coluna _description_ estamos passando um "length = 1500", isso se dá ao fato de o valor default de 250 caracteres não ser suficiente para armazenar algumas descrições.
 
#### &emsp; &emsp;Criando o repositório para Comic
&emsp; &emsp; No pacote _com.marvelcomicsapi.repository_ criaremos mais uma interface. A chamaremos de  _ComicRepository_:

```java
@Repository
public interface ComicRepository  extends JpaRepository<Comic, Integer> {

}
```

#### &emsp; &emsp;Consumindo API da Marvel

&emsp; &emsp;Para consumirmos a API da Marvel iremos criar as classes que armazenarão os resultados que serão retornados.

&emsp; &emsp;Para isso iremos criar um novo pacote chamado _com.marvelcomicsapi.objects_ e nele criaremos as seguintes classes:
- _CharacterList_:
```java 
public class CharacterList extends ObjectList<CharacterSummary> {
}
```
- _CharacterSummary_:
```java
public class CharacterSummary extends Summary {
    private String role;

    //Todos os getters e setters
    //...
}
``` 
- _ComicDate_:
```java
public class ComicDate {
    private String type;
    private String date;

       //Todos os getters e setters
	   //...
}

```
&emsp; &emsp;Note que a data foi colocada com sendo do tipo String, essa escolha foi feita pelo fato da API da Marvel em algumas comics retornar um formato de data inválido.

- _ComicPrice_:
```java
public class ComicPrice {
    private String type;
    private Float price;

        //Todos os getters e setters
	    //...
}
```

- _ComicSummary_:
```java
public class ComicSummary extends Summary {

}
```
- _CreatorList_:
```java
public class CreatorList extends ObjectList<CreatorSummary> {
}
```

- _CreatorSummary_:
```java
public class CreatorSummary extends Summary {
    private String role;

        //Todos os getters e setters
	    //...
}
```
- _EventList_:
```java
public class EventList extends ObjectList<EventSummary> {

}
```
- _EventSummary_:
```java
public class EventSummary extends Summary {
}
```
- _Image_:
```java
public class Image {
    private String path;
    private String extension;

        //Todos os getters e setters
	    //...
}
```
- _SeriesSummary_:
```java
public class SeriesSummary extends Summary {

}
```
- _StoryList_:
```java
public class StoryList extends ObjectList<StorySummary> {

}
```
- _StorySummary_:
```java
public class StorySummary extends Summary {
    private String type;

        //Todos os getters e setters
	    //...
}
```
- _TextObject_:
```java
public class TextObject {
    private String type;
    private String language;
    private String text;

        //Todos os getters e setters
	    //...
}
```
- _Url_:
```java
public class Url {
    private String type;
    private String url;

       //Todos os getters e setters
	    //...
}
```

&emsp; &emsp;Por fim criaremos a classe que irá reunir as informações obtidas pelas classes mostradas acima.

- _ComicApi_:
```java
public class ComicApi {
    private int id;
    private int digitalId;
    private String title;
    private Double issueNumber;
    private String variantDescription;
    private String description;
    private String modified;
    private String isbn;
    private String upc;
    private String diamondCode;
    private String ean;
    private String issn;
    private String format;
    private int pageCount;
    private List<TextObject> textObjects;
    private String resourceURI;
    private List<Url> urls;
    private SeriesSummary series;
    private List<ComicSummary> variants;
    private List<ComicSummary> collections;
    private List<ComicSummary> collectedIssues;
    private List<ComicDate> dates;
    private List<ComicPrice> prices;
    private Image thumbnail;
    private List<Image> images;
    private CreatorList creators;
    private CharacterList characters;
    private StoryList stories;
    private EventList events;

	//Todos os getters e setters
	//...
}
```

&emsp; &emsp; Note que a IDE irá apontar erro nas classes que estendem _Summary_ e _ObjectList<>_ , pois ainda não implementamos estas classes. Essa implementação será o nosso próximo passo.

&emsp; &emsp;Para isso criaremos um novo pacote dentro de objects chamado _com.marvelcomicsapi.objects.ref_, nele criaremos essas classes:

- _Summary_:
```java
public class Summary {
    protected String resourceURI;
    protected String name;

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

- _ObjectList_:
```java
public class ObjectList<T extends Summary> {
    protected int available;
    protected int returned;
    protected String collectionURI;
    protected List<T> items;

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getReturned() {
        return returned;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
```
&emsp; &emsp;Neste mesmo pacote criaremos também as seguintes classes: _DataWrapper_ que irá mapear o objeto de retorno da API com informações a respeito da requisição e o _DataContainer_ que está dentro do DataWrapper que contêm informações para paginação e o array de comics.

- _DataContainer_:
```java
public class DataContainer<T> {
    private int offset;
    private int limit;
    private int total;
    private int count;
    private List<T> results;

    //Todos os getters e setters
    //...
}
```
-  _DataWrapper_:
```java
public class DataWrapper<T> {

    private int code;
    private String status;
    private String copyright;
    private String attributionText;
    private String attributionHTML;
    private DataContainer<T> data;
    private String etag;

    //Todos os getters e setters
    //...
}
```
&emsp; &emsp;Agora que temos onde armazenar os dados obtidos podemos fazer as requisições na API da Marvel. Para isso criaremos um novo pacote chamado _com.marvelcomicsapi.client_ e nele criaremos a interface _ComicClient_:
```java
@FeignClient(url = "https://gateway.marvel.com:443/v1/public/comics/", name = "comic")
public interface ComicClient {

	@GetMapping("{idComic}?apikey=PUBLIC_KEY=thesoer&hash=HASH(ts+PUBLIC_KEY+PRIVATE_KEY)")
	DataWrapper<ComicApi> getComicById(@PathVariable("idComic") Integer idComic);
}
```
&emsp; &emsp;Essa interface será a responsável por fazer a requisição na API da Marvel e armazenar os dados obtidos dessa chamada em nosso _DataWrapper< ComicApi >_.

&emsp; &emsp;Com isso temos tudo preparado para consumir a API e salvar os dados necessários em nossa entidade _Comic_. 

&emsp; &emsp;Sobre as anotações:

- @FeignClient: É uma biblioteca para a criação de clientes REST API de forma declarativa;
- @GetMapping: É uma versão especializada da anotação @RequestMapping que atua como um atalho para @RequestMapping(method = RequestMethod.GET).

#### &emsp; &emsp;Criando o service para Comic

&emsp; &emsp; No pacote _com.marvelcomicsapi.service_ criaremos outra classe com o nome _ComicService_:

```java
@Service
public class ComicService {

	@Autowired
	private ComicRepository comicRepository;
	
	private final ComicClient comicClient;
	
	public ComicService (ComicClient comicClient) {
		this.comicClient = comicClient;
	}
	
	public Optional<Comic> save(Integer idComic) {
		Comic comicToSave = new Comic();

		ComicApi comicApiMarvel = comicClient.getComicById(idComic).getData()
        .getResults().get(0);

		comicToSave.setIdComic(comicApiMarvel.getId());
		
        comicToSave.setTitle(comicApiMarvel.getTitle());
		
        comicToSave.setDescription(comicApiMarvel.getDescription()
        .replaceAll("\r<br>", "").replaceAll("\r\n", ""));
		
        comicToSave.setIsbn(comicApiMarvel.getIsbn());
		comicToSave.setPrice(comicApiMarvel.getPrices().get(0).getPrice());

		for(CreatorSummary author : comicApiMarvel.getCreators().getItems()) {
			if (comicToSave.getAuthor() != null && author.getName() != null) {
				comicToSave.setAuthor(author.getName() + ", " + comicToSave.getAuthor());
			} else {
				comicToSave.setAuthor(author.getName());
			}
		}

		comicRepository.save(comicToSave);
		
		return this.comicRepository.findById(idComic);
	}
	
	public Comic fromDto(Comic comic) {
		return new Comic(comic);
	}
}
```
&emsp; &emsp;Aqui estamos pegando os dados fornecidos pela API da Marvel e salvando na nossa entidade _Comic_.

#### &emsp; &emsp;Criando a entidade UserComic
&emsp; &emsp;Para que seja possível relacionar nossos usuários com suas comics iremos criar uma nova entidade no pacote  _com.marvelcomicsapi.entity_ com o nome _UserComic_:

```java 
@Entity
@Table(name = "user_comic")
public class UserComic implements Serializable{
	 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUserComic;
	
	@Column(nullable = false)
	private Date date;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="idUser", insertable = true, updatable = false)
	@JsonIgnoreProperties(value = {"userComics", "hibernateLazyInitializer"})
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="idComic", insertable = true, updatable = false)
	@JsonIgnoreProperties(value = {"userComics", "hibernateLazyInitializer"})
	private Comic comic;
	
	public UserComic() {
		
	}	

	public UserComic(UserComic userComic) {
		this.idUserComic = userComic.getIdUserComic();
		this.date = userComic.getDate();
		this.comic = userComic.getComic();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	//Todos os getters e setters
	//...
}

```
 &emsp; &emsp;Sobre as anotações:
 -  @ManyToOne: Indica que há uma relação "muitos para um" entre as entidades;
 -  @JoinColumn: Marca uma coluna como uma coluna de junção para uma associação de entidades;
 -  @JsonIgnoreProperties: Pode ser usada para suprimir a serialização de propriedades (durante a serialização) ou ignorar o processamento de propriedades JSON lidas (durante a desserialização).

#### &emsp; &emsp;Criando o repositório para UserComic
 &emsp; &emsp;No pacote  _com.marvelcomicsapi.repository_ criaremos a interface _UserComicRepository_:
```java
@Repository
public interface UserComicRepository extends JpaRepository<UserComic, Integer> {

}
```
#### &emsp; &emsp;Criando DTO para request de UserComic 
&emsp; &emsp;Agora criaremos uma classe para mapear o corpo da requisição feita para _UserComic_. Para isso criaremos um novo pacote em  _com.marvelcomicsapi.objects_ com o nome _com.marvelcomicsapi.objects.request_  e nele implementamos a classe _UserComicRequest_ :
```java
public class UserComicRequest {

	private Integer idUserComic;
	private Date date;
	private Integer idUser;
	private Integer idComic;
		
	public UserComicRequest() {
			
	}	

	public UserComicRequest(UserComicRequest userComic) {
		this.idUserComic = userComic.getIdUserComic();
		this.date = userComic.getDate();
		this.idUser = userComic.getIdUser();
		this.idComic = userComic.getIdComic();
	}

	//Todos os getters e setters
	//...
	
}

```
#### &emsp; &emsp;Criando o service para UserComic
&emsp; &emsp;No pacote _com.marvelcomicsapi.service_ criaremos a classe _UserComicService_:
```java
@Service
public class UserComicService {
	
	@Autowired
	private UserComicRepository userComicRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ComicRepository comicRepository;

	@Autowired
	private ComicService comicService;
	
	public UserComic save(UserComicRequest dto) {
		Optional<User> user = this.userRepository.findById(dto.getIdUser());
		
		if(user.isEmpty())
			throw new NotFoundException("User with id " + dto.getIdUser() + " not found");	

		Optional<Comic> comic = this.comicRepository.findById(dto.getIdComic());
		
		if (comic.isEmpty()) {
			comic = this.comicService.save(dto.getIdComic());
		}
		
		UserComic userComic = new UserComic();
		Date date = new Date();
		
		userComic.setIdUserComic(null);
		userComic.setDate(date);
		userComic.setUser(user.get());
		userComic.setComic(comic.get());
		
		return this.userComicRepository.save(userComic);
		
	}
	
	public UserComic fromDto(UserComic userComic) {
		return new UserComic(userComic);
	}

}

```
&emsp; &emsp;Note que a IDE irá apontar um erro em _NotFoundException_, pois ainda não implementamos esta classe. A implementação será mostrada mais à frent neste post.
#### &emsp; &emsp;Criando o controller para UserComic
 &emsp; &emsp;No pacote _com.marvelcomicsapi.controller_ criaremos a classe _UserComicontroller_:
```java
@RestController
@RequestMapping("/usercomics")
public class UserComicController {
	
	@Autowired
	private UserComicService userComicService;
	
	@PostMapping
	public ResponseEntity<UserComic> save(@RequestBody UserComicRequest dto) {
		
		return new ResponseEntity<UserComic>(userComicService.save(dto), HttpStatus.CREATED);
		
	}
		
}
```
#### Testando o Endpoint
&emsp; &emsp;Para cadastrar comics para um usuario iremos fazer uma requisição do tipo POST em http://localhost:8080/usercomics, passando o id do usuário e o id da comic que desejamos cadastrar:

![](https://i.imgur.com/ehCse9L.png)

&emsp; &emsp;Quando a requisição for feita a aplicação irá retornar os dados do usuário e os dados da comic cadastrada:

![](https://i.imgur.com/FeFLsuT.png)


### Listar usuário e suas respectivas comics
#### &emsp; &emsp;Criando o resposnse
&emsp; &emsp;Agora criaremos as classes que irão mapear o corpo da resposta feita para o _findOne_ de _User_. Para isso criaremos um novo pacote em  _com.marvelcomicsapi.objects_ com o nome _com.marvelcomicsapi.objects.response_  e nele implementamos as seguintes classes:
- UserResponse:
```java
public class UserResponse {

	private Integer idUser;
	private String name;
	private String email;
	private String cpf;
	private Date birthDate;
	private List<UserComicResponse> userComics = new ArrayList<>();

	public UserResponse() {
		
	}

	public UserResponse(Integer idUser, String name, String email, String cpf,
    Date birthDate) {
		this.idUser = idUser;
		this.name = name;
		this.email = email;
		this.cpf = cpf;
		this.birthDate = birthDate;
	}

	public UserResponse(UserResponse user) {
		this.idUser = user.getIdUser();
		this.name = user.getName();
		this.email = user.getEmail();
		this.cpf = user.getCpf();
		this.birthDate = user.getBirthDate();
		this.userComics = user.getUserComics();
	}
	
	public void addUserComic(UserComicResponse userComic) {
		this.userComics.add(userComic);
	}

	//Todos os getters e setters
	//...
	
}
```

- ComicResponse:
```java
public class ComicResponse {

	private Integer idComic;
	private String title;
	private Float price;
	private String author;
	private String isbn;
	private String description;
	private String discountDay;
	private Boolean discountActive;

	public ComicResponse() {
		
	}

	public ComicResponse(Integer idComic, String title, Float price, String author, 
    String isbn, String description, String discountDay, Boolean discountActive) {
		this.idComic = idComic;
		this.title = title;
		this.price = price;
		this.author = author;
		this.isbn = isbn;
		this.description = description;
		this.discountDay = discountDay;
		this.discountActive = discountActive;
	}

	public ComicResponse(ComicResponse comic) {
		this.idComic = comic.getIdComic();
		this.title = comic.getTitle();
		this.price = comic.getPrice();
		this.author = comic.getAuthor();
		this.isbn = comic.getIsbn();
		this.description = comic.getDescription();
		this.discountDay = comic.getDiscountDay();
		this.discountActive = comic.getDiscountActive();
	}
	
	//Todos os getters e setters
	//...
}

```

- UserComicResponse:
```java
public class UserComicResponse {

	private Integer idUserComic;
	private Date date;
	@JsonIgnore
	private UserResponse user;
	private ComicResponse comic;
	
	public UserComicResponse() {
			
	}	
	
	public UserComicResponse(Integer idUserComic, Date date) {
		this.idUserComic = idUserComic;
		this.date = date;
	}	

	public UserComicResponse(UserComicResponse userComic) {
		this.idUserComic = userComic.getIdUserComic();
		this.date = userComic.getDate();
		this.user = userComic.getUser();
		this.comic = userComic.getComic();
	}

	//Todos os getters e setters
	//...

}
```
#### &emsp; &emsp;Adicionando o método _FindOne_  em _UserService_
&emsp; &emsp;Com nossas classes de response feitas o próximo passo é criar um novo método em _UserService_ que irá retornar o usuário e suas comics cadastradas.

&emsp; &emsp;Além disso neste método iremos implementar as condições que irão aplicar o desconto para as comics:

```java
public UserResponse findOne(Integer idUser) {
		Optional<User> user = this.userRepository.findById(idUser);
		if(user.isEmpty())
			throw new NotFoundException("Usuário com id " + idUser + " não foi encontrado");
			
		User userToResponse = user.get();

		UserResponse userResponse = new UserResponse(userToResponse.getIdUser(),
        		userToResponse.getName(), 
				userToResponse.getEmail(), userToResponse.getCpf(),
                userToResponse.getBirthDate());

		String[] daysOfWeek = {"Domingo", "Segunda-Feira", "Terça-feira", "Quarta-Feira",
        "Quinta-feira", "Sexta-Feira", "Sábado"};
		Calendar calendar = Calendar.getInstance();
		int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
			
		for (UserComic userComic : userToResponse.getUserComics()) {
				
			UserComicResponse userComicResponse = new UserComicResponse(userComic
            .getIdUserComic(), 
            userComic.getDate());
				
			userComicResponse.setComic(new ComicResponse(userComic
            .getComic().getIdComic(), userComic.getComic().getTitle(), 
            userComic.getComic().getPrice(), userComic.getComic().getAuthor(),
            userComic.getComic().getIsbn(), 
			userComic.getComic().getDescription(), null, null));

			if (!userComic.getComic().getIsbn().isEmpty()) {

				String isbn = userComic.getComic().getIsbn();
				String lastCharIsbn = isbn.substring(isbn.length() - 1); 

				switch (lastCharIsbn) {
					case "0": case "1":
						userComicResponse.getComic().setDiscountDay(daysOfWeek[1]);
						break;
					case "2": case "3":
						userComicResponse.getComic().setDiscountDay(daysOfWeek[2]);
						break;
					case "4": case "5":
						userComicResponse.getComic().setDiscountDay(daysOfWeek[3]);
						break;
					case "6": case "7":
						userComicResponse.getComic().setDiscountDay(daysOfWeek[4]);
						break;
					case "8": case "9":
						userComicResponse.getComic().setDiscountDay(daysOfWeek[5]);
						break;
				}

				if (daysOfWeek[dayWeek - 1] == userComicResponse.getComic()
                .getDiscountDay()) {
						userComicResponse.getComic().setDiscountActive(true);
						Float newPrice = (float) (userComic.getComic().getPrice() * 0.9);
						userComicResponse.getComic().setPrice(newPrice);
				} else {
					userComicResponse.getComic().setDiscountActive(false);
				}

			}
				
			userResponse.addUserComic(userComicResponse);
				
		}

		return userResponse;
	}
```
#### &emsp; &emsp;Adicionando o método _FindOne_  em _UserController_
&emsp; &emsp;Por fim, em nosso controller de usuário iremos adicionar o seguinte método:
```java

@GetMapping("/{idUser}")
	public ResponseEntity<UserResponse> findOne(@PathVariable(value = "idUser")
    Integer idUser) {
		UserResponse user = this.userService.findOne(idUser);
		
		return ResponseEntity.ok().body(user);
	}
```
#### Testando o Endpoint
&emsp; &emsp;Podemos encontrar um usuário fazendo uma requisição do tipo GET em  http://localhost:8080/users/ID passando no lugar de "ID" o id do usuário desejado, caso tenha alguma comic cadastrada para aquele usuário ele irá retornar dentro de um array de UserComic:

![](https://i.imgur.com/1iBv5fo.png)

&emsp; &emsp;Caso seja dia do desconto o preço que a comic retorna terá 10% de desconto em relação ao valor retornado pela API da Marvel. A comic mostrada a seguir tem o preço original de $9.99:

![](https://i.imgur.com/PtUHiqW.png)

&emsp; &emsp;Caso não seja o dia do desconto, a comic retornará seu preço original:

![](https://i.imgur.com/5QnOoT9.png)

&emsp; &emsp;Caso o id passado não pertença a nenhum usuário cadastrado, nossa aplicação irá retornar um erro informando que não foi possível encontar um usuário com o id informado:

![](https://i.imgur.com/DvliW9p.png)

### Erros
&emsp; &emsp;Para o tratamento de erros, criaremos um novo pacote dentro de service chamado _com.marvelcomicsapi.service.exception_.

&emsp; &emsp;Primeiro será criada a classe _ExceptionResponse_, ela será responsável por personalizar as respostas de erro:
```java
public class ExceptionResponse {
	private Date timestamp;
    private String message;
    private String details;

    public ExceptionResponse() {
        super();
    }

    public ExceptionResponse(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
    
    
}
```
&emsp; &emsp;Agora, escrevemos o código para configurar o Spring para lançar esse objeto JSON. Para isso criaremos a classe _CustomException_:
```java
@ControllerAdvice
public class CustomException extends ResponseEntityExceptionHandler {
	
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex, 
    WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), 
        ex.getMessage(),request.getDescription(true));

        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(DuplicatedEntryException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final ResponseEntity<Object> handleDuplicatedEntryException(NotFoundException ex, 
    WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), 
        ex.getMessage(), request.getDescription(true));

        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(Exception ex, 
    WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), 
        "Ocorreu um erro interno, por favor tente novamente.", 
        		request.getDescription(true));

        return new ResponseEntity<Object>(exceptionResponse, 
        HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
```
&emsp; &emsp;Por fim criaremos as classes _NotFoundException_ e _DuplicatedEntryException_:
```java
public class NotFoundException extends RuntimeException{

		private static final long serialVersionUID = 1L;
		
		public NotFoundException(String message) {
			super(message);
		}
}
```
```java
public class DuplicatedEntryException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

    public DuplicatedEntryException(String message){
        super(message);
    }

    public DuplicatedEntryException(String message, Throwable anException){
        super(message, anException);
    }
}
```
&emsp; &emsp;Estas são as classes para indicar qual é o objeto JSON que deve retornar se uma exceção do tipo NotFoundException ou DuplicatedEntryException for lançada.

&emsp; &emsp;Sobre as anotações:

-   @ControllerAdvice:é uma especialização da anotação @Component que permite lidar com exceções em todo o aplicativo em um componente de tratamento global;
-   @ExceptionHandler: é uma anotação Spring que fornece um mecanismo para tratar exceções que são lançadas durante a execução de manipuladores (operações do controlador).

### Considerações finais

&emsp; &emsp;Ao longo deste guia, você se envolveu em várias etapas para construir uma API REST para cadastro de usuários e suas comics. Você encontrará o código fonte no repositório disponível em https://github.com/santannaizadora/orange-talents-comics.

#### Meus Dados

E-mail: santannaizadora@gmail.com

Linkedin: https://www.linkedin.com/in/santannaizadora/
