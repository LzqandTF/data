/*
 * An XML document type.
 * Localname: manualHandlerCupsAgainDz
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualHandlerCupsAgainDzDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one manualHandlerCupsAgainDz(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ManualHandlerCupsAgainDzDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ManualHandlerCupsAgainDzDocument
{
    private static final long serialVersionUID = 1L;
    
    public ManualHandlerCupsAgainDzDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MANUALHANDLERCUPSAGAINDZ$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "manualHandlerCupsAgainDz");
    
    
    /**
     * Gets the "manualHandlerCupsAgainDz" element
     */
    public cn.com.chinaebi.dz.webservice.ManualHandlerCupsAgainDzType getManualHandlerCupsAgainDz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualHandlerCupsAgainDzType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualHandlerCupsAgainDzType)get_store().find_element_user(MANUALHANDLERCUPSAGAINDZ$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "manualHandlerCupsAgainDz" element
     */
    public void setManualHandlerCupsAgainDz(cn.com.chinaebi.dz.webservice.ManualHandlerCupsAgainDzType manualHandlerCupsAgainDz)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualHandlerCupsAgainDzType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualHandlerCupsAgainDzType)get_store().find_element_user(MANUALHANDLERCUPSAGAINDZ$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ManualHandlerCupsAgainDzType)get_store().add_element_user(MANUALHANDLERCUPSAGAINDZ$0);
            }
            target.set(manualHandlerCupsAgainDz);
        }
    }
    
    /**
     * Appends and returns a new empty "manualHandlerCupsAgainDz" element
     */
    public cn.com.chinaebi.dz.webservice.ManualHandlerCupsAgainDzType addNewManualHandlerCupsAgainDz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualHandlerCupsAgainDzType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualHandlerCupsAgainDzType)get_store().add_element_user(MANUALHANDLERCUPSAGAINDZ$0);
            return target;
        }
    }
}
