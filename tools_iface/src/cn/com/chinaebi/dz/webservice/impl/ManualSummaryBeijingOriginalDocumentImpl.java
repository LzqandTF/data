/*
 * An XML document type.
 * Localname: manualSummaryBeijingOriginal
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualSummaryBeijingOriginalDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one manualSummaryBeijingOriginal(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ManualSummaryBeijingOriginalDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ManualSummaryBeijingOriginalDocument
{
    private static final long serialVersionUID = 1L;
    
    public ManualSummaryBeijingOriginalDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MANUALSUMMARYBEIJINGORIGINAL$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "manualSummaryBeijingOriginal");
    
    
    /**
     * Gets the "manualSummaryBeijingOriginal" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryBeijingOriginalType getManualSummaryBeijingOriginal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryBeijingOriginalType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryBeijingOriginalType)get_store().find_element_user(MANUALSUMMARYBEIJINGORIGINAL$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "manualSummaryBeijingOriginal" element
     */
    public void setManualSummaryBeijingOriginal(cn.com.chinaebi.dz.webservice.ManualSummaryBeijingOriginalType manualSummaryBeijingOriginal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryBeijingOriginalType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryBeijingOriginalType)get_store().find_element_user(MANUALSUMMARYBEIJINGORIGINAL$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ManualSummaryBeijingOriginalType)get_store().add_element_user(MANUALSUMMARYBEIJINGORIGINAL$0);
            }
            target.set(manualSummaryBeijingOriginal);
        }
    }
    
    /**
     * Appends and returns a new empty "manualSummaryBeijingOriginal" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryBeijingOriginalType addNewManualSummaryBeijingOriginal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryBeijingOriginalType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryBeijingOriginalType)get_store().add_element_user(MANUALSUMMARYBEIJINGORIGINAL$0);
            return target;
        }
    }
}
