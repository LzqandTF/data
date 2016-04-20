/*
 * An XML document type.
 * Localname: manualSummaryBeijingDzFileData
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualSummaryBeijingDzFileDataDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one manualSummaryBeijingDzFileData(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ManualSummaryBeijingDzFileDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ManualSummaryBeijingDzFileDataDocument
{
    private static final long serialVersionUID = 1L;
    
    public ManualSummaryBeijingDzFileDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MANUALSUMMARYBEIJINGDZFILEDATA$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "manualSummaryBeijingDzFileData");
    
    
    /**
     * Gets the "manualSummaryBeijingDzFileData" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryBeijingDzFileDataType getManualSummaryBeijingDzFileData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryBeijingDzFileDataType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryBeijingDzFileDataType)get_store().find_element_user(MANUALSUMMARYBEIJINGDZFILEDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "manualSummaryBeijingDzFileData" element
     */
    public void setManualSummaryBeijingDzFileData(cn.com.chinaebi.dz.webservice.ManualSummaryBeijingDzFileDataType manualSummaryBeijingDzFileData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryBeijingDzFileDataType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryBeijingDzFileDataType)get_store().find_element_user(MANUALSUMMARYBEIJINGDZFILEDATA$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ManualSummaryBeijingDzFileDataType)get_store().add_element_user(MANUALSUMMARYBEIJINGDZFILEDATA$0);
            }
            target.set(manualSummaryBeijingDzFileData);
        }
    }
    
    /**
     * Appends and returns a new empty "manualSummaryBeijingDzFileData" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryBeijingDzFileDataType addNewManualSummaryBeijingDzFileData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryBeijingDzFileDataType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryBeijingDzFileDataType)get_store().add_element_user(MANUALSUMMARYBEIJINGDZFILEDATA$0);
            return target;
        }
    }
}
