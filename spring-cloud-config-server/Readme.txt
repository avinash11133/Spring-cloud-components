/////////////////////////////////////////////////////////////////////////////////////////////////////////////

Components Included: {
	CurrencyExchangeService		-		Producer Service
	CurrencyConversionService	-		Consumer Service
	SpringCloudConfigServer		-		Configuration Server
	NetflixEurekaNamingServer	-		For Service Registry and Discovery
	NetflixZuulApiGateway		-		To intercept the requests (and other functionalities)
	FeignClient					-		To consume a service
	RibbonLoadBalancer			-		To deal with load balancing between multiple instances
	HystrixCircuitBreaker		-		To deal with Fault Tolerance
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

Component Description: {
	CurrencyExchangeService:
	This is the Producer service and it produces("from', "to" & "port" fields vary.)
	
	{"id":123,"from":"USD","to":"INR","conversionMultiple":65,"port":8000}
	
	The "port" field above has the port number on which this port is running. It is taken from the configuration
	of the microservice(vmarguments)to know the port number of a particular instance of the service.
	------------------------------------------------------------------------------------------------------------
	CurrencyConversionService:
	This is the Consumer service and it consumes the information from the currencyExchangeService and uses the 
	"conversionMultiple" to calculate the totalCalculatedAmount. The JSON produced by this service looks like
	
	{"id":123,"from":"USD","to":"INR","conversionMultiple":65,"quantity":1000,"totalCalculatedAmount":65000,"port":8000}
	
	We have two rest endpoints in the controller. One for the above functionality and the other(/number) which gets us
	the number property from the currency-conversion-service.properties in the Git repo(through config server). This is
	to just show the demo of the functionality.
	------------------------------------------------------------------------------------------------------------
	SpringCloudConfigServer:
	We have connected the SpringCloudConfigServer to the Git local repository and each component communicates with the
	config server to get the properties from the Git repositories. We can connect this to the remote repositories by
	providing the url in the application.properties file.
	
	With each component we want to connect to the config server, we need to change the component's name to
	bootstrap.properties.
	
	Each component has three properties in the bootstrap.properties file.
	1. spring.application.name
	2. server.port
	3. uri to the config server
	
	Remember each time we change the properties of a config file, first we need to Add, commit and push into the repository.
	(no push in local repo).
	------------------------------------------------------------------------------------------------------------
	NetflixZuulApiGateway:
	The Zuul Api Gateway is present between the two microservices.Each request 
	from the Producer to Consumer is intercepted through the Api Gateway. In this application, we have implemented a
	where with each request that comes to the Api Gateway, it logs the request name on the console. We can check this
	by running the microservices and we can refresh the url of the consumer. Each time we refresh we can see a new log
	on the console. 
	
	We can also run the API Gateway even before we run the CurrencyConversionService by using the URL
	in the URLs section. At that point(when we hit the URL) we will see two logs on the console - One for the request
	intercepted through the ZuulProxyServer even before the CurrencyConversionService and one more log for the request
	going through API Gateway before reaching the CurrencyExchangeService.
	------------------------------------------------------------------------------------------------------------
	Feign Client:
	We have setup a feign client on the Consumer application(which can talk to single instance without load balancer
	enabled). We need to give the name(spring.application.name) of the Zuul Proxy as the name field in the feign Client
	annotation to intercept through API gateway(And one more thing is to change the uri in GetMapping as
	/{application-name}/{uri)).
	------------------------------------------------------------------------------------------------------------
	RibbonLoadBalancer:
	We have setup the load balancer on the the consumer to talk to multiple instances of the Producer.
	
	To create multiple instances see the instructions provided in the last section.
	
	To check the functionality of the load balancer, run multiple instances of the producer and keep refreshing the consumer
	url. the Consumer balances the load between the multiple instances and in the application we can see that by looking at
	the 'port" field.
	------------------------------------------------------------------------------------------------------------
	HystrixCircuitBreaker:
	The Hystrix circuit breaker is enabled in the Consumer Service. If the producer fails, hystrix takes care of it and gives
	out alternate response back(in a chain of microservices).
	
	To check the functionality of Hystrix, run all the components. If we hit the consumer url, we can check the response. Now,
	kill the Producer service. Without hystrix we should see a 'White label Error'. Since we enabled Hystrix, we will see an
	alternate response with changed values when we hit the consumer url.
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

Port Numbers: {
	CurrencyExchangeService - 8000(Multiple instances can be run on 8001, 8002 etc.)
	CurrencyConversionService - 8100(Multiple instances can be run on 8101, 8102 etc.)
	SpringCloudConfigServer - 8888(Standard)
	NetflixEurekaNamingServer - 8761(Standard)
	NetflixZuulApiGateway - 8765(Standard)
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

Order to run the components: {
	1. SpringCloudConfigServer(Since the configurations of all the components are present here)
	2. EurekaNamingServer(To check all the instances running)
	3. NetflixZuulApiGateway(Requests are intercepted through this service)
	4. CurrencyExchangeService(This is the Producer Service)
	5. CurrencyConversionService(This is the Consumer Service)
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

URLs: {
	Swagger-UI: {
		CurrencyExchangeService - localhost:8000/swagger-ui.html
		CurrencyConversionService - localhost:8100/swagger-ui.html
	}
	(For the URLs of the Microservices refer to swagger-ui)

	SpringCloudconfigServer: { //To display the configurations of property files in the config server
		currency-exchange-service.properties - localhost:8888/currency-exchange-service/default(default environment)
		currency-exchange-service-dev.properties - localhost:8888/currency-exchange-service/dev(dev environment)
		currency-conversion-service.properties - localhost:8888/currency-conversion-service/default(default environment)
	}

	EurekaNamingServer: localhost:8761

	NetflixZuulApiGateway: //To run the api gateway even before currency-conversion-service
	http://localhost:8765/currency-conversion-service/currency-converter/from/USD/to/INR/quantity/1000
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

To run multiple instances of a service:
	Right click on the main class -> Run as -> Run Configurations -> Right click on the main class and duplicate and
	rename it -> Change the tab to vmarguents and give the arguments as '-Dserver.port={different port no.}' and run it.
	A different instance will be running on the given port number.
	