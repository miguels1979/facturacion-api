/**
 * SolicitudReversionAnulacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class SolicitudReversionAnulacion  extends bo.gob.impuestos.siat.SolicitudRecepcion  implements java.io.Serializable {
    private java.lang.String cuf;

    public SolicitudReversionAnulacion() {
    }

    public SolicitudReversionAnulacion(
           int codigoAmbiente,
           int codigoDocumentoSector,
           int codigoEmision,
           int codigoModalidad,
           java.lang.Integer codigoPuntoVenta,
           java.lang.String codigoSistema,
           int codigoSucursal,
           java.lang.String cufd,
           java.lang.String cuis,
           long nit,
           int tipoFacturaDocumento,
           java.lang.String cuf) {
        super(
            codigoAmbiente,
            codigoDocumentoSector,
            codigoEmision,
            codigoModalidad,
            codigoPuntoVenta,
            codigoSistema,
            codigoSucursal,
            cufd,
            cuis,
            nit,
            tipoFacturaDocumento);
        this.cuf = cuf;
    }


    /**
     * Gets the cuf value for this SolicitudReversionAnulacion.
     * 
     * @return cuf
     */
    public java.lang.String getCuf() {
        return cuf;
    }


    /**
     * Sets the cuf value for this SolicitudReversionAnulacion.
     * 
     * @param cuf
     */
    public void setCuf(java.lang.String cuf) {
        this.cuf = cuf;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SolicitudReversionAnulacion)) return false;
        SolicitudReversionAnulacion other = (SolicitudReversionAnulacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.cuf==null && other.getCuf()==null) || 
             (this.cuf!=null &&
              this.cuf.equals(other.getCuf())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getCuf() != null) {
            _hashCode += getCuf().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SolicitudReversionAnulacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudReversionAnulacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cuf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
