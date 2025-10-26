# Clone de Banco - Teste Técnico Android


## 🎯 Objetivo do Projeto

Este projeto é a implementação de um teste técnico para uma vaga de Desenvolvedor Android.
O objetivo foi criar um aplicativo bancário simples, composto por duas telas (Login e Pagamentos),
seguindo as melhores práticas de desenvolvimento Android moderno, com Jetpack Compose e uma arquitetura MVVM robusta.

## 🎬 Vídeo de Demonstração
[![Assista ao Vídeo](https://caminho/para/sua/imagem.jpg)]([https://youtu.be/wXLWLhfIJzc])
    

## ✨ Funcionalidades Implementadas

### Tela de Login

-   **Validação de E-mail:** O campo de e-mail valida o formato em tempo real.
-   **Validação de Senha:** A senha deve ter no mínimo 6 caracteres, com pelo menos uma letra e um número.
-   **Botão Inteligente:** O botão "ENTRAR" só fica habilitado quando ambos os campos são válidos.
-   **Feedback de Carregamento e Erro:** A tela exibe um indicador de progresso durante o login e mostra mensagens de erro claras (Toast) em caso de falha.
-   **Persistência de Sessão:** Após o login, os dados do usuário são salvos localmente usando **DataStore**, simulando uma sessão.

### Tela de Pagamentos

-   **Dados do Usuário:** Exibe os dados do cliente (nome, agência, conta e saldo) obtidos no login.
-   **Lista de Pagamentos:** Carrega e exibe uma lista de contas pagas.
-   **Estratégia Offline-First:**
    -   Os dados de pagamento são primeiramente buscados via API.
    -   Em caso de sucesso, são salvos no banco de dados local (**Room**).
    -   Se a chamada à API falhar (ex: sem internet), o app exibe os últimos dados salvos no banco, garantindo a funcionalidade offline.
-   **Feedback Visual:** Mostra um estado de carregamento ao buscar os dados e mensagens de erro, se necessário.

## 🛠️ Tecnologias e Arquitetura

Este projeto foi construído com foco em escalabilidade, manutenibilidade e testabilidade.

-   **Linguagem:** **Kotlin**
-   **UI:** **Jetpack Compose** para uma UI declarativa e moderna.
-   **Arquitetura:**
    -   **MVVM (Model-View-ViewModel):** Separação clara entre a lógica de UI e a lógica de negócios.
    -   **Clean Architecture (camadas):** O projeto é dividido em `data`, `domain` e `ui` para desacoplamento e independência de frameworks.
    -   **Repository Pattern:** Abstrai as fontes de dados (rede e local).
-   **Gerenciamento de Estado:** Utiliza `ViewModel` com **StateFlow** e classes `UiState` para gerenciar o estado da UI de forma reativa e segura.
-   **Injeção de Dependência:** **Koin** para gerenciar e fornecer as dependências de forma simples.
-   **Comunicação com a API:**
    -   **Retrofit:** Para chamadas de rede REST.
    -   **Coroutines:** Para gerenciamento de concorrência e operações assíncronas.
-   **Persistência de Dados Local:**
    -   **DataStore:** Para salvar os dados do usuário logado (key-value).
    -   **Room:** Para cache dos dados de pagamentos (banco de dados relacional).
-   **Testes:**
    -   **Testes Unitários:** Para ViewModels e Repositórios.
    -   **JUnit4:** Estrutura base para os testes.
    -   **MockK:** Para criar mocks das dependências.
    -   **Turbine:** Para testar `Flow`s de forma eficiente e declarativa.
-   **Integração Contínua (CI/CD):**
    -   **GitHub Actions:** Workflow automatizado para garantir a qualidade do código a cada push/pull request.

## 🚀 Como Executar

Para executar o projeto localmente, siga os passos abaixo:

1.  **Clone o Repositório**
   
-   **Abra o seu terminal e clone este repositório usando o comando:**
    -   git clone https://github.com/guing2003/CloneDeBanco

2.  **Abra no Android Studio**
   
   ◦  Abra o Android Studio (recomenda-se a versão mais recente).
   ◦  No menu inicial, clique em "Open".
   ◦  Navegue até a pasta do projeto que você acabou de clonar e selecione-a.
  
4.  **Sincronize o Projeto**

   °  Aguarde alguns instantes enquanto o Android Studio indexa os arquivos e o Gradle baixa todas as dependências necessárias.
       -   Você pode acompanhar o progresso na barra de status inferior.
           
6.  **Execute o Aplicativo**
   °  Selecione um emulador disponível ou conecte um dispositivo Android físico ao seu computador (com o modo de desenvolvedor ativado).
   °  Pressione o botão Run 'app' (ícone de play ▶️) na barra de ferramentas superior ou use o atalho Shift + F10.


⚙️ **Integração Contínua (CI)**

Este projeto possui um pipeline de Integração Contínua configurado com GitHub Actions (.github/workflows/android.yml). 

A cada push ou pull request para o branch develop, o workflow executa automaticamente as seguintes tarefas:  
   1.  Lint Check: Analisa o código em busca de problemas estruturais e de qualidade.                                                                                                        
   2.  Unit Tests: Executa todos os testes unitários para garantir que nenhuma regressão foi introduzida.                                                                                                          
   3.  Assemble Build: Compila o aplicativo para garantir que ele está funcional e sem erros de compilação.Isso assegura a estabilidade e a qualidade contínua da base de código.                                                          
  
  
Desenvolvido por Guilherme Nunes Gusson Delecrode

    
