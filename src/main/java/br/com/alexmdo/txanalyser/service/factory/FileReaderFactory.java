package br.com.alexmdo.txanalyser.service.factory;

public class FileReaderFactory<T> {

    public FileReader getFileReader(String contentType) {
        FileReader fileReader = null;
        if ("text/csv".equals(contentType)) {
            fileReader = new CSVFileReader();
        }

        return fileReader;
    }

}
