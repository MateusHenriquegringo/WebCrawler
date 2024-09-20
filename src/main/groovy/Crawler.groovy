import com.opencsv.CSVWriter
import groovyx.net.http.optional.Download
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import java.nio.file.Paths

import static groovyx.net.http.HttpBuilder.configure

class Crawler {

    private Document currentDocument;
    private Element currentElement;
    private List<ComponentesTISS> componentesTISS = new ArrayList<>();

    Crawler requestHomePage() {
        String URL = "https://www.gov.br/ans/pt-br"
        setCurrentDocument(Jsoup.connect(URL).get());
        return this
    }

    Crawler findButton(URLS url) {
        setCurrentElement(getCurrentDocument().getElementsByAttributeValue("href", url.getUrl()).first())
        return this
    }

    Crawler clickHttpGET() {
        setCurrentDocument(Jsoup.connect(currentElement.attr("href")).get())
        return this
    }

    Crawler downloadXLSX() {
        def urlDownload = getCurrentDocument()
                .select('a.internal-link').first().attr('href')

        File pathToDownload = Paths.get("CSV").toFile();
        File fileToDownload = new File(pathToDownload, "Tabela de Erros.xlsx");

        File file = configure({
            request.uri = urlDownload
        }).get({
            Download.toFile(delegate, fileToDownload)
        }) as File

        file.createNewFile()

        return this
    }

    Crawler downloadZIP() {
        File pathToDownload = Paths.get("download").toFile();
        String fileName = getCurrentElement().attr("href").substring(getCurrentElement().attr("href").lastIndexOf('/') + 1);
        File fileToDownload = new File(pathToDownload, fileName);

        if (!pathToDownload.exists()) pathToDownload.mkdirs()

        File file = configure({
            request.uri = getCurrentElement().attr("href")
        }).get({
            Download.toFile(delegate, fileToDownload)
        }) as File
        file.createNewFile()

        return this
    }

    Crawler collectDataFromTable() {

        Element tbody = getCurrentDocument().getElementsByTag("table").first()
                .getElementsByTag("tbody").first();
        Elements trList = tbody.getElementsByTag("tr");

        for (Element row : trList) {
            Elements tdList = row.getElementsByTag("td");

            def comp = this.getTextFromElement(tdList.first(), "span");
            if (isBefore2016(comp)) continue
            def publi = this.getTextFromElement(tdList.get(1), "span");
            def startVig = this.getTextFromElement(tdList.get(2), "span");

            this.getComponentesTISS().add(new ComponentesTISS(comp, publi, startVig));
        }

        return this;
    }

    private boolean isBefore2016(String data) {
        try {
            String yearString = data.replaceAll("[^0-9]", "");
            int year = Integer.parseInt(yearString);
            return year < 2016;
        } catch (NumberFormatException e) {
            throw new RuntimeException("Erro ao extrair ano da string: " + e.getMessage());
        }
    }


    Crawler writeCsvFileOfComponentesTISS() {
        try (
                CSVWriter writer = new CSVWriter(new FileWriter("CSV/HistoricoDasVersoes.csv"))
        ) {
            String[] header = ["Componente", "Publicação", "Início de Vigência"];
            writer.writeNext(header);

            this.getComponentesTISS().forEach { componente ->
                String[] data = [
                        componente.getCompetencia(),
                        componente.getPublicacao(),
                        componente.getInicioVigencia()
                ];
                writer.writeNext(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    private String getTextFromElement(Element element, String tag) {
        return Optional.ofNullable(element.getElementsByTag(tag).first())
                .orElse(element)
                .text();
    }

    Document getCurrentDocument() {
        return currentDocument
    }

    void setCurrentDocument(Document currentDocument) {
        this.currentDocument = currentDocument
    }

    Element getCurrentElement() {
        return currentElement
    }

    void setCurrentElement(Element currentElement) {
        this.currentElement = currentElement
    }

    List<ComponentesTISS> getComponentesTISS() {
        return componentesTISS
    }

    void setComponentesTISS(List<ComponentesTISS> componentesTISS) {
        this.componentesTISS = componentesTISS
    }

}
