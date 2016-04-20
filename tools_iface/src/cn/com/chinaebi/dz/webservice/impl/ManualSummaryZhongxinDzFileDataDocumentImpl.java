/*
 * An XML document type.
 * Localname: manualSummaryZhongxinDzFileData
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinDzFileDataDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one manualSummaryZhongxinDzFileData(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ManualSummaryZhongxinDzFileDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinDzFileDataDocument
{
    private static final long serialVersionUID = 1L;
    
    public ManualSummaryZhongxinDzFileDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MANUALSUMMARYZHONGXINDZFILEDATA$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "manualSummaryZhongxinDzFileData");
    
    
    /**
     * Gets the "manualSummaryZhongxinDzFileData" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinDzFileDataType getManualSummaryZhongxinDzFileData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinDzFileDataType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinDzFileDataType)get_store().find_element_user(MANUALSUMMARYZHONGXINDZFILEDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "manualSummaryZhongxinDzFileData" element
     */
    public void setManualSummaryZhongxinDzFileData(cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinDzFileDataType manualSummaryZhongxinDzFileData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinDzFileDataType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinDzFileDataType)get_store().find_element_user(MANUALSUMMARYZHONGXINDZFILEDATA$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinDzFileDataType)get_store().add_element_user(MANUALSUMMARYZHONGXINDZFILEDATA$0);
            }
            target.set(manualSummaryZhongxinDzFileData);
        }
    }
    
    /**
     * Appends and returns a new empty "manualSummaryZhongxinDzFileData" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinDzFileDataType addNewManualSummaryZhongxinDzFileData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinDzFileDataType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryZhongxinDzFileDataType)get_store().add_element_user(MANUALSUMMARYZHONGXINDZFILEDATA$0);
            return target;
        }
    }
}
