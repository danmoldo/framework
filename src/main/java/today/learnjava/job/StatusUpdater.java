package today.learnjava.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Leonard
 * Date: 3/8/15
 * Time: 8:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class StatusUpdater {

    @Scheduled(fixedDelay = 10000)
    public void reportCurrentTime() {

    }

}
