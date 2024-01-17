package com.Rohit.route;

import com.Rohit.model.Book;
import com.Rohit.service.BookService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.rest.RestParamType.path;

@Component
public class BookRoute extends RouteBuilder {
    @Autowired
    BookService bookService;

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);
//                .dataFormatProperty("prettyPrint","true")
//                .apiContextPath("/api-doc")
//                .apiProperty("api.title","Rohit.UK Rest Camel Api")
//                .apiProperty("api.version","1.0")
//                .apiContextRouteId("api-doc")
//                .contextPath("/BookStoreApp");

        rest("/books")
                .get("/all")
                .produces("application/json")
                .responseMessage("200","Good Request")
                .responseMessage("400","Bad Request")
                .responseMessage("404","Not Found")
                .responseMessage("500","Internal Server Error")
                .description("Get List Of All Books Present In BookStore ")
                .to("direct:getAllBooks")

                .get("/{id}")
                .responseMessage("200","Accepted")
                .responseMessage("400","Bad Request")
                .responseMessage("404","Not Found")
                .responseMessage("500","Internal Server Error")
                .description("Get Book For Given BookId")
                .param().name("id").type(path).description("Id of the Book ex. 2 or 4")
                .endParam()
                .outType(Book.class) // Specify the output type as Book
                .to("direct:getBookById")

                .post("/")
                .consumes("application/json")
                .responseMessage("200","Successful")
                .responseMessage("400","Bad Request")
                .responseMessage("404","Not Found")
                .responseMessage("500","Internal Server Error")
                .description("Adding Book In BookStore")
                .type(Book.class)
                .to("direct:addBook")

                .delete("/{id}")
                .to("direct:deleteBook")
                .responseMessage("200","Good Request")
                .responseMessage("400","Bad Request")
                .responseMessage("404","Not Found")
                .responseMessage("500","Internal Server Error")
                .description("Delete Book For Given BookId")
                .param().name("id").type(path).description("Id of the Book ex. 2 or 4...")
                .endParam()

                .put("/{id}")
                .consumes("application/json")
                .responseMessage("200","Good Request")
                .responseMessage("400","Bad Request")
                .responseMessage("404","Not Found")
                .responseMessage("500","Internal Server Error")
                .description("Update Book Details")
                .param().name("id").type(path).description("Get Book By Id For Updation")
                .endParam()
                .type(Book.class)
                .to("direct:updateBook");

        // Define a route for processing the book addition
        // Route for fetching all books
        from("direct:getAllBooks")
                .routeId("getAllBooksRoute")
                // Add your logic to fetch all books (e.g., call a service or retrieve from a database)
                .to("bean:bookService?method=getAllBooks")
                .setHeader("Content-Type", constant("application/json"))
                .setBody().simple("${body}"); // Set the response body to the list of books

        from("direct:getBookById")
                .routeId("getBookByIdRoute")
                .process(exchange -> {
                    // Extract the book ID from the route path
                    Integer bookId = exchange.getIn().getHeader("id", Integer.class);

                    // Call the BookService to get the book by ID
                    Book book = bookService.getBookById(bookId);

                    // Set the retrieved book as the response body
                    exchange.getIn().setBody(book);
                })
                .setHeader("Content-Type", constant("application/json"));

        // Route for adding a book
        from("direct:addBook")
                .routeId("addBookRoute")
                .bean(bookService, "addBook")
                .setHeader("Content-Type", constant("application/json"))
                .setBody().constant("{\"status\":\"success\"}");

        from("direct:deleteBook")
                .routeId("deleteBookRoute")
                .process(exchange -> {
                    // Extract the book ID from the route path
                    Integer bookId = exchange.getIn().getHeader("id", Integer.class);
                    exchange.getIn().setHeader("bookId", bookId);
                })
                .bean(bookService, "deleteBook(${header.bookId})")
//                .setHeader("Content-Type", constant("application/json"))
                .setBody().simple("${body}"); // You can customize the response accordingly
//                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));

        from("direct:updateBook")
                .routeId("updateBookRoute")
                .process(exchange -> {
                    // Extract the book ID from the route path
                    Integer bookId = exchange.getIn().getHeader("id", Integer.class);
                    exchange.getIn().setHeader("bookId", bookId);
                })
                .bean(bookService, "updateBookById(${header.bookId}, ${body})")
                .setHeader("Content-Type", constant("application/json"))
                .setBody().simple("${body}");

    }
}
