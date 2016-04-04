package hello;

import java.util.Map;

import com.aspectran.core.activity.Translet;
import com.aspectran.core.context.bean.annotation.Action;
import com.aspectran.core.context.bean.annotation.Configuration;
import com.aspectran.core.context.bean.annotation.Dispatch;
import com.aspectran.core.context.bean.annotation.Request;
import com.aspectran.core.context.bean.annotation.Transform;
import com.aspectran.core.context.rule.type.RequestMethodType;
import com.aspectran.core.context.rule.type.TransformType;
import com.aspectran.core.util.logging.Log;
import com.aspectran.core.util.logging.LogFactory;

@Configuration
public class SimplestAction {
	
	private final Log log = LogFactory.getLog(SimplestAction.class);

	@Request(translet = "helloWorld")
	@Transform(transformType = TransformType.XML)
	@Action(id = "message")
	public String helloWorld() {
		String msg = "Hello, World.";
		
		log.info("The message generated by my first aciton is: " + msg);

		return msg;
	}

	@Request(translet = "${templateName}.jsp", method = RequestMethodType.ALL)
	@Dispatch(name = "${templateName}")
	@Action(id = "title")
	public String restHelloWorld() {
		return "The message generated by my first aciton is: ";
	}

	@Request(translet = "echo/${echoMsg}", method = RequestMethodType.ALL)
	@Transform(transformType = TransformType.JSON, pretty = true)
	@Action(id = "result")
	public String restEchoMessage(Translet translet) {
		String echoMsg = translet.getParameter("echoMsg");

		log.info("The message generated by my third aciton is: " + echoMsg);

		return echoMsg;
	}

	@Request(translet = "echo")
	@Transform(transformType = TransformType.JSON, pretty = true)
	@Action(id = "params")
	public Map<String, Object> echoParams(Translet translet) {
		Map<String, Object> params = translet.getParameterMap();

		log.info("params: " + params);

		return params;
	}

}
