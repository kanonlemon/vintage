import com.kanon.vintage.subscription.adapter.RSSAdapter;
import com.kanon.vintage.subscription.model.GroupSubscription;
import com.kanon.vintage.subscription.service.Retrieve;
import com.kanon.vintage.subscription.ehcacheImp.SubscriptionServiceEhcacheImp;
import com.kanon.vintage.subscription.model.Subscription;
import com.rometools.rome.io.FeedException;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class easyTest {

    @Test
    public void testSubscription() throws IOException, FeedException {
        Retrieve retrieve = new Retrieve(SubscriptionServiceEhcacheImp.class);
        SubscriptionServiceEhcacheImp subscriptionServiceEhcacheImp = new SubscriptionServiceEhcacheImp();


        //RSSAdapter rss = new RSSAdapter("https://dedicated.wallstreetcn.com/rss.xml", "wallstreetcn");
        //GroupSubscription groupSubscription = new GroupSubscription("wallstreetcn", rss.parseRss2Subscription());

        //ubscriptionServiceEhcacheImp.addGroup("wengxuan", groupSubscription );



        List<Subscription> res =  retrieve.retrieval("wengxuan", (String) null, "工作");


        for( Subscription sr: res){
            System.out.println(sr);
        }


    }

}
