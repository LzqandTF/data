/*
 * An XML document type.
 * Localname: manualSummaryCupsDzFileData
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualSummaryCupsDzFileDataDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one manualSummaryCupsDzFileData(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ManualSummaryCupsDzFileDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ManualSummaryCupsDzFileDataDocument
{
    private static final long serialVersionUID = 1L;
    
    public ManualSummaryCupsDzFileDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MANUALSUMMARYCUPSDZFILEDATA$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "manualSummaryCupsDzFileData");
    
    
    /**
     * Gets the "manualSummaryCupsDzFileData" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryCupsDzFileDataType getManualSummaryCupsDzFileData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryCupsDzFileDataType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryCupsDzFileDataType)get_store().find_element_user(MANUALSUMMARYCUPSDZFILEDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "manualSummaryCupsDzFileData" element
     */
    public void setManualSummaryCupsDzFileData(cn.com.chinaebi.dz.webservice.ManualSummaryCupsDzFileDataType manualSummaryCupsDzFileData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryCupsDzFileDataType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryCupsDzFileDataType)get_store().find_element_user(MANUALSUMMARYCUPSDZFILEDATA$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ManualSummaryCupsDzFileDataType)get_store().add_element_user(MANUALSUMMARYCUPSDZFILEDATA$0);
            }
            target.set(manualSummaryCupsDzFileData);
        }
    }
    
    /**
     * Appends and returns a new empty "manualSummaryCupsDzFileData" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryCupsDzFileDataType addNewManualSummaryCupsDzFileData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryCupsDzFileDataType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryCupsDzFileDataType)get_store().add_element_user(MANUALSUMMARYCUPSDZFILEDATA$0);
            return target;
        }
    }
}
