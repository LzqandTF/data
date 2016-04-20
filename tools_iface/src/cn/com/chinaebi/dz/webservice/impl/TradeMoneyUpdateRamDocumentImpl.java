/*
 * An XML document type.
 * Localname: tradeMoneyUpdateRam
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.TradeMoneyUpdateRamDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one tradeMoneyUpdateRam(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class TradeMoneyUpdateRamDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.TradeMoneyUpdateRamDocument
{
    private static final long serialVersionUID = 1L;
    
    public TradeMoneyUpdateRamDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TRADEMONEYUPDATERAM$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "tradeMoneyUpdateRam");
    
    
    /**
     * Gets the "tradeMoneyUpdateRam" element
     */
    public cn.com.chinaebi.dz.webservice.TradeMoneyUpdateRamType getTradeMoneyUpdateRam()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TradeMoneyUpdateRamType target = null;
            target = (cn.com.chinaebi.dz.webservice.TradeMoneyUpdateRamType)get_store().find_element_user(TRADEMONEYUPDATERAM$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "tradeMoneyUpdateRam" element
     */
    public void setTradeMoneyUpdateRam(cn.com.chinaebi.dz.webservice.TradeMoneyUpdateRamType tradeMoneyUpdateRam)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TradeMoneyUpdateRamType target = null;
            target = (cn.com.chinaebi.dz.webservice.TradeMoneyUpdateRamType)get_store().find_element_user(TRADEMONEYUPDATERAM$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.TradeMoneyUpdateRamType)get_store().add_element_user(TRADEMONEYUPDATERAM$0);
            }
            target.set(tradeMoneyUpdateRam);
        }
    }
    
    /**
     * Appends and returns a new empty "tradeMoneyUpdateRam" element
     */
    public cn.com.chinaebi.dz.webservice.TradeMoneyUpdateRamType addNewTradeMoneyUpdateRam()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TradeMoneyUpdateRamType target = null;
            target = (cn.com.chinaebi.dz.webservice.TradeMoneyUpdateRamType)get_store().add_element_user(TRADEMONEYUPDATERAM$0);
            return target;
        }
    }
}
