package tests2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import tests1.CounterDto;
import tests1.DoubleDto;
import tests1.LongDto;
import tests1.NullIndexDto;
import tests1.Person;
import tests1.StringDto;

import com.curlap.orb.security.RemoteService;

import curl.language.date_time.DateTime;

@Service("foo2")
@RemoteService
public class Foo {

	public Person getPerson() {
        Person person = new Person();
        person.setName("tests2");
        person.setBirthday(2222);
        return person;
    }

    public PersonHitoshi getPersonHitoshi() {
        PersonHitoshi person = new PersonHitoshi();
        person.setName("hitoshi");
        person.setHobby("hitoshi-hobby");
        person.setBirthday(3333);
        return person;
    }
    
    private String str = null;
    public void setBar(String s) {
        str = s;
    }
    public String getBar() {
        return str;
    }
    
	// echo/parrot
	public String echo(String s) {
		return s;
	}
	public DateTime echoDate(DateTime d) {
		return d;
	}
	
	public Object execService(
			String v0,
			CounterDto[] v1,
			StringDto[] v2,
			DoubleDto[] v3,
			LongDto[] v4,
			NullIndexDto[] v5
	) {
		Object[] result = new Object[] {v0, v1, v2, v3, v4, v5};
		return result;
	}
	public List<Object> execServiceForList(
			String v0,
			List<CounterDto> v1,
			List<StringDto> v2,
			List<DoubleDto> v3,
			List<LongDto> v4,
			List<NullIndexDto> v5
	) {
		List<Object> result = new ArrayList<Object>();
		result.add(v0);
		result.add(v1);
		result.add(v2);
		result.add(v3);
		result.add(v4);
		result.add(v5);
		return result;
	}
	public Map<Object, Object> execServiceForMap(
			String v0,
			Map v1, //Map<CounterDto, StringDto> v1,
			Map v2  //Map<CounterDto, StringDto> v2
	) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		result.put("v0", v0);
		result.put("v1", v1);
		result.put("v2", v2);
		return result;
	}
}
