import groovyx.net.http.optional.Download
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

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

    Crawler findButton(String buttonUrl) {
        setCurrentElement(getCurrentDocument().getElementsByAttributeValue("href", buttonUrl).first())
        return this
    }

    Crawler clickHttpGET() {
        setCurrentDocument(Jsoup.connect(currentElement.attr("href")).get())
        return this
    }

    void download() {
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
