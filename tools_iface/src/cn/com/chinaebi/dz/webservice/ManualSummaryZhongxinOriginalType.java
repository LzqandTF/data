/*
 * XML Type:  manualSummaryZhongxinOriginalType
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice;


/**
 * An XML manualSummaryZhongxinOriginalType(@http://www.dz.chinaebi.com.cn/webservice).
 *
 * This is a complex type.
 */
public interface ManualSummaryZhongxinOriginalType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ManualSummaryZhongxinOriginalType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sE0B55BF23D517DE371EED6F1E6E36493").resolveHandle("manualsummaryzhongxinoriginaltype97a9type");
    
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
     * Gets the "summaryDate" element
     */
    java.lang.String getSummaryDate();
    
    /**
     * Gets (as xml) the "summaryDate" element
     */
    org.apache.xmlbeans.XmlString xgetSummaryDate();
    
    /**
     * Sets the "summaryDate" element
     */
    void setSummaryDate(java.lang.String summaryDate);
    
    /**
     * Sets (as xml) the "summaryDate" element
     */
    void xsetSummaryDate(org.apache.xmlbeans.XmlString summaryDate);
    
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
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType newInstance() {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
