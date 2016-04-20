/*
 * An XML document type.
 * Localname: manualHandlerErrorDz
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one manualHandlerErrorDz(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ManualHandlerErrorDzDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzDocument
{
    private static final long serialVersionUID = 1L;
    
    public ManualHandlerErrorDzDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MANUALHANDLERERRORDZ$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "manualHandlerErrorDz");
    
    
    /**
     * Gets the "manualHandlerErrorDz" element
     */
    public cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzType getManualHandlerErrorDz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzType)get_store().find_element_user(MANUALHANDLERERRORDZ$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "manualHandlerErrorDz" element
     */
    public void setManualHandlerErrorDz(cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzType manualHandlerErrorDz)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzType)get_store().find_element_user(MANUALHANDLERERRORDZ$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzType)get_store().add_element_user(MANUALHANDLERERRORDZ$0);
            }
            target.set(manualHandlerErrorDz);
        }
    }
    
    /**
     * Appends and returns a new empty "manualHandlerErrorDz" element
     */
    public cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzType addNewManualHandlerErrorDz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzType)get_store().add_element_user(MANUALHANDLERERRORDZ$0);
            return target;
        }
    }
}
