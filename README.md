![aspectran](http://www.aspectran.com/images/header_aspectran.png)

# ga-annotated-config
Aspectran Annotation based Configuration Example

```java
package sample;

import java.util.Map;

import com.aspectran.core.activity.Translet;
import com.aspectran.core.context.bean.annotation.Action;
import com.aspectran.core.context.bean.annotation.Configuration;
import com.aspectran.core.context.bean.annotation.Dispatch;
import com.aspectran.core.context.bean.annotation.Request;
import com.aspectran.core.context.bean.annotation.Transform;
import com.aspectran.core.context.rule.type.MethodType;
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

	@Request(translet = "/front/${templateName}", method = MethodType.ALL)
	@Dispatch(name = "${templateName}")
	@Action(id = "title")
	public String restHelloWorld() {
		return "The message generated by my first aciton is: ";
	}

	@Request(translet = "echo/${echoMsg}", method = MethodType.ALL)
	@Transform(transformType = TransformType.JSON, pretty = true)
	@Action(id = "message")
	public String restEchoMessage(Translet translet) {
		String echoMsg = translet.getParameter("echoMsg");

		log.info("The message generated by my third aciton is: " + echoMsg);

		return echoMsg;
	}

	@Request(translet = "echo")
	@Transform(transformType = TransformType.JSON, pretty = true)
	@Action(id = "message")
	public Map<String, Object> echoParams(Translet translet) {
		Map<String, Object> params = translet.getParameterMap();

		log.info("params: " + params);

		return params;
	}

}
```

```console
DEBUG 2016-09-07 22:30:23,955 Request URI: /ga-annotated-config/helloWorld ~com.aspectran.web.service.WebAspectranService^service:116
DEBUG 2016-09-07 22:30:23,958 translet {name=/ga-annotated-config/helloWorld, requestRule={}, responseRule={response={responseType=transform, transformType=transform/xml, contentType=text/xml, pretty=false}}, aspectAdviceRuleRegistry={settings=2}} ~com.aspectran.core.activity.CoreActivity^prepare:142
DEBUG 2016-09-07 22:30:23,961 action {actionType=method, methodActionRule={class=sample.SimplestAction, method=public java.lang.String sample.SimplestAction.helloWorld()}} ~com.aspectran.core.activity.CoreActivity^execute:498
DEBUG 2016-09-07 22:30:23,962 cache relevantAspectRuleHolder [/ga-annotated-config/helloWorld@class:sample.SimplestAction^helloWorld] {relevantAspectRuleList=[{id=simplestAspect, joinpointRule={joinpointType=translet, pointcutRule={pointcutPatternRule=[{translet=/ga-annotated-config/**/helloWorld*, class=sample.SimplestAction}]}}, aspectAdviceRuleList=[{aspectId=simplestAspect, aspectAdviceType=before, action={actionType=bean, beanActionRule={bean=class:sample.SimplestAdvice, method=welcome, aspectAdviceRule={aspectId=simplestAspect, aspectAdviceType=before}}}}, {aspectId=simplestAspect, aspectAdviceType=after, action={actionType=bean, beanActionRule={bean=class:sample.SimplestAdvice, method=goodbye, aspectAdviceRule={aspectId=simplestAspect, aspectAdviceType=after}}}}], beanRelevanted=true}]} ~com.aspectran.core.context.bean.proxy.AbstractDynamicBeanProxy^getRelevantAspectRuleHolder:80
DEBUG 2016-09-07 22:30:23,962 register AspectRule {id=simplestAspect, joinpointRule={joinpointType=translet, pointcutRule={pointcutPatternRule=[{translet=/ga-annotated-config/**/helloWorld*, class=sample.SimplestAction}]}}, aspectAdviceRuleList=[{aspectId=simplestAspect, aspectAdviceType=before, action={actionType=bean, beanActionRule={bean=class:sample.SimplestAdvice, method=welcome, aspectAdviceRule={aspectId=simplestAspect, aspectAdviceType=before}}}}, {aspectId=simplestAspect, aspectAdviceType=after, action={actionType=bean, beanActionRule={bean=class:sample.SimplestAdvice, method=goodbye, aspectAdviceRule={aspectId=simplestAspect, aspectAdviceType=after}}}}], beanRelevanted=true} ~com.aspectran.core.activity.AbstractActivity^registerAspectRule:497
INFO  2016-09-07 22:30:23,963 Welcome to Aspectran! (0:0:0:0:0:0:0:1) ~sample.SimplestAdvice^welcome:18
INFO  2016-09-07 22:30:23,963 The message generated by my first aciton is: Hello, World. ~sample.SimplestAction^helloWorld:27
INFO  2016-09-07 22:30:23,964 activityDataMap {message=Hello, World.} ~sample.SimplestAdvice^goodbye:24
INFO  2016-09-07 22:30:23,964 Goodbye! ~sample.SimplestAdvice^goodbye:28
DEBUG 2016-09-07 22:30:23,964 response {responseType=transform, transformType=transform/xml, contentType=text/xml, pretty=false} ~com.aspectran.core.activity.response.transform.XmlTransform^response:85
```