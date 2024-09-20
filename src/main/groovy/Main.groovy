public class Main {

    static Crawler crawler = new Crawler()

    public static void main(String[] args) {

        //TAREFA 1
        crawler.requestHomePage()
                .findButton(URLS.PAGE_1)
                .clickHttpGET()
                .findButton(URLS.PAGE_2)
                .clickHttpGET()
                .findButton(URLS.PAGE_PADRAO_TISS)
                .clickHttpGET()
                .findButton(URLS.DOWNLOAD)
                .downloadZIP()

        //TAREFA 2
                .requestHomePage()
                .findButton(URLS.PAGE_1)
                .clickHttpGET()
                .findButton(URLS.PAGE_2)
                .clickHttpGET()
                .findButton(URLS.PAGE_HISTORICO_VERSOES)
                .clickHttpGET()
                .collectDataFromTable()
                .writeCsvFileOfComponentesTISS()

        //TAREFA 3
                .requestHomePage()
                .findButton(URLS.PAGE_1)
                .clickHttpGET()
                .findButton(URLS.PAGE_2)
                .clickHttpGET()
                .findButton(URLS.PAGE_TABELAS_RELACIONADAS)
                .clickHttpGET()
                .downloadXLSX()

    }
}

