package app;

import io.jooby.Context;
import io.jooby.annotations.ContextParam;
import io.jooby.annotations.GET;
import io.jooby.annotations.Path;
import io.swagger.v3.oas.annotations.tags.Tag;
import services.ServiceClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

/**
 * Controller for returning the results from your solutions for task 1
 */
@Tag(name = "FruitController", description = "Controller for the first task of the code test")
@Path("/fruit")
public class FruitController {
	
	private ServiceClass service = new ServiceClass();

    // A logger, should you need one
    private static final Logger logger = LoggerFactory.getLogger(FruitController.class);

    // Entity manager giving you access to the in memory H2 database, should you need it
    private final EntityManager entityManager;
    
    public FruitController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Endpoint for your solution
     * @param context the jooby HTTP request context,
     *                lets you interact with and manipulate the HTTP request and HTTP response
     * @return your result
     */
    @GET("/task1")
    public String task1(@ContextParam Context context) {
    	// First Scenario
    	return service.getStandWithMinTotalPrice();
    	
    	 /*Scenario 1 - modification:
    	   Return first stand with pears and (cherries or peaches) */
//    	return service.getFirstStandWithPearsAndCherriesOrPeaches();
    	
    	/* Scenario 3 - modification:
    	   Return stands, price, what fruits were purchased and by how many stands the selection was made.*/
//    	return service.getStandsFruitPricePurchased();
    	
//        return "your result here";
//        return "your result here";
    }
}
