/*
 * An XML document type.
 * Localname: manualHandlerBeijingAgainDz
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualHandlerBeijingAgainDzDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one manualHandlerBeijingAgainDz(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ManualHandlerBeijingAgainDzDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ManualHandlerBeijingAgainDzDocument
{
    private static final long serialVersionUID = 1L;
    
    public ManualHandlerBeijingAgainDzDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MANUALHANDLERBEIJINGAGAINDZ$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "manualHandlerBeijingAgainDz");
    
    
    /**
     * Gets the "manualHandlerBeijingAgainDz" element
     */
    public cn.com.chinaebi.dz.webservice.ManualHandlerBeijingAgainDzType getManualHandlerBeijingAgainDz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualHandlerBeijingAgainDzType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualHandlerBeijingAgainDzType)get_store().find_element_user(MANUALHANDLERBEIJINGAGAINDZ$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "manualHandlerBeijingAgainDz" element
     */
    public void setManualHandlerBeijingAgainDz(cn.com.chinaebi.dz.webservice.ManualHandlerBeijingAgainDzType manualHandlerBeijingAgainDz)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualHandlerBeijingAgainDzType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualHandlerBeijingAgainDzType)get_store().find_element_user(MANUALHANDLERBEIJINGAGAINDZ$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ManualHandlerBeijingAgainDzType)get_store().add_element_user(MANUALHANDLERBEIJINGAGAINDZ$0);
            }
            target.set(manualHandlerBeijingAgainDz);
        }
    }
    
    /**
     * Appends and returns a new empty "manualHandlerBeijingAgainDz" element
     */
    public cn.com.chinaebi.dz.webservice.ManualHandlerBeijingAgainDzType addNewManualHandlerBeijingAgainDz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualHandlerBeijingAgainDzType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualHandlerBeijingAgainDzType)get_store().add_element_user(MANUALHANDLERBEIJINGAGAINDZ$0);
            return target;
        }
    }
}
