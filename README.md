# softvinhows
Back-end REST loja de vinhos <br>

Framework REST: Jersey <br>
Controle de dependências: Maven <br>
Banco de dados utilizado: PostgreSQL 9.5 <br>
servlet container: Apache Tomcat 8 <br>
JPA: Hibernate <br>

Requisitos do banco de dados(PostgreSQL 9.5): 
  - nome do banco: softvinho
  - schema: teste
  - usuário(com permissão para criar tabelas): softvinho
  - senha: 123
  (tabelas geradas automaticamente no deploy da aplicação)
  
Compilação (maven): 
  - mvn clean package
  - arquivo gerado para deploy: target/softvinhows.war
  