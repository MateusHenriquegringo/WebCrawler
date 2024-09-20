# Crawler de Download e Coleta de Dados TISS

Este projeto é um crawler desenvolvido em Groovy para automatizar a navegação e coleta de dados do site da Agência Nacional de Saúde Suplementar (ANS), focado em três tarefas principais: download de arquivos ZIP e XLSX e extração de dados de uma tabela HTML. A aplicação usa `Jsoup` para manipular o HTML e `HttpBuilder-NG` para fazer as requisições HTTP. Além disso, o `OpenCSV` é utilizado para exportar os dados coletados para arquivos CSV.

## Funcionalidades

### Tarefa 1: Download de Arquivo ZIP
Navega até uma página específica no site da ANS e baixa um arquivo ZIP. O arquivo é salvo no diretório `download/`.

### Tarefa 2: Coleta de Dados da Tabela e Exportação para CSV
Navega até uma página de histórico de versões e coleta dados de uma tabela HTML. Os dados são filtrados para incluir apenas versões publicadas a partir de 2016. O resultado é exportado para um arquivo CSV no diretório `CSV/`.

### Tarefa 3: Download de Arquivo XLSX
Navega até uma página de tabelas relacionadas e baixa um arquivo XLSX. O arquivo é salvo no diretório `CSV/`.

## Dependências

- Groovy
- Jsoup
- HttpBuilder-NG
- OpenCSV

## Estrutura do Código

- **Crawler.java**: Contém a lógica principal de navegação e coleta de dados.
- **ComponentesTISS**: Classe modelo que armazena as informações coletadas da tabela HTML.

