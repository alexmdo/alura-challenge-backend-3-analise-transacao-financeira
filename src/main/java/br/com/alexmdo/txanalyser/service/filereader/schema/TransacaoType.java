//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2022.10.01 às 04:39:05 PM BRT 
//


package br.com.alexmdo.txanalyser.service.filereader.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de transacaoType complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="transacaoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="origem" type="{}origemType"/>
 *         &lt;element name="destino" type="{}destinoType"/>
 *         &lt;element name="valor">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="8000"/>
 *               &lt;enumeration value="210"/>
 *               &lt;enumeration value="79800.22"/>
 *               &lt;enumeration value="11.50"/>
 *               &lt;enumeration value="100"/>
 *               &lt;enumeration value="19000.50"/>
 *               &lt;enumeration value="1000"/>
 *               &lt;enumeration value="2000"/>
 *               &lt;enumeration value="300"/>
 *               &lt;enumeration value="900"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="data">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="2022-01-02T07:30:00"/>
 *               &lt;enumeration value="2022-01-02T08:12:00"/>
 *               &lt;enumeration value="2022-01-02T08:44:00"/>
 *               &lt;enumeration value="2022-01-02T12:32:00"/>
 *               &lt;enumeration value="2022-01-02T16:30:00"/>
 *               &lt;enumeration value="2022-01-02T16:55:00"/>
 *               &lt;enumeration value="2022-01-02T19:30:00"/>
 *               &lt;enumeration value="2022-01-02T19:34:00"/>
 *               &lt;enumeration value="2022-01-02T20:30:00"/>
 *               &lt;enumeration value="2022-01-02T22:30:00"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transacaoType", propOrder = {
    "origem",
    "destino",
    "valor",
    "data"
})
public class TransacaoType {

    @XmlElement(required = true)
    protected OrigemType origem;
    @XmlElement(required = true)
    protected DestinoType destino;
    @XmlElement(required = true)
    protected String valor;
    @XmlElement(required = true)
    protected String data;

    /**
     * Obtém o valor da propriedade origem.
     * 
     * @return
     *     possible object is
     *     {@link OrigemType }
     *     
     */
    public OrigemType getOrigem() {
        return origem;
    }

    /**
     * Define o valor da propriedade origem.
     * 
     * @param value
     *     allowed object is
     *     {@link OrigemType }
     *     
     */
    public void setOrigem(OrigemType value) {
        this.origem = value;
    }

    /**
     * Obtém o valor da propriedade destino.
     * 
     * @return
     *     possible object is
     *     {@link DestinoType }
     *     
     */
    public DestinoType getDestino() {
        return destino;
    }

    /**
     * Define o valor da propriedade destino.
     * 
     * @param value
     *     allowed object is
     *     {@link DestinoType }
     *     
     */
    public void setDestino(DestinoType value) {
        this.destino = value;
    }

    /**
     * Obtém o valor da propriedade valor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValor() {
        return valor;
    }

    /**
     * Define o valor da propriedade valor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValor(String value) {
        this.valor = value;
    }

    /**
     * Obtém o valor da propriedade data.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getData() {
        return data;
    }

    /**
     * Define o valor da propriedade data.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setData(String value) {
        this.data = value;
    }

}
