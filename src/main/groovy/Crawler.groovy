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

    Crawler download() {
        File pathToDownload = Paths.get("download").toFile();
        String fileName = getCurrentElement().attr("href").substring(getCurrentElement().attr("href").lastIndexOf('/') + 1);
        File fileToDownload = new File(pathToDownload, fileName);
        int counter = 1;
        while (fileToDownload.exists()) {
            String newFileName = fileName.substring(0, fileName.lastIndexOf('.')) + "_" + counter + fileName.substring(fileName.lastIndexOf('.'));
            fileToDownload = new File(pathToDownload, newFileName);
            counter++;
        }

        if (!pathToDownload.exists()) pathToDownload.mkdirs()

        File file = configure({
            request.uri = getCurrentElement().attr("href")
        }).get({
            Download.toFile(delegate, fileToDownload)
        }) as File
        file.createNewFile()

        return this
    }

    List<ComponentesTISS> collectDataFromTable() {
        List<ComponentesTISS> listComponents = new ArrayList<>();

        Element tbody = getCurrentDocument().getElementsByTag("table").first()
                .getElementsByTag("tbody").first();
        Elements trList = tbody.getElementsByTag("tr");

        trList.forEach { row ->
            Elements tdList = row.getElementsByTag("td");

            def comp = getTextFromElement(tdList.first(), "span");
            def publi = getTextFromElement(tdList.get(1), "span");
            def startVig = getTextFromElement(tdList.get(2), "span");

            listComponents.add(new ComponentesTISS(comp, publi, startVig));
        }

        return listComponents;
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

}
