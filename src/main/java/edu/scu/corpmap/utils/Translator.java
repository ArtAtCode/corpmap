package edu.scu.corpmap.utils;

import edu.scu.corpmap.entity.neo4j.Partner;
import edu.scu.corpmap.entity.neo4j.Shareholder;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

public class Translator {
    public static List<Shareholder> translate(List<Partner> partnerList){
        Shareholder s = new Shareholder();
        List<Shareholder> list = new ArrayList<>();
        Partner p ;
        for(int i=0;i<partnerList.size();i++){
            p = partnerList.get(i);
            s.setSubscription(p.getSubscription());
            s.setActual_subscription(p.getActual_subscription());
            s.setSubscp_date(p.getSubscp_date());
            s.setSh_type(p.getPartnerType());
            s.setMethod(p.getMethod());
            s.setSh_name(p.getPartner_name());
            s.setSh_id(p.getPartner_id());
            s.setGraphId(p.getGraphId());

            list.add(s);
        }
        return list;
    }
}
