package com.kanon.vintage.subscription.imp.ehcache;

import com.kanon.vintage.subscription.service.SubscriptionService;

/**
 * default dao with ehchache, definition in abstract class
 *
 * @author kanon
 * @date 2018年8月16日
 */
public class SubscriptionServiceEhcacheImp extends SubscriptionService {


    public SubscriptionServiceEhcacheImp() {
        super(new SubscriptionDAOEhcacheImp());
    }
}
