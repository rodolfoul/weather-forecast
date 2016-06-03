## Build do projeto:
Dado que o usuário já tem o [gradle instalado](https://docs.gradle.org/current/userguide/installation.html) na máquina, basta executar o comando `gradle build` da linha de comando no diretório raiz do projeto.

### Configuração do projeto na IDE
Para que seja possível compilar o projeto utilizando uma IDE como o eclipse ou intellij é necessário utilizar o plugin lombok. Lembrando que
a compilação já funciona normalmente se feita diretamente pelo gradle.
- [Instalação no Eclipse, intellij e outras IDEs](https://projectlombok.org/download.html)

## Rodar o projeto:
Para rodar o projeto, navegar até a pasta raiz do projeto e executar o comando `java -jar build\libs\weather-forecast-1.0.jar`

## Descrição da arquitetura do sistema:
### Preparação do modelo e banco de dados
O sistema faz uso do banco de dados embarcado H2 através de um arquivo na pasta temporária do sistema operacional do usuário.
No banco de dados são armazenadas as cidades cadastradas pelo usuário na tabela `city` e a referência de todas as cidades é cadastrada automaticamente
ao levantar a aplicação na tabela `city_reference` onde encontramos o `id` do open weather map, nome da cidade e país.

### Spring boot + Spring MVC
O sistema utiliza spring boot para facilitar o desenvolvimento rápido de uma aplicação simples que está de acordo com o nosso caso. Pelo
spring boot temos também a disposição o sistema de log do slf4j, spring MVC que no nosso caso nos facilitarão o desenvolvimento. 

### Arquitetura "MVW"
No backend foi utilizado o servidor de aplicações Tomcat que responde as chamadas REST do cliente feito pelo frontend em AngularJS.
Isto é, todas as chamadas a api do Open Weather Map são feitas em java utilizando a biblioteca Jersey, que devolve para o controlador do spring MVC,
que por sua vez devolve o modelo através da interface REST para o cliente AngularJS.

Um dos principais modelos para esse caso de estudo é a previsão do tempo em si, que é passada para a camada de view feita em Javascript e então mostrada
para o usuário.

Na parte do backend foi utilizado o padrão de camadas que separa a primeira parte de acesso aos dados DAO das regras de negócios implementadas na camada
de Service, que por sua vez devolve ao Controller os dados processados. O Controller por sua vez devolve o dado pela interface Rest ao cliente.

## Funcionamento do sistema
Para utilizar o sistema basta acessar o browser e entrar com a URL `http://localhost:8080/`. Nesta tela são exibidas todas as cidades cadastradas no
sistema e ainda possibilita o cadastro de mais cidades se desejado.

Ao clicar no nome de aluma das cidades cadastradas, é mostrada a previsão do tempo de 5 dias (Hoje mais 4 dias para frente).