/*
 * An XML document type.
 * Localname: manualSummaryCupsOriginal
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualSummaryCupsOriginalDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one manualSummaryCupsOriginal(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ManualSummaryCupsOriginalDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ManualSummaryCupsOriginalDocument
{
    private static final long serialVersionUID = 1L;
    
    public ManualSummaryCupsOriginalDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MANUALSUMMARYCUPSORIGINAL$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "manualSummaryCupsOriginal");
    
    
    /**
     * Gets the "manualSummaryCupsOriginal" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryCupsOriginalType getManualSummaryCupsOriginal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryCupsOriginalType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryCupsOriginalType)get_store().find_element_user(MANUALSUMMARYCUPSORIGINAL$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "manualSummaryCupsOriginal" element
     */
    public void setManualSummaryCupsOriginal(cn.com.chinaebi.dz.webservice.ManualSummaryCupsOriginalType manualSummaryCupsOriginal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryCupsOriginalType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryCupsOriginalType)get_store().find_element_user(MANUALSUMMARYCUPSORIGINAL$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ManualSummaryCupsOriginalType)get_store().add_element_user(MANUALSUMMARYCUPSORIGINAL$0);
            }
            target.set(manualSummaryCupsOriginal);
        }
    }
    
    /**
     * Appends and returns a new empty "manualSummaryCupsOriginal" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryCupsOriginalType addNewManualSummaryCupsOriginal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryCupsOriginalType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryCupsOriginalType)get_store().add_element_user(MANUALSUMMARYCUPSORIGINAL$0);
            return target;
        }
    }
}
