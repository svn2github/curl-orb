package tests1;

import org.springframework.stereotype.Service;

@Service("inherited-fuga")
public class InheritedFugaImpl extends FugaImpl {

	@Override
	public String method1() {
		return super.method1();
	}
}
