package br.com.alexmdo.txanalyser.service.filereader;

import br.com.alexmdo.txanalyser.controller.dto.TransactionDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FileReader {

    List<TransactionDto> read(InputStream inputStream) throws IOException;

}
