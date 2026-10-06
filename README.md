# Portal-Cidadao-2.0

# 🏛️ Portal Cidadão

Projeto acadêmico desenvolvido como Atividade de Extensão e Projetos (AEP) do 5º Semestre do curso de Análise e Desenvolvimento de Sistemas.

O Portal Cidadão é um sistema de gestão de solicitações públicas via terminal, onde cidadãos podem registrar ocorrências urbanas — como buracos, falta de iluminação ou problemas de saúde — e acompanhar o andamento de cada caso por meio de um número de protocolo. Do lado da prefeitura, gestores têm acesso a um painel exclusivo para gerenciar e atualizar o status das solicitações.

---

## 👥 Integrantes

| Nome | RA |
|------|----|
| Guilherme Tamioso Bueno do Prado | 24368069-2 |

---

## ⚙️ Funcionalidades

### Cidadão
- **Abrir solicitação** — o cidadão informa categoria, descrição, bairro e seus dados pessoais (nome, CPF, telefone e e-mail). Também é possível realizar solicitações de forma **anônima**, com exigência de descrição mais detalhada (mínimo 100 caracteres).
- **Acompanhar solicitação** — consultando pelo número de protocolo, o cidadão visualiza o status atual e o histórico completo de atualizações.

### Painel do Gestor (acesso restrito por senha)
- **Listar com filtros** — filtra solicitações por bairro e/ou categoria.
- **Atualizar status** — permite avançar o ciclo de vida de uma solicitação, registrando responsável e comentário. Solicitações **atrasadas** exigem justificativa obrigatória para atualização.

---

## 🛠️ Tecnologias Utilizadas

- **Java 24**
- **Maven**

---

## 📌 Status do Projeto

🚧 Finalizado — Projeto acadêmico (AEP 5º Semestre)

Executar:
mvn spring-boot:run

Ou executar a classe principal:
PortalCidadaoApplication.java

Após a inicialização, o sistema estará disponível em:
http://localhost:8080

##  Logins

PORTAL CIDADÃO — Login Criados automaticamente:
-  Admin  : admin@prefeitura.gov.br / admin123
-  Cidadão: joao@email.com / senha123
-  Cidadão: maria@email.com / senha123
