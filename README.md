# Bakehouse
**🥖 Sobre o Projeto**

Este é um projeto acadêmico para o desenvolvimento de um sistema de gestão para uma padaria, com foco em uma arquitetura MVC bem definida, princípios de orientação a objetos e persistência de dados em um banco de dados relacional.

O principal objetivo do sistema é gerenciar produtos, vendas, clientes e um programa de fidelidade, no qual os clientes acumulam pontos a cada compra para trocar por produtos.

#

**✨ Funcionalidades**

O sistema conta com as seguintes funcionalidades mínimas:

- **Gestão de Produtos:**

  - CRUD (Criar, Ler, Atualizar, Deletar) completo para produtos.

  - Campos: Nome, preço, tipo (pão, doce, etc.) e quantidade em estoque.

- **Gestão de Clientes:**

  - CRUD completo para clientes.

  - Campos: Nome, CPF, telefone e total de pontos acumulados.

- **Módulo de Vendas:**

  - Registrar vendas associando múltiplos produtos a um cliente.

  - Atualização automática do estoque após a venda.

  - Cálculo e acúmulo de pontos de fidelidade para o cliente (ex: 1 ponto a cada R$10,00 gastos).

- **Programa de Fidelidade:**

  - Permite ao cliente trocar pontos por produtos designados como "resgatáveis".

  - Valida se o cliente possui saldo de pontos suficiente para a troca.

  - Atualiza o estoque do produto resgatado e o saldo de pontos do cliente.
 
  #

**🛠️ Tecnologias Utilizadas**

- **Linguagem:** Java 


- **Banco de Dados:** PostgreSQL 


- **Interface Gráfica:** Swing 

#

**🏗️ Arquitetura**

O projeto foi estruturado seguindo o padrão de arquitetura 

**MVC (Model-View-Controller) para garantir uma clara separação de responsabilidades:**


- Model: Camada que contém as classes de domínio (entidades) , a lógica de negócio e as classes DAO (Data Access Object) para a comunicação e persistência com o banco de dados PostgreSQL.



- View: Responsável pela apresentação da interface gráfica ao usuário. Todas as telas e componentes visuais foram desenvolvidos utilizando a biblioteca Swing.



- Controller: Camada que atua como intermediária, recebendo as requisições do usuário através da View e acionando as devidas regras de negócio no Model.

#

**Database**

<img width="1148" height="864" alt="bakery database" src="https://github.com/user-attachments/assets/87b5777b-aeeb-47cb-9a7b-44a01b0e9cb6" />


**👥 Autores**
  
Este trabalho foi desenvolvido por:

- Igor de Pin Faggiani
- Lucas Marschner Franke
