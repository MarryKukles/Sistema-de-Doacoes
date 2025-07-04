
# 🤝 Sistema de Gerenciamento de Doações para o RS

Projeto acadêmico da disciplina **Programação de Soluções Computacionais** — feito em Java com o objetivo de auxiliar na organização de doações para as vítimas das enchentes no Rio Grande do Sul.  
O sistema permite o registro, armazenamento e análise das doações realizadas, promovendo mais controle e transparência no processo de ajuda humanitária.

---

## ✨ Funcionalidades

- 📝 **Cadastro de Doações**  
  Permite registrar o tipo da doação (dinheiro, alimento, roupa, etc.), quantidade/valor e a data da contribuição.

- 📊 **Cálculo de Total de Doações**  
  Apresenta o total arrecadado com base em todas as doações cadastradas.

- 📂 **Persistência de Dados**  
  As doações são armazenadas em um arquivo `doacoes.txt` para garantir acesso futuro.

- 📄 **Relatório de Doações**  
  Exibe todas as doações cadastradas com informações detalhadas.

- 🛡️ **Tratamento de Erros**  
  Entrada de dados e manipulação de arquivos são protegidas com tratamento de exceções.

---

## 🛠️ Tecnologias / Conceitos

- **Java SE** (JDK 17+)
- Swing
- Programação orientada a objetos (POO)
- Estruturas de Dados (`ArrayList`)
- Condicionais e laços de repetição
- Manipulação de arquivos (`BufferedReader`, `BufferedWriter`)
- Tratamento de exceções
- API de Data e Hora (`java.time`)

---

## 🚀 Como executar

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
javac Doacao.java GerenciadorDoacoes.java SistemaPrincipal.java
java SistemaPrincipal
```

---

## 🗂️ Estrutura dos Arquivos

```
📁 DoacoesRS
 ├── Doacao.java                  # Classe que representa a doação
 ├── GerenciadorDoacoes.java       # Classe que gerencia as doações
 ├── SistemaPrincipal.java         # Classe principal com o menu de console (versão CLI do sistema)
 ├── SistemaDoacoesGUI.java        # Classe principal com a Interface Gráfica do Usuário (GUI) 
 ├── doacoes.txt                   # Arquivo gerado automaticamente para armazenar os registros das doações
 └── README.md                     # Documentação do projeto
```

---

## 👩🏽‍💻 Autor(a)

Feito por **Duda Morais**  
📧 Contato: modias643@gmail.com  

---

## ❤️ Agradecimento

Este projeto é dedicado a todas as pessoas que, de alguma forma, estendem a mão para quem precisa.  
**Doar é um ato de empatia. Programar para ajudar é um ato de transformação.**

