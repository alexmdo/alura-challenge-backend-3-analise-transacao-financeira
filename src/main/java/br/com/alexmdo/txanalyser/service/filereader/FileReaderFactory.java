package br.com.alexmdo.txanalyser.service.filereader;

public class FileReaderFactory<T> {

    public FileReader getFileReader(String contentType) {
        return switch (contentType) {
            case "text/csv" -> new CSVFileReader();
            case "text/xml" -> new XMLFileReader();
            default -> null;
        };
    }

}
