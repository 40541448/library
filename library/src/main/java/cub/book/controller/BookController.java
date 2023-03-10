package cub.book.controller;

import java.time.LocalDateTime;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

import cub.book.dto.BookQueryRq;
import cub.book.dto.BookQueryRs;
import cub.book.dto.BookUpdateRq;
import cub.book.dto.base.CubResponse;
import cub.book.service.BookService;

@RequestScoped
@Path("")
public class BookController {
	
	private BookService bookService;
	
	@Inject
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@Operation(summary = "書籍查詢")
	@POST
	@Path("/book/query")
	public RestResponse<CubResponse<BookQueryRs>> bookQuery(@Valid BookQueryRq bookQueryRq){
		
		CubResponse<BookQueryRs> cubRs = bookService.bookQuery(bookQueryRq);
		
		LocalDateTime currentTime = LocalDateTime.now();
     
        //CubResponse<BookQueryRs> cubRs = new CubResponse<BookQueryRs>();
        //cubRs.setReturnCodeAndDesc(ReturnCodeEnum.SUCCESS);
        
        return ResponseBuilder.ok(cubRs, MediaType.APPLICATION_JSON).header("date",currentTime).build();
        				
	}
	
	@Operation(summary = "書籍修改")
	@POST
	@Path("/book/modify")
	public RestResponse<CubResponse<BookUpdateRq>> bookUpdate(@Valid BookUpdateRq bookUpdateRq){
		CubResponse<BookUpdateRq> cubRs = bookService.bookUpdate(bookUpdateRq);
		LocalDateTime currentTime = LocalDateTime.now();
		return ResponseBuilder.ok(cubRs, MediaType.APPLICATION_JSON).header("date",currentTime).build();
	}
}

// https://developer.salesforce.com/docs/atlas.en-us.apexref.meta/apexref/apex_methods_system_restresponse.htm
// https://stackoverflow.com/questions/74646006/theres-a-alternative-for-responseentity-in-quarkus
// https://quarkus.io/guides/resteasy-reactive#resource-types
// https://quarkus.io/guides/rest-json

// @Valid CubRequest<BookQueryRq> cubRq
// 雖然說 CubRequest<BookQueryRq> 有寫 valid 了
// 但 controller 還要再寫 valid 才可以做驗證

// Swagger-UI 小陷阱
// The value / is not allowed as it blocks the application from serving anything else. A value prefixed with '/' makes it absolute and not relative.
// 所以 controller 前面要加上 @Path("")
// https://quarkus.io/guides/openapi-swaggerui
