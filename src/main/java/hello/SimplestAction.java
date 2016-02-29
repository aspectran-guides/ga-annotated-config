package hello;

import com.aspectran.core.activity.Translet;
import com.aspectran.core.context.bean.annotation.Action;
import com.aspectran.core.context.bean.annotation.Configuration;
import com.aspectran.core.context.bean.annotation.Request;
import com.aspectran.core.context.bean.annotation.Transform;
import com.aspectran.core.context.rule.type.RequestMethodType;
import com.aspectran.core.context.rule.type.TransformType;
import com.aspectran.core.util.logging.Log;
import com.aspectran.core.util.logging.LogFactory;

@Configuration
public class SimplestAction {
	
	private final Log log = LogFactory.getLog(SimplestAction.class);

	@Request(translet = "/ga-annotated-config/helloWorld")
	@Transform(transformType = TransformType.XML)
	@Action(id = "hello")
	public String helloWorld() {
		String msg = "Hello, World.";
		
		log.info("The message generated by my first aciton is: " + msg);

		return msg;
	}

	@Request(translet = "/ga-annotated-config/echo")
	@Transform(transformType = TransformType.JSON, pretty = true)
	@Action(id = "result")
	public String echoMessage(Translet translet) {
		String msg = translet.getRequestAdapter().getParameter("msg");

		log.info("The message generated by my second aciton is: " + msg);

		return msg;
	}

	@Request(translet = "/ga-annotated-config/echo/${echoMsg}", method = RequestMethodType.ALL)
	@Transform(transformType = TransformType.JSON, pretty = true)
	@Action(id = "result")
	public String restEchoMessage(Translet translet) {
		String msg = translet.getRequestAdapter().getParameter("echoMsg");

		log.info("The message generated by my third aciton is: " + msg);

		return msg;
	}

}