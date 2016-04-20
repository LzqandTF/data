/*
 * An XML document type.
 * Localname: manualSummaryZhongxinOriginal
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one manualSummaryZhongxinOriginal(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ManualSummaryZhongxinOriginalDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalDocument
{
    private static final long serialVersionUID = 1L;
    
    public ManualSummaryZhongxinOriginalDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MANUALSUMMARYZHONGXINORIGINAL$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "manualSummaryZhongxinOriginal");
    
    
    /**
     * Gets the "manualSummaryZhongxinOriginal" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType getManualSummaryZhongxinOriginal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType)get_store().find_element_user(MANUALSUMMARYZHONGXINORIGINAL$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "manualSummaryZhongxinOriginal" element
     */
    public void setManualSummaryZhongxinOriginal(cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType manualSummaryZhongxinOriginal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType)get_store().find_element_user(MANUALSUMMARYZHONGXINORIGINAL$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType)get_store().add_element_user(MANUALSUMMARYZHONGXINORIGINAL$0);
            }
            target.set(manualSummaryZhongxinOriginal);
        }
    }
    
    /**
     * Appends and returns a new empty "manualSummaryZhongxinOriginal" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType addNewManualSummaryZhongxinOriginal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinOriginalType)get_store().add_element_user(MANUALSUMMARYZHONGXINORIGINAL$0);
            return target;
        }
    }
}
