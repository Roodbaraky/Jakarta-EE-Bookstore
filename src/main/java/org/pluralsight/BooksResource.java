package org.pluralsight;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/api/books")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class BooksResource {

    @Inject
    UriInfo uriInfo;
    @Inject
    BookService bookService;
    @GET
    @Operation(summary = "Returns all books from the database")
    @APIResponse(responseCode = "200", description = "A list of books", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Book.class, type = SchemaType.ARRAY)))
    @APIResponse(responseCode = "204", description = "No books")

    public Response getBooks() {
        List<Book> books = bookService.findAll();
        if(books.isEmpty())
            return Response.noContent().build();

        return Response.ok(books).build();
    }

    @GET
    @Operation(summary = "Returns a book corresponding to an id")
    @APIResponse(responseCode = "200", description = "The book corresponding to the id", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Book.class)))
    @APIResponse(responseCode = "404", description = "Book not found")


    @Path("/{id}")
    public Response getBook(@PathParam("id") @Min(1) Long id) {
        Book book = bookService.find(id);
        if (book == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(book).build();
    }

    @GET
    @Operation(summary = "Returns the total number of books in the database")
    @APIResponse(responseCode = "200", description = "The number of books in the database")
    @APIResponse(responseCode = "204", description = "No books")
    @Produces(TEXT_PLAIN)
    @Path("/count")
    public Response countBooks() {
        Long nbOfBooks = bookService.countAll();

        if (nbOfBooks == 0)
            return Response.noContent().build();

        return Response.ok(nbOfBooks).build();
    }

    @POST
    @Operation(summary = "Adds the posted book to the database")
    @APIResponse(responseCode = "201",description ="The added book to the database" )
    @APIResponse(responseCode = "400",description ="Invalid book in request body" )

    public Response createBook(Book book) throws URISyntaxException {
        book = bookService.create(book);
        URI createdURI = uriInfo.getAbsolutePathBuilder().path(Long.toString(book.getId())).build();
        return Response.created(createdURI).entity(book).build();
    }

    @DELETE
    @Operation(summary = "Deletes the book from the database corresponding to the id parameter")
    @APIResponse(responseCode = "204",description ="Deleted from database" )
    @APIResponse(responseCode = "404",description ="Book with passed id not found in database" )
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") @Min(1) Long id) {
        Book book = bookService.find(id);
        if (book == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        bookService.delete(id);
        return Response.noContent().build();
    }
}
