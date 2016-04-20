/*
 * An XML document type.
 * Localname: manualHandlerAgainDz
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualHandlerAgainDzDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one manualHandlerAgainDz(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ManualHandlerAgainDzDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ManualHandlerAgainDzDocument
{
    private static final long serialVersionUID = 1L;
    
    public ManualHandlerAgainDzDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MANUALHANDLERAGAINDZ$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "manualHandlerAgainDz");
    
    
    /**
     * Gets the "manualHandlerAgainDz" element
     */
    public cn.com.chinaebi.dz.webservice.ManualHandlerAgainDzType getManualHandlerAgainDz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualHandlerAgainDzType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualHandlerAgainDzType)get_store().find_element_user(MANUALHANDLERAGAINDZ$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "manualHandlerAgainDz" element
     */
    public void setManualHandlerAgainDz(cn.com.chinaebi.dz.webservice.ManualHandlerAgainDzType manualHandlerAgainDz)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualHandlerAgainDzType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualHandlerAgainDzType)get_store().find_element_user(MANUALHANDLERAGAINDZ$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ManualHandlerAgainDzType)get_store().add_element_user(MANUALHANDLERAGAINDZ$0);
            }
            target.set(manualHandlerAgainDz);
        }
    }
    
    /**
     * Appends and returns a new empty "manualHandlerAgainDz" element
     */
    public cn.com.chinaebi.dz.webservice.ManualHandlerAgainDzType addNewManualHandlerAgainDz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualHandlerAgainDzType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualHandlerAgainDzType)get_store().add_element_user(MANUALHANDLERAGAINDZ$0);
            return target;
        }
    }
}
