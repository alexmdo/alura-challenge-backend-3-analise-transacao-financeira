package br.com.alexmdo.txanalyser.service.filereader;

import br.com.alexmdo.txanalyser.controller.dto.BankDto;
import br.com.alexmdo.txanalyser.controller.dto.TransactionDto;
import br.com.alexmdo.txanalyser.service.filereader.schema.TransacaoType;
import br.com.alexmdo.txanalyser.service.filereader.schema.TransacoesType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class XMLFileReader implements FileReader {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public List<TransactionDto> read(InputStream inputStream) {
        try {
            JAXBContext context = JAXBContext.newInstance(TransacoesType.class);
            TransacoesType transacoesType = (TransacoesType) context.createUnmarshaller().unmarshal(inputStream);
            List<TransacaoType> transacao = transacoesType.getTransacao();
            if (transacao != null) {
                return transacao.stream().map(t -> new TransactionDto(
                        new BankDto(t.getOrigem().getBanco(), t.getOrigem().getAgencia(), t.getOrigem().getConta()),
                        new BankDto(t.getDestino().getBanco(), t.getDestino().getAgencia(), t.getDestino().getConta()),
                        new BigDecimal(t.getValor()),
                        LocalDateTime.parse(t.getData(), formatter)
                )).collect(Collectors.toList());
            }
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
