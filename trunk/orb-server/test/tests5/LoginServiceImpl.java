package tests5;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.curl.orb.security.RemoteService;

/* ログインサービス */
@RemoteService
@Service("login")
public class LoginServiceImpl extends BaseService implements LoginService {

	/* ログインメソッド */
	public boolean login(String userId, String password) {
		Log log = LogFactory.getLog(getClass());
		log.info("login:" + userId + " " + password);
		if (userId.equals("user01") && password.equals("pass")) {
			return true;
		}
		return false;
	}
}
