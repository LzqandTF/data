/*
 * An XML document type.
 * Localname: reductionGenerateDzFile
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one reductionGenerateDzFile(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ReductionGenerateDzFileDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileDocument
{
    private static final long serialVersionUID = 1L;
    
    public ReductionGenerateDzFileDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName REDUCTIONGENERATEDZFILE$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "reductionGenerateDzFile");
    
    
    /**
     * Gets the "reductionGenerateDzFile" element
     */
    public cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType getReductionGenerateDzFile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType target = null;
            target = (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType)get_store().find_element_user(REDUCTIONGENERATEDZFILE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "reductionGenerateDzFile" element
     */
    public void setReductionGenerateDzFile(cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType reductionGenerateDzFile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType target = null;
            target = (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType)get_store().find_element_user(REDUCTIONGENERATEDZFILE$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType)get_store().add_element_user(REDUCTIONGENERATEDZFILE$0);
            }
            target.set(reductionGenerateDzFile);
        }
    }
    
    /**
     * Appends and returns a new empty "reductionGenerateDzFile" element
     */
    public cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType addNewReductionGenerateDzFile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType target = null;
            target = (cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType)get_store().add_element_user(REDUCTIONGENERATEDZFILE$0);
            return target;
        }
    }
}
