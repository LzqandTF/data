/*
 * An XML document type.
 * Localname: manualSummaryOriginal
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualSummaryOriginalDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one manualSummaryOriginal(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ManualSummaryOriginalDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ManualSummaryOriginalDocument
{
    private static final long serialVersionUID = 1L;
    
    public ManualSummaryOriginalDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MANUALSUMMARYORIGINAL$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "manualSummaryOriginal");
    
    
    /**
     * Gets the "manualSummaryOriginal" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryOriginalType getManualSummaryOriginal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryOriginalType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryOriginalType)get_store().find_element_user(MANUALSUMMARYORIGINAL$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "manualSummaryOriginal" element
     */
    public void setManualSummaryOriginal(cn.com.chinaebi.dz.webservice.ManualSummaryOriginalType manualSummaryOriginal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryOriginalType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryOriginalType)get_store().find_element_user(MANUALSUMMARYORIGINAL$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ManualSummaryOriginalType)get_store().add_element_user(MANUALSUMMARYORIGINAL$0);
            }
            target.set(manualSummaryOriginal);
        }
    }
    
    /**
     * Appends and returns a new empty "manualSummaryOriginal" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryOriginalType addNewManualSummaryOriginal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryOriginalType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryOriginalType)get_store().add_element_user(MANUALSUMMARYORIGINAL$0);
            return target;
        }
    }
}
