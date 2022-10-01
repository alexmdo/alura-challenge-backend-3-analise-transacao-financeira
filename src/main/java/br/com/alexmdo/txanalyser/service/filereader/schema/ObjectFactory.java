//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2022.10.01 às 04:39:05 PM BRT 
//


package br.com.alexmdo.txanalyser.service.filereader.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.com.alexmdo.txanalyser.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Transacoes_QNAME = new QName("", "transacoes");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.com.alexmdo.txanalyser.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TransacoesType }
     * 
     */
    public TransacoesType createTransacoesType() {
        return new TransacoesType();
    }

    /**
     * Create an instance of {@link TransacaoType }
     * 
     */
    public TransacaoType createTransacaoType() {
        return new TransacaoType();
    }

    /**
     * Create an instance of {@link OrigemType }
     * 
     */
    public OrigemType createOrigemType() {
        return new OrigemType();
    }

    /**
     * Create an instance of {@link DestinoType }
     * 
     */
    public DestinoType createDestinoType() {
        return new DestinoType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransacoesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "transacoes")
    public JAXBElement<TransacoesType> createTransacoes(TransacoesType value) {
        return new JAXBElement<TransacoesType>(_Transacoes_QNAME, TransacoesType.class, null, value);
    }

}
