/*
 * An XML document type.
 * Localname: tradeTypeMoneyHandler
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.TradeTypeMoneyHandlerDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one tradeTypeMoneyHandler(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class TradeTypeMoneyHandlerDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.TradeTypeMoneyHandlerDocument
{
    private static final long serialVersionUID = 1L;
    
    public TradeTypeMoneyHandlerDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TRADETYPEMONEYHANDLER$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "tradeTypeMoneyHandler");
    
    
    /**
     * Gets the "tradeTypeMoneyHandler" element
     */
    public cn.com.chinaebi.dz.webservice.TradeTypeMoneyHandlerType getTradeTypeMoneyHandler()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TradeTypeMoneyHandlerType target = null;
            target = (cn.com.chinaebi.dz.webservice.TradeTypeMoneyHandlerType)get_store().find_element_user(TRADETYPEMONEYHANDLER$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "tradeTypeMoneyHandler" element
     */
    public void setTradeTypeMoneyHandler(cn.com.chinaebi.dz.webservice.TradeTypeMoneyHandlerType tradeTypeMoneyHandler)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TradeTypeMoneyHandlerType target = null;
            target = (cn.com.chinaebi.dz.webservice.TradeTypeMoneyHandlerType)get_store().find_element_user(TRADETYPEMONEYHANDLER$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.TradeTypeMoneyHandlerType)get_store().add_element_user(TRADETYPEMONEYHANDLER$0);
            }
            target.set(tradeTypeMoneyHandler);
        }
    }
    
    /**
     * Appends and returns a new empty "tradeTypeMoneyHandler" element
     */
    public cn.com.chinaebi.dz.webservice.TradeTypeMoneyHandlerType addNewTradeTypeMoneyHandler()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TradeTypeMoneyHandlerType target = null;
            target = (cn.com.chinaebi.dz.webservice.TradeTypeMoneyHandlerType)get_store().add_element_user(TRADETYPEMONEYHANDLER$0);
            return target;
        }
    }
}
