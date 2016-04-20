/*
 * XML Type:  reductionGenerateDzFileType
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice;


/**
 * An XML reductionGenerateDzFileType(@http://www.dz.chinaebi.com.cn/webservice).
 *
 * This is a complex type.
 */
public interface ReductionGenerateDzFileType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ReductionGenerateDzFileType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sE0B55BF23D517DE371EED6F1E6E36493").resolveHandle("reductiongeneratedzfiletype16c3type");
    
    /**
     * Gets the "trace" element
     */
    java.lang.String getTrace();
    
    /**
     * Gets (as xml) the "trace" element
     */
    org.apache.xmlbeans.XmlString xgetTrace();
    
    /**
     * Sets the "trace" element
     */
    void setTrace(java.lang.String trace);
    
    /**
     * Sets (as xml) the "trace" element
     */
    void xsetTrace(org.apache.xmlbeans.XmlString trace);
    
    /**
     * Gets the "instId" element
     */
    int getInstId();
    
    /**
     * Gets (as xml) the "instId" element
     */
    org.apache.xmlbeans.XmlInt xgetInstId();
    
    /**
     * Sets the "instId" element
     */
    void setInstId(int instId);
    
    /**
     * Sets (as xml) the "instId" element
     */
    void xsetInstId(org.apache.xmlbeans.XmlInt instId);
    
    /**
     * Gets the "fileType" element
     */
    int getFileType();
    
    /**
     * Gets (as xml) the "fileType" element
     */
    cn.com.chinaebi.dz.webservice.FileType xgetFileType();
    
    /**
     * Sets the "fileType" element
     */
    void setFileType(int fileType);
    
    /**
     * Sets (as xml) the "fileType" element
     */
    void xsetFileType(cn.com.chinaebi.dz.webservice.FileType fileType);
    
    /**
     * Gets the "GenerateDate" element
     */
    java.lang.String getGenerateDate();
    
    /**
     * Gets (as xml) the "GenerateDate" element
     */
    org.apache.xmlbeans.XmlString xgetGenerateDate();
    
    /**
     * Sets the "GenerateDate" element
     */
    void setGenerateDate(java.lang.String generateDate);
    
    /**
     * Sets (as xml) the "GenerateDate" element
     */
    void xsetGenerateDate(org.apache.xmlbeans.XmlString generateDate);
    
    /**
     * Gets the "issueInstant" attribute
     */
    java.util.Calendar getIssueInstant();
    
    /**
     * Gets (as xml) the "issueInstant" attribute
     */
    org.apache.xmlbeans.XmlDateTime xgetIssueInstant();
    
    /**
     * True if has "issueInstant" attribute
     */
    boolean isSetIssueInstant();
    
    /**
     * Sets the "issueInstant" attribute
     */
    void setIssueInstant(java.util.Calendar issueInstant);
    
    /**
     * Sets (as xml) the "issueInstant" attribute
     */
    void xsetIssueInstant(org.apache.xmlbeans.XmlDateTime issueInstant);
    
    /**
     * Unsets the "issueInstant" attribute
     */
    void unsetIssueInstant();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType newInstance() {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
