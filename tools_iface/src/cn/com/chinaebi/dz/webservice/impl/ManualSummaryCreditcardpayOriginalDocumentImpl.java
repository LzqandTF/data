/*
 * An XML document type.
 * Localname: manualSummaryCreditcardpayOriginal
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualSummaryCreditcardpayOriginalDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one manualSummaryCreditcardpayOriginal(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ManualSummaryCreditcardpayOriginalDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ManualSummaryCreditcardpayOriginalDocument
{
    private static final long serialVersionUID = 1L;
    
    public ManualSummaryCreditcardpayOriginalDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MANUALSUMMARYCREDITCARDPAYORIGINAL$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "manualSummaryCreditcardpayOriginal");
    
    
    /**
     * Gets the "manualSummaryCreditcardpayOriginal" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryCreditcardpayOriginalType getManualSummaryCreditcardpayOriginal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryCreditcardpayOriginalType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryCreditcardpayOriginalType)get_store().find_element_user(MANUALSUMMARYCREDITCARDPAYORIGINAL$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "manualSummaryCreditcardpayOriginal" element
     */
    public void setManualSummaryCreditcardpayOriginal(cn.com.chinaebi.dz.webservice.ManualSummaryCreditcardpayOriginalType manualSummaryCreditcardpayOriginal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryCreditcardpayOriginalType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryCreditcardpayOriginalType)get_store().find_element_user(MANUALSUMMARYCREDITCARDPAYORIGINAL$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ManualSummaryCreditcardpayOriginalType)get_store().add_element_user(MANUALSUMMARYCREDITCARDPAYORIGINAL$0);
            }
            target.set(manualSummaryCreditcardpayOriginal);
        }
    }
    
    /**
     * Appends and returns a new empty "manualSummaryCreditcardpayOriginal" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryCreditcardpayOriginalType addNewManualSummaryCreditcardpayOriginal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryCreditcardpayOriginalType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryCreditcardpayOriginalType)get_store().add_element_user(MANUALSUMMARYCREDITCARDPAYORIGINAL$0);
            return target;
        }
    }
}
