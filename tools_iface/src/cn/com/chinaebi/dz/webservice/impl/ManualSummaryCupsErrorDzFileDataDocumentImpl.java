/*
 * An XML document type.
 * Localname: manualSummaryCupsErrorDzFileData
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualSummaryCupsErrorDzFileDataDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one manualSummaryCupsErrorDzFileData(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ManualSummaryCupsErrorDzFileDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ManualSummaryCupsErrorDzFileDataDocument
{
    private static final long serialVersionUID = 1L;
    
    public ManualSummaryCupsErrorDzFileDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MANUALSUMMARYCUPSERRORDZFILEDATA$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "manualSummaryCupsErrorDzFileData");
    
    
    /**
     * Gets the "manualSummaryCupsErrorDzFileData" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryCupsErrorDzFileDataType getManualSummaryCupsErrorDzFileData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryCupsErrorDzFileDataType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryCupsErrorDzFileDataType)get_store().find_element_user(MANUALSUMMARYCUPSERRORDZFILEDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "manualSummaryCupsErrorDzFileData" element
     */
    public void setManualSummaryCupsErrorDzFileData(cn.com.chinaebi.dz.webservice.ManualSummaryCupsErrorDzFileDataType manualSummaryCupsErrorDzFileData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryCupsErrorDzFileDataType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryCupsErrorDzFileDataType)get_store().find_element_user(MANUALSUMMARYCUPSERRORDZFILEDATA$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ManualSummaryCupsErrorDzFileDataType)get_store().add_element_user(MANUALSUMMARYCUPSERRORDZFILEDATA$0);
            }
            target.set(manualSummaryCupsErrorDzFileData);
        }
    }
    
    /**
     * Appends and returns a new empty "manualSummaryCupsErrorDzFileData" element
     */
    public cn.com.chinaebi.dz.webservice.ManualSummaryCupsErrorDzFileDataType addNewManualSummaryCupsErrorDzFileData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ManualSummaryCupsErrorDzFileDataType target = null;
            target = (cn.com.chinaebi.dz.webservice.ManualSummaryCupsErrorDzFileDataType)get_store().add_element_user(MANUALSUMMARYCUPSERRORDZFILEDATA$0);
            return target;
        }
    }
}
