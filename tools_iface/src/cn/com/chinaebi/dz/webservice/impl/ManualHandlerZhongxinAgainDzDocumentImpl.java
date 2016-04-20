/*
 * An XML document type.
 * Localname: manualHandlerZhongxinAgainDz
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualHandlerZhongxinAgainDzDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one manualHandlerZhongxinAgainDz(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ManualHandlerZhongxinAgainDzDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ManualHandlerZhongxinAgainDzDocument
{
    private static final long serialVersionUID = 1L;
    
    public ManualHandlerZhongxinAgainDzDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MANUALHANDLERZHONGXINAGAINDZ$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "manualHandlerZhongxinAgainDz");
    
    
    /**
     * Gets the "manualHandlerZhongxinAgainDz" element
     */
    public cn.com.chinaebi.dz.webservice.ManualHandlerZhongxinAgainDzType getManualHandlerZhongxinAgainDz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualHandlerZhongxinAgainDzType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualHandlerZhongxinAgainDzType)get_store().find_element_user(MANUALHANDLERZHONGXINAGAINDZ$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "manualHandlerZhongxinAgainDz" element
     */
    public void setManualHandlerZhongxinAgainDz(cn.com.chinaebi.dz.webservice.ManualHandlerZhongxinAgainDzType manualHandlerZhongxinAgainDz)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualHandlerZhongxinAgainDzType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualHandlerZhongxinAgainDzType)get_store().find_element_user(MANUALHANDLERZHONGXINAGAINDZ$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ManualHandlerZhongxinAgainDzType)get_store().add_element_user(MANUALHANDLERZHONGXINAGAINDZ$0);
            }
            target.set(manualHandlerZhongxinAgainDz);
        }
    }
    
    /**
     * Appends and returns a new empty "manualHandlerZhongxinAgainDz" element
     */
    public cn.com.chinaebi.dz.webservice.ManualHandlerZhongxinAgainDzType addNewManualHandlerZhongxinAgainDz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualHandlerZhongxinAgainDzType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualHandlerZhongxinAgainDzType)get_store().add_element_user(MANUALHANDLERZHONGXINAGAINDZ$0);
            return target;
        }
    }
}
