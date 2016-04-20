/*
 * An XML document type.
 * Localname: dzTradeContext
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.DzTradeContextDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice;


/**
 * A document containing one dzTradeContext(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public interface DzTradeContextDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DzTradeContextDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sE0B55BF23D517DE371EED6F1E6E36493").resolveHandle("dztradecontext9834doctype");
    
    /**
     * Gets the "dzTradeContext" element
     */
    cn.com.chinaebi.dz.webservice.DzTradeContextType getDzTradeContext();
    
    /**
     * Sets the "dzTradeContext" element
     */
    void setDzTradeContext(cn.com.chinaebi.dz.webservice.DzTradeContextType dzTradeContext);
    
    /**
     * Appends and returns a new empty "dzTradeContext" element
     */
    cn.com.chinaebi.dz.webservice.DzTradeContextType addNewDzTradeContext();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument newInstance() {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static cn.com.chinaebi.dz.webservice.DzTradeContextDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (cn.com.chinaebi.dz.webservice.DzTradeContextDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
