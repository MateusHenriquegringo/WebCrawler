public class Main {

    static Crawler crawler = new Crawler()

    public static void main(String[] args) {


        crawler.requestHomePage()
                .findButton(URLS.PAGE_1.getUrl())
                .clickHttpGET()
                .findButton(URLS.PAGE_2.getUrl())
                .clickHttpGET()
                .findButton(URLS.PAGE_3.getUrl())
                .clickHttpGET()
                .findButton(URLS.DOWNLOAD.getUrl())
                .download()


        print(crawler.getCurrentDocument().title())
    }
}
