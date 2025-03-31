package com.kosmostecnologia.facturador.domain.helpers;

import com.kosmostecnologia.facturador.application.rest.exception.ProcessException;
import com.kosmostecnologia.facturador.domain.DatosCUF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    private static final String ALGORITHM = "SHA-256";

    public static XMLGregorianCalendar localDateToXmlGregorianCalendar(LocalDateTime dateTime) throws DatatypeConfigurationException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS");
        String formattedDate = dateTime.format(formatter);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(formattedDate);
    }

    public static LocalDateTime xMLGregorianCalendarToLocalDateTime(XMLGregorianCalendar calendar){
        if(calendar != null){
            ZonedDateTime zonedDateTime = calendar.toGregorianCalendar().toZonedDateTime();
            return ZonedDateTime.ofInstant(zonedDateTime.toInstant(), ZoneId.systemDefault()).toLocalDateTime();
        }
        return null;
    }

    public static String obtenerCUF(DatosCUF datosFacturaCUF, String codigoControl){
        String datosConCeros = rellenaCerosIzquierda(String.valueOf(datosFacturaCUF.getNitEmisor()), (short) 13);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formatDateTime = datosFacturaCUF.getFechaHora().format(formatter);
        datosConCeros += rellenaCerosIzquierda(formatDateTime, (short) 17);
        datosConCeros += rellenaCerosIzquierda(String.valueOf(datosFacturaCUF.getSucursal()), (short) 4);
        datosConCeros += rellenaCerosIzquierda(String.valueOf(datosFacturaCUF.getModalidad()), (short) 1);
        datosConCeros += rellenaCerosIzquierda(String.valueOf(datosFacturaCUF.getTipoEmision()), (short) 1);
        datosConCeros += rellenaCerosIzquierda(String.valueOf(datosFacturaCUF.getTipoFacturaDocumentoAjuste()), (short) 1);
        datosConCeros += rellenaCerosIzquierda(String.valueOf(datosFacturaCUF.getTipoDocumentoSector()), (short) 2);
        datosConCeros += rellenaCerosIzquierda(String.valueOf(datosFacturaCUF.getNumeroFactura()), (short) 10);
        datosConCeros += rellenaCerosIzquierda(String.valueOf(datosFacturaCUF.getPuntoVenta()), (short) 4);

        String codigoModulo11 = datosConCeros + calculaDigitoMod11(datosConCeros,1,9,false);
        String codigoBase16 = base16(codigoModulo11);

        return  codigoBase16 + codigoControl;
    }

    public static String rellenaCerosIzquierda(String pString, short pMaxChar){
        StringBuilder vNewString = new StringBuilder(pString);
        if(pString.length() < pMaxChar){
            for(int i = pString.length(); i < pMaxChar; i++){
                vNewString.insert(0, "0");
            }
        }
        return vNewString.toString();
    }

    public static String calculaDigitoMod11(String cadena, int numDig, int limMult, boolean x10) {
        int mult, suma, i, n, dig;
        if (!x10) numDig = 1;
        StringBuilder cadenaBuilder = new StringBuilder(cadena);
        for (n = 1; n <= numDig; n++) {
            suma = 0;
            mult = 2;
            for (i = cadenaBuilder.length() - 1; i >= 0; i--) {
                suma += (mult * Integer.parseInt(cadenaBuilder.substring(i, i + 1)));
                if (++mult > limMult) mult = 2;
            }
            if (x10) {
                dig = ((suma * 10) % 11) % 10;
            } else {
                dig = suma % 11;
            }

            if (dig == 10) {
                cadenaBuilder.append("1");
            }
            if (dig < 10) {
                cadenaBuilder.append(dig);
            }
        }
        cadena = cadenaBuilder.toString();
        return cadena.substring(cadena.length() - numDig);
    }

    public static String base16(String pString){
        try{
            BigInteger vValor = new BigInteger(pString);
            return vValor.toString(16).toUpperCase();
        }catch (NumberFormatException e){
            return "ERROR: Entrada no válida";
        }
    }

    public static String obtenerHash(byte[] pArchivo){
        if(pArchivo == null || pArchivo.length == 0 ){
            throw new IllegalArgumentException("El archivo no puede estar vacío o ser nulo");
        }
        String hashValue = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(Utils.ALGORITHM);
            messageDigest.update(pArchivo);
            byte[] digestedBytes = messageDigest.digest();
            hashValue = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();
        }catch(Exception e ){
            LOGGER.error("Error generando Hash");
            throw new ProcessException("Error generando Hash");
        }
        return hashValue;
    }



}
